//
//  eKYC.h
//  Hello
//
//  Created by Nguyen Tuyen on 3/8/24.
//

#import <Foundation/Foundation.h>
#import <Cordova/CDV.h>
NS_ASSUME_NONNULL_BEGIN

@interface Ekyc : CDVPlugin
- (void)startSDK:(CDVInvokedUrlCommand*)command;
@end

NS_ASSUME_NONNULL_END
