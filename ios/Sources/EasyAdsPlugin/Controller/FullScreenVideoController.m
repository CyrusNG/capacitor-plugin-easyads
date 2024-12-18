//
//  FullScreenVideoController.h
//
//  Created by CherryKing on 2020/4/13.
//  Updated by CyrusNG on 2024/12/18.
//  Copyright © 2019 CherryKing. All rights reserved.
//

#import "FullScreenVideoController.h"
#import "EasyAdFullScreenVideo.h"
#import "AdDataJsonManager.h"
#import "AdCallbackProtocol.h"
#import "AdControllerProtocol.h"
#import "EasyAdFullScreenVideoDelegate.h"
#import "SettingModel.h"
#import "NSObject+EasyAdModel.h"
@interface FullScreenVideoController () <EasyAdFullScreenVideoDelegate>
@property (nonatomic, strong) EasyAdFullScreenVideo *easyAdFullScreenVideo;
@property (nonatomic, strong) SettingModel *setting;
@property (nonatomic, strong) UIViewController *viewController;
@property (nonatomic, strong) CAPPluginCall *call;              // Cap插件调用响应实例
@property (nonatomic, weak) id<AdCallbackProtocol> delegate;    // Cap插件事件回调代理
@end

@implementation FullScreenVideoController

- (instancetype)initWithViewController:(nullable UIViewController *)viewController setting:(SettingModel*)settingModel pluginCall:(nullable CAPPluginCall *)capPluginCall delegate:(nullable id<AdCallbackProtocol>)callbackDelegate {
    self = [super init];
    if (self) {
        self.setting = settingModel;
        self.viewController = viewController;
        self.call = capPluginCall;
        self.delegate = callbackDelegate;
    }
    return self;
}

- (void)load {
    // 通知代理因viewController为nil而失败
    if(!self.viewController && self.delegate != nil) [self.delegate notify:self.call.callbackId event:@"fail" data:nil];
    // 广告实例不要用初始化加载, 要确保每次都用最新的实例, 且一次广告流程中 delegate 不能发生变化
    [self destroy];
    //初始化easyAdFullScreenVideo
    self.easyAdFullScreenVideo = [[EasyAdFullScreenVideo alloc] initWithJsonDic:[self.setting easyAd_modelToJSONObject] viewController:self.viewController];
    //设置代理
    self.easyAdFullScreenVideo.delegate = self;
    //加载广告
    [self.easyAdFullScreenVideo loadAndShowAd];
}

- (void)destroy {
    self.easyAdFullScreenVideo = nil;
    self.easyAdFullScreenVideo.delegate = nil;
}

// MARK: ======================= EasyAdFullScreenVideoDelegate =======================

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
    if (self.delegate != nil) [self.delegate notify:self.call.callbackId event:@"fail" data:description];
    [self destroy];
}


// 广告数据加载成功
- (void)easyAdSucceed {
    NSLog(@"请求广告数据成功后调用 %s", __func__);
    if (self.delegate != nil) [self.delegate notify:self.call.callbackId event:@"ready" data:nil];
}

// 广告曝光
- (void)easyAdExposured {
    NSLog(@"广告曝光回调 %s", __func__);
    if (self.delegate != nil) [self.delegate notify:self.call.callbackId event:@"start" data:nil];
}

// 广告点击
- (void)easyAdClicked {
    NSLog(@"广告点击 %s", __func__);
    if (self.delegate != nil) [self.delegate notify:self.call.callbackId event:@"did-click" data:nil];
}

// 广告播放完成
- (void)easyAdVideoPlayed {
    NSLog(@"广告播放完成 %s", __func__);
    if (self.delegate != nil) [self.delegate notify:self.call.callbackId event:@"did-play" data:nil];
}

// 广告关闭
- (void)easyAdDidClose {
    NSLog(@"广告关闭了 %s", __func__);
    if (self.delegate != nil) [self.delegate notify:self.call.callbackId event:@"end" data:nil];
    [self destroy];

}




@end
