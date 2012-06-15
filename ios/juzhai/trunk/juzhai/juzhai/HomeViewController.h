//
//  HomeViewController.h
//  juzhai
//
//  Created by JiaJun Wu on 12-6-14.
//  Copyright (c) 2012年 __MyCompanyName__. All rights reserved.
//

#import <UIKit/UIKit.h>

@class UserHomeView;
@class JZData;

@interface HomeViewController : UIViewController<UITableViewDelegate,UITableViewDataSource>
{
    UserHomeView *_userHomeView;
    JZData *_data;
}

@property (strong, nonatomic) IBOutlet UIImageView *logoImageView;
@property (strong, nonatomic) IBOutlet UILabel *nicknameLabel;
@property (strong, nonatomic) IBOutlet UIButton *interestUserListButton;
@property (strong, nonatomic) IBOutlet UIButton *interestMeListButton;
@property (strong, nonatomic) IBOutlet UIButton *sendPostButton;
@property (strong, nonatomic) IBOutlet UITableView *postTableView;

@end
