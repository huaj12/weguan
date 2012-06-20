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
#import "CustomButton.h"

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
//    [_saveButton addTarget:self action:@selector(save:) forControlEvents:UIControlEventTouchUpInside];
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
        professionLabel.text = userView.profession;
        genderLabel.text = [userView.gender intValue] == 0 ? @"女" : @"男";
        birthLabel.text = [NSString stringWithFormat:@"%d年%d月%d日", userView.birthYear.intValue, userView.birthMonth.intValue, userView.birthDay.intValue];
        featureLabel.text = userView.feature;
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

-(void) saveSingleInfo:(NSInteger)tag withValue:(NSString *)value{
    UITableViewCell *cell;
    for(id oneObject in _settingCells){
        if([oneObject tag] == tag){
            cell = oneObject;
        }
    }
    if(nil != cell){
        cell.detailTextLabel.text = value;
        _saveButton.enabled = YES;
    }
}

-(void) initUserView:(UserView *)newUserView{
    if (nil != _saveButton) {
        _saveButton.enabled = NO;
    }
    self.userView = newUserView;
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

-(void)tableView:(UITableView *)tableView didSelectRowAtIndexPath:(NSIndexPath *)indexPath{
    if(indexPath.section == 0 && indexPath.row == 0){
        //弹框选择性别
        UIActionSheet *actionSheet = [[UIActionSheet alloc] 
                                      initWithTitle:@"上传头像" 
                                      delegate:self 
                                      cancelButtonTitle:@"取消"
                                      destructiveButtonTitle:nil
                                      otherButtonTitles:@"用户相册", @"拍照", nil];
        [actionSheet showInView:self.view];
    } else if (indexPath.section == 1 && indexPath.row == 0){
        NicknameEditorViewController *nicknameEditorViewController = [[NicknameEditorViewController alloc] initWithNibName:@"NicknameEditorViewController" bundle:nil];
        nicknameEditorViewController.textValue = nicknameLabel.text;
        nicknameEditorViewController.tag = indexPath.section * 10 + indexPath.row;
        nicknameEditorViewController.profileSettingViewController = self;
        [self.navigationController pushViewController:nicknameEditorViewController animated:YES];
    }
    [tableView deselectRowAtIndexPath:indexPath animated:YES];
}

#pragma mark - 
#pragma mark Action Sheet Delegate
- (void)actionSheet:(UIActionSheet *)actionSheet
didDismissWithButtonIndex:(NSInteger)buttonIndex
{
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
