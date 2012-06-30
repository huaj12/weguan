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
#import "NSString+Chinese.h"
#import "MessageShow.h"

@interface FeatureEditorViewController ()

@end

@implementation FeatureEditorViewController

@synthesize cellIdentifier;
@synthesize textView;
@synthesize textValue;
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
    NSString *value = [textView.text stringByTrimmingCharactersInSet: 
                       [NSCharacterSet whitespaceAndNewlineCharacterSet]];
    NSInteger textLength = [value chineseLength];
    if (textLength < FEATURE_MIN_LENGTH) {
        [MessageShow error:FEATURE_MIN_ERROR_TEXT onView:self.view];
        return;
    }
    if (textLength > FEATURE_MAX_LENGTH) {
        [MessageShow error:FEATURE_MAX_ERROR_TEXT onView:self.view];
        return;
    }
    NSInteger tag = [value isEqualToString:@""] ? 0 : 1;
    [settingViewController saveSingleInfo:self.cellIdentifier withValue:value withValueId:tag];
    [self.navigationController popViewControllerAnimated:YES];
}

@end
