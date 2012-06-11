//
//  Idea.m
//  juzhai
//
//  Created by JiaJun Wu on 12-5-12.
//  Copyright (c) 2012年 __MyCompanyName__. All rights reserved.
//

#import "IdeaView.h"
#import "SBJson.h"

@implementation IdeaView

@synthesize ideaId;
@synthesize content;
@synthesize place;
@synthesize charge;
@synthesize cityName;
@synthesize townName;
@synthesize categoryName;
@synthesize useCount;
@synthesize pic;
@synthesize hasUsed;

+ (id) ideaConvertFromDictionary:(NSDictionary *)info{
    IdeaView *idea = [IdeaView alloc];
    idea.ideaId = [info valueForKey:@"ideaId"];
    idea.content = [info valueForKey:@"content"];
    idea.place = [info valueForKey:@"place"];
    idea.charge = [info valueForKey:@"charge"];
    idea.cityName = [info valueForKey:@"cityName"];
    idea.townName = [info valueForKey:@"townName"];
    idea.categoryName = [info valueForKey:@"categoryName"];
    idea.useCount = [info valueForKey:@"useCount"];
    idea.hasUsed = [info valueForKey:@"hasUsed"];
    idea.pic = [info valueForKey:@"pic"];
    return idea;
}

@end
