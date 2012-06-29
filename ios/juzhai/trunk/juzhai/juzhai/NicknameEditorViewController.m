//
//  NicknameEditorViewController.m
//  juzhai
//
//  Created by JiaJun Wu on 12-6-20.
//  Copyright (c) 2012年 __MyCompanyName__. All rights reserved.
//

#import "NicknameEditorViewController.h"
#import "CustomButton.h"
#import "ProfileSettingViewController.h"
#import "UserView.h"
#import <QuartzCore/QuartzCore.h>

@interface NicknameEditorViewController ()

@end

@implementation NicknameEditorViewController

@synthesize textField;
@synthesize textValue;
@synthesize cellIdentifier;
@synthesize settingViewController;

- (id)initWithNibName:(NSString *)nibNameOrNil bundle:(NSBundle *)nibBundleOrNil
{
    self = [super initWithNibName:nibNameOrNil bundle:nibBundleOrNil];
    if (self) {
        // Custom initialization
    }
    return self;
}

- (void)viewDidLoad
{
    [super viewDidLoad];
    // Do any additional setup after loading the view from its nib.
    self.title = @"昵称";
    
    CustomButton *saveButton = [[CustomButton alloc] initWithWidth:45.0 buttonText:@"保存" CapLocation:CapLeftAndRight];
    [saveButton addTarget:self action:@selector(save:) forControlEvents:UIControlEventTouchUpInside];
    self.navigationItem.rightBarButtonItem = [[UIBarButtonItem alloc] initWithCustomView:saveButton];
}

- (void) viewWillAppear:(BOOL)animated{
    self.textField.text = self.textValue;
    [self.textField becomeFirstResponder];
}

- (void)viewDidUnload
{
    [super viewDidUnload];
    // Release any retained subviews of the main view.
    // e.g. self.myOutlet = nil;
}

- (BOOL)shouldAutorotateToInterfaceOrientation:(UIInterfaceOrientation)interfaceOrientation
{
    return (interfaceOrientation == UIInterfaceOrientationPortrait);
}

- (IBAction)save:(id)sender{
    // TODO 验证
    [settingViewController saveSingleInfo:self.cellIdentifier withValue:textField.text withValueId:0];
    [self.navigationController popViewControllerAnimated:YES];
}

@end
