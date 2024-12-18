#import <Foundation/Foundation.h>
#import "SettingModel.h"
#import "ConfigModel.h"

NS_ASSUME_NONNULL_BEGIN

@interface ModelConverter : NSObject

- (nullable SettingModel *) convertSettingFromConfig: (ConfigModel*) configModel adspotTag: (NSString *) adspotTag;

@end

NS_ASSUME_NONNULL_END
