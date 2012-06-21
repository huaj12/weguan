//
//  ProfileSettingViewController.m
//  juzhai
//
//  Created by JiaJun Wu on 12-6-19.
//  Copyright (c) 2012年 __MyCompanyName__. All rights reserved.
//

#import "ProfileSettingViewController.h"
#import "UserView.h"
#import "SDWebImage/UIImageView+WebCache.h"
#import <QuartzCore/QuartzCore.h>
#import "NicknameEditorViewController.h"
#import "ProfessionEditorViewController.h"
#import "CustomButton.h"
#import "BaseData.h"
#import "FeatureEditorViewController.h"

@interface ProfileSettingViewController ()

@end

@implementation ProfileSettingViewController

@synthesize logoImageView;
@synthesize nicknameLabel;
@synthesize birthLabel;
@synthesize genderLabel;
@synthesize featureLabel;
@synthesize professionLabel;
@synthesize userView;

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
    self.title = @"设置个人资料";
    _settingCells = [[NSBundle mainBundle] loadNibNamed:@"ProfileSettingForm" owner:self options:nil];
    
    _saveButton = [[CustomButton alloc] initWithWidth:45.0 buttonText:@"保存" CapLocation:CapLeftAndRight];
    [_saveButton addTarget:self action:@selector(save:) forControlEvents:UIControlEventTouchUpInside];
    _saveButton.enabled = NO;
    self.navigationItem.rightBarButtonItem = [[UIBarButtonItem alloc] initWithCustomView:_saveButton];
}

- (void) viewWillAppear:(BOOL)animated{
    if(!_saveButton.enabled){
        SDWebImageManager *manager = [SDWebImageManager sharedManager];
        NSURL *imageURL = [NSURL URLWithString:userView.logo];
        [manager downloadWithURL:imageURL delegate:self options:0 success:^(UIImage *image) {
            logoImageView.image = image;
            logoImageView.layer.shouldRasterize = YES;
            logoImageView.layer.masksToBounds = YES;
            logoImageView.layer.cornerRadius = 3.0;
        } failure:nil];
        nicknameLabel.text = userView.nickname;
        professionLabel.tag = userView.professionId.intValue;
        professionLabel.text = userView.profession;
        genderLabel.text = [userView.gender intValue] == 0 ? @"女" : @"男";
        birthLabel.text = [NSString stringWithFormat:@"%d-%d-%d", userView.birthYear.intValue, userView.birthMonth.intValue, userView.birthDay.intValue];
        featureLabel.text = userView.feature;
    }
    CGSize contentSize = [featureLabel.text sizeWithFont:featureLabel.font constrainedToSize:CGSizeMake(280.0, 640.0) lineBreakMode:UILineBreakModeCharacterWrap];
    featureLabel.frame = CGRectMake(featureLabel.frame.origin.x, featureLabel.frame.origin.y, contentSize.width, contentSize.height);
    
    [self.tableView reloadData];
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

-(void) saveSingleInfo:(NSInteger)tag withValue:(NSString *)value withValueId:(NSInteger)valueId{
    UITableViewCell *cell;
    for(id oneObject in _settingCells){
        if([oneObject tag] == tag){
            cell = oneObject;
        }
    }
    if(nil != cell){
        cell.detailTextLabel.text = value;
        cell.detailTextLabel.tag = valueId;
        _saveButton.enabled = YES;
    }
}

-(void) initUserView:(UserView *)newUserView{
    if (nil != _saveButton) {
        _saveButton.enabled = NO;
    }
    self.userView = newUserView;
}

- (IBAction)save:(id)sender{
    
}

#pragma mark -
#pragma mark Table View Data Source

-(NSInteger) numberOfSectionsInTableView:(UITableView *)tableView{
    return 3;
}

-(NSInteger)tableView:(UITableView *)tableView numberOfRowsInSection:(NSInteger)section{
    switch (section) {
        case 0:
            return 1;
            break;
        case 1:
            return 3;
            break;
        case 2:
            return 2;
            break;
    }
    return 0;
}

-(UITableViewCell *) tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath{
    for(id oneObject in _settingCells){
        if([oneObject tag] == (indexPath.section * 10 + indexPath.row)){
            return oneObject;
        }
    }
    return nil;
}

#pragma mark - 
#pragma mark Table View Delegate

-(CGFloat)tableView:(UITableView *)tableView heightForRowAtIndexPath:(NSIndexPath *)indexPath{
    if (indexPath.section == 2 && indexPath.row ==  0) {
        return 35.0 + featureLabel.frame.size.height;
    }else {
        return 44.0;
    }
}

-(void)tableView:(UITableView *)tableView didSelectRowAtIndexPath:(NSIndexPath *)indexPath{
    if(indexPath.section == 0 && indexPath.row == 0){
        //弹框选择性别
        UIActionSheet *actionSheet = [[UIActionSheet alloc] 
                                      initWithTitle:@"上传头像" 
                                      delegate:self 
                                      cancelButtonTitle:@"取消"
                                      destructiveButtonTitle:nil
                                      otherButtonTitles:@"用户相册", @"拍照", nil];
        actionSheet.tag = 0;
        [actionSheet showInView:self.view];
    } else if (indexPath.section == 1 && indexPath.row == 0){
        if(_nicknameEditorViewController == nil){
            _nicknameEditorViewController = [[NicknameEditorViewController alloc] initWithNibName:@"NicknameEditorViewController" bundle:nil];
            _nicknameEditorViewController.profileSettingViewController = self;
        }
        _nicknameEditorViewController.textValue = nicknameLabel.text;
        _nicknameEditorViewController.tag = indexPath.section * 10 + indexPath.row;
        [self.navigationController pushViewController:_nicknameEditorViewController animated:YES];
    } else if (indexPath.section == 1 && indexPath.row == 1) {
        if (nil == _datePicker) {
            _datePicker = [[UIDatePicker alloc] init];
            _datePicker.datePickerMode = UIDatePickerModeDate;
            _datePicker.tag = indexPath.section * 10 + indexPath.row;
        }
        NSDateFormatter *dateFormatter = [[NSDateFormatter alloc] init];
        [dateFormatter setDateFormat:@"yyyy-MM-dd"];
        _datePicker.date = [dateFormatter dateFromString:birthLabel.text];
        
        UIActionSheet *actionSheet = [[UIActionSheet alloc] 
                                      initWithTitle:@"\n\n\n\n\n\n\n\n\n\n\n\n" 
                                      delegate:self 
                                      cancelButtonTitle:nil
                                      destructiveButtonTitle:nil
                                      otherButtonTitles:@"设置", nil];
        actionSheet.tag = 1;
        [actionSheet showInView:self.view];
        [actionSheet addSubview:_datePicker];
    } else if (indexPath.section == 2 && indexPath.row == 1) {
        if (_professionEditorViewController == nil) {
            _professionEditorViewController = [[ProfessionEditorViewController alloc] initWithNibName:@"ProfessionEditorViewController" bundle:nil];
            _professionEditorViewController.profileSettingViewController = self;
        }
        _professionEditorViewController.valueId = professionLabel.tag;
        _professionEditorViewController.textValue = professionLabel.text;
        _professionEditorViewController.tag = indexPath.section * 10 + indexPath.row;
        [self.navigationController pushViewController:_professionEditorViewController animated:YES];
    } else if (indexPath.section == 2 && indexPath.row == 0) {
        if (_featureEditorViewController == nil) {
            _featureEditorViewController = [[FeatureEditorViewController alloc] initWithNibName:@"FeatureEditorViewController" bundle:nil];
            _featureEditorViewController.profileSettingViewController = self;
        }
        _featureEditorViewController.textValue = featureLabel.text;
        _featureEditorViewController.tag = indexPath.section * 10 + indexPath.row;
        [self.navigationController pushViewController:_featureEditorViewController animated:YES];
    }
    [tableView deselectRowAtIndexPath:indexPath animated:YES];
}

#pragma mark - 
#pragma mark Action Sheet Delegate
- (void)actionSheet:(UIActionSheet *)actionSheet
didDismissWithButtonIndex:(NSInteger)buttonIndex
{
    if (actionSheet.tag == 0){
        if(buttonIndex == 2){
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
    } else if (actionSheet.tag == 1) {
        NSDateFormatter *dateFormatter = [[NSDateFormatter alloc] init];
        [dateFormatter setDateFormat:@"yyyy-MM-dd"];
        [self saveSingleInfo:_datePicker.tag withValue:[dateFormatter stringFromDate:[_datePicker date]] withValueId:0];
    }
}

#pragma mark -
#pragma mark Image Picker Controller Delegate
- (void)imagePickerController:(UIImagePickerController *)picker didFinishPickingMediaWithInfo:(NSDictionary *)info{
    [picker dismissModalViewControllerAnimated:YES];
    UIImage *image = [info objectForKey:UIImagePickerControllerEditedImage];
    logoImageView.image = image;
    _saveButton.enabled = YES;
}

- (void) imagePickerControllerDidCancel:(UIImagePickerController *)picker{
    [picker dismissModalViewControllerAnimated:YES];
}

@end
