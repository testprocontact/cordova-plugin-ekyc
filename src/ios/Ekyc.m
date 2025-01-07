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
	

    NSString *urlFront = @"";
    NSString *urlBack = @"";
	NSString *lang = "vi";
    
    if ([command arguments].count > 4) {
        urlFront = [[command arguments] objectAtIndex:4];
    }
    if ([command arguments].count > 5) {
        urlBack = [[command arguments] objectAtIndex:5];
    }
	
	if([command arguments].count > 6) {
		lang = [[command arguments] objectAtIndex:6];
	}
	
    
    NSArray *ocrTypes = [[NSArray alloc] initWithObjects:[NSNumber numberWithInt:docType], nil];
    
    
    FEKYCConfig *config = [[FEKYCConfig alloc] initWithApiKey:apiKey sessionId:@"" flow:@"" urlFrontImage:urlFront urlBackImage:urlBack isFullFlow:YES clientUUID:uuid ocrTypes:ocrTypes environment:env livenessType:1 onlyDoccument:NO breakFlow:NO isShowResult:NO submitResult:NO language:lang countryCode:@"vn" customInfo:nil setBaseUrl:@"" themes:FEKYCThemesLight headers:nil nfcAmount:9999 titleData:nil facingBack:NO];

    [FEKYC startFPTEKYCFlowWithConfig:config from:self onSuccess:^(NSDictionary<NSString *,id> * _Nullable) {
        NSString *liveData = [NSString stringWithFormat:@"%@",[result valueForKey:@"liveData"]];
        if (![liveData isEqualToString:@"(null)"] && ![liveData isEqualToString:@"<null>"] && liveData != nil) {
            NSMutableDictionary *dictResult = [result mutableCopy];
            [self.viewController removeFromParentViewController];
            CDVPluginResult* resultCordova = [CDVPluginResult resultWithStatus:CDVCommandStatus_OK messageAsDictionary:dictResult];
            [self.commandDelegate sendPluginResult:resultCordova callbackId:command.callbackId];
        }
    } onFail:^(NSDictionary<NSString *,id> * _Nullable, NSString * _Nullable) {
        NSString *liveData = [NSString stringWithFormat:@"%@",[result valueForKey:@"liveData"]];
        if (![liveData isEqualToString:@"(null)"] && ![liveData isEqualToString:@"<null>"] && liveData != nil) {
            NSMutableDictionary *dictResult = [result mutableCopy];
            [self.viewController removeFromParentViewController];
            CDVPluginResult* resultCordova = [CDVPluginResult resultWithStatus:CDVCommandStatus_OK messageAsDictionary:dictResult];
            [self.commandDelegate sendPluginResult:resultCordova callbackId:command.callbackId];
        }
    } onTracking:^(NSString * _Nullable) {
        
    }];
}


@end
