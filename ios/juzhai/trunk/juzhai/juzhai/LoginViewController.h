//
//  LoginViewController.h
//  juzhai
//
//  Created by JiaJun Wu on 12-5-3.
//  Copyright (c) 2012年 __MyCompanyName__. All rights reserved.
//

#import <UIKit/UIKit.h>
@class LoginService;

@interface LoginViewController : UIViewController <UITableViewDelegate, UITableViewDataSource>
{
    NSArray *_loginFormCells;
}
@property (strong,nonatomic) IBOutlet UITextField *nameField;
@property (strong,nonatomic) IBOutlet UITextField *pwdField;
@property (strong,nonatomic) IBOutlet UITableView *loginFormTableView;
@property (strong,nonatomic) UITabBarController *startController;

- (IBAction)goRegister:(id)sender;
- (IBAction)login:(id)sender;
- (IBAction)backgroundTap:(id)sender;
- (IBAction)nameFieldDoneEditing:(id)sender;
- (IBAction)pwdFieldDoneEditing:(id)sender;

@end
