//
//  LoginService.m
//  juzhai
//
//  Created by JiaJun Wu on 12-5-3.
//  Copyright (c) 2012年 __MyCompanyName__. All rights reserved.
//

#import "LoginService.h"
#import "ASIFormDataRequest.h"
#import "SBJson.h"
#import "LoginUser.h"
#import "HttpRequestSender.h"
#import "MessageShow.h"
#import "UserContext.h"
#import "UserView.h"
#import "LoginViewController.h"
#import "GuideSettingViewController.h"
#import "CustomNavigationController.h"

@interface LoginService(Private)
    
//+ (NSString *)dataFilePath;
    
@end

@implementation LoginService

+ (NSString *) useLoginName:(NSString *)account byPassword:(NSString *)password{
    if(account == nil || password == nil || [account isEqualToString:@""] || [password isEqualToString:@""]){
        return @"请输入登录账号和密码";
    }
    LoginUser *loginUser = [[LoginUser alloc] initWithAccount:account password:password];
    //Http请求
    NSDictionary *params = [[NSDictionary alloc] initWithObjectsAndKeys:loginUser.account, @"account", loginUser.password, @"password", [NSNumber numberWithBool:YES], @"remember", nil];
    ASIFormDataRequest *request = [HttpRequestSender postRequestWithUrl:@"http://test.51juzhai.com/app/ios/login" withParams:params];
    [request startSynchronous];
    NSError *error = [request error];
    if (!error && [request responseStatusCode] == 200){
        NSString *response = [request responseString];
        NSMutableDictionary *jsonResult = [response JSONValue];
        if([jsonResult valueForKey:@"success"] == [NSNumber numberWithBool:YES]){
            //登录成功
            [UserContext setUserView:[UserView userConvertFromDictionary:[jsonResult valueForKey:@"result"]]];
            [loginUser save];
            return nil;
        }else{
            return [jsonResult valueForKey:@"errorInfo"];
        }
    }else{
        NSLog(@"error: %@", [request responseStatusMessage]);
    }
    return SERVER_ERROR_INFO;
}

+ (NSString *) loginWithTpId:(NSInteger)tpId withQuery:(NSString *)query{
    //Http请求
    NSString *url = [NSString stringWithFormat:@"http://test.51juzhai.com/app/ios/tpAccess/%d?%@", tpId, query];
    ASIFormDataRequest *request = [HttpRequestSender postRequestWithUrl:url withParams:nil];
    [request startSynchronous];
    NSError *error = [request error];
    if (!error && [request responseStatusCode] == 200){
        NSString *response = [request responseString];
        NSMutableDictionary *jsonResult = [response JSONValue];
        if([jsonResult valueForKey:@"success"] == [NSNumber numberWithBool:YES]){
            //登录成功
            [UserContext setUserView:[UserView userConvertFromDictionary:[jsonResult valueForKey:@"result"]]];
            return nil;
        }else{
            return [jsonResult valueForKey:@"errorInfo"];
        }
    }else{
        NSLog(@"error: %@", [request responseStatusMessage]);
    }
    return SERVER_ERROR_INFO;
}

+(BOOL) checkLogin{
    LoginUser *loginUser = [[LoginUser alloc] initFromData];
    if(loginUser != nil && ![@"" isEqualToString:loginUser.account] && ![@"" isEqualToString:loginUser.password]){
        NSString *errorInfo = [self useLoginName:loginUser.account byPassword:loginUser.password];
        if(errorInfo != nil){
            [loginUser reset];
            return NO;
        }
        return YES;
    }
    return NO;
}

//+ (NSString *) dataFilePath{
//    NSArray *paths = NSSearchPathForDirectoriesInDomains(NSDocumentDirectory, NSUserDomainMask, YES);
//    NSString *documentsDirectory = [paths objectAtIndex:0];
//    return [documentsDirectory stringByAppendingPathComponent:kFilename];
//}

+(void) logout{
    ASIHTTPRequest *request = [HttpRequestSender getRequestWithUrl:@"http://test.51juzhai.com/app/ios/logout" withParams:nil];
    [request startSynchronous];
    //清除帐号信息
    [[[LoginUser alloc] init] reset];
    [UserContext logout];
}

+(UIViewController *) loginTurnToViewController{
    UIViewController *startController;
    if (![UserContext hasLogin]) {
        startController = [[CustomNavigationController alloc] initWithRootViewController:[[LoginViewController alloc] initWithNibName:@"LoginViewController" bundle:nil]];
    } else if (![UserContext hasCompleteGuide]){
        startController = [[CustomNavigationController alloc] initWithRootViewController:[[GuideSettingViewController alloc] initWithStyle:UITableViewStyleGrouped]];
    }else {
        NSArray *nib = [[NSBundle mainBundle] loadNibNamed:@"TabBar" owner:self options:nil];
        for(id oneObject in nib){
            if([oneObject isKindOfClass:[UITabBarController class]]){
                startController = (UITabBarController *) oneObject;
                break;
            }
        }
    }
    return startController;
}

@end
