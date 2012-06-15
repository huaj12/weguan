//
//  UserHomeView.h
//  juzhai
//
//  Created by JiaJun Wu on 12-6-14.
//  Copyright (c) 2012年 __MyCompanyName__. All rights reserved.
//

#import <Foundation/Foundation.h>

@interface UserHomeView : NSObject

@property (strong,nonatomic) NSDecimalNumber *uid;
@property (strong,nonatomic) NSString *nickname;
@property (strong,nonatomic) NSNumber *gender;
@property (strong,nonatomic) NSString *logo;
@property (strong,nonatomic) NSNumber *interestUserCount;
@property (strong,nonatomic) NSNumber *interestMeCount;
@end
