#import <Foundation/Foundation.h>
#import "RuleModel.h"
#import "AppModel.h"
#import "AdspotModel.h"

@class ConfigModel;

NS_ASSUME_NONNULL_BEGIN

#pragma mark - Object interfaces

@interface ConfigModel : NSObject

@property (nonatomic, strong) NSMutableArray<RuleModel *> *rules;
@property (nonatomic, strong) NSMutableArray<AppModel *> *apps;
@property (nonatomic, strong) NSMutableArray<AdspotModel *> *adspots;

@end

NS_ASSUME_NONNULL_END
