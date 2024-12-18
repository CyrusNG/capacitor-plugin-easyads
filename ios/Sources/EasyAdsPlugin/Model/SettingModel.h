#import <Foundation/Foundation.h>
#import "RuleModel.h"
#import "SupplierModel.h"

@class SettingModel;

NS_ASSUME_NONNULL_BEGIN

#pragma mark - Object interfaces

@interface SettingModel : NSObject

@property (nonatomic, strong) NSMutableArray<RuleModel *> *rules;
@property (nonatomic, strong) NSMutableArray<SupplierModel *> *suppliers;

@end

NS_ASSUME_NONNULL_END
