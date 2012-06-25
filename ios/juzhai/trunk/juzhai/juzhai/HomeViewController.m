//
//  HomeViewController.m
//  juzhai
//
//  Created by JiaJun Wu on 12-6-14.
//  Copyright (c) 2012年 __MyCompanyName__. All rights reserved.
//

#import "HomeViewController.h"
#import "BaseData.h"
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

@interface HomeViewController ()

@end

@implementation HomeViewController

@synthesize logoView;
@synthesize nicknameLabel;
@synthesize interestUserCountButton;
@synthesize interestMeCountButton;
@synthesize sendPostButton;
@synthesize postTableView;

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
    MBProgressHUD *hud = [MBProgressHUD showHUDAddedTo:self.navigationController.view animated:YES];
    hud.labelText = @"加载中...";
    dispatch_async(dispatch_get_global_queue(DISPATCH_QUEUE_PRIORITY_HIGH, 0), ^{
        sleep(1);
        NSMutableDictionary *params = [[NSMutableDictionary alloc] initWithObjectsAndKeys:[NSNumber numberWithInt:page], @"page", nil];
        __block ASIHTTPRequest *_request = [HttpRequestSender getRequestWithUrl:@"http://test.51juzhai.com/app/ios/home" withParams:params];
        __unsafe_unretained ASIHTTPRequest *request = _request;
        [request setCompletionBlock:^{
            // Use when fetching text data
            [MBProgressHUD hideHUDForView:self.navigationController.view animated:YES];
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
                    PostView *postView = [PostView postConvertFromDictionary:[postViewList objectAtIndex:i]];
                    [_data addObject:postView withIdentity:postView.postId];
                }
                [postTableView reloadData];
            }
        }];
        [request setFailedBlock:^{
            [MBProgressHUD hideHUDForView:self.navigationController.view animated:YES];
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
    UIView *view = [UIView new];
    view.backgroundColor = [UIColor clearColor];
    [postTableView setTableFooterView:view];
    [self loadListDataWithPage:1];
}

- (void)viewWillAppear:(BOOL)animated{
    _userView = [UserContext getUserView];
    
    SDWebImageManager *manager = [SDWebImageManager sharedManager];
    NSURL *imageURL = [NSURL URLWithString:_userView.rawLogo];
    [manager downloadWithURL:imageURL delegate:self options:0 success:^(UIImage *image) {
        logoView.image = image;
        logoView.layer.shouldRasterize = YES;
        logoView.layer.masksToBounds = YES;
        logoView.layer.cornerRadius = 3.0;
    } failure:nil];
    
    nicknameLabel.font = [UIFont fontWithName:DEFAULT_FONT_FAMILY size:18.0];
    if(_userView.gender.intValue == 0){
        nicknameLabel.textColor = [UIColor redColor];
    }else {
        nicknameLabel.textColor = [UIColor blueColor];
    }
    nicknameLabel.text = _userView.nickname;
    
    UIImage *interestCountImage = [[UIImage imageNamed:INTEREST_COUNT_BUTTON_IMAGE]stretchableImageWithLeftCapWidth:INTEREST_COUNT_BUTTON_CAP_WIDTH topCapHeight:0.0];
    
    interestUserCountButton.titleLabel.font = [UIFont fontWithName:DEFAULT_FONT_FAMILY size:12.0];
    [interestUserCountButton setTitle:[NSString stringWithFormat:@"关注 %d", _userView.interestUserCount.intValue] forState:UIControlStateNormal];
    CGSize interestUserCountButtonSize = [[interestUserCountButton titleForState:UIControlStateNormal] sizeWithFont:interestUserCountButton.titleLabel.font];
    interestUserCountButton.frame = CGRectMake(interestUserCountButton.frame.origin.x, interestUserCountButton.frame.origin.y, interestUserCountButtonSize.width + 24.0, interestUserCountButton.frame.size.height);
    [interestUserCountButton setBackgroundImage:interestCountImage forState:UIControlStateNormal];
    
    interestMeCountButton.titleLabel.font = [UIFont fontWithName:DEFAULT_FONT_FAMILY size:12.0];
    [interestMeCountButton setTitle:[NSString stringWithFormat:@"粉丝 %d", _userView.interestMeCount.intValue] forState:UIControlStateNormal];
    CGSize interestMeCountButtonSize = [[interestMeCountButton titleForState:UIControlStateNormal] sizeWithFont:interestMeCountButton.titleLabel.font];
    interestMeCountButton.frame = CGRectMake(interestUserCountButton.frame.origin.x + interestUserCountButton.frame.size.width + 10.0, interestMeCountButton.frame.origin.y, interestMeCountButtonSize.width + 24.0, interestMeCountButton.frame.size.height);
    [interestMeCountButton setBackgroundImage:interestCountImage forState:UIControlStateNormal];
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

-(IBAction)editor:(id)sender{
    if (nil == _profileSettingViewController) {
        _profileSettingViewController = [[ProfileSettingViewController alloc] initWithStyle:UITableViewStyleGrouped];
        _profileSettingViewController.hidesBottomBarWhenPushed = YES;
    }
    [_profileSettingViewController initUserView:_userView];
    [self.navigationController pushViewController:_profileSettingViewController animated:YES];
}

-(IBAction)nextPage:(id)sender{
    UIButton *moreButton = (UIButton *)sender;
    UITableViewCell *cell = (UITableViewCell *)moreButton.superview;
    [moreButton setHidden:YES];
    UIActivityIndicatorView *spinner = (UIActivityIndicatorView *)[cell viewWithTag:2];
    [spinner startAnimating];
    [self loadListDataWithPage:_data.pager.currentPage + 1];
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
        static NSString *PagerCellIdentifier = @"PagerCellIdentifier";
        UITableViewCell *cell = [tableView dequeueReusableCellWithIdentifier:PagerCellIdentifier];
        if(cell == nil){
            cell = [[UITableViewCell alloc]initWithStyle:UITableViewCellStyleDefault reuseIdentifier:PagerCellIdentifier];
            cell.selectionStyle = UITableViewCellSelectionStyleNone;
            
            UIButton *moreButton = [UIButton buttonWithType:UIButtonTypeCustom];
            moreButton.frame = CGRectMake(0, 0, 320, 30);
            [moreButton setBackgroundImage:[UIImage imageNamed:@"idea_more_btn.png"] forState:UIControlStateNormal];
            [moreButton setTitle:@"查看更多" forState:UIControlStateNormal];
            [moreButton addTarget:self action:@selector(nextPage:) forControlEvents:UIControlEventTouchUpInside];
            moreButton.tag = 1;
            [cell addSubview:moreButton];
            
            UIActivityIndicatorView *spinner = [[UIActivityIndicatorView alloc] initWithActivityIndicatorStyle:UIActivityIndicatorViewStyleGray];
            spinner.tag = 2;
            [cell addSubview:spinner];
            [spinner setCenter:CGPointMake(160, 15)];
        }else {
            UIButton *button = (UIButton *)[cell viewWithTag:1];
            [button setHidden:NO];
            UIActivityIndicatorView *spinner = (UIActivityIndicatorView *)[cell viewWithTag:2];
            [spinner stopAnimating];
        }
        return cell;
    }else {
        NSString *postListCellIdentifier = @"PostListCellIdentifier";
        PostListCell *cell = [tableView dequeueReusableCellWithIdentifier:postListCellIdentifier];
        if(cell == nil){
            NSArray *nib = [[NSBundle mainBundle] loadNibNamed:@"PostListCell" owner:self options:nil];
            for(id oneObject in nib){
                if([oneObject isKindOfClass:[PostListCell class]]){
                    cell = (PostListCell *) oneObject;
                }
            }
            [cell setBackground];
        }
        
        PostView *postView = (PostView *)[_data objectAtIndex:indexPath.row];
        [cell redrawn:postView];
        [cell sizeToFit];
        return cell;
    }
}

- (NSString *) tableView:(UITableView *)tableView titleForHeaderInSection:(NSInteger)section{
    return [NSString stringWithFormat:TABLE_HEAD_TITLE, _data.pager.totalResults];
}

#pragma mark -
#pragma mark Table View Deletage

- (CGFloat) tableView:(UITableView *)tableView heightForRowAtIndexPath:(NSIndexPath *)indexPath{
    if (_data.pager.hasNext && indexPath.row == [_data count]) {
        return 30.0;
    }else {
        return [PostListCell heightForCell:[_data objectAtIndex:indexPath.row]];
    }
}

- (CGFloat) tableView:(UITableView *)tableView heightForHeaderInSection:(NSInteger)section{
    return TABLE_HEAD_HEIGHT;
}

- (UIView *) tableView:(UITableView *)tableView viewForHeaderInSection:(NSInteger)section{
    
    NSString *sectionTitle = [self tableView:tableView titleForHeaderInSection:section];
    if (sectionTitle == nil) {
        return  nil;
    }
    
    UILabel * label = [[UILabel alloc] init];
    label.frame = CGRectMake(10, 7, tableView.bounds.size.width, 11);
    label.backgroundColor = [UIColor clearColor];
    label.font=[UIFont fontWithName:DEFAULT_FONT_FAMILY size:11];
    label.textColor = [UIColor grayColor];
    label.text = sectionTitle;
    
    UIView * sectionView = [[UIView alloc] initWithFrame:CGRectMake(0, 0, tableView.bounds.size.width, TABLE_HEAD_HEIGHT)];
    [sectionView setBackgroundColor: [UIColor colorWithPatternImage: [UIImage imageNamed: TABLE_HEAD_BG_IMAGE]]];
    [sectionView addSubview:label];
    return sectionView;
}

-(void) tableView:(UITableView *)tableView didSelectRowAtIndexPath:(NSIndexPath *)indexPath{
    if (indexPath.row < [_data count]) {
        PostDetailViewController *postDetailViewController = [[PostDetailViewController alloc] initWithNibName:@"PostDetailViewController" bundle:nil];
        postDetailViewController.hidesBottomBarWhenPushed = YES;
        _userView.post = [_data objectAtIndex:indexPath.row];
        postDetailViewController.userView = _userView;
        [self.navigationController pushViewController:postDetailViewController animated:YES];
    }
    [tableView deselectRowAtIndexPath:indexPath animated:YES];
}

@end
