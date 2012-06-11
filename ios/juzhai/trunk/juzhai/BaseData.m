//
//  BaseData.m
//  juzhai
//
//  Created by JiaJun Wu on 12-6-2.
//  Copyright (c) 2012年 __MyCompanyName__. All rights reserved.
//

#import "BaseData.h"
#import "ASIHTTPRequest.h"
#import "HttpRequestSender.h"
#import "SBJson.h"
#import "Category.h"

@interface BaseData (Private)

+ (BaseData *)sharedData;

@end

@implementation BaseData

@synthesize categoryArray;

static BaseData *baseData;

+ (BaseData *) sharedData{
    @synchronized(baseData){
        if (!baseData) {
            baseData = [[BaseData alloc]init];
        }
        return baseData;
    }
}

+ (NSArray *)getCategories{
    BaseData *baseData = [BaseData sharedData];
    if(!baseData.categoryArray){
        //http load
        __block ASIHTTPRequest *_request = [HttpRequestSender initGetRequestWithUrl:@"http://test.51juzhai.com/app/ios/categoryList" withParams:nil];
        __unsafe_unretained ASIHTTPRequest *request = _request;
        [request setCompletionBlock:^{
            // Use when fetching text data
            NSString *responseString = [request responseString];
            NSMutableDictionary *jsonResult = [responseString JSONValue];
            if([jsonResult valueForKey:@"success"] == [NSNumber numberWithBool:YES]){
                NSArray *array = [jsonResult objectForKey:@"result"];
                baseData.categoryArray = [[NSMutableArray alloc] initWithCapacity:array.count];
                for(NSDictionary *dic in array){
                    NSDecimalNumber *cId = nil;
                    for(NSDecimalNumber *key in [dic allKeys]){
                        cId = key;
                    }
                    [baseData.categoryArray addObject:[[Category alloc] initWithCategoryId:cId withName:[dic objectForKey:cId]]];
                }
            }
        }];
        [request setFailedBlock:^{
            NSError *error = [request error];
            NSLog(@"%@", [error description]);
        }];
        [request startAsynchronous];
    }
    return baseData.categoryArray;
}

@end
