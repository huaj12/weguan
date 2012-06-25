//
//  HomeViewController.h
//  juzhai
//
//  Created by JiaJun Wu on 12-6-14.
//  Copyright (c) 2012年 __MyCompanyName__. All rights reserved.
//

#import <UIKit/UIKit.h>

@class UserView;
@class JZData;
@class ProfileSettingViewController;

#define INTEREST_COUNT_BUTTON_IMAGE @"my_jz_fans_btn.png"
#define INTEREST_COUNT_BUTTON_CAP_WIDTH 4.0
#define TABLE_HEAD_HEIGHT 25.0
#define TABLE_HEAD_BG_IMAGE @"my_jz_title_bg.png"
#define TABLE_HEAD_TITLE @"共 %d 条拒宅"

@interface HomeViewController : UIViewController<UITableViewDelegate,UITableViewDataSource>
{
    UserView *_userView;
    JZData *_data;
    ProfileSettingViewController *_profileSettingViewController;
}

@property (strong, nonatomic) IBOutlet UIImageView *logoView;
@property (strong, nonatomic) IBOutlet UILabel *nicknameLabel;
@property (strong, nonatomic) IBOutlet UIButton *interestUserCountButton;
@property (strong, nonatomic) IBOutlet UIButton *interestMeCountButton;
@property (strong, nonatomic) IBOutlet UIButton *sendPostButton;
@property (strong, nonatomic) IBOutlet UITableView *postTableView;

-(IBAction)editor:(id)sender;

@end
