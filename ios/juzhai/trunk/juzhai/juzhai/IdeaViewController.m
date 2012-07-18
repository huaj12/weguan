//
//  IdeaViewController.m
//  juzhai
//
//  Created by JiaJun Wu on 12-5-11.
//  Copyright (c) 2012年 __MyCompanyName__. All rights reserved.
//

#import "IdeaViewController.h"
#import "JZData.h"
#import "CheckNetwork.h"
#import "FPPopoverController.h"
#import "CategoryTableViewController.h"
#import "CustomSegmentedControl.h"
#import "CustomButton.h"
#import "IdeaListCell.h"
#import "ASIHTTPRequest.h"
#import "HttpRequestSender.h"
#import "SBJson.h"
#import "IdeaView.h"
#import "MBProgressHUD.h"
#import "Constant.h"
#import "IdeaDetailViewController.h"
#import "Pager.h"
#import "PagerCell.h"
#import "SendPostBarButtonItem.h"
#import "UrlUtils.h"
#import "BaseData.h"

@interface IdeaViewController (Private)
- (void) loadListDataWithPage:(NSInteger)page;
@end

@implementation IdeaViewController

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
    //右侧最新最热切换  
    UIImage *orderImage = [UIImage imageNamed:@"new_hot_btn_link.png"];
    UIImage *activeOrderImage = [UIImage imageNamed:@"new_hot_btn_hover.png"];
    _orderButton = [UIButton buttonWithType:UIButtonTypeCustom];
    _orderButton.frame = CGRectMake(0, 0, orderImage.size.width, orderImage.size.height);
    [_orderButton setBackgroundImage:orderImage forState:UIControlStateNormal];
    [_orderButton setBackgroundImage:activeOrderImage forState:UIControlStateHighlighted];
    _orderButton.titleLabel.font = [UIFont fontWithName:DEFAULT_FONT_FAMILY size:12.0];
    [_orderButton setTitle:@"最新" forState:UIControlStateNormal];
    [_orderButton setTitleColor:[UIColor colorWithRed:153.0f/255.0f green:153.0f/255.0f blue:153.0f/255.0f alpha:1.0] forState:UIControlStateNormal];
    [_orderButton setTitleColor:[UIColor colorWithRed:204.0f/255.0f green:204.0f/255.0f blue:204.0f/255.0f alpha:1.0] forState:UIControlStateHighlighted];
    [_orderButton setTitleEdgeInsets:UIEdgeInsetsMake(0.0, 17.0, 0.0, 0.0)];
    _orderButton.tag = ORDER_BY_TIME;
    [_orderButton addTarget:self action:@selector(changeOrder:) forControlEvents:UIControlEventTouchUpInside];
    self.navigationItem.rightBarButtonItem = [[UIBarButtonItem alloc] initWithCustomView:_orderButton];
    
    //中央分类选择按钮
    UIImage *categoryImage = [UIImage imageNamed:@"select_down_link.png"];
    UIImage *activeCategoryImage = [UIImage imageNamed:@"select_down_hover.png"];
    _categoryButton = [UIButton buttonWithType:UIButtonTypeCustom];
    _categoryButton.frame = CGRectMake(0, 0, categoryImage.size.width, categoryImage.size.height);
    [_categoryButton setBackgroundImage:categoryImage forState:UIControlStateNormal];
    [_categoryButton setBackgroundImage:activeCategoryImage forState:UIControlStateHighlighted];
    _categoryButton.titleLabel.font = [UIFont fontWithName:DEFAULT_FONT_FAMILY size:12.0];
    [_categoryButton setTitle:@"全部分类" forState:UIControlStateNormal];
    [_categoryButton setTitleColor:[UIColor colorWithRed:153.0f/255.0f green:153.0f/255.0f blue:153.0f/255.0f alpha:1.0] forState:UIControlStateNormal];
    [_categoryButton setTitleColor:[UIColor colorWithRed:204.0f/255.0f green:204.0f/255.0f blue:204.0f/255.0f alpha:1.0] forState:UIControlStateHighlighted];
    [_categoryButton setTitleEdgeInsets:UIEdgeInsetsMake(0.0, -29.0, 0.0, 0.0)];
    _categoryButton.tag = ALL_CATEGORY_ID;
    [_categoryButton addTarget:self action:@selector(showCategory:) forControlEvents:UIControlEventTouchUpInside];
    self.navigationItem.titleView = _categoryButton;
    
    self.navigationItem.leftBarButtonItem = [[SendPostBarButtonItem alloc] initWithOwnerViewController:self];
    
    //隐藏下方线条
    UIView *view = [UIView new];
    view.backgroundColor = [UIColor clearColor];
    [self.tableView setTableFooterView:view];
    
    //设置分割线
    self.tableView.separatorColor = [UIColor colorWithRed:0.78f green:0.78f blue:0.78f alpha:1.00f];
    self.tableView.backgroundColor = [UIColor colorWithRed:0.93f green:0.93f blue:0.93f alpha:1.00f];
    //加载初始化分类数据
    [BaseData getCategories];
    [super viewDidLoad];
}

- (void)viewWillAppear:(BOOL)animated{
    if (_data != nil) {
        [self.tableView reloadData];
    }
}

- (BOOL)shouldAutorotateToInterfaceOrientation:(UIInterfaceOrientation)interfaceOrientation
{
    return (interfaceOrientation == UIInterfaceOrientationPortrait);
}

#pragma mark - Memory Management

- (void)didReceiveMemoryWarning {
    [super didReceiveMemoryWarning];
}

- (void)viewDidUnload {
    _refreshHeaderView = nil;
    _data = nil;
}

- (void) loadListDataWithPage:(NSInteger)page{
	if ([CheckNetwork isExistenceNetwork]) {
        if(page <= 0){
            page = 1;
        }
        NSInteger categoryId = _categoryButton.tag;
        NSString *orderType;
        if(_orderButton.tag == ORDER_BY_TIME){
            orderType = @"time";
        }else {
            orderType = @"pop";
        }
        NSDictionary *params = [[NSDictionary alloc] initWithObjectsAndKeys:[NSNumber numberWithInt:categoryId], @"categoryId",orderType, @"orderType", [NSNumber numberWithInt:page], @"page", nil];
        __unsafe_unretained __block ASIHTTPRequest *request = [HttpRequestSender getRequestWithUrl:[UrlUtils urlStringWithUri:@"ideaList"] withParams:params];
        [request setCompletionBlock:^{
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
                NSMutableArray *ideaViewList = [[jsonResult valueForKey:@"result"] valueForKey:@"ideaViewList"];
                
                for (int i = 0; i < ideaViewList.count; i++) {
                    IdeaView *ideaView = [IdeaView convertFromDictionary:[ideaViewList objectAtIndex:i]];
                    [_data addObject:ideaView withIdentity:[NSNumber numberWithInt:ideaView.ideaId]];
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

#pragma mark -
#pragma mark Navigation Bar item

-(void)popover:(id)sender
{
    //the controller we want to present as a popover
    CategoryTableViewController *controller = [[CategoryTableViewController alloc] initWithStyle:UITableViewStylePlain];
    controller.rootController = self;
    _categoryPopver = [[FPPopoverController alloc] initWithViewController:controller];
    _categoryPopver.tint = FPPopoverDefaultTint;
    _categoryPopver.arrowDirection = FPPopoverArrowDirectionAny;
    _categoryPopver.delegate = self;
    //sender is the UIButton view
    [_categoryPopver presentPopoverFromView:sender]; 
}

-(void)popoverControllerDidDismissPopover:(FPPopoverController *)popover{
    UIImage *categoryImage = [UIImage imageNamed:@"select_down_link.png"];
    UIImage *activeCategoryImage = [UIImage imageNamed:@"select_down_hover.png"];
    [_categoryButton setBackgroundImage:categoryImage forState:UIControlStateNormal];
    [_categoryButton setBackgroundImage:activeCategoryImage forState:UIControlStateHighlighted];
}

-(IBAction)showCategory:(id)sender{
    UIImage *categoryImage = [UIImage imageNamed:@"select_up_link.png"];
    UIImage *activeCategoryImage = [UIImage imageNamed:@"select_up_hover.png"];
    [_categoryButton setBackgroundImage:categoryImage forState:UIControlStateNormal];
    [_categoryButton setBackgroundImage:activeCategoryImage forState:UIControlStateHighlighted];
    [self popover: sender];
}

-(IBAction)changeOrder:(id)sender{
    _orderButton.tag = 1 - _orderButton.tag;
    switch (_orderButton.tag) {
        case ORDER_BY_TIME:
            [_orderButton setTitle:@"最新" forState:UIControlStateNormal];
            break;
        case ORDER_BY_HOT:
            [_orderButton setTitle:@"最热" forState:UIControlStateNormal];
            break;
        default:
            break;
    }
    //reload data
    [_refreshHeaderView autoRefresh:self.tableView];
}

- (void)selectByCategory:(UITableViewCell *)cell{
    _categoryButton.tag = cell.textLabel.tag;
    [_categoryButton setTitle:cell.textLabel.text forState:UIControlStateNormal];
    [_categoryPopver dismissPopoverAnimated:YES];
    [_refreshHeaderView autoRefresh:self.tableView];
}

#pragma mark -
#pragma mark Table View Data Source methods

-(NSInteger)tableView:(UITableView *)tableView numberOfRowsInSection:(NSInteger)section{
    return [_data cellRows];
}

-(UITableViewCell *)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath{
    if (_data.pager.hasNext && indexPath.row == [_data count]) {
        return [PagerCell dequeueReusablePagerCell:tableView];
    }
    static NSString *IdeaListCellIdentifier = @"IdeaListCellIdentifier";
    IdeaListCell * cell = (IdeaListCell *)[tableView dequeueReusableCellWithIdentifier:IdeaListCellIdentifier];
    if(cell == nil){
        cell = [IdeaListCell cellFromNib];
    }
    if (indexPath.row < [_data count]) {
        IdeaView *ideaView = (IdeaView *)[_data objectAtIndex:indexPath.row];
        [cell redrawn:ideaView];
    }
    return cell;
}

#pragma mark -
#pragma mark Table View Delegate methods

-(CGFloat)tableView:(UITableView *)tableView heightForRowAtIndexPath:(NSIndexPath *)indexPath{
    if (_data.pager.hasNext && indexPath.row == [_data count]) {
        return PAGER_CELL_HEIGHT;
    }else {
        return [IdeaListCell heightForCell:[_data objectAtIndex:indexPath.row]];
    }
}

-(void)tableView:(UITableView *)tableView didSelectRowAtIndexPath:(NSIndexPath *)indexPath{
    if (indexPath.row < [_data count]) {
        IdeaDetailViewController *ideaDetailViewController = [[IdeaDetailViewController alloc] initWithNibName:@"ideaDetailViewController" bundle:nil];
        ideaDetailViewController.hidesBottomBarWhenPushed = YES;
        ideaDetailViewController.ideaView = [_data objectAtIndex:indexPath.row];
        [self.navigationController pushViewController:ideaDetailViewController animated:YES];
    } else {
        [self loadListDataWithPage:_data.pager.currentPage + 1];
    }
    [tableView deselectRowAtIndexPath:indexPath animated:YES];
}

@end
