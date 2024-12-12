#ifndef AdControllerProtocol_h
#define AdControllerProtocol_h

@protocol AdControllerProtocol <NSObject>

@required
- (void)load;
- (void)destroy;

@end

#endif
