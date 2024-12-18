//
//  SplashController.h
//
//  Created by CherryKing on 2019/11/8.
//  Updated by CyrusNG on 2024/12/18.
//  Copyright © 2019 CherryKing. All rights reserved.
//

#import "Capacitor.h"
#import "BaseViewController.h"
#import "AdCallbackProtocol.h"
#import "AdControllerProtocol.h"
#import "SettingModel.h"

NS_ASSUME_NONNULL_BEGIN

@interface SplashController : NSObject <AdControllerProtocol>

- (instancetype)initWithViewController:(nullable UIViewController *)viewController setting:(SettingModel*)settingModel pluginCall:(nullable CAPPluginCall *)capPluginCall delegate:(nullable id<AdCallbackProtocol>)callbackDelegate;

@end

NS_ASSUME_NONNULL_END
