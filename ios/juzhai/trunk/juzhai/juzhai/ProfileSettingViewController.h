//
//  ProfileSettingViewController.h
//  juzhai
//
//  Created by JiaJun Wu on 12-6-19.
//  Copyright (c) 2012年 __MyCompanyName__. All rights reserved.
//

#import <UIKit/UIKit.h>

@class UserView;
@class CustomButton;

@interface ProfileSettingViewController : UITableViewController <UIActionSheetDelegate, UIImagePickerControllerDelegate,UINavigationControllerDelegate>
{
    NSArray *_settingCells;
    UIImage *_newLogo;
    CustomButton *_saveButton;
}

@property (strong, nonatomic) UserView *userView;

@property (strong, nonatomic) IBOutlet UIImageView *logoImageView;
@property (strong, nonatomic) IBOutlet UILabel *nicknameLabel;
@property (strong, nonatomic) IBOutlet UILabel *birthLabel;
@property (strong, nonatomic) IBOutlet UILabel *genderLabel;
@property (strong, nonatomic) IBOutlet UILabel *professionLabel;
@property (strong, nonatomic) IBOutlet UILabel *featureLabel;

- (void) saveSingleInfo:(NSInteger)tag withValue:(NSString *)value;
- (void) initUserView:(UserView *)userView;
@end
