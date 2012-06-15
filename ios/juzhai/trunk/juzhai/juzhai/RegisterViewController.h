//
//  RegisterViewController.h
//  juzhai
//
//  Created by JiaJun Wu on 12-5-3.
//  Copyright (c) 2012年 __MyCompanyName__. All rights reserved.
//

#import <UIKit/UIKit.h>

@interface RegisterViewController : UIViewController

@property (nonatomic, strong) IBOutlet UITextField *accountField;
@property (nonatomic, strong) IBOutlet UITextField *nicknameField;
@property (nonatomic, strong) IBOutlet UITextField *passwordField;
@property (nonatomic, strong) IBOutlet UITextField *confirmPwdField;


-(IBAction) goLogin:(id)sender;
-(IBAction) backgroundTap:(id)sender;
-(IBAction) accountFieldDoneEditing:(id)sender;
-(IBAction) nicknameFieldDoneEditing:(id)sender;
-(IBAction) passwordFieldDoneEditing:(id)sender;
-(IBAction) confirmPwdFieldDoneEditing:(id)sender;
-(IBAction) doRegister:(id)sender;
@end
