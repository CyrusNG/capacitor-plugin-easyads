#ifndef AdCallbackProtocol_h
#define AdCallbackProtocol_h

@protocol AdCallbackProtocol <NSObject>

@required
- (void)notify:(nonnull NSString *) callId event:(nonnull NSString *) event data:(nullable NSDictionary<NSString *,id> *) data;

@end

#endif
