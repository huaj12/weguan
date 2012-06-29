//
//  NicknameEditorViewController.h
//  juzhai
//
//  Created by JiaJun Wu on 12-6-20.
//  Copyright (c) 2012年 __MyCompanyName__. All rights reserved.
//

#import <UIKit/UIKit.h>
#import "SettingViewController.h"

@class ProfileSettingViewController;

@interface NicknameEditorViewController : UIViewController

@property (strong, nonatomic) SettingViewController *settingViewController;
@property (strong, nonatomic) NSString *textValue;
@property (strong, nonatomic) NSString *cellIdentifier;

@property (strong, nonatomic) IBOutlet UITextField *textField;

- (IBAction)save:(id)sender;

@end
