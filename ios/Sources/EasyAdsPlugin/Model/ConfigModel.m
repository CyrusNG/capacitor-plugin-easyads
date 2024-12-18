#import "ConfigModel.h"

@implementation ConfigModel

+ (NSDictionary *)modelContainerPropertyGenericClass {
    return @{
        @"rules" : [RuleModel class],
        @"apps" : [AppModel class],
        @"adspots" : [AdspotModel class]
    };
}

@end
