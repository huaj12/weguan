//
//  User.m
//  juzhai
//
//  Created by JiaJun Wu on 12-5-30.
//  Copyright (c) 2012年 __MyCompanyName__. All rights reserved.
//

#import "UserView.h"
#import "PostView.h"

@implementation UserView

@synthesize uid;
@synthesize nickname;
@synthesize gender;
@synthesize logo;
@synthesize birthYear;
@synthesize birthMonth;
@synthesize birthDay;
@synthesize constellation;
@synthesize profession;
@synthesize cityName;
@synthesize townName;
@synthesize post;

+ (id) userConvertFromDictionary:(NSDictionary *)info{
    UserView *user = [UserView alloc];
    user.uid = [info valueForKey:@"uid"];
    user.nickname = [info valueForKey:@"nickname"];
    user.gender = [info valueForKey:@"gender"];
    user.logo = [info valueForKey:@"logo"];
    user.birthYear = [info valueForKey:@"birthYear"];
    user.birthMonth = [info valueForKey:@"birthMonth"];
    user.birthDay = [info valueForKey:@"birthDay"];
    user.constellation = [info valueForKey:@"constellation"];
    user.profession = [info valueForKey:@"profession"];
    user.cityName = [info valueForKey:@"cityName"];
    user.townName = [info valueForKey:@"townName"];
    NSDictionary *postInfo = [info valueForKey:@"postView"];
    if(![postInfo isEqual:[NSNull null]]){
        user.post = [PostView postConvertFromDictionary:postInfo];
    }
    return user;
}

@end
