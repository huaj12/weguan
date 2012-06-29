//
//  ProfileSettingViewController.m
//  juzhai
//
//  Created by JiaJun Wu on 12-6-19.
//  Copyright (c) 2012年 __MyCompanyName__. All rights reserved.
//

#import "ProfileSettingViewController.h"
#import "BaseData.h"
#import "MBProgressHUD.h"
#import "ASIHTTPRequest.h"
#import "HttpRequestSender.h"
#import "SBJson.h"
#import "MessageShow.h"
#import "UserContext.h"
#import "CustomButton.h"
#import "UserView.h"

@interface ProfileSettingViewController ()

@end

@implementation ProfileSettingViewController

- (void)viewDidLoad
{
    [super viewDidLoad];
    // Do any additional setup after loading the view from its nib.
    self.title = @"设置个人资料";
    
    _cellIdentifierDictionary = [[NSDictionary alloc] initWithObjectsAndKeys:LOGO_CELL_IDENTIFIER, [NSNumber numberWithInt:0], NICKNAME_CELL_IDENTIFIER, [NSNumber numberWithInt:10], GENDER_CELL_IDENTIFIER, [NSNumber numberWithInt:11], BIRTH_CELL_IDENTIFIER, [NSNumber numberWithInt:12], FEATURE_CELL_IDENTIFIER, [NSNumber numberWithInt:20], LOCATION_CELL_IDENTIFIER, [NSNumber numberWithInt:21], PROFESSION_CELL_IDENTIFIER, [NSNumber numberWithInt:22], nil];
}

- (void) viewWillAppear:(BOOL)animated{
    [super viewWillAppear:animated];
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

- (void) doSave:(MBProgressHUD *)hud{
    sleep(1);
    NSLog(@"%@", self.nicknameLabel.text);
    NSLog(@"%@", self.birthLabel.text);
    NSLog(@"%@", self.featureLabel.text);
    NSLog(@"%d", self.professionLabel.tag);
    NSLog(@"%@", self.professionLabel.text);
    NSLog(@"%@", self.locationLabel.text);
    NSLog(@"%d", self.locationLabel.tag);
    
    NSDictionary *params = [[NSDictionary alloc] initWithObjectsAndKeys:self.nicknameLabel.text, @"nickname", [NSNumber numberWithInt:self.genderLabel.tag], @"gender", self.birthLabel.text, @"birth", self.featureLabel.text, @"feature", [NSNumber numberWithInt:self.professionLabel.tag], @"professionId", self.professionLabel.text, @"profession", [NSNumber numberWithInt:self.locationLabel.tag], @"cityId", nil];
    ASIFormDataRequest *request = [HttpRequestSender postRequestWithUrl:@"http://test.51juzhai.com/app/ios/profile/save" withParams:params];
    if (_newLogo != nil) {
        CGFloat compression = 0.9f;
        CGFloat maxCompression = 0.1f;
        int maxFileSize = 2*1024*1024;
        
        NSData *imageData = UIImageJPEGRepresentation(_newLogo, compression);
        while ([imageData length] > maxFileSize && compression > maxCompression){
            compression -= 0.1;
            imageData = UIImageJPEGRepresentation(_newLogo, compression);
        }
        [request setData:imageData withFileName:@"logo.jpg" andContentType:@"image/jpeg" forKey:@"logo"];
    }
    [request startSynchronous];
    NSError *error = [request error];
    NSString *errorInfo = SERVER_ERROR_INFO;
    if (!error && [request responseStatusCode] == 200){
        NSString *response = [request responseString];
        NSMutableDictionary *jsonResult = [response JSONValue];
        if([jsonResult valueForKey:@"success"] == [NSNumber numberWithBool:YES]){
            //保存成功
            _saveButton.enabled = NO;
            [[UserContext getUserView] updateUserInfo:[jsonResult valueForKey:@"result"]];
            hud.customView = [[UIImageView alloc] initWithImage:[UIImage imageNamed:@"37x-Checkmark.png"]];
            hud.mode = MBProgressHUDModeCustomView;
            hud.labelText = @"保存成功";
            sleep(1);
            return;
        }else{
            errorInfo = [jsonResult valueForKey:@"errorInfo"];
        }
    }else{
        NSLog(@"error: %@", [request responseStatusMessage]);
    }
    [MessageShow error:errorInfo onView:self.navigationController.view];
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
            return 3;
            break;
    }
    return 0;
}

@end
