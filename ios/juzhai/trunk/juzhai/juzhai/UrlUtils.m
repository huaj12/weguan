//
//  UrlUtils.m
//  juzhai
//
//  Created by JiaJun Wu on 12-7-12.
//  Copyright (c) 2012年 __MyCompanyName__. All rights reserved.
//

#import "UrlUtils.h"

@implementation UrlUtils

+ (NSString *)urlStringWithUri:(NSString *)uri;
{
    return [NSString stringWithFormat:@"%@%@", BASE_DOMAIN, uri];
}

@end
