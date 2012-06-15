//
//  LoginViewController.m
//  juzhai
//
//  Created by JiaJun Wu on 12-5-3.
//  Copyright (c) 2012年 __MyCompanyName__. All rights reserved.
//

#import <QuartzCore/QuartzCore.h>
#import "LoginViewController.h"
#import "LoginService.h"
#import "RegisterViewController.h"
#import "MBProgressHUD.h"

@implementation LoginViewController

@synthesize nameField;
@synthesize pwdField;
@synthesize startController;


-(IBAction)goRegister:(id)sender{
    RegisterViewController *registerViewController = [[RegisterViewController alloc] initWithNibName:@"RegisterViewController" bundle:nil];
    [self.navigationController pushViewController:registerViewController animated:YES];
}

-(IBAction)login:(id)sender{
    MBProgressHUD *hud = [MBProgressHUD showHUDAddedTo:self.view animated:YES];
    hud.labelText = @"登录中...";
    dispatch_async(dispatch_get_global_queue(DISPATCH_QUEUE_PRIORITY_HIGH, 0), ^{
        sleep(1);
        NSString *errorInfo = [LoginService useLoginName:[nameField text] byPassword:[pwdField text]];
        dispatch_async(dispatch_get_main_queue(), ^{
            [MBProgressHUD hideHUDForView:self.view animated:YES];
            if(errorInfo == nil){
                NSArray *nib = [[NSBundle mainBundle] loadNibNamed:@"TabBar" owner:self options:nil];
                for(id oneObject in nib){
                    if([oneObject isKindOfClass:[UITabBarController class]]){
                        self.startController = (UITabBarController *) oneObject;
                        break;
                    }
                }
                [self.navigationController pushViewController:startController animated:NO];
//                [self.view.window addSubview:tabBarController.view];
            }else{
                MBProgressHUD *hud2 = [MBProgressHUD showHUDAddedTo:self.view animated:YES];
                hud2.mode = MBProgressHUDModeText;
                hud2.margin = 10.f;
                hud2.yOffset = 150.f;
                hud2.removeFromSuperViewOnHide = YES;
                [hud2 hide:YES afterDelay:1];
                hud2.labelText = errorInfo;
            }
        });
    });
}

- (IBAction)nameFieldDoneEditing:(id)sender{
    [pwdField becomeFirstResponder];
    [sender resignFirstResponder];
}

- (IBAction)pwdFieldDoneEditing:(id)sender{
    [sender resignFirstResponder];
//    [LoginService useLoginName:[nameField text] byPassword:[pwdField text]];
    [self login:nil];
}

- (IBAction)backgroundTap:(id)sender{
    [self.nameField resignFirstResponder];
    [self.pwdField resignFirstResponder];
}

- (id)initWithNibName:(NSString *)nibNameOrNil bundle:(NSBundle *)nibBundleOrNil
{
    self = [super initWithNibName:nibNameOrNil bundle:nibBundleOrNil];
    if (self) {
        // Custom initialization
    }
    return self;
}

- (void)didReceiveMemoryWarning
{
    // Releases the view if it doesn't have a superview.
    [super didReceiveMemoryWarning];
    
    // Release any cached data, images, etc that aren't in use.
}

#pragma mark - View lifecycle

- (void)viewDidAppear:(BOOL)animated{
}

- (void)viewDidLoad
{
    [super viewDidLoad];
    // Do any additional setup after loading the view from its nib.
    
    [[nameField layer] setShadowOffset:CGSizeMake(0, 0)];
    [[nameField layer] setShadowRadius:10];
    [[nameField layer] setShadowOpacity:0.5];
    [[nameField layer] setShadowColor:[UIColor blackColor].CGColor];
    
//    NSShadow *shadow = [[NSShadow alloc] init];
//    //设置阴影为白色
//    
//    [shadow setShadowColor:[NSColor whiteColor]];
//    
//    //设置阴影为右下方
//    
//    [shadow setShadowOffset:NSMakeSize(1, 1)];
//    
//    //这一步不可少，设置NSView的任何与Layer有关的效果都需要
//    
//    [textField setWantsLayer:YES];
//    
//    //最后一步，完成
//    
//    [textField setShadow:shadow];
}

- (void)viewDidUnload
{
    [super viewDidUnload];
    // Release any retained subviews of the main view.
    // e.g. self.myOutlet = nil;
}

- (BOOL)shouldAutorotateToInterfaceOrientation:(UIInterfaceOrientation)interfaceOrientation
{
    // Return YES for supported orientations
    return (interfaceOrientation == UIInterfaceOrientationPortrait);
}

@end
