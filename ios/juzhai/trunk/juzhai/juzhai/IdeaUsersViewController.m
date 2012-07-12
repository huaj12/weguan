//
//  IdeaUsersViewController.m
//  juzhai
//
//  Created by JiaJun Wu on 12-7-10.
//  Copyright (c) 2012年 __MyCompanyName__. All rights reserved.
//

#import <CoreText/CoreText.h>
#import "IdeaUsersViewController.h"
#import "Constant.h"
#import "IdeaView.h"
#import "MBProgressHUD.h"
#import "HttpRequestSender.h"
#import "SBJson.h"
#import "JZData.h"
#import "IdeaUserView.h"
#import "UserView.h"
#import "PagerCell.h"
#import "Pager.h"
#import "IdeaUserListCell.h"
#import "HomeViewController.h"
#import "UrlUtils.h"

@interface IdeaUsersViewController ()

@end

@implementation IdeaUsersViewController

@synthesize ideaView;

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
    self.title = @"想去的人";
    
    //隐藏下方线条
    UIView *view = [UIView new];
    view.backgroundColor = [UIColor clearColor];
    [self.tableView setTableFooterView:view];
    
    //设置分割线
    self.tableView.separatorColor = [UIColor colorWithRed:0.78f green:0.78f blue:0.78f alpha:1.00f];
    self.tableView.backgroundColor = [UIColor colorWithRed:0.93f green:0.93f blue:0.93f alpha:1.00f];
    
    //loadData
    [self loadListDataWithPage:1];
}

- (void)viewDidUnload
{
    [super viewDidUnload];
}

- (void)viewWillAppear:(BOOL)animated
{
    
}

- (BOOL)shouldAutorotateToInterfaceOrientation:(UIInterfaceOrientation)interfaceOrientation
{
    return (interfaceOrientation == UIInterfaceOrientationPortrait);
}

- (void) loadListDataWithPage:(NSInteger)page
{
    if(page <= 0)
        page = 1;
    MBProgressHUD *hud = [MBProgressHUD showHUDAddedTo:self.navigationController.view animated:YES];
    hud.labelText = @"加载中...";
    dispatch_async(dispatch_get_global_queue(DISPATCH_QUEUE_PRIORITY_HIGH, 0), ^{
        NSMutableDictionary *params = [[NSMutableDictionary alloc] initWithObjectsAndKeys:ideaView.ideaId, @"ideaId", [NSNumber numberWithInt:page], @"page", nil];
        __unsafe_unretained __block ASIHTTPRequest *request = [HttpRequestSender getRequestWithUrl:[UrlUtils urlStringWithUri:@"ideaUsers"] withParams:params];
        //        __unsafe_unretained ASIHTTPRequest *request = _request;
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
                NSMutableArray *ideaUserViewList = [[jsonResult valueForKey:@"result"] valueForKey:@"ideaUserViewList"];
                for (int i = 0; i < ideaUserViewList.count; i++) {
                    IdeaUserView *ideaUserView = [IdeaUserView convertFromDictionary:[ideaUserViewList objectAtIndex:i]];
                    [_data addObject:ideaUserView withIdentity:ideaUserView.userView.uid];
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

#pragma mark - Table view data source

- (NSInteger)tableView:(UITableView *)tableView numberOfRowsInSection:(NSInteger)section
{
    return [_data cellRows];
}

- (UITableViewCell *)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath
{
    if (_data.pager.hasNext && indexPath.row == [_data count]) {
        return [PagerCell dequeueReusablePagerCell:tableView];
    }
    static NSString *IdeaUserListCellIdentifier = @"IdeaUserListCellIdentifier";
    IdeaUserListCell *cell = (IdeaUserListCell *)[tableView dequeueReusableCellWithIdentifier:IdeaUserListCellIdentifier];
    if(cell == nil){
        cell = [IdeaUserListCell cellFromNib];
    }
    IdeaUserView *ideaUserView = (IdeaUserView *)[_data objectAtIndex:indexPath.row];
    [cell redrawn:ideaUserView];
    return cell;
}

#pragma mark - Table view delegate

- (CGFloat) tableView:(UITableView *)tableView heightForRowAtIndexPath:(NSIndexPath *)indexPath
{
    if (_data.pager.hasNext && indexPath.row == [_data count]) {
        return PAGER_CELL_HEIGHT;
    }else {
        return [IdeaUserListCell heightForCell:[_data objectAtIndex:indexPath.row]];
    }
}

- (CGFloat) tableView:(UITableView *)tableView heightForHeaderInSection:(NSInteger)section{
    return TABLE_HEAD_HEIGHT;
}

- (UIView *) tableView:(UITableView *)tableView viewForHeaderInSection:(NSInteger)section{
    
//    NSString *sectionTitle = [self tableView:tableView titleForHeaderInSection:section];
//    if (sectionTitle == nil) {
//        return nil;
//    }
//    CTFontRef fontRef = CTFontCreateWithName((CFStringRef)DEFAULT_FONT_FAMILY,
//                                             24.0f, NULL);
//    NSDictionary *attrs = [NSDictionary dictionaryWithObjectsAndKeys:
//                           (id)[UIColor blueColor].CGColor, kCTForegroundColorAttributeName,
//                           fontRef, kCTFontAttributeName,
//                           (id)[UIColor blueColor].CGColor, (NSString *) kCTStrokeColorAttributeName,
//                           (id)[NSNumber numberWithFloat: 20], (NSString *)kCTStrokeWidthAttributeName,
//                           nil];
//    NSAttributedString *aString = [[NSAttributedString alloc] initWithString:@"想去" attributes:attrs];
//    CFRelease(fontRef);
    
    UILabel * label1 = [[UILabel alloc] init];
    label1.frame = CGRectMake(10, 0, 24, TABLE_HEAD_HEIGHT);
    label1.backgroundColor = [UIColor clearColor];
    label1.font=[UIFont fontWithName:DEFAULT_FONT_FAMILY size:12];
    label1.textColor = [UIColor blackColor];
    label1.text = @"想去";
    
    UILabel * label2 = [[UILabel alloc] init];
    label2.backgroundColor = [UIColor clearColor];
    label2.font=[UIFont fontWithName:DEFAULT_FONT_FAMILY size:12];
    label2.textColor = [UIColor blueColor];
    label2.text = self.ideaView.content;
    CGSize label2Size = [label2.text sizeWithFont:label2.font constrainedToSize:CGSizeMake(240.0, TABLE_HEAD_HEIGHT) lineBreakMode:UILineBreakModeTailTruncation];
    label2.frame = CGRectMake(39, 0, label2Size.width, TABLE_HEAD_HEIGHT);
    
    UILabel * label3 = [[UILabel alloc] init];
    label3.backgroundColor = [UIColor clearColor];
    label3.font=[UIFont fontWithName:DEFAULT_FONT_FAMILY size:12];
    label3.textColor = [UIColor blackColor];
    label3.text = @"的人";
    label3.frame = CGRectMake(label2.frame.origin.x + label2.frame.size.width + 5, 0, 24, TABLE_HEAD_HEIGHT);
    
    UIView *borderView = [[UIView alloc] initWithFrame:CGRectMake(0, TABLE_HEAD_HEIGHT - 1, 320, 1)];
    [borderView setBackgroundColor:[UIColor colorWithRed:0.78f green:0.78f blue:0.78f alpha:1.00f]];
    
    UIView *sectionView = [[UIView alloc] initWithFrame:CGRectMake(0, 0, tableView.bounds.size.width, TABLE_HEAD_HEIGHT)];
    [sectionView setBackgroundColor:[UIColor colorWithRed:0.96f green:0.96f blue:0.96f alpha:1.00f]];
    [sectionView addSubview:label1];
    [sectionView addSubview:label2];
    [sectionView addSubview:label3];
    [sectionView addSubview:borderView];
    return sectionView;
}

- (void)tableView:(UITableView *)tableView didSelectRowAtIndexPath:(NSIndexPath *)indexPath
{
    if (indexPath.row < [_data count]) {
        HomeViewController *homeViewController = [[HomeViewController alloc] initWithNibName:@"HomeViewController" bundle:nil];
        homeViewController.hidesBottomBarWhenPushed = YES;
        IdeaUserView *ideaUserView = (IdeaUserView *)[_data objectAtIndex:indexPath.row];
        homeViewController.userView = ideaUserView.userView;
        [self.navigationController pushViewController:homeViewController animated:YES];
    } else {
        [self loadListDataWithPage:_data.pager.currentPage + 1];
    }
    [tableView deselectRowAtIndexPath:indexPath animated:YES];
}

@end
