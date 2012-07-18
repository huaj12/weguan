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
#import "Constant.h"
#import "MBProgressHUD.h"
#import "HttpRequestSender.h"
#import "SBJson.h"
#import "Pager.h"
#import "HomeViewController.h"
#import "PagerCell.h"
#import "UrlUtils.h"
#import "CheckNetwork.h"

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
    if (self.isInterest) {
        self.title = @"我的关注";
    } else {
        self.title = @"我的粉丝";
    }
    
    UIView *view = [UIView new];
    view.backgroundColor = [UIColor clearColor];
    [self.tableView setTableFooterView:view];
    
    self.tableView.backgroundColor = [UIColor colorWithPatternImage:[UIImage imageNamed:APP_BG_IMG]];
    self.tableView.separatorColor = [UIColor colorWithRed:0.71f green:0.71f blue:0.71f alpha:1.00f];
    [super viewDidLoad];
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
    if ([CheckNetwork isExistenceNetwork]) {
        if(page <= 0)
            page = 1;
        NSMutableDictionary *params = [[NSMutableDictionary alloc] initWithObjectsAndKeys:[NSNumber numberWithInt:page], @"page", nil];
        NSString *requestUrl = self.isInterest ? [UrlUtils urlStringWithUri:@"interestList"] : [UrlUtils urlStringWithUri:@"interestMeList"];
        
        __unsafe_unretained __block ASIHTTPRequest *request = [HttpRequestSender getRequestWithUrl:requestUrl withParams:params];
        [request setCompletionBlock:^{
            // Use when fetching text data
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
                    UserView *userView = [UserView convertFromDictionary:[userViewList objectAtIndex:i]];
                    [_data addObject:userView withIdentity:userView.uid];
                }
                [self doneLoadingTableViewData];
            }
        }];
        [request setFailedBlock:^{
            [self doneLoadingTableViewData];
        }];
        [request startAsynchronous];
    }
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
        return [PagerCell dequeueReusablePagerCell:tableView];
    }
    static NSString *InterestUserCellIdentifier = @"InterestUserCellCell";
    UITableViewCell *cell = [tableView dequeueReusableCellWithIdentifier:InterestUserCellIdentifier];
    if (!cell) {
        cell = [[UITableViewCell alloc] initWithStyle:UITableViewCellStyleDefault reuseIdentifier:InterestUserCellIdentifier];
        cell.accessoryType = UITableViewCellAccessoryDisclosureIndicator;
        
        UIView *separatorView = [[UIView alloc] initWithFrame:CGRectMake(0, 0, cell.frame.size.width, 1)];
        separatorView.backgroundColor = [UIColor whiteColor];
        [cell addSubview:separatorView];
        
        UIImageView *logo = [[UIImageView alloc] initWithFrame:CGRectMake(10, 10, 40, 40)];
        logo.tag = INTEREST_USER_LOGO_TAG;
        [cell addSubview:logo];
        
        UILabel *nicknameLabel = [[UILabel alloc] initWithFrame:CGRectMake(60, 12, 150, 14)];
        nicknameLabel.backgroundColor = [UIColor clearColor];
        nicknameLabel.tag = INTEREST_USER_NICKNAME_TAG;
        [cell addSubview:nicknameLabel];
        
        UILabel *infoLabel = [[UILabel alloc] initWithFrame:CGRectMake(60, 35, 150, 13)];
        infoLabel.backgroundColor = [UIColor clearColor];
        infoLabel.tag = INTEREST_USER_INFO_TAG;
        [cell addSubview:infoLabel];
        
        UIView *selectBgColorView = [[UIView alloc] init];
        selectBgColorView.backgroundColor = [UIColor whiteColor];
        cell.selectedBackgroundView = selectBgColorView;
        cell.backgroundColor = [UIColor clearColor];
    }
    UserView *userView = [_data objectAtIndex:indexPath.row];
    UIImageView *logo = (UIImageView *)[cell viewWithTag:INTEREST_USER_LOGO_TAG];
    logo.image = [UIImage imageNamed:FACE_LOADING_IMG];
    SDWebImageManager *manager = [SDWebImageManager sharedManager];
    NSURL *imageURL = [NSURL URLWithString:userView.smallLogo];
    [manager downloadWithURL:imageURL delegate:self options:0 success:^(UIImage *image) {
        logo.image = image;
        logo.layer.shouldRasterize = YES;
        logo.layer.masksToBounds = YES;
        logo.layer.cornerRadius = 5.0;
    } failure:nil];
    
    UILabel *nicknameLabel = (UILabel *)[cell viewWithTag:INTEREST_USER_NICKNAME_TAG];
    nicknameLabel.font = [UIFont fontWithName:DEFAULT_FONT_FAMILY size:14.0];
    if(userView.gender.intValue == 0){
        nicknameLabel.textColor = [UIColor colorWithRed:1.00f green:0.40f blue:0.60f alpha:1.00f];
    }else {
        nicknameLabel.textColor = [UIColor colorWithRed:0.24f green:0.51f blue:0.76f alpha:1.00f];
    }
    nicknameLabel.text = userView.nickname;
    
    UILabel *infoLabel = (UILabel *)[cell viewWithTag:INTEREST_USER_INFO_TAG];
    infoLabel.font = [UIFont fontWithName:DEFAULT_FONT_FAMILY size:13.0];
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
        return PAGER_CELL_HEIGHT;
    }else {
        return 60.0;
    }
}

- (void)tableView:(UITableView *)tableView didSelectRowAtIndexPath:(NSIndexPath *)indexPath
{
    if (indexPath.row < [_data count]) {
        HomeViewController *homeViewController = [[HomeViewController alloc] initWithNibName:@"HomeViewController" bundle:nil];
        homeViewController.hidesBottomBarWhenPushed = YES;
        homeViewController.userView = [_data objectAtIndex:indexPath.row];
        [self.navigationController pushViewController:homeViewController animated:YES];
    } else {
        [self loadListDataWithPage:_data.pager.currentPage + 1];
    }
    [tableView deselectRowAtIndexPath:indexPath animated:YES];
}

@end
