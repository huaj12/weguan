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
@class InterestUserViewController;

#define INTEREST_COUNT_BUTTON_IMAGE @"my_jz_fans_btn.png"
#define INTEREST_COUNT_BUTTON_CAP_WIDTH 4.0
#define TABLE_HEAD_HEIGHT 25.0
#define TABLE_HEAD_BG_IMAGE @"my_jz_title_bg.png"
#define TABLE_HEAD_TITLE @"    共 %d 条拒宅"

@interface HomeViewController : UIViewController<UITableViewDelegate,UITableViewDataSource>
{
    JZData *_data;
    BOOL _isMe;
    ProfileSettingViewController *_profileSettingViewController;
    InterestUserViewController *_interestUserViewController;
    InterestUserViewController *_interestMeUserViewController;
}

@property (strong, nonatomic) UserView *userView;

@property (strong, nonatomic) IBOutlet UIImageView *logoView;
@property (strong, nonatomic) IBOutlet UILabel *nicknameLabel;
@property (strong, nonatomic) IBOutlet UIButton *interestUserCountButton;
@property (strong, nonatomic) IBOutlet UIButton *interestMeCountButton;
@property (strong, nonatomic) IBOutlet UILabel *postCountLabel;
@property (strong, nonatomic) IBOutlet UIButton *sendPostButton;
@property (strong, nonatomic) IBOutlet UITableView *postTableView;
@property (strong, nonatomic) IBOutlet UIButton *editorButton;

- (IBAction)editor:(id)sender;
- (IBAction)goInterestList:(id)sender;
- (IBAction)goInterestMeList:(id)sender;

@end
