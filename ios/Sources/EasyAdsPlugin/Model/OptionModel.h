#import <Foundation/Foundation.h>

@class OptionModel;

NS_ASSUME_NONNULL_BEGIN

#pragma mark - Object interfaces

@interface OptionModel : NSObject

@property (nonatomic, strong) NSNumber *width;      // Banner Ads
@property (nonatomic, strong) NSNumber *height;     // Banner Ads
@property (nonatomic, assign) BOOL showLogo;        // Splash Ads
@property (nonatomic, assign) BOOL showLater;       // Show Ads after loaded

@end

NS_ASSUME_NONNULL_END
