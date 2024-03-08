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
    
    FEKYCConfig *config = [[FEKYCConfig alloc] initWithApiKey:apiKey sessionId:@"" flow:@"1" isFullFlow:YES clientUUID:uuid ocrTypes:ocrTypes environment:env livenessType:1 onlyDoccument:NO isShowResult:YES isReturnPhoto:NO themeColor:nil submitResult:NO nfcHasLiveness:NO language:@"vi" countryCode:@"vn" mainStoryboard:@"" customInfo:nil customFont:nil];

    [FEKYC startFPTEKYCFlowWithConfig:config from:self.viewController completion:^(NSDictionary<NSString *,id> * _Nullable result) {
        NSLog(@"result:%@",result);
    }];
}


@end
