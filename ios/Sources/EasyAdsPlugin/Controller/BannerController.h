//
//  BannerController.h
//  Example
//
//  Created by CherryKing on 2019/11/8.
//  Copyright © 2019 CherryKing. All rights reserved.
//

#import "Capacitor.h"
#import "BaseViewController.h"
#import "AdCallbackProtocol.h"
#import "AdControllerProtocol.h"
#import "SettingModel.h"

NS_ASSUME_NONNULL_BEGIN

@interface BannerController : NSObject <AdControllerProtocol>

- (instancetype)initWithViewController:(nullable UIViewController *)viewController setting:(SettingModel*)settingModel pluginCall:(nullable CAPPluginCall *)capPluginCall delegate:(nullable id<AdCallbackProtocol>)callbackDelegate;

@end

NS_ASSUME_NONNULL_END