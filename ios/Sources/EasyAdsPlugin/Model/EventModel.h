#import <Foundation/Foundation.h>

@class EventModel;

NS_ASSUME_NONNULL_BEGIN

#pragma mark - Object interfaces

@interface EventModel : NSObject

@property (nonatomic, copy)   NSString *event;      //e.g. ready, start, end, fail, did-click, did-play, did-cache, did-rewardable, did-countdown, did-skip
@property (nonatomic, copy)   NSString *adType;     //e.g. splash, banner, interstitial, reward, fullscreen
@property (nonatomic, copy)   NSString *adTag;      //e.g. app_splash, task_banner, project_interstitial, report_reward
@property (nonatomic, copy)   NSString *callId;     //capacitor's plugin call.callbackId
@property (nonatomic, strong) NSObject *error;      //event's error

@end

NS_ASSUME_NONNULL_END
