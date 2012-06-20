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
    SDWebImageManager *manager = [SDWebImageManager sharedManager];
    NSURL *imageURL = [NSURL URLWithString:_userView.logo];
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
    
    postTableView.delegate = self;
    postTableView.dataSource = self;
    [postTableView reloadData];
    //    postTableView.hidden = YES;
    postTableView.separatorColor = [UIColor greenColor];
    
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

- (void)refresh
{
    // Do any additional setup after loading the view from its nib.
    
    MBProgressHUD *hud = [MBProgressHUD showHUDAddedTo:self.navigationController.view animated:YES];
    hud.labelText = @"加载中...";
    dispatch_async(dispatch_get_global_queue(DISPATCH_QUEUE_PRIORITY_HIGH, 0), ^{
        sleep(1);
        NSMutableDictionary *params = [[NSMutableDictionary alloc] initWithObjectsAndKeys:[NSNumber numberWithInt:1], @"page", nil];
        __block ASIHTTPRequest *_request = [HttpRequestSender getRequestWithUrl:@"http://test.51juzhai.com/app/ios/home" withParams:params];
        __unsafe_unretained ASIHTTPRequest *request = _request;
        [request setCompletionBlock:^{
            // Use when fetching text data
            [MBProgressHUD hideHUDForView:self.navigationController.view animated:YES];
            NSString *responseString = [request responseString];
            NSMutableDictionary *jsonResult = [responseString JSONValue];
            if([jsonResult valueForKey:@"success"] == [NSNumber numberWithBool:YES]){
                NSDictionary *result = [jsonResult valueForKey:@"result"];
                
                NSDictionary *userInfo = [result valueForKey:@"userView"];
                if(_userView == nil){
                    _userView = [UserView userConvertFromDictionary:userInfo];
                }else {
                    [_userView updateUserInfo:userInfo];
                }
                if(_data == nil){
                    _data = [[JZData alloc] init];
                }
                NSMutableArray *postViewList = [result valueForKey:@"postViewList"];
                
                NSNumber *currentPage = [[result valueForKey:@"pager"] valueForKey:@"currentPage"];
                if([currentPage intValue] == 1){
                    [_data clear];
                }
                _postTotalCount = [[[result valueForKey:@"pager"] valueForKey:@"totalResults"] intValue];
                for (int i = 0; i < postViewList.count; i++) {
                    PostView *postView = [PostView postConvertFromDictionary:[postViewList objectAtIndex:i]];
                    [_data addObject:postView withIdentity:postView.postId];
                }
                [self showContent];
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
    [self refresh];
    //隐藏下方线条
    UIView *view = [UIView new];
    view.backgroundColor = [UIColor clearColor];
    [postTableView setTableFooterView:view];
    
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

#pragma mark -
#pragma mark Table View Data Source

- (NSInteger) tableView:(UITableView *)tableView numberOfRowsInSection:(NSInteger)section{
    return _data.count;
}

- (UITableViewCell *)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath{
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

- (NSString *) tableView:(UITableView *)tableView titleForHeaderInSection:(NSInteger)section{
    return [NSString stringWithFormat:TABLE_HEAD_TITLE, _postTotalCount];
}

#pragma mark -
#pragma mark Table View Deletage

- (CGFloat) tableView:(UITableView *)tableView heightForRowAtIndexPath:(NSIndexPath *)indexPath{
    return [PostListCell heightForCell:[_data objectAtIndex:indexPath.row]];
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
    PostDetailViewController *postDetailViewController = [[PostDetailViewController alloc] initWithNibName:@"PostDetailViewController" bundle:nil];
    postDetailViewController.hidesBottomBarWhenPushed = YES;
    _userView.post = [_data objectAtIndex:indexPath.row];
    postDetailViewController.userView = _userView;
    [self.navigationController pushViewController:postDetailViewController animated:YES];
}

@end
