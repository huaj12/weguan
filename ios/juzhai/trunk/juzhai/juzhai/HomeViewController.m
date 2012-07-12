//
//  HomeViewController.m
//  juzhai
//
//  Created by JiaJun Wu on 12-6-14.
//  Copyright (c) 2012年 __MyCompanyName__. All rights reserved.
//

#import "HomeViewController.h"
#import "Constant.h"
#import <QuartzCore/QuartzCore.h>
#import "UserView.h"
#import "SDWebImage/UIImageView+WebCache.h"
#import "ASIHTTPRequest.h"
#import "HttpRequestSender.h"
#import "SBJson.h"
#import "MBProgressHUD.h"
#import "JZData.h"
#import "PostView.h"
#import "PostListCell.h"
#import "PostDetailViewController.h"
#import "ProfileSettingViewController.h"
#import "UserContext.h"
#import "Pager.h"
#import "InterestUserViewController.h"
#import "PagerCell.h"
#import "SendPostBarButtonItem.h"
#import "MessageShow.h"
#import "UrlUtils.h"

@interface HomeViewController ()

@end

@implementation HomeViewController

@synthesize userView = _userView;
@synthesize logoView;
@synthesize nicknameLabel;
@synthesize interestUserCountButton;
@synthesize interestMeCountButton;
@synthesize sendPostButton;
@synthesize postTableView;
@synthesize editorButton;
@synthesize postCountLabel;
@synthesize interestButton;
@synthesize cancelInterestButton;

- (id)initWithNibName:(NSString *)nibNameOrNil bundle:(NSBundle *)nibBundleOrNil
{
    self = [super initWithNibName:nibNameOrNil bundle:nibBundleOrNil];
    if (self) {
        // Custom initialization
    }
    return self;
}

- (void)showContent
{
    //    logoView.image = [UIImage imageNamed:USER_DEFAULT_LOGO];
    
    [postTableView reloadData];
    //    postTableView.hidden = YES;
    
    //    UILabel * label = [[UILabel alloc] init];
    //    label.frame = CGRectMake(10, 7, postTableView.bounds.size.width, 11);
    //    label.backgroundColor = [UIColor clearColor];
    //    label.font=[UIFont fontWithName:DEFAULT_FONT_FAMILY size:11];
    //    label.textColor = [UIColor grayColor];
    //    label.text = @"共 12 条拒宅";
    //    
    //    UIView * sectionView = [[UIView alloc] initWithFrame:CGRectMake(0, 0, postTableView.bounds.size.width, 25)];
    //    [sectionView setBackgroundColor: [UIColor colorWithPatternImage: [UIImage imageNamed: @"my_jz_title_bg.png"]]];
    //    [sectionView addSubview:label];
    //    
    //    postTableView.tableHeaderView = sectionView;
}

- (void) loadListDataWithPage:(NSInteger)page{
    if(page <= 0){
        page = 1;
    }
    MBProgressHUD *hud = [MBProgressHUD showHUDAddedTo:self.postTableView animated:YES];
    hud.labelText = @"加载中...";
    dispatch_async(dispatch_get_global_queue(DISPATCH_QUEUE_PRIORITY_HIGH, 0), ^{
        NSMutableDictionary *params = [[NSMutableDictionary alloc] initWithObjectsAndKeys:[NSNumber numberWithInt:page], @"page", nil];
        if (!_isMe) {
            [params setValue:[NSNumber numberWithInt:_userView.uid.intValue] forKey:@"uid"];
        }
        __block ASIHTTPRequest *_request = [HttpRequestSender getRequestWithUrl:[UrlUtils urlStringWithUri:@"home"] withParams:params];
        __unsafe_unretained ASIHTTPRequest *request = _request;
        [request setCompletionBlock:^{
            // Use when fetching text data
            [MBProgressHUD hideHUDForView:self.postTableView animated:YES];
            NSString *responseString = [request responseString];
            NSMutableDictionary *jsonResult = [responseString JSONValue];
            if([jsonResult valueForKey:@"success"] == [NSNumber numberWithBool:YES]){
                NSDictionary *result = [jsonResult valueForKey:@"result"];
                
                NSDictionary *pagerInfo = [result valueForKey:@"pager"];
                if(_data == nil){
                    _data = [[JZData alloc] initWithPager:[Pager pagerConvertFromDictionary:pagerInfo]];
                }else {
                    [_data.pager updatePagerFromDictionary:pagerInfo];
                    if(_data.pager.currentPage == 1){
                        [_data clear];
                    }
                }
                NSMutableArray *postViewList = [result valueForKey:@"postViewList"];
                for (int i = 0; i < postViewList.count; i++) {
                    PostView *postView = [PostView convertFromDictionary:[postViewList objectAtIndex:i]];
                    [_data addObject:postView withIdentity:postView.postId];
                }
                postCountLabel.text = [NSString stringWithFormat:TABLE_HEAD_TITLE, _data.pager.totalResults];
                if (_data.count == 0) {
                    postTableView.hidden = YES;
                }else {
                    postTableView.hidden = NO;
                    [postTableView reloadData];
                }
            }
        }];
        [request setFailedBlock:^{
            [MBProgressHUD hideHUDForView:self.postTableView animated:YES];
        }];
        [request startAsynchronous];
    });
}

- (void)viewDidLoad
{
    [super viewDidLoad];
    
    postTableView.delegate = self;
    postTableView.dataSource = self;
    postTableView.separatorColor = [UIColor greenColor];
    if (_userView != nil) {
        postTableView.superview.frame = CGRectMake(postTableView.superview.frame.origin.x, postTableView.superview.frame.origin.y, postTableView.superview.frame.size.width, postTableView.superview.frame.size.height + 50);
    } else {
        self.navigationItem.leftBarButtonItem = [[SendPostBarButtonItem alloc] initWithOwnerViewController:self];
    }
    UIView *view = [UIView new];
    view.backgroundColor = [UIColor clearColor];
    [postTableView setTableFooterView:view];
    
    postCountLabel.backgroundColor = [UIColor colorWithPatternImage: [UIImage imageNamed: TABLE_HEAD_BG_IMAGE]];
    postCountLabel.font=[UIFont fontWithName:DEFAULT_FONT_FAMILY size:12];
    postCountLabel.textColor = [UIColor colorWithRed:0.60f green:0.60f blue:0.60f alpha:1.00f];
    
    //设置分割线
    self.postTableView.separatorColor = [UIColor colorWithRed:0.78f green:0.78f blue:0.78f alpha:1.00f];
    self.postTableView.backgroundColor = [UIColor colorWithRed:0.93f green:0.93f blue:0.93f alpha:1.00f];
    
    [self loadListDataWithPage:1];
}

- (void)viewWillAppear:(BOOL)animated{
    if (_userView == nil) {
        _userView = [UserContext getUserView];
    }
    _isMe = _userView.uid.intValue == [UserContext getUserView].uid.intValue;
    if (!_isMe) {
        self.title = [NSString stringWithFormat:@"%@的拒宅", _userView.nickname];
        self.sendPostButton.enabled = NO;
        self.sendPostButton.hidden = YES;
    }else {
        self.title = [NSString stringWithFormat:@"我的拒宅"];
    }
    SDWebImageManager *manager = [SDWebImageManager sharedManager];
    NSURL *imageURL = [NSURL URLWithString:(_isMe ? _userView.rawLogo : _userView.logo)];
    [manager downloadWithURL:imageURL delegate:self options:0 success:^(UIImage *image) {
        logoView.image = image;
        logoView.layer.shouldRasterize = YES;
        logoView.layer.masksToBounds = YES;
        logoView.layer.cornerRadius = 5.0;
    } failure:nil];
    
    nicknameLabel.font = [UIFont fontWithName:DEFAULT_FONT_FAMILY size:18.0];
    if(_userView.gender.intValue == 0){
        nicknameLabel.textColor = [UIColor colorWithRed:1.00f green:0.40f blue:0.60f alpha:1.00f];
    }else {
        nicknameLabel.textColor = [UIColor blueColor];
    }
    nicknameLabel.text = _userView.nickname;
    
    if (_isMe) {
        UIImage *interestCountImage = [[UIImage imageNamed:INTEREST_COUNT_BUTTON_IMAGE]stretchableImageWithLeftCapWidth:INTEREST_COUNT_BUTTON_CAP_WIDTH topCapHeight:0.0];
        UIImage *interestCountHighlightImage = [[UIImage imageNamed:INTEREST_COUNT_BUTTON_HIGHLIGHT_IMAGE]stretchableImageWithLeftCapWidth:INTEREST_COUNT_BUTTON_CAP_WIDTH topCapHeight:0.0];
        
        interestUserCountButton.titleLabel.font = [UIFont fontWithName:DEFAULT_FONT_FAMILY size:12.0];
        [interestUserCountButton setTitle:[NSString stringWithFormat:@"关注 %d", _userView.interestUserCount.intValue] forState:UIControlStateNormal];
        [interestUserCountButton setTitleColor:[UIColor colorWithRed:0.40f green:0.40f blue:0.40f alpha:1.00f] forState:UIControlStateNormal];
        [interestUserCountButton setTitleColor:[UIColor colorWithRed:0.40f green:0.40f blue:0.40f alpha:1.00f] forState:UIControlStateHighlighted];
        CGSize interestUserCountButtonSize = [[interestUserCountButton titleForState:UIControlStateNormal] sizeWithFont:interestUserCountButton.titleLabel.font];
        interestUserCountButton.frame = CGRectMake(interestUserCountButton.frame.origin.x, interestUserCountButton.frame.origin.y, interestUserCountButtonSize.width + 24.0, interestUserCountButton.frame.size.height);
        [interestUserCountButton setBackgroundImage:interestCountImage forState:UIControlStateNormal];
        [interestUserCountButton setBackgroundImage:interestCountHighlightImage forState:UIControlStateHighlighted];
        
        interestMeCountButton.titleLabel.font = [UIFont fontWithName:DEFAULT_FONT_FAMILY size:12.0];
        [interestMeCountButton setTitle:[NSString stringWithFormat:@"粉丝 %d", _userView.interestMeCount.intValue] forState:UIControlStateNormal];
        [interestMeCountButton setTitleColor:[UIColor colorWithRed:0.40f green:0.40f blue:0.40f alpha:1.00f] forState:UIControlStateNormal];
        [interestMeCountButton setTitleColor:[UIColor colorWithRed:0.40f green:0.40f blue:0.40f alpha:1.00f] forState:UIControlStateHighlighted];
        CGSize interestMeCountButtonSize = [[interestMeCountButton titleForState:UIControlStateNormal] sizeWithFont:interestMeCountButton.titleLabel.font];
        interestMeCountButton.frame = CGRectMake(interestUserCountButton.frame.origin.x + interestUserCountButton.frame.size.width + 10.0, interestMeCountButton.frame.origin.y, interestMeCountButtonSize.width + 24.0, interestMeCountButton.frame.size.height);
        [interestMeCountButton setBackgroundImage:interestCountImage forState:UIControlStateNormal];
        [interestMeCountButton setBackgroundImage:interestCountHighlightImage forState:UIControlStateHighlighted];
        
        interestUserCountButton.hidden = NO;
        interestUserCountButton.enabled = YES;
        interestMeCountButton.hidden = NO;
        interestMeCountButton.enabled = YES;
        editorButton.hidden = NO;
        editorButton.enabled = YES;
        
        interestButton.hidden = YES;
        interestButton.enabled = NO;
        cancelInterestButton.hidden = YES;
        cancelInterestButton.enabled = NO;
    } else {
        interestUserCountButton.hidden = YES;
        interestUserCountButton.enabled = NO;
        interestMeCountButton.hidden = YES;
        interestMeCountButton.enabled = NO;
        editorButton.hidden = YES;
        editorButton.enabled = NO;
        
        if ([self.userView.hasInterest boolValue]) {
            cancelInterestButton.hidden = NO;
            cancelInterestButton.enabled = YES;
            interestButton.hidden = YES;
            interestButton.enabled = NO;
        }else {            
            cancelInterestButton.hidden = YES;
            cancelInterestButton.enabled = NO;
            interestButton.hidden = NO;
            interestButton.enabled = YES;
        }
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

- (IBAction)editor:(id)sender{
    if (nil == _profileSettingViewController) {
        _profileSettingViewController = [[ProfileSettingViewController alloc] initWithStyle:UITableViewStyleGrouped];
        _profileSettingViewController.hidesBottomBarWhenPushed = YES;
    }
    [_profileSettingViewController initUserView:_userView];
    [self.navigationController pushViewController:_profileSettingViewController animated:YES];
}

- (IBAction)goInterestList:(id)sender{
    if (_userView.interestUserCount.intValue > 0) {
        if (nil == _interestUserViewController) {
        _interestUserViewController = [[InterestUserViewController alloc] initWithStyle:UITableViewStylePlain];
        _interestUserViewController.hidesBottomBarWhenPushed = YES;
    }
        _interestUserViewController.isInterest = YES;
        [self.navigationController pushViewController:_interestUserViewController animated:YES];
    }
}

- (IBAction)goInterestMeList:(id)sender{
    if (_userView.interestMeCount.intValue > 0) {
        if (nil == _interestMeUserViewController) {
            _interestMeUserViewController = [[InterestUserViewController alloc] initWithStyle:UITableViewStylePlain];
            _interestMeUserViewController.hidesBottomBarWhenPushed = YES;
        }
        _interestMeUserViewController.isInterest = NO;
        [self.navigationController pushViewController:_interestMeUserViewController animated:YES];
    }
}

- (IBAction)interestUser:(id)sender
{
    MBProgressHUD *hud = [MBProgressHUD showHUDAddedTo:self.view animated:YES];
    hud.labelText = @"操作中...";
    dispatch_async(dispatch_get_global_queue(DISPATCH_QUEUE_PRIORITY_HIGH, 0), ^{
        NSDictionary *params = [[NSDictionary alloc] initWithObjectsAndKeys:_userView.uid, @"uid", nil];
        __unsafe_unretained __block ASIFormDataRequest *request = [HttpRequestSender postRequestWithUrl:[UrlUtils urlStringWithUri:@"interest"] withParams:params];
//        __unsafe_unretained ASIHTTPRequest *request = _request;
        [request setCompletionBlock:^{
            [MBProgressHUD hideHUDForView:self.view animated:YES];
            NSString *responseString = [request responseString];
            NSMutableDictionary *jsonResult = [responseString JSONValue];
            if([jsonResult valueForKey:@"success"] == [NSNumber numberWithBool:YES]){
                _userView.hasInterest = [NSNumber numberWithInt:1];
                interestButton.hidden = YES;
                interestButton.enabled = NO;
                cancelInterestButton.hidden = NO;
                cancelInterestButton.enabled = YES;
                hud.customView = [[UIImageView alloc] initWithImage:[UIImage imageNamed:@"37x-Checkmark.png"]];
                hud.mode = MBProgressHUDModeCustomView;
                hud.labelText = @"关注成功";
                [hud hide:YES afterDelay:1];
                return;
            }
            NSString *errorInfo = [jsonResult valueForKey:@"errorInfo"];
            NSLog(@"%@", errorInfo);
            if (errorInfo == nil || [errorInfo isEqual:[NSNull null]] || [errorInfo isEqualToString:@""]) {
                errorInfo = SERVER_ERROR_INFO;
            }
            [MBProgressHUD hideHUDForView:self.view animated:YES];
            [MessageShow error:errorInfo onView:self.view];
        }];
        [request setFailedBlock:^{
            [MBProgressHUD hideHUDForView:self.view animated:YES];
            [MessageShow error:SERVER_ERROR_INFO onView:self.view];
        }];
        [request startAsynchronous];
    });
}

- (IBAction)cancelInterestUser:(id)sender
{
    UIAlertView *alertView = [[UIAlertView alloc] initWithTitle:nil message:@"确定取消关注？" delegate:self cancelButtonTitle:@"返回" otherButtonTitles:@"确定", nil];
    [alertView show];
}

#pragma mark -
#pragma mark Alert Delegate
- (void)alertView:(UIAlertView *)alertView didDismissWithButtonIndex:(NSInteger)buttonIndex
{
    if (buttonIndex != alertView.cancelButtonIndex) {
        MBProgressHUD *hud = [MBProgressHUD showHUDAddedTo:self.view animated:YES];
        hud.labelText = @"操作中...";
        dispatch_async(dispatch_get_global_queue(DISPATCH_QUEUE_PRIORITY_HIGH, 0), ^{
            NSDictionary *params = [[NSDictionary alloc] initWithObjectsAndKeys:_userView.uid, @"uid", nil];
            __unsafe_unretained __block ASIFormDataRequest *request = [HttpRequestSender postRequestWithUrl:[UrlUtils urlStringWithUri:@"removeInterest"] withParams:params];
            //        __unsafe_unretained ASIHTTPRequest *request = _request;
            [request setCompletionBlock:^{
                [MBProgressHUD hideHUDForView:self.view animated:YES];
                NSString *responseString = [request responseString];
                NSMutableDictionary *jsonResult = [responseString JSONValue];
                if([jsonResult valueForKey:@"success"] == [NSNumber numberWithBool:YES]){
                    _userView.hasInterest = [NSNumber numberWithInt:0];
                    cancelInterestButton.hidden = YES;
                    cancelInterestButton.enabled = NO;
                    interestButton.hidden = NO;
                    interestButton.enabled = YES;
                    hud.customView = [[UIImageView alloc] initWithImage:[UIImage imageNamed:@"37x-Checkmark.png"]];
                    hud.mode = MBProgressHUDModeCustomView;
                    hud.labelText = @"取消成功";
                    [hud hide:YES afterDelay:1];
                    return;
                }
                NSString *errorInfo = [jsonResult valueForKey:@"errorInfo"];
                NSLog(@"%@", errorInfo);
                if (errorInfo == nil || [errorInfo isEqual:[NSNull null]] || [errorInfo isEqualToString:@""]) {
                    errorInfo = SERVER_ERROR_INFO;
                }
                [MBProgressHUD hideHUDForView:self.view animated:YES];
                [MessageShow error:errorInfo onView:self.view];
            }];
            [request setFailedBlock:^{
                [MBProgressHUD hideHUDForView:self.view animated:YES];
                [MessageShow error:SERVER_ERROR_INFO onView:self.view];
            }];
            [request startAsynchronous];
        });
    }
}

#pragma mark -
#pragma mark Table View Data Source

- (NSInteger) tableView:(UITableView *)tableView numberOfRowsInSection:(NSInteger)section{
    int count = _data.count;
    if (_data.pager.hasNext) {
        count += 1;
    }
    return count;
}

- (UITableViewCell *)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath{
    if (_data.pager.hasNext && indexPath.row == [_data count]) {
        return [PagerCell dequeueReusablePagerCell:tableView];
    }
    NSString *postListCellIdentifier = @"PostListCellIdentifier";
    PostListCell *cell = [tableView dequeueReusableCellWithIdentifier:postListCellIdentifier];
    if(cell == nil){
        cell = [PostListCell cellFromNib];
    }
    
    PostView *postView = (PostView *)[_data objectAtIndex:indexPath.row];
    [cell redrawn:postView];
    [cell sizeToFit];
    return cell;
}

//- (NSString *) tableView:(UITableView *)tableView titleForHeaderInSection:(NSInteger)section{
//    return [NSString stringWithFormat:TABLE_HEAD_TITLE, _data.pager.totalResults];
//}

#pragma mark -
#pragma mark Table View Deletage

- (CGFloat) tableView:(UITableView *)tableView heightForRowAtIndexPath:(NSIndexPath *)indexPath{
    if (_data.pager.hasNext && indexPath.row == [_data count]) {
        return PAGER_CELL_HEIGHT;
    }else {
        return [PostListCell heightForCell:[_data objectAtIndex:indexPath.row]];
    }
}

//- (CGFloat) tableView:(UITableView *)tableView heightForHeaderInSection:(NSInteger)section{
//    return TABLE_HEAD_HEIGHT;
//}

//- (UIView *) tableView:(UITableView *)tableView viewForHeaderInSection:(NSInteger)section{
//    
//    NSString *sectionTitle = [self tableView:tableView titleForHeaderInSection:section];
//    if (sectionTitle == nil) {
//        return  nil;
//    }
//    
//    UILabel * label = [[UILabel alloc] init];
//    label.frame = CGRectMake(10, 7, tableView.bounds.size.width, 11);
//    label.backgroundColor = [UIColor clearColor];
//    label.font=[UIFont fontWithName:DEFAULT_FONT_FAMILY size:11];
//    label.textColor = [UIColor grayColor];
//    label.text = sectionTitle;
//    
//    UIView * sectionView = [[UIView alloc] initWithFrame:CGRectMake(0, 0, tableView.bounds.size.width, TABLE_HEAD_HEIGHT)];
//    [sectionView setBackgroundColor: [UIColor colorWithPatternImage: [UIImage imageNamed: TABLE_HEAD_BG_IMAGE]]];
//    [sectionView addSubview:label];
//    return sectionView;
//}

-(void) tableView:(UITableView *)tableView didSelectRowAtIndexPath:(NSIndexPath *)indexPath{
    if (indexPath.row < [_data count]) {
        PostDetailViewController *postDetailViewController = [[PostDetailViewController alloc] initWithNibName:@"PostDetailViewController" bundle:nil];
        postDetailViewController.hidesBottomBarWhenPushed = YES;
        _userView.post = [_data objectAtIndex:indexPath.row];
        postDetailViewController.userView = _userView;
        [self.navigationController pushViewController:postDetailViewController animated:YES];
    } else {
        [self loadListDataWithPage:_data.pager.currentPage + 1];
    }
    [tableView deselectRowAtIndexPath:indexPath animated:YES];
}

@end
