#ifndef AdCallbackProtocol_h
#define AdCallbackProtocol_h
#import "EventModel.h"

@protocol AdCallbackProtocol <NSObject>

@required
- (void)notify:(nonnull NSString *)event call:(nonnull CAPPluginCall *)call error:(nullable NSError *)error;

@end

#endif
