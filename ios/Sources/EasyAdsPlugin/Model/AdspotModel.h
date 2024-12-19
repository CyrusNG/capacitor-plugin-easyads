#import <Foundation/Foundation.h>
#import "OptionModel.h"

@class AdspotModel;

NS_ASSUME_NONNULL_BEGIN

#pragma mark - Object interfaces

@interface AdspotModel : NSObject

@property (nonatomic, copy)   NSString *tag;
@property (nonatomic, strong) NSArray<NSString *> *targets;
@property (nonatomic, strong) OptionModel *options;

@end

NS_ASSUME_NONNULL_END
