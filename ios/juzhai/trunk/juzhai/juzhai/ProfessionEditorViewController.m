//
//  ProfessionEditorViewController.m
//  juzhai
//
//  Created by JiaJun Wu on 12-6-21.
//  Copyright (c) 2012年 __MyCompanyName__. All rights reserved.
//

#import "ProfessionEditorViewController.h"
#import "BaseData.h"
#import "Profession.h"
#import "CustomButton.h"
#import "ProfileSettingViewController.h"

@interface ProfessionEditorViewController ()

@end

@implementation ProfessionEditorViewController

@synthesize profileSettingViewController;
@synthesize tag;
@synthesize textValue;
@synthesize professionPicker;
@synthesize professionField;
@synthesize tipLabel;
@synthesize valueId;

- (id)initWithNibName:(NSString *)nibNameOrNil bundle:(NSBundle *)nibBundleOrNil
{
    self = [super initWithNibName:nibNameOrNil bundle:nibBundleOrNil];
    if (self) {
        _professionArray = [BaseData getProfessions];
    }
    return self;
}

- (void)viewDidLoad
{
    [super viewDidLoad];
    // Do any additional setup after loading the view from its nib.    
    self.title = @"职业";
    
    CustomButton *saveButton = [[CustomButton alloc] initWithWidth:45.0 buttonText:@"保存" CapLocation:CapLeftAndRight];
    [saveButton addTarget:self action:@selector(save:) forControlEvents:UIControlEventTouchUpInside];
    self.navigationItem.rightBarButtonItem = [[UIBarButtonItem alloc] initWithCustomView:saveButton];
}

- (void) showTextField:(bool)isShow{
    professionField.hidden = !isShow;
    tipLabel.hidden = !isShow;
}

- (void) viewWillAppear:(BOOL)animated{
    if (valueId == -1){
        [professionPicker selectRow:[_professionArray count] inComponent:0 animated:NO];
        [self showTextField:YES];
        professionField.text = textValue;
    }else {
        NSInteger index = 0;
        int i = 0;
        for (Profession *p in _professionArray) {
            if (p.professionId.intValue == valueId) {
                index = i;
                break;
            }
            i++;
        }
        [professionPicker selectRow:index inComponent:0 animated:NO];
        [self showTextField:NO];
        professionField.text = @"";
    }
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
    if (professionField.hidden){
        NSInteger row = [professionPicker selectedRowInComponent:0];
        Profession *p = [_professionArray objectAtIndex:row];
        if(p != nil){
            [self.profileSettingViewController saveSingleInfo:self.tag withValue:p.name withValueId:p.professionId.intValue];
        }
    }else {
        //验证
        [self.profileSettingViewController saveSingleInfo:self.tag withValue:professionField.text withValueId:-1];
    }
    [self.navigationController popViewControllerAnimated:YES];
}

#pragma mark -
#pragma mark Picker Data Source Methods
- (NSInteger) numberOfComponentsInPickerView:(UIPickerView *)pickerView{
    return 1;
}

- (NSInteger) pickerView:(UIPickerView *)pickerView numberOfRowsInComponent:(NSInteger)component{
    return [_professionArray count] + 1;
}

#pragma mark Picker Delegate Methods
- (NSString *) pickerView:(UIPickerView *)pickerView titleForRow:(NSInteger)row forComponent:(NSInteger)component{
    if (row == [_professionArray count]) {
        return @"其它";
    }
    Profession *profession = (Profession *)[_professionArray objectAtIndex:row];
    return profession.name;
}

- (void) pickerView:(UIPickerView *)pickerView didSelectRow:(NSInteger)row inComponent:(NSInteger)component{
    if (row == [_professionArray count]) {
        [self showTextField:YES];
    }else {
        [professionField resignFirstResponder];
        [self showTextField:NO];
    }
}

@end
