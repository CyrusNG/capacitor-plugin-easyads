#import <Foundation/Foundation.h>

@class AppModel;

NS_ASSUME_NONNULL_BEGIN

#pragma mark - Object interfaces

@interface AppModel : NSObject

@property (nonatomic, copy)   NSString *tag;
@property (nonatomic, copy)   NSString *appId;
@property (nonatomic, strong) NSNumber *index;

@end

NS_ASSUME_NONNULL_END
