#import <Foundation/Foundation.h>

@class SupplierModel;

NS_ASSUME_NONNULL_BEGIN

#pragma mark - Object interfaces

@interface SupplierModel : NSObject

@property (nonatomic, copy)   NSString *tag;
@property (nonatomic, copy)   NSString *appId;
@property (nonatomic, strong) NSNumber *index;
@property (nonatomic, copy)   NSString *adspotId;

@end

NS_ASSUME_NONNULL_END
