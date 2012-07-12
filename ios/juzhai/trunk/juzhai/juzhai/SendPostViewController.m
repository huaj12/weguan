//
//  SendPostViewController.m
//  juzhai
//
//  Created by JiaJun Wu on 12-7-4.
//  Copyright (c) 2012年 __MyCompanyName__. All rights reserved.
//

#import "SendPostViewController.h"
#import <QuartzCore/QuartzCore.h>
#import "CustomActionSheet.h"
#import "NSString+Chinese.h"
#import "MessageShow.h"
#import "CustomButton.h"
#import "PostService.h"

@interface SendPostViewController ()

@end

@implementation SendPostViewController

@synthesize navigationBar;
@synthesize textView;
@synthesize imageView;
@synthesize timeButton;
@synthesize placeButton;
@synthesize imageButton;
@synthesize timeLabel;
@synthesize placeLabel;

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
    
    UIImage *backImage = [UIImage imageNamed:BACK_NORMAL_PIC_NAME];
    UIImage *activeBackImage = [UIImage imageNamed:BACK_HIGHLIGHT_PIC_NAME];
    UIButton *backButton = [UIButton buttonWithType:UIButtonTypeCustom];
    backButton.frame = CGRectMake(0, 0, backImage.size.width, backImage.size.height);
    [backButton setBackgroundImage:backImage forState:UIControlStateNormal];
    [backButton setBackgroundImage:activeBackImage forState:UIControlStateHighlighted];
    [backButton addTarget:self action:@selector(back:) forControlEvents:UIControlEventTouchUpInside];
    UIBarButtonItem *backItem = [[UIBarButtonItem alloc] initWithCustomView:backButton];
    navigationBar.topItem.leftBarButtonItem = backItem;
    
    _saveButton = [[CustomButton alloc] initWithWidth:45.0 buttonText:@"发布" CapLocation:CapLeftAndRight];
    [_saveButton addTarget:self action:@selector(sendPost:) forControlEvents:UIControlEventTouchUpInside];
    _saveButton.enabled = NO;
    navigationBar.topItem.rightBarButtonItem = [[UIBarButtonItem alloc] initWithCustomView:_saveButton];
    
    [textView becomeFirstResponder];
    [textView.layer setCornerRadius:10];
    [textView.layer setBorderWidth:1];
    [textView.layer setBorderColor:[UIColor grayColor].CGColor];
    textView.delegate = self;
    
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

- (IBAction)back:(id)sender
{
    [self dismissModalViewControllerAnimated:YES];
}

- (IBAction)timeButtonClick:(id)sender
{
    if (nil == _datePicker) {
        _datePicker = [[UIDatePicker alloc] init];
        _datePicker.datePickerMode = UIDatePickerModeDate;
    }
//    if (![self.birthLabel.text isEqualToString:@""]) {
//        NSDateFormatter *dateFormatter = [[NSDateFormatter alloc] init];
//        [dateFormatter setDateFormat:@"yyyy-MM-dd"];
//        _datePicker.date = [dateFormatter dateFromString:self.birthLabel.text];
//    }
    
    CustomActionSheet *actionSheet = [[CustomActionSheet alloc] initWithHeight:_datePicker.frame.size.height withSheetTitle:@"拒宅时间" delegate:self];
    [actionSheet.view addSubview: _datePicker];
    [actionSheet showInView:self.view];
}

- (IBAction)placeButtonClick:(id)sender
{
    UIAlertView *alertView = [[UIAlertView alloc] initWithTitle:@"输入地点" message:@"\n\n" delegate:self cancelButtonTitle:@"取消" otherButtonTitles:@"确定", nil];
    
    if (_placeField == nil) {
        _placeField = [[UITextField alloc] initWithFrame:CGRectMake(15.0f, 51.0f, 254.0f, 30.0f)];
        _placeField.contentVerticalAlignment = UIControlContentVerticalAlignmentCenter;
        _placeField.clearButtonMode = UITextFieldViewModeWhileEditing;
        _placeField.placeholder = @"输入地点";
        _placeField.borderStyle = UITextBorderStyleRoundedRect;
    }
    if (_lastPlaceErrorInput != nil && ![_lastPlaceErrorInput isEqualToString:@""]) {
        _placeField.text = _lastPlaceErrorInput;
    }else {
        _placeField.text = placeLabel.text;
    }
    [alertView addSubview:_placeField];
    [alertView show];
}

- (IBAction)imageButtonClick:(id)sender
{
    UIActionSheet *actionSheet = [[UIActionSheet alloc] 
                                  initWithTitle:@"上传头像" 
                                  delegate:self 
                                  cancelButtonTitle:@"取消"
                                  destructiveButtonTitle:nil
                                  otherButtonTitles:@"用户相册", @"拍照", nil];
    [actionSheet showInView:self.view];
}

- (IBAction)sendPost:(id)sender
{
    if (!_postService) {
        _postService = [[PostService alloc] init];
    }
    [_postService sendPost:textView.text withDate:timeLabel.text withPlace:placeLabel.text withImage:_image onView:self.view withSuccessCallback:^{
        [self back:nil];
    }];
}

#pragma mark - 
#pragma mark Custom Action Sheet Delegate

- (void) done:(CustomActionSheet *)actionSheet{
    NSDateFormatter *dateFormatter = [[NSDateFormatter alloc] init];
    [dateFormatter setDateFormat:@"yyyy-MM-dd"];
    timeLabel.text = [dateFormatter stringFromDate:[_datePicker date]];
}

#pragma mark - 
#pragma mark Action Sheet Delegate
- (void)actionSheet:(UIActionSheet *)actionSheet
didDismissWithButtonIndex:(NSInteger)buttonIndex
{
    if(buttonIndex == [actionSheet cancelButtonIndex]){
        return;
    }
    UIImagePickerControllerSourceType sourceType;
    if(buttonIndex == 1){
        sourceType = UIImagePickerControllerSourceTypePhotoLibrary;
    }else {
        if (![UIImagePickerController isSourceTypeAvailable: UIImagePickerControllerSourceTypeCamera]) {
            sourceType = UIImagePickerControllerSourceTypePhotoLibrary;
        }else {
            sourceType = UIImagePickerControllerSourceTypeCamera;
        }
    }
    UIImagePickerController *picker = [[UIImagePickerController alloc] init];
    picker.delegate = self;
    picker.allowsEditing = YES;
    picker.sourceType = sourceType;
    [self presentModalViewController:picker animated:YES];
}

#pragma mark -
#pragma mark Image Picker Controller Delegate
- (void)imagePickerController:(UIImagePickerController *)picker didFinishPickingMediaWithInfo:(NSDictionary *)info{
    [picker dismissModalViewControllerAnimated:YES];
    UIImage *image = [info objectForKey:UIImagePickerControllerEditedImage];
    _image = image;
    self.imageView.image = image;
}

- (void) imagePickerControllerDidCancel:(UIImagePickerController *)picker{
    [picker dismissModalViewControllerAnimated:YES];
}

#pragma mark -
#pragma mark Alert View Delegate

- (void)didPresentAlertView:(UIAlertView *)alertView {
    [_placeField becomeFirstResponder];
}

- (void)alertView:(UIAlertView *)alertView didDismissWithButtonIndex:(NSInteger)buttonIndex {
	if (buttonIndex != [alertView cancelButtonIndex]) {
		//验证字数
        NSString *value = [_placeField.text stringByTrimmingCharactersInSet: 
                           [NSCharacterSet whitespaceAndNewlineCharacterSet]];
        NSInteger textLength = [value chineseLength];
        if (textLength > PLACE_MAX_LENGTH) {
            _lastPlaceErrorInput = value;
            [MessageShow error:PLACE_MAX_ERROR_TEXT onView:alertView];
            return;
        }else {
            _lastPlaceErrorInput = nil;
            placeLabel.text = value;
        }
	}
}

#pragma mark -
#pragma mark Text View Delegate

- (void)textViewDidChange:(UITextView *)textView
{
    NSString *value = [self.textView.text stringByTrimmingCharactersInSet: 
                       [NSCharacterSet whitespaceAndNewlineCharacterSet]];
    _saveButton.enabled = ![value isEqualToString:@""];
}

@end
