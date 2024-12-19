//
//  RewardVideoController.h
//
//  Created by CherryKing on 2020/1/3.
//  Updated by CyrusNG on 2024/12/18.
//  Copyright © 2019 CherryKing. All rights reserved.
//

#import "RewardVideoController.h"
#import "EasyAdRewardVideo.h"
#import "AdCallbackProtocol.h"
#import "AdControllerProtocol.h"
#import "EasyAdRewardVideoDelegate.h"
#import "SettingModel.h"
#import "NSObject+EasyAdModel.h"
@interface RewardVideoController () <EasyAdRewardVideoDelegate>
@property (nonatomic, strong) EasyAdRewardVideo *easyAdRewardVideo;
@property (nonatomic, strong) SettingModel *setting;
@property (nonatomic, strong) OptionModel *option;
@property (nonatomic, strong) UIViewController *viewController;
@property (nonatomic, strong) CAPPluginCall *call;              // Cap插件调用响应实例
@property (nonatomic, weak) id<AdCallbackProtocol> delegate;    // Cap插件事件回调代理
@end

@implementation RewardVideoController

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
    //初始化easyAdRewardVideo
    self.easyAdRewardVideo = [[EasyAdRewardVideo alloc] initWithJsonDic:[self.setting easyAd_modelToJSONObject] viewController:self.viewController];
    //设置代理
    self.easyAdRewardVideo.delegate = self;
    //加载广告
    [self.easyAdRewardVideo loadAndShowAd];
}

- (void)destroy {
    self.easyAdRewardVideo = nil;
    self.easyAdRewardVideo.delegate = nil;
}

// MARK: ======================= EasyAdRewardVideoDelegate =======================

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
    NSLog(@"广告数据拉取成功, 正在缓存... %s", __func__);
    if (self.delegate != nil) [self.delegate notify:self.call.callbackId event:@"ready" data:nil];
}

// 广告曝光
- (void)easyAdExposured {
    NSLog(@"广告曝光回调 %s", __func__);
    if (self.delegate != nil) [self.delegate notify:self.call.callbackId event:@"start" data:nil];
}

// 视频缓存成功
// 激励视频播放器采用的是边下边播的方式, 理论上拉取数据成功 即可展示, 但如果网速慢导致缓冲速度慢, 则激励视频会出现卡顿
// 广点通推荐在 easyAdVideoCached 视频缓冲完成后再调用showad
- (void)easyAdVideoCached {
    NSLog(@"视频缓存成功 %s", __func__);
    if (self.delegate != nil) [self.delegate notify:self.call.callbackId event:@"did-cach" data:nil];
}

// 广告点击
- (void)easyAdClicked {
    NSLog(@"广告点击 %s", __func__);
    if (self.delegate != nil) [self.delegate notify:self.call.callbackId event:@"did-click" data:nil];
}

// 到达激励时间
- (void)easyAdVideoRewardable {
    NSLog(@"到达激励时间 %s", __func__);
    if (self.delegate != nil) [self.delegate notify:self.call.callbackId event:@"did-rewardable" data:nil];
}

// 播放完成
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
