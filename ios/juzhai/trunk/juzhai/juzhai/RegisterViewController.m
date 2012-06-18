//
//  RegisterViewController.m
//  juzhai
//
//  Created by JiaJun Wu on 12-5-3.
//  Copyright (c) 2012年 __MyCompanyName__. All rights reserved.
//

#import "RegisterViewController.h"
#import "LoginViewController.h"
#import "TpLoginDelegate.h"

@implementation RegisterViewController

@synthesize accountField;
@synthesize nicknameField;
@synthesize passwordField;
@synthesize confirmPwdField;
@synthesize accountCell;
@synthesize nicknameCell;
@synthesize passwordCell;
@synthesize confirmPwdCell;
@synthesize registerFormTableView;
@synthesize tpLoginTableView;


-(IBAction)accountFieldDoneEditing:(id)sender{
    [sender resignFirstResponder];
    [self.nicknameField becomeFirstResponder];
}

-(IBAction)nicknameFieldDoneEditing:(id)sender{
    [sender resignFirstResponder];
    [self.passwordField becomeFirstResponder];
}

-(IBAction)passwordFieldDoneEditing:(id)sender{
    [sender resignFirstResponder];
    [self.confirmPwdField becomeFirstResponder];
}

-(IBAction)confirmPwdFieldDoneEditing:(id)sender{
    [sender resignFirstResponder];
    [self doRegister:nil];
}

-(IBAction)doRegister:(id)sender{
    
}

- (IBAction)backgroundTap:(id)sender{
    [self.accountField resignFirstResponder];
    [self.nicknameField resignFirstResponder];
    [self.passwordField resignFirstResponder];
    [self.confirmPwdField resignFirstResponder];
}

-(IBAction)goLogin:(id)sender{
//    LoginViewController *loginViewController = [[LoginViewController alloc] initWithNibName:@"LoginViewController" bundle:nil];
    [self.navigationController popViewControllerAnimated:YES];
}

- (id)initWithNibName:(NSString *)nibNameOrNil bundle:(NSBundle *)nibBundleOrNil
{
    self = [super initWithNibName:nibNameOrNil bundle:nibBundleOrNil];
    if (self) {
        // Custom initialization
        _tpLoginDelegate = [[TpLoginDelegate alloc] init];
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

- (void)viewDidLoad
{
    [super viewDidLoad];
    // Do any additional setup after loading the view from its nib.
    _registerFormCells = [[NSArray alloc] initWithObjects:self.accountCell, self.nicknameCell, self.passwordCell, self.confirmPwdCell, nil];
    
    self.title = @"帐号注册";
    
    registerFormTableView.dataSource = self;
    registerFormTableView.backgroundView = nil;
    registerFormTableView.backgroundColor = [UIColor clearColor];
    registerFormTableView.opaque = NO;
    
    //    [tpLoginTableView setDelegate:self];
    [tpLoginTableView setDataSource:_tpLoginDelegate];
    tpLoginTableView.backgroundView = nil;
    tpLoginTableView.backgroundColor = [UIColor clearColor];
    tpLoginTableView.opaque = NO;
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
    return 4;
}

-(UITableViewCell *)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath{
    return [_registerFormCells objectAtIndex:indexPath.row];
}

@end
