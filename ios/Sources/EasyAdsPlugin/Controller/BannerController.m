#import "BannerController.h"
#import "EasyAdBanner.h"
#import "AdDataJsonManager.h"
#import "AdCallbackProtocol.h"
#import "AdControllerProtocol.h"
#import "EasyAdBannerDelegate.h"
@interface BannerController () <EasyAdBannerDelegate>
@property (nonatomic, strong) NSDictionary *dic;
@property (nonatomic, strong) EasyAdBanner *easyAdBanner;
@property (nonatomic, strong) UIView *adspotView;
@property (nonatomic, strong) UIViewController *viewController;
@property (nonatomic, strong) CAPPluginCall *call;              // Cap插件调用响应实例
@property (nonatomic, weak) id<AdCallbackProtocol> delegate;    // Cap插件事件回调代理
@end

@implementation BannerController

- (instancetype)initWithViewController:(nullable UIViewController *)viewController pluginCall:(nullable CAPPluginCall *)capPluginCall delegate:(nullable id<AdCallbackProtocol>)callbackDelegate {
    self = [super init];
    if (self) {
        self.dic = [[AdDataJsonManager shared] loadAdDataWithType:JsonDataType_banner];
        self.viewController = viewController;
        self.call = capPluginCall;
        self.delegate = callbackDelegate;
    }
    return self;
}

- (void)load {
    // 通知代理因viewController为nil而失败
    if(!self.viewController && self.delegate != nil) [self.delegate notify:@"fail" data:nil];
    // 添加广告view到cap根view
    if (!self.adspotView) {
        self.adspotView = [[UIView alloc] initWithFrame:CGRectMake(0, 100, self.viewController.view.bounds.size.width, self.viewController.view.bounds.size.width *5/32)];
        [self.viewController.view addSubview:self.adspotView];
    }
    //初始化easyAdBanner
    self.easyAdBanner = [[EasyAdBanner alloc] initWithJsonDic:self.dic adContainer:self.adspotView viewController:self.viewController];
    //设置代理
    self.easyAdBanner.delegate = self;
    //加载广告
    [self.easyAdBanner loadAndShowAd];
}

- (void)destroy {
    self.adspotView = nil;
    self.easyAdBanner = nil;
    self.easyAdBanner.delegate = nil;
}

// MARK: ======================= EasyAdBannerDelegate =======================

// 根据策略选择内部渠道
- (void)easyAdSuccessSortTag:(NSString *)sortTag {
    NSLog(@"选中了 rule '%@' %s", sortTag,__func__);
}

// 内部渠道开始加载时调用
- (void)easyAdSupplierWillLoad:(NSString *)supplierId {
    NSLog(@"内部渠道开始加载 %s  supplierId: %@", __func__, supplierId);
}

// 广告加载失败
- (void)easyAdFailedWithError:(NSError *)error description:(NSDictionary *)description{
    NSLog(@"广告展示失败 %s  error: %@ 详情:%@", __func__, error, description);
    if (self.delegate != nil) [self.delegate notify:@"fail" data:description];
    [self destroy];
}



// 广告数据拉取成功回调
- (void)easyAdSucceed {
    NSLog(@"广告数据拉取成功 %s", __func__);
    if (self.delegate != nil) [self.delegate notify:@"start" data:nil];
}

// 广告曝光
- (void)easyAdExposured {
    NSLog(@"广告曝光回调 %s", __func__);
}

// 广告点击
- (void)easyAdClicked {
    NSLog(@"广告点击 %s", __func__);
}

// 广告关闭回调
- (void)easyAdDidClose {
    NSLog(@"广告关闭了 %s", __func__);
    if (self.delegate != nil) [self.delegate notify:@"end" data:nil];
}



@end
