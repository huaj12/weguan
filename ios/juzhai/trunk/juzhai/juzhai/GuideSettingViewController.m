//
//  GuideSettingViewController.m
//  juzhai
//
//  Created by JiaJun Wu on 12-6-26.
//  Copyright (c) 2012年 __MyCompanyName__. All rights reserved.
//

#import "GuideSettingViewController.h"
#import "LoginService.h"
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
    if ([self.locationLabel.text isEqualToString:@""] || self.locationLabel.tag <= 0) {
        [MessageShow error:@"请选择所在地" onView:self.navigationController.view];
        return NO;
    }
    if ([self.professionLabel.text isEqualToString:@""]) {
        [MessageShow error:@"请选择职业" onView:self.navigationController.view];
        return NO;
    }
    return YES;
}

- (NSString *)postUrl{
    return @"http://test.51juzhai.com/app/ios/profile/guide";
}

- (void)saveSuccess{
    UIViewController *startController = [LoginService loginTurnToViewController];
    if(startController){
        self.view.window.rootViewController = startController;
        [self.view.window makeKeyAndVisible];
    }
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
