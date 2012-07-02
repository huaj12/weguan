//
//  InterestUserViewController.m
//  juzhai
//
//  Created by JiaJun Wu on 12-6-30.
//  Copyright (c) 2012年 __MyCompanyName__. All rights reserved.
//

#import <QuartzCore/QuartzCore.h>
#import "InterestUserViewController.h"
#import "JZData.h"
#import "SDWebImage/UIImageView+WebCache.h"
#import "UserView.h"
#import "BaseData.h"
#import "MBProgressHUD.h"
#import "HttpRequestSender.h"
#import "SBJson.h"
#import "Pager.h"
#import "HomeViewController.h"

@interface InterestUserViewController ()

@end

@implementation InterestUserViewController

@synthesize isInterest;

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

    // Uncomment the following line to preserve selection between presentations.
    // self.clearsSelectionOnViewWillAppear = NO;
 
    // Uncomment the following line to display an Edit button in the navigation bar for this view controller.
    // self.navigationItem.rightBarButtonItem = self.editButtonItem;
    
    if (self.isInterest) {
        self.title = @"我的关注";
    } else {
        self.title = @"我的粉丝";
    }
    
    UIView *view = [UIView new];
    view.backgroundColor = [UIColor clearColor];
    [self.tableView setTableFooterView:view];
    
    [self loadListDataWithPage:1];
}

- (void)viewDidUnload
{
    [super viewDidUnload];
}

- (BOOL)shouldAutorotateToInterfaceOrientation:(UIInterfaceOrientation)interfaceOrientation
{
    return (interfaceOrientation == UIInterfaceOrientationPortrait);
}

- (void) loadListDataWithPage:(NSInteger)page{
    if(page <= 0)
        page = 1;
    MBProgressHUD *hud = [MBProgressHUD showHUDAddedTo:self.navigationController.view animated:YES];
    hud.labelText = @"加载中...";
    dispatch_async(dispatch_get_global_queue(DISPATCH_QUEUE_PRIORITY_HIGH, 0), ^{
        sleep(1);
        
        NSMutableDictionary *params = [[NSMutableDictionary alloc] initWithObjectsAndKeys:[NSNumber numberWithInt:page], @"page", nil];
        NSString *requestUrl = self.isInterest ? @"http://test.51juzhai.com/app/ios/interestList" : @"http://test.51juzhai.com/app/ios/interestMeList";
        
        __block ASIHTTPRequest *_request = [HttpRequestSender getRequestWithUrl:requestUrl withParams:params];
        __unsafe_unretained ASIHTTPRequest *request = _request;
        [request setCompletionBlock:^{
            // Use when fetching text data
            [MBProgressHUD hideHUDForView:self.navigationController.view animated:YES];
            NSString *responseString = [request responseString];
            NSMutableDictionary *jsonResult = [responseString JSONValue];
            if([jsonResult valueForKey:@"success"] == [NSNumber numberWithBool:YES]){
                //reload
                NSDictionary *pagerInfo = [[jsonResult valueForKey:@"result"] valueForKey:@"pager"];
                if(_data == nil){
                    _data = [[JZData alloc] initWithPager:[Pager pagerConvertFromDictionary:pagerInfo]];
                }else {
                    [_data.pager updatePagerFromDictionary:pagerInfo];
                    if(_data.pager.currentPage == 1){
                        [_data clear];
                    }
                }
                NSMutableArray *userViewList = [[jsonResult valueForKey:@"result"] valueForKey:@"userViewList"];
                for (int i = 0; i < userViewList.count; i++) {
                    UserView *userView = [UserView userConvertFromDictionary:[userViewList objectAtIndex:i]];
                    [_data addObject:userView withIdentity:userView.uid];
                }
                [self.tableView reloadData];
            }
        }];
        [request setFailedBlock:^{
            [MBProgressHUD hideHUDForView:self.navigationController.view animated:YES];
        }];
        [request startAsynchronous];
    });
}

-(IBAction)nextPage:(id)sender{
    UIButton *moreButton = (UIButton *)sender;
    UITableViewCell *cell = (UITableViewCell *)moreButton.superview;
    [moreButton setHidden:YES];
    UIActivityIndicatorView *spinner = (UIActivityIndicatorView *)[cell viewWithTag:2];
    [spinner startAnimating];
    [self loadListDataWithPage:_data.pager.currentPage + 1];
}

#pragma mark - Table view data source

- (NSInteger)tableView:(UITableView *)tableView numberOfRowsInSection:(NSInteger)section
{
    int count = _data.count;
    if (_data.pager.hasNext) {
        count += 1;
    }
    return count;
}

- (UITableViewCell *)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath
{
    if (_data.pager.hasNext && indexPath.row == [_data count]) {
        static NSString *PagerCellIdentifier = @"PagerCellIdentifier";
        UITableViewCell *cell = [tableView dequeueReusableCellWithIdentifier:PagerCellIdentifier];
        if(cell == nil){
            cell = [[UITableViewCell alloc]initWithStyle:UITableViewCellStyleDefault reuseIdentifier:PagerCellIdentifier];
            cell.selectionStyle = UITableViewCellSelectionStyleNone;
            
            UIButton *moreButton = [UIButton buttonWithType:UIButtonTypeCustom];
            moreButton.frame = CGRectMake(0, 10, 320, 30);
            [moreButton setBackgroundImage:[UIImage imageNamed:@"idea_more_btn.png"] forState:UIControlStateNormal];
            [moreButton setTitle:@"查看更多" forState:UIControlStateNormal];
            [moreButton addTarget:self action:@selector(nextPage:) forControlEvents:UIControlEventTouchUpInside];
            moreButton.tag = 1;
            [cell addSubview:moreButton];
            
            UIActivityIndicatorView *spinner = [[UIActivityIndicatorView alloc] initWithActivityIndicatorStyle:UIActivityIndicatorViewStyleGray];
            spinner.tag = 2;
            [cell addSubview:spinner];
            [spinner setCenter:CGPointMake(160, 25)];
        }else {
            UIButton *button = (UIButton *)[cell viewWithTag:1];
            [button setHidden:NO];
            UIActivityIndicatorView *spinner = (UIActivityIndicatorView *)[cell viewWithTag:2];
            [spinner stopAnimating];
        }
        return cell;
    }
    
    static NSString *InterestUserCellIdentifier = @"InterestUserCellCell";
    UITableViewCell *cell = [tableView dequeueReusableCellWithIdentifier:InterestUserCellIdentifier];
    if (!cell) {
        cell = [[UITableViewCell alloc] initWithStyle:UITableViewCellStyleDefault reuseIdentifier:InterestUserCellIdentifier];
        cell.accessoryType = UITableViewCellAccessoryDisclosureIndicator;
        
        UIImageView *logo = [[UIImageView alloc] initWithFrame:CGRectMake(10, 10, 40, 40)];
        logo.tag = INTEREST_USER_LOGO_TAG;
        [cell addSubview:logo];
        
        UILabel *nicknameLabel = [[UILabel alloc] initWithFrame:CGRectMake(60, 12, 150, 12)];
        nicknameLabel.tag = INTEREST_USER_NICKNAME_TAG;
        [cell addSubview:nicknameLabel];
        
        UILabel *infoLabel = [[UILabel alloc] initWithFrame:CGRectMake(60, 34, 150, 12)];
        infoLabel.tag = INTEREST_USER_INFO_TAG;
        [cell addSubview:infoLabel];
    }
    UserView *userView = [_data objectAtIndex:indexPath.row];
    UIImageView *logo = (UIImageView *)[cell viewWithTag:INTEREST_USER_LOGO_TAG];
    logo.image = [UIImage imageNamed:INTEREST_USER_DEFAULT_PIC];
    SDWebImageManager *manager = [SDWebImageManager sharedManager];
    NSURL *imageURL = [NSURL URLWithString:userView.logo];
    [manager downloadWithURL:imageURL delegate:self options:0 success:^(UIImage *image) {
        logo.image = image;
        logo.layer.shouldRasterize = YES;
        logo.layer.masksToBounds = YES;
        logo.layer.cornerRadius = 3.0;
    } failure:nil];
    
    UILabel *nicknameLabel = (UILabel *)[cell viewWithTag:INTEREST_USER_NICKNAME_TAG];
    nicknameLabel.font = [UIFont fontWithName:DEFAULT_FONT_FAMILY size:12.0];
    if(userView.gender.intValue == 0){
        nicknameLabel.textColor = [UIColor redColor];
    }else {
        nicknameLabel.textColor = [UIColor blueColor];
    }
    nicknameLabel.text = userView.nickname;
    
    UILabel *infoLabel = (UILabel *)[cell viewWithTag:INTEREST_USER_INFO_TAG];
    infoLabel.font = [UIFont fontWithName:DEFAULT_FONT_FAMILY size:12.0];
    infoLabel.textColor = [UIColor grayColor];
    NSMutableString *info = [NSMutableString stringWithCapacity:0];
    if(![userView.birthYear isEqual:[NSNull null]]){
        NSDate *now = [NSDate date];
        NSCalendar *cal = [NSCalendar currentCalendar];
        unsigned int unitFlags = NSYearCalendarUnit;
        NSDateComponents *dd = [cal components:unitFlags fromDate:now];
        int age = [dd year] - userView.birthYear.intValue;
        [info appendFormat:@"%d岁 ", age];
    }
    if(![userView.constellation isEqual:[NSNull null]]){
        [info appendFormat:@"%@", userView.constellation];
    }
    infoLabel.text = info;
    
    return cell;
}

#pragma mark - Table view delegate

- (CGFloat) tableView:(UITableView *)tableView heightForRowAtIndexPath:(NSIndexPath *)indexPath{
    if (_data.pager.hasNext && indexPath.row == [_data count]) {
        return 40.0;
    }else {
        return 60.0;
    }
}

- (void)tableView:(UITableView *)tableView didSelectRowAtIndexPath:(NSIndexPath *)indexPath
{
    if (indexPath.row < [_data count]) {
//        if (_homeViewController == nil) {
            _homeViewController = [[HomeViewController alloc] initWithNibName:@"HomeViewController" bundle:nil];
            _homeViewController.hidesBottomBarWhenPushed = YES;
//        }
        _homeViewController.userView = [_data objectAtIndex:indexPath.row];
        [self.navigationController pushViewController:_homeViewController animated:YES];
    }
    [tableView deselectRowAtIndexPath:indexPath animated:YES];
}

@end
