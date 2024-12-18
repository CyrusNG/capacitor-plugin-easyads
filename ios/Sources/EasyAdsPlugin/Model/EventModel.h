#import <Foundation/Foundation.h>

@class EventModel;

NS_ASSUME_NONNULL_BEGIN

#pragma mark - Object interfaces

@interface EventModel : NSObject

@property (nonatomic, copy)   NSString *type;
@property (nonatomic, copy)   NSString *event;
@property (nonatomic, copy)   NSString *call;
@property (nonatomic, copy)   NSString *tag;
@property (nonatomic, strong) NSObject *error;

@end

NS_ASSUME_NONNULL_END
