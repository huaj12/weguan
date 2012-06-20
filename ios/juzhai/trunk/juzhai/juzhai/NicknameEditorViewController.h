//
//  NicknameEditorViewController.h
//  juzhai
//
//  Created by JiaJun Wu on 12-6-20.
//  Copyright (c) 2012年 __MyCompanyName__. All rights reserved.
//

#import <UIKit/UIKit.h>

@class ProfileSettingViewController;

@interface NicknameEditorViewController : UIViewController

@property (strong, nonatomic) ProfileSettingViewController *profileSettingViewController;

@property (strong, nonatomic) IBOutlet UITextField *textField;
@property (strong, nonatomic) NSString *textValue;
@property (nonatomic) NSInteger tag;

- (IBAction)save:(id)sender;

@end
