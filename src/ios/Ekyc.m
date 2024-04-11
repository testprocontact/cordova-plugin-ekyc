//
//  eKYC.m
//  Hello
//
//  Created by Nguyen Tuyen on 3/8/24.
//

#import "Ekyc.h"
@import eKYC;
@implementation Ekyc

- (void)startSDK:(CDVInvokedUrlCommand*)command
{
    NSString* apiKey = [[command arguments] objectAtIndex:0];
    NSString* uuid = [[command arguments] objectAtIndex:1];
    int docType = [[[command arguments] objectAtIndex:2] intValue];
    NSInteger env = [[[command arguments] objectAtIndex:3] integerValue];

    NSArray *ocrTypes = [[NSArray alloc] initWithObjects:[NSNumber numberWithInt:docType], nil];

    FEKYCConfig *config = [[FEKYCConfig alloc] initWithApiKey:apiKey sessionId:@"" flow:@"" isFullFlow:YES clientUUID:uuid ocrTypes:ocrTypes environment:env livenessType:1 onlyDoccument:NO breakFlow:NO isShowResult:NO submitResult:NO language:@"vi" countryCode:@"vn" customInfo:nil];
    
    [FEKYC startFPTEKYCFlowWithConfig:config from:self.viewController completion:^(NSDictionary<NSString *,id> * _Nullable result) {
        NSString *facematch = [NSString stringWithFormat:@"%@",[result valueForKey:@"facematch"]];
        if (![facematch isEqualToString:@"(null)"] && ![facematch isEqualToString:@"<null>"] && facematch != nil) {
            NSMutableDictionary *dictResult = [result mutableCopy];
            [self.viewController removeFromParentViewController];
            CDVPluginResult* resultCordova = [CDVPluginResult resultWithStatus:CDVCommandStatus_OK messageAsDictionary:dictResult];
            [self.commandDelegate sendPluginResult:resultCordova callbackId:command.callbackId];
        }
    }];
}


@end
