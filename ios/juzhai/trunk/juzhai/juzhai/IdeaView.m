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
@synthesize startTime;
@synthesize endTime;
@synthesize charge;
@synthesize cityName;
@synthesize townName;
@synthesize categoryName;
@synthesize useCount;
@synthesize pic;
@synthesize bigPic;
@synthesize hasUsed;

+ (id) convertFromDictionary:(NSDictionary *)info{
    IdeaView *idea = [IdeaView alloc];
    idea.ideaId = [info valueForKey:@"ideaId"];
    idea.content = [info valueForKey:@"content"];
    idea.place = [info valueForKey:@"place"];
    idea.startTime = [info valueForKey:@"startTime"];
    idea.endTime = [info valueForKey:@"endTime"];
    idea.charge = [info valueForKey:@"charge"];
    idea.cityName = [info valueForKey:@"cityName"];
    idea.townName = [info valueForKey:@"townName"];
    idea.categoryName = [info valueForKey:@"categoryName"];
    idea.useCount = [info valueForKey:@"useCount"];
    idea.hasUsed = [info valueForKey:@"hasUsed"];
    idea.pic = [info valueForKey:@"pic"];
    idea.bigPic = [info valueForKey:@"bigPic"];
    return idea;
}

- (BOOL) hasPlace{
    return self.place != nil && ![self.place isEqual:[NSNull null]] && ![self.place isEqualToString:@""];
}

- (BOOL) hasTime{
    return (self.startTime != nil && ![self.startTime isEqual:[NSNull null]] && ![self.startTime isEqualToString:@""]) || (self.endTime != nil && ![self.endTime isEqual:[NSNull null]] && ![self.endTime isEqualToString:@""]);
}

- (BOOL) hasCategory{
    return self.categoryName != nil && ![self.categoryName isEqual:[NSNull null]] && ![self.categoryName isEqualToString:@""];
}

@end
