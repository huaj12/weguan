//
//  FeatureEditorViewController.m
//  juzhai
//
//  Created by JiaJun Wu on 12-6-21.
//  Copyright (c) 2012年 __MyCompanyName__. All rights reserved.
//

#import "FeatureEditorViewController.h"
#import <QuartzCore/QuartzCore.h>
#import "CustomButton.h"
#import "ProfileSettingViewController.h"

@interface FeatureEditorViewController ()

@end

@implementation FeatureEditorViewController

@synthesize tag;
@synthesize textView;
@synthesize textValue;

@synthesize profileSettingViewController;

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
    
    self.title = @"自我评价";
    
    CustomButton *saveButton = [[CustomButton alloc] initWithWidth:45.0 buttonText:@"保存" CapLocation:CapLeftAndRight];
    [saveButton addTarget:self action:@selector(save:) forControlEvents:UIControlEventTouchUpInside];
    self.navigationItem.rightBarButtonItem = [[UIBarButtonItem alloc] initWithCustomView:saveButton];
    
    [textView.layer setCornerRadius:10];
    [textView.layer setBorderWidth:1];
    [textView.layer setBorderColor:[UIColor grayColor].CGColor];
}

- (void) viewWillAppear:(BOOL)animated{
    [textView becomeFirstResponder];
    textView.text = textValue;
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

-(IBAction)save:(id)sender{
    //验证
    
    [profileSettingViewController saveSingleInfo:self.tag withValue:textView.text withValueId:0];
    [self.navigationController popViewControllerAnimated:YES];
}

@end
