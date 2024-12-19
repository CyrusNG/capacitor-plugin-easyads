#import "LayoutUtils.h"

@interface LayoutUtils ()
@end

@implementation LayoutUtils

static LayoutUtils *instance;
+ (instancetype)shareInstance {
    static dispatch_once_t onceToken;
    dispatch_once(&onceToken, ^{
        instance = [[super allocWithZone:NULL] init];
    });
    return instance;
}

+ (CGFloat)getStatusBarHeight {
    CGSize statusBarSize;
    if (@available(iOS 13, *)) {
        UIStatusBarManager *statusBarManager = [UIApplication sharedApplication].windows.firstObject.windowScene.statusBarManager;
        statusBarSize = statusBarManager.statusBarFrame.size;
    }
    else {
        statusBarSize = [[UIApplication sharedApplication] statusBarFrame].size;
    }
    return MIN(statusBarSize.width, statusBarSize.height);
}

@end
