//
//  HttpRequestSender.m
//  juzhai
//
//  Created by JiaJun Wu on 12-5-12.
//  Copyright (c) 2012年 __MyCompanyName__. All rights reserved.
//

#import "HttpRequestSender.h"
#import "ASIFormDataRequest.h"
#import "ASIHTTPRequest.h"
#import "CheckNetwork.h"

@implementation HttpRequestSender

+(ASIFormDataRequest *)postRequestWithUrl:(NSString *)url withParams:(NSDictionary *)params{
    if (![CheckNetwork isExistenceNetwork]) {
        return nil;
    }
    NSURL *requestUrl = [NSURL URLWithString:url];
    ASIFormDataRequest *request = [ASIFormDataRequest requestWithURL:requestUrl];
    [request addRequestHeader:@"Content-Type" value:@"application/html;charset=UTF-8;"];
    [request setResponseEncoding:NSUTF8StringEncoding];
    [request setUseCookiePersistence:YES];
    if(params != nil && [params count] > 0){
        NSArray *keys = [params allKeys];
        for(NSString *key in keys){
            [request setPostValue:[params objectForKey:key] forKey:key];
        }
    }
    return request;
}

+(ASIHTTPRequest *)getRequestWithUrl:(NSString *)url withParams:(NSDictionary *)params{
    if (![CheckNetwork isExistenceNetwork]) {
        return nil;
    }
    if(params != nil && [params count] > 0){
        NSString *parameString = @"";
        int i = 0;
        for(NSString *key in [params allKeys]){
            if(i>0){
                parameString = [parameString stringByAppendingString:@"&"];
            }
            parameString = [parameString stringByAppendingFormat:@"%@=%@",key,[params objectForKey:key]];
            i++;
        }
        NSRange range = [url rangeOfString:@"?"];
        if (range.location != NSNotFound) {
            url = [url stringByAppendingFormat:@"&%@", parameString];
        }else {
            url = [url stringByAppendingFormat:@"?%@", parameString];
        }
    }
    NSURL *requestUrl = [NSURL URLWithString:url];
    ASIHTTPRequest *request = [ASIHTTPRequest requestWithURL:requestUrl];
    [request addRequestHeader:@"Content-Type" value:@"application/html;charset=UTF-8;"];
    [request setResponseEncoding:NSUTF8StringEncoding];
    [request setUseCookiePersistence:YES];
    return request;
}

@end
