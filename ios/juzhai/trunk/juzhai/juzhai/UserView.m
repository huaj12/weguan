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
@synthesize interestMeCount;
@synthesize interestUserCount;

+ (id) userConvertFromDictionary:(NSDictionary *)info{
    UserView *user = [UserView alloc];
    [user updateUserInfo:info];
    NSDictionary *postInfo = [info valueForKey:@"postView"];
    if(![postInfo isEqual:[NSNull null]]){
        user.post = [PostView postConvertFromDictionary:postInfo];
    }
    return user;
}

- (void) updateUserInfo:(NSDictionary *)info{
    self.uid = [info valueForKey:@"uid"];
    self.nickname = [info valueForKey:@"nickname"];
    self.gender = [info valueForKey:@"gender"];
    self.logo = [info valueForKey:@"logo"];
    self.birthYear = [info valueForKey:@"birthYear"];
    self.birthMonth = [info valueForKey:@"birthMonth"];
    self.birthDay = [info valueForKey:@"birthDay"];
    self.constellation = [info valueForKey:@"constellation"];
    self.profession = [info valueForKey:@"profession"];
    self.cityName = [info valueForKey:@"cityName"];
    self.townName = [info valueForKey:@"townName"];
    self.interestUserCount = [info valueForKey:@"interestUserCount"];
    self.interestMeCount = [info valueForKey:@"interestMeCount"];
}

@end
