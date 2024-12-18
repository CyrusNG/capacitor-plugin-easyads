#import <Foundation/Foundation.h>

@class RuleModel;

NS_ASSUME_NONNULL_BEGIN

#pragma mark - Object interfaces

@interface RuleModel : NSObject

@property (nonatomic, copy)   NSString *tag;
@property (nonatomic, strong) NSMutableArray<NSNumber *> *sort;
@property (nonatomic, assign) NSInteger percent;

@end

NS_ASSUME_NONNULL_END
