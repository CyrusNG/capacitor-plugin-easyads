//
//  SplashController.h
//
//  Created by CherryKing on 2019/11/1.
//  Updated by CyrusNG on 2024/12/18.
//  Copyright © 2019 CherryKing. All rights reserved.
//
#import "SplashController.h"
#import "EasyAdSplash.h"
#import "AdCallbackProtocol.h"
#import "AdControllerProtocol.h"
#import "EasyAdBannerDelegate.h"
#import "SettingModel.h"
#import "NSObject+EasyAdModel.h"
@interface SplashController () <EasyAdSplashDelegate>
@property (nonatomic, strong) EasyAdSplash *easyAdSplash;
@property (nonatomic, strong) SettingModel *setting;
@property (nonatomic, strong) OptionModel *option;
@property (nonatomic, strong) UIViewController *viewController;
@property (nonatomic, strong) CAPPluginCall *call;              // Cap插件调用响应实例
@property (nonatomic, weak) id<AdCallbackProtocol> delegate;    // Cap插件事件回调代理
@end

@implementation SplashController

- (instancetype)initWithViewController:(nullable UIViewController *)viewController pluginCall:(nullable CAPPluginCall *)capPluginCall delegate:(nullable id<AdCallbackProtocol>)callbackDelegate setting:(SettingModel*)settingModel option: (OptionModel*) optionModel {
    self = [super init];
    if (self) {
        self.setting = settingModel;
        self.option = optionModel;
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
    //初始化easyAdSplash并设置
    self.easyAdSplash = [[EasyAdSplash alloc] initWithJsonDic:[self.setting easyAd_modelToJSONObject] viewController:self.viewController];
    self.easyAdSplash.showLogoRequire = self.option.showLogo;
    self.easyAdSplash.logoImage = [UIImage imageNamed:@"app_logo"];
    self.easyAdSplash.backgroundImage = [UIImage imageNamed:@"LaunchImage_img"];
    self.easyAdSplash.timeout = 5;
    //设置代理
    self.easyAdSplash.delegate = self;
    //加载广告
    [self.easyAdSplash loadAndShowAd];
}

- (void)destroy {
    self.easyAdSplash = nil;
    self.easyAdSplash.delegate = nil;
}


// MARK: ======================= EasyAdSplashDelegate =======================

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

// 广告数据拉取成功
- (void)easyAdSucceed {
    NSLog(@"广告数据拉取成功 %s", __func__);
    if (self.delegate != nil) [self.delegate notify:self.call.callbackId event:@"ready" data:nil];
}

// 广告曝光成功
- (void)easyAdExposured {
    NSLog(@"广告曝光成功 %s", __func__);
    if (self.delegate != nil) [self.delegate notify:self.call.callbackId event:@"start" data:nil];
}

// 广告点击
- (void)easyAdClicked {
    NSLog(@"广告点击 %s", __func__);
    if (self.delegate != nil) [self.delegate notify:self.call.callbackId event:@"did-click" data:nil];
}

// 广告倒计时结束
- (void)easyAdCountdowned {
    NSLog(@"广告倒计时结束 %s", __func__);
    if (self.delegate != nil) [self.delegate notify:self.call.callbackId event:@"did-countdown" data:nil];
}

// 点击了跳过
- (void)easyAdSkipped {
    NSLog(@"点击了跳过 %s", __func__);
    if (self.delegate != nil) [self.delegate notify:self.call.callbackId event:@"did-skip" data:nil];
    [self destroy];
}

// 广告关闭
- (void)easyAdDidClose {
    NSLog(@"广告关闭了 %s", __func__);
    if (self.delegate != nil) [self.delegate notify:self.call.callbackId event:@"end" data:nil];
    [self destroy];
}

@end
