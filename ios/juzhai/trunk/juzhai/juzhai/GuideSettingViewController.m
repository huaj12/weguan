//
//  GuideSettingViewController.m
//  juzhai
//
//  Created by JiaJun Wu on 12-6-26.
//  Copyright (c) 2012年 __MyCompanyName__. All rights reserved.
//

#import "GuideSettingViewController.h"
#import "MBProgressHUD.h"
#import "MessageShow.h"

@interface GuideSettingViewController ()

@end

@implementation GuideSettingViewController

- (id)initWithStyle:(UITableViewStyle)style
{
    self = [super initWithStyle:style];
    if (self) {
        // Custom initialization
    }
    return self;
}

- (void)viewDidLoad
{
    [super viewDidLoad];
    
    self.title = @"个人引导";
    
    _cellIdentifierDictionary = [[NSDictionary alloc] initWithObjectsAndKeys:LOGO_CELL_IDENTIFIER, [NSNumber numberWithInt:0], NICKNAME_CELL_IDENTIFIER, [NSNumber numberWithInt:10], GENDER_CELL_IDENTIFIER, [NSNumber numberWithInt:11], BIRTH_CELL_IDENTIFIER, [NSNumber numberWithInt:12], LOCATION_CELL_IDENTIFIER, [NSNumber numberWithInt:20], PROFESSION_CELL_IDENTIFIER, [NSNumber numberWithInt:21], nil];
    _disableSelectCellIdentifiterArray = [[NSArray alloc] initWithObjects:NICKNAME_CELL_IDENTIFIER, nil];
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

- (BOOL)validateSave{
    if ([self.birthLabel.text isEqualToString:@""]) {
        [MessageShow error:@"请填写生日" onView:self.navigationController.view];
        return NO;
    }
    if ([self.professionLabel.text isEqualToString:@""]) {
        [MessageShow error:@"请选择职业" onView:self.navigationController.view];
        return NO;
    }
    return YES;
}

- (void) doSave:(MBProgressHUD *)hud{
    sleep(1);
//    NSLog(@"%@", self.nicknameLabel.text);
//    NSLog(@"%@", self.birthLabel.text);
//    NSLog(@"%@", self.featureLabel.text);
//    NSLog(@"%d", self.professionLabel.tag);
//    NSLog(@"%@", self.professionLabel.text);
//    
//    NSDictionary *params = [[NSDictionary alloc] initWithObjectsAndKeys:self.nicknameLabel.text, @"nickname", self.birthLabel.text, @"birth", self.featureLabel.text, @"feature", [NSNumber numberWithInt:self.professionLabel.tag], @"professionId", self.professionLabel.text, @"profession", nil];
//    ASIFormDataRequest *request = [HttpRequestSender postRequestWithUrl:@"http://test.51juzhai.com/app/ios/profile/save" withParams:params];
//    if (_newLogo != nil) {
//        CGFloat compression = 0.9f;
//        CGFloat maxCompression = 0.1f;
//        int maxFileSize = 2*1024*1024;
//        
//        NSData *imageData = UIImageJPEGRepresentation(_newLogo, compression);
//        while ([imageData length] > maxFileSize && compression > maxCompression){
//            compression -= 0.1;
//            imageData = UIImageJPEGRepresentation(_newLogo, compression);
//        }
//        [request setData:imageData withFileName:@"logo.jpg" andContentType:@"image/jpeg" forKey:@"logo"];
//    }
//    [request startSynchronous];
//    NSError *error = [request error];
//    NSString *errorInfo = SERVER_ERROR_INFO;
//    if (!error && [request responseStatusCode] == 200){
//        NSString *response = [request responseString];
//        NSMutableDictionary *jsonResult = [response JSONValue];
//        if([jsonResult valueForKey:@"success"] == [NSNumber numberWithBool:YES]){
//            //保存成功
//            _saveButton.enabled = NO;
//            [[UserContext getUserView] updateUserInfo:[jsonResult valueForKey:@"result"]];
//            hud.customView = [[UIImageView alloc] initWithImage:[UIImage imageNamed:@"37x-Checkmark.png"]];
//            hud.mode = MBProgressHUDModeCustomView;
//            hud.labelText = @"保存成功";
//            sleep(1);
//            return;
//        }else{
//            errorInfo = [jsonResult valueForKey:@"errorInfo"];
//        }
//    }else{
//        NSLog(@"error: %@", [request responseStatusMessage]);
//    }
//    [MessageShow error:errorInfo onView:self.navigationController.view];
    hud.customView = [[UIImageView alloc] initWithImage:[UIImage imageNamed:@"37x-Checkmark.png"]];
    hud.mode = MBProgressHUDModeCustomView;
    hud.labelText = @"保存成功";
    sleep(1);
}

#pragma mark - Table view data source

- (NSInteger)numberOfSectionsInTableView:(UITableView *)tableView
{
    // Return the number of sections.
    return 3;
}

- (NSInteger)tableView:(UITableView *)tableView numberOfRowsInSection:(NSInteger)section
{
    // Return the number of rows in the section.
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

@end
