//
//  InterestUserViewController.h
//  juzhai
//
//  Created by JiaJun Wu on 12-6-30.
//  Copyright (c) 2012年 __MyCompanyName__. All rights reserved.
//

#import <UIKit/UIKit.h>

@class JZData;
@class HomeViewController;

#define INTEREST_USER_DEFAULT_PIC @"none_img.png"
#define INTEREST_USER_LOGO_TAG 1
#define INTEREST_USER_NICKNAME_TAG 2
#define INTEREST_USER_INFO_TAG 3

@interface InterestUserViewController : UITableViewController
{
    JZData *_data;
}

@property (nonatomic) BOOL isInterest;
@end
