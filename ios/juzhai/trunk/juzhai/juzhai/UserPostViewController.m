//
//  UserPostViewController.m
//  juzhai
//
//  Created by JiaJun Wu on 12-7-19.
//  Copyright (c) 2012年 __MyCompanyName__. All rights reserved.
//

#import "UserPostViewController.h"
#import "Constant.h"
#import "JZData.h"
#import "Pager.h"
#import "PagerCell.h"
#import "PostListCell.h"
#import "PostDetailViewController.h"
#import "UserContext.h"
#import "UserView.h"
#import "CheckNetwork.h"
#import "SBJson.h"
#import "HttpRequestSender.h"
#import "UrlUtils.h"
#import "PostView.h"

@interface UserPostViewController ()

@end

@implementation UserPostViewController

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
    self.title = @"我的拒宅";
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
    // Release any retained subviews of the main view.
}

- (BOOL)shouldAutorotateToInterfaceOrientation:(UIInterfaceOrientation)interfaceOrientation
{
    return (interfaceOrientation == UIInterfaceOrientationPortrait);
}

- (void)loadListDataWithPage:(NSInteger)page
{
    if(page <= 0){
        page = 1;
    }
    if ([CheckNetwork isExistenceNetwork]) {
        NSMutableDictionary *params = [[NSMutableDictionary alloc] initWithObjectsAndKeys:[NSNumber numberWithInt:page], @"page", nil];
        __unsafe_unretained __block ASIHTTPRequest *request = [HttpRequestSender getRequestWithUrl:[UrlUtils urlStringWithUri:@"home"] withParams:params];
        [request setCompletionBlock:^{
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
                [self doneLoadingTableViewData];
            }
        }];
        [request setFailedBlock:^{
            [self doneLoadingTableViewData];
        }];
        [request startAsynchronous];
    };
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
    if (indexPath.row < [_data count]) {
        PostView *postView = (PostView *)[_data objectAtIndex:indexPath.row];
        [cell redrawn:postView];
    }
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

-(void) tableView:(UITableView *)tableView didSelectRowAtIndexPath:(NSIndexPath *)indexPath{
    if (indexPath.row < [_data count]) {
        PostDetailViewController *postDetailViewController = [[PostDetailViewController alloc] initWithNibName:@"PostDetailViewController" bundle:nil];
        postDetailViewController.hidesBottomBarWhenPushed = YES;
        [UserContext getUserView].post = [_data objectAtIndex:indexPath.row];
        postDetailViewController.userView = [UserContext getUserView];
        [self.navigationController pushViewController:postDetailViewController animated:YES];
    } else {
        [self loadListDataWithPage:_data.pager.currentPage + 1];
    }
    [tableView deselectRowAtIndexPath:indexPath animated:YES];
}

@end
