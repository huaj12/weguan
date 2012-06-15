//
//  UserHomeView.m
//  juzhai
//
//  Created by JiaJun Wu on 12-6-14.
//  Copyright (c) 2012年 __MyCompanyName__. All rights reserved.
//

#import "UserHomeView.h"

@implementation UserHomeView

@synthesize uid;
@synthesize nickname;
@synthesize gender;
@synthesize logo;
@synthesize interestMeCount;
@synthesize interestUserCount;

+ (id) userHomeConvertFromDictionary:(NSDictionary *)info{
    UserHomeView *userHome = [UserHomeView alloc];
    userHome.uid = [info valueForKey:@"uid"];
    userHome.nickname = [info valueForKey:@"nickname"];
    userHome.gender = [info valueForKey:@"gender"];
    userHome.logo = [info valueForKey:@"logo"];
    userHome.interestUserCount = [info valueForKey:@"interestUserCount"];
    userHome.interestMeCount = [info valueForKey:@"interestMeCount"];
    return userHome;
}

@end
