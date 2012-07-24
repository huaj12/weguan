//
//  DialogService.m
//  juzhai
//
//  Created by JiaJun Wu on 12-7-17.
//  Copyright (c) 2012年 __MyCompanyName__. All rights reserved.
//

#import "DialogService.h"
#import "HttpRequestSender.h"
#import "UrlUtils.h"
#import "SBJson.h"
#import "MessageShow.h"
#import "NSString+Chinese.h"

@implementation DialogService

- (void)sendSms:(NSString *)content toUser:(NSInteger)uid onSuccess:(void (^)(NSDictionary *))aSuccessBlock
{
    content = [content stringByTrimmingCharactersInSet: 
                       [NSCharacterSet whitespaceAndNewlineCharacterSet]];
    NSInteger textLength = [content chineseLength];
    if (textLength < DIALOG_CONTENT_LENGTH_MIN || textLength > DIALOG_CONTENT_LENGTH_MAX) {
        [MessageShow error:DIALOG_ERROR_TEXT onView:nil];
        return;
    }
    NSDictionary *params = [[NSDictionary alloc] initWithObjectsAndKeys:content, @"content", [NSNumber numberWithInt:uid], @"uid", nil];
    __unsafe_unretained __block ASIFormDataRequest *request = [HttpRequestSender postRequestWithUrl:[UrlUtils urlStringWithUri:@"dialog/sendSms"] withParams:params];
    [request setCompletionBlock:^{
        NSString *responseString = [request responseString];
        NSMutableDictionary *jsonResult = [responseString JSONValue];
        if([jsonResult valueForKey:@"success"] == [NSNumber numberWithBool:YES]){
            if (aSuccessBlock) {
                aSuccessBlock([jsonResult valueForKey:@"result"]);
            }
            return;
        }
        NSString *errorInfo = [jsonResult valueForKey:@"errorInfo"];
        NSLog(@"%@", errorInfo);
        if (errorInfo == nil || [errorInfo isEqual:[NSNull null]] || [errorInfo isEqualToString:@""]) {
            errorInfo = SERVER_ERROR_INFO;
        }
        [MessageShow error:errorInfo onView:nil];
    }];
    [request setFailedBlock:^{
        [HttpRequestDelegate requestFailedHandle:request];
    }];
    [request startAsynchronous];
}

@end
