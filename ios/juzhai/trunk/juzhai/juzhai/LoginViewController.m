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
@synthesize loginFormTableView;


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
                [self.navigationController setNavigationBarHidden:YES];
                [self.navigationController pushViewController:startController animated:NO];
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
    NSLog(@"%@", [self.nameField description]);
    [self.nameField resignFirstResponder];
    [self.pwdField resignFirstResponder];
}

- (id)initWithNibName:(NSString *)nibNameOrNil bundle:(NSBundle *)nibBundleOrNil
{
    self = [super initWithNibName:nibNameOrNil bundle:nibBundleOrNil];
    if (self) {
        
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
//    LoginForm *loginForm = [[LoginForm alloc] init];
//    loginForm.loginViewController = self;
    [loginFormTableView setDelegate:self];
    [loginFormTableView setDataSource:self];
    _loginFormCells = [[NSBundle mainBundle] loadNibNamed:@"LoginForm" owner:self options:nil];
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

#pragma mark -
#pragma mark Table View Data Source

-(NSInteger)tableView:(UITableView *)tableView numberOfRowsInSection:(NSInteger)section{
    return 2;
}

-(UITableViewCell *)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath{
//    static NSString *AccountCellIdentifier = @"AccountCellIdentifier";
//    static NSString *PasswordCellIdentifier = @"PasswordCellIdentifier";
//    NSArray *CellIdentifierArray = [[NSArray alloc] initWithObjects:AccountCellIdentifier, PasswordCellIdentifier, nil];
//    UITableViewCell * cell = [tableView dequeueReusableCellWithIdentifier:[CellIdentifierArray objectAtIndex:indexPath.row]];
//    if(cell == nil){
//        NSArray *nib = [[NSBundle mainBundle] loadNibNamed:@"LoginForm" owner:self options:nil];
//        NSLog(@"111");
//        for(id oneObject in nib){
//            if([oneObject tag] == indexPath.row){
//                cell = oneObject;
//            }
//        }
//    }
//    return cell;
    for(id oneObject in _loginFormCells){
        if([oneObject tag] == indexPath.row){
            return oneObject;
        }
    }
    return nil;
}

#pragma mark -
#pragma mark Table View Delegate

-(CGFloat) tableView:(UITableView *)tableView heightForRowAtIndexPath:(NSIndexPath *)indexPath{
    return 50.0;
}

-(CGFloat) tableView:(UITableView *)tableView heightForHeaderInSection:(NSInteger)section{
    return 10.0;
}

-(CGFloat) tableView:(UITableView *)tableView heightForFooterInSection:(NSInteger)section{
    return 10.0;
}

@end
