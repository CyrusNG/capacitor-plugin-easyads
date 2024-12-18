#import <Foundation/Foundation.h>

@class AdspotModel;

NS_ASSUME_NONNULL_BEGIN

#pragma mark - Object interfaces

@interface AdspotModel : NSObject

@property (nonatomic, copy)   NSString *tag;
@property (nonatomic, strong) NSArray<NSString *> *targets;

@end

NS_ASSUME_NONNULL_END
