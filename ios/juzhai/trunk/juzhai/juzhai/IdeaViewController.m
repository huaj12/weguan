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
#import "BaseData.h"
#import "IdeaDetailViewController.h"
#import "Pager.h"

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
    [super viewDidLoad];
    // Do any additional setup after loading the view from its nib.
//    if (_refreshHeaderView == nil) {
//		EGORefreshTableHeaderView *view = [[EGORefreshTableHeaderView alloc] initWithFrame:CGRectMake(0.0f, 0.0f - self.tableView.bounds.size.height, self.view.frame.size.width, self.tableView.bounds.size.height)];
//		view.delegate = self;
//		[self.tableView addSubview:view];
//		_refreshHeaderView = view;
//	}
    
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
    
    //隐藏下方线条
    UIView *view = [UIView new];
    view.backgroundColor = [UIColor clearColor];
    [self.tableView setTableFooterView:view];
    
    //设置分组
//    self.tableView = [[UITableView alloc] initWithFrame:[UIScreen mainScreen].applicationFrame style:UITableViewStyleGrouped];
    
    //设置分割线
    self.tableView.separatorColor = [UIColor colorWithRed:0.78f green:0.78f blue:0.78f alpha:1.00f];
    self.tableView.backgroundColor = [UIColor colorWithRed:0.93f green:0.93f blue:0.93f alpha:1.00f];
    //加载初始化分类数据
    [BaseData getCategories];
    //加载列表数据
    [self loadListDataWithPage:1];
    
    
    //请求数据
    //update the last update date
    //    [_refreshHeaderView refreshLastUpdatedDate];
    //    [_refreshHeaderView egoRefreshScrollViewDidScroll:self.tableView];
    //    [_refreshHeaderView egoRefreshScrollViewDidEndDragging:self.tableView];
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
    //	_refreshHeaderView = nil;
    _data = nil;
}

- (void) loadListDataWithPage:(NSInteger)page{
    if(page <= 0){
        page = 1;
    }
    MBProgressHUD *hud = [MBProgressHUD showHUDAddedTo:self.navigationController.view animated:YES];
    hud.labelText = @"加载中...";
    dispatch_async(dispatch_get_global_queue(DISPATCH_QUEUE_PRIORITY_HIGH, 0), ^{
        sleep(1);
        NSInteger categoryId = _categoryButton.tag;
        NSString *orderType;
        if(_orderButton.tag == ORDER_BY_TIME){
            orderType = @"time";
        }else {
            orderType = @"pop";
        }
        NSDictionary *params = [[NSDictionary alloc] initWithObjectsAndKeys:[NSNumber numberWithInt:categoryId], @"categoryId",orderType, @"orderType", [NSNumber numberWithInt:page], @"page", nil];
        __block ASIHTTPRequest *_request = [HttpRequestSender getRequestWithUrl:@"http://test.51juzhai.com/app/ios/ideaList" withParams:params];
        __unsafe_unretained ASIHTTPRequest *request = _request;
        [request setCompletionBlock:^{
            // Use when fetching text data
            [MBProgressHUD hideHUDForView:self.navigationController.view animated:YES];
            NSString *responseString = [request responseString];
            NSLog(@"%@", responseString);
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
                    IdeaView *ideaView = [IdeaView ideaConvertFromDictionary:[ideaViewList objectAtIndex:i]];
                    [_data addObject:ideaView withIdentity:ideaView.ideaId];
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

//-(void)reloadTableViewDataSource{
//    //取数据
//    _reloading = YES;
//	//如果网络存在则加载数据
//	if ([CheckNetwork isExistenceNetwork]) {
//		//获取数据
//        
//	}
//}

//-(void)doneLoadingTableViewData{
//    //reloading结束
//	_reloading = NO;
//    //告诉refreshHeaderView,已经完成loading
//	[_refreshHeaderView egoRefreshScrollViewDataSourceDidFinishedLoading:self.tableView];
//    //让tableView 重新加载数据
//	[self.tableView reloadData];
//}

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
    [self loadListDataWithPage:1];
}

- (void)selectByCategory:(UITableViewCell *)cell{
    _categoryButton.tag = cell.textLabel.tag;
    [_categoryButton setTitle:cell.textLabel.text forState:UIControlStateNormal];
    [_categoryPopver dismissPopoverAnimated:YES];
    [self loadListDataWithPage:1];
}

#pragma mark -
#pragma mark Table View Data Source methods

-(NSInteger)tableView:(UITableView *)tableView numberOfRowsInSection:(NSInteger)section{
    int count = _data.count;
    if (_data.pager.hasNext) {
        count += 1;
    }
    return count;
}

-(UITableViewCell *)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath{
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
        static NSString *IdeaListCellIdentifier = @"IdeaListCellIdentifier";
        IdeaListCell * cell = (IdeaListCell *)[tableView dequeueReusableCellWithIdentifier:IdeaListCellIdentifier];
        if(cell == nil){
            NSArray *nib = [[NSBundle mainBundle] loadNibNamed:@"IdeaListCell" owner:self options:nil];
            for(id oneObject in nib){
                if([oneObject isKindOfClass:[IdeaListCell class]]){
                    cell = (IdeaListCell *) oneObject;
                }
            }
            [cell setBackground];
        }
        IdeaView *ideaView = (IdeaView *)[_data objectAtIndex:indexPath.row];
        [cell redrawn:ideaView];
        //    [wantToButton addTarget:self action:@selector(loadListData) forControlEvents:UIControlEventTouchUpInside];
        return cell;
    }
}

#pragma mark -
#pragma mark Table View Delegate methods

-(CGFloat)tableView:(UITableView *)tableView heightForRowAtIndexPath:(NSIndexPath *)indexPath{
    if (_data.pager.hasNext && indexPath.row == [_data count]) {
        return 30.0;
    }else {
        return [IdeaListCell heightForCell:[_data objectAtIndex:indexPath.row]];
    }
}

-(void)tableView:(UITableView *)tableView didSelectRowAtIndexPath:(NSIndexPath *)indexPath{
    if (indexPath.row < [_data count]) {
        if(_ideaDetailViewController == nil){
            _ideaDetailViewController = [[IdeaDetailViewController alloc] initWithNibName:@"ideaDetailViewController" bundle:nil];
            _ideaDetailViewController.hidesBottomBarWhenPushed = YES;
        }
        _ideaDetailViewController.ideaView = [_data objectAtIndex:indexPath.row];
        [self.navigationController pushViewController:_ideaDetailViewController animated:YES];
    }
    [tableView deselectRowAtIndexPath:indexPath animated:YES];
}

#pragma mark - UIScrollViewDelegate Methods

//- (void)scrollViewDidScroll:(UIScrollView *)scrollView{	
//	[_refreshHeaderView egoRefreshScrollViewDidScroll:scrollView];
//}
//
//- (void)scrollViewDidEndDragging:(UIScrollView *)scrollView willDecelerate:(BOOL)decelerate{
//	[_refreshHeaderView egoRefreshScrollViewDidEndDragging:scrollView];
//}

#pragma mark - EGORefreshTableHeaderDelegate Methods

//- (void)egoRefreshTableHeaderDidTriggerRefresh:(EGORefreshTableHeaderView*)view{
//	//重新获取数据
//	[self reloadTableViewDataSource];
////    [self doneLoadingTableViewData];
//    //3秒后执行加载完成
//    [self performSelector:@selector(doneLoadingTableViewData) withObject:nil afterDelay:3];
//}
//
//- (BOOL)egoRefreshTableHeaderDataSourceIsLoading:(EGORefreshTableHeaderView*)view{
//	//正在reloading
//	return _reloading; // should return if data source model is reloading
//	
//}
//
//- (NSDate*)egoRefreshTableHeaderDataSourceLastUpdated:(EGORefreshTableHeaderView*)view{
//	//最后更新时间
//	return [NSDate date]; // should return date data source was last changed
//	
//}

@end
