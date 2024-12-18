#import "SettingModel.h"

@implementation SettingModel

+ (NSDictionary *)modelContainerPropertyGenericClass {
    return @{
        @"rules" : [RuleModel class],
        @"suppliers" : [SupplierModel class]
    };
}


@end
