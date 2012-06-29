//
//  LoginService.h
//  juzhai
//
//  Created by JiaJun Wu on 12-5-3.
//  Copyright (c) 2012年 __MyCompanyName__. All rights reserved.
//

#import <Foundation/Foundation.h>
@class LoginUser;

@interface LoginService : NSObject

+(NSString *) useLoginName:(NSString *)account byPassword:(NSString *)password;

+(BOOL) checkLogin;

+(void) logout;

+(UIViewController *) loginTurnToViewController;

@end
