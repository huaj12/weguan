//
//  Post.m
//  juzhai
//
//  Created by JiaJun Wu on 12-5-30.
//  Copyright (c) 2012年 __MyCompanyName__. All rights reserved.
//

#import "PostView.h"

@implementation PostView

@synthesize postId;
@synthesize purpose;
@synthesize content;
@synthesize place;
@synthesize pic;
@synthesize categoryName;
@synthesize date;
@synthesize respCnt;
@synthesize hasResp;

+ (id) postConvertFromDictionary:(NSDictionary *)info{
    PostView *post = [PostView alloc];
    post.postId = [info valueForKey:@"postId"];
    post.purpose = [info valueForKey:@"purpose"];
    post.content = [info valueForKey:@"content"];
    post.place = [info valueForKey:@"place"];
    post.pic = [info valueForKey:@"pic"];
    post.categoryName = [info valueForKey:@"categoryName"];
    post.date = [info valueForKey:@"date"];
    post.respCnt = [info valueForKey:@"respCnt"];
    post.hasResp = [info valueForKey:@"hasResp"];
    return post;
}

@end
