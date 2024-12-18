//
//  NSTimeUtils.m
//  EasyAdsSDKExample
//
//  Created by CherryKing on 2020/3/16.
//  Copyright © 2020 Mercury. All rights reserved.
//

#import "ModelConverter.h"
#import "NSObject+EasyAdModel.h"

@interface ModelConverter ()
@end

@implementation ModelConverter

static ModelConverter *instance;
+ (instancetype)shareInstance {
    static dispatch_once_t onceToken;
    dispatch_once(&onceToken, ^{
        instance = [[super allocWithZone:NULL] init];
    });
    return instance;
}

- (nullable SettingModel *) convertSettingFromConfig: (ConfigModel*) configModel adspotTag: (NSString *) adspotTag {
    // 获取所有相关models
    NSArray<RuleModel*> *rules = configModel.rules;
    NSArray<AppModel*> *apps = configModel.apps;
    NSArray<AdspotModel*> *adspots = configModel.adspots;
    NSMutableArray<SupplierModel*> *suppliers = [NSMutableArray array];
    // 遍历adspots找到adspotName一致项
    NSArray<NSString*> *targets = [NSArray array];
    for (AdspotModel *adspot in adspots) {
        if([adspotTag isEqualToString:adspot.tag]) targets = adspot.targets;
    }
    // 遍历apps添加adspotId
    for (AppModel *app in apps) {
        for (NSString *target in targets) {
            NSArray<NSString*> *parts = [target componentsSeparatedByString:@"-"];
            if(parts.count >= 2) {
                NSString *appTag = parts[0];
                NSString *appTarget = [[parts subarrayWithRange:NSMakeRange(1, parts.count-1)] componentsJoinedByString:@"-"];
                if([app.tag isEqualToString:appTag]) [suppliers addObject: [SupplierModel easyAd_modelWithJSON:@{@"tag": app.tag, @"appId": app.appId, @"index": app.index, @"adspotId": appTarget}]];
            }
        }
    }
    // 返回settingModel
    return suppliers.count == 0? nil : [SettingModel easyAd_modelWithJSON:@{@"rules": rules, @"suppliers": suppliers}];
}

@end
