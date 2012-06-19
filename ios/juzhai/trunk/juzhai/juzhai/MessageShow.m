//
//  MessageShow.m
//  juzhai
//
//  Created by JiaJun Wu on 12-6-19.
//  Copyright (c) 2012年 __MyCompanyName__. All rights reserved.
//

#import "MessageShow.h"
#import "MBProgressHUD.h"

@implementation MessageShow

+(void) error:(NSString *)msg onView:(UIView *)view{
//    MBProgressHUD *hud = [MBProgressHUD showHUDAddedTo:view animated:YES];
//    hud.mode = MBProgressHUDModeText;
//    //                hud2.margin = 10.f;
//    //                hud2.yOffset = 135.f;
//    hud.removeFromSuperViewOnHide = YES;
//    [hud hide:YES afterDelay:1];
//    hud.labelText = msg;
    UIAlertView *alert = [[UIAlertView alloc] initWithTitle:msg message:
                          nil delegate:nil cancelButtonTitle:@"确定" otherButtonTitles:nil]; 
    [alert show];
}

@end
