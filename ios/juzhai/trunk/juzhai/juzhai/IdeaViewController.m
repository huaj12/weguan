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
#import "MenuButton.h"
#import "ListHttpRequestDelegate.h"

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
    _data = [[JZData alloc] init];
    _listHttpRequestDelegate = [[ListHttpRequestDelegate alloc] init];
    _listHttpRequestDelegate.jzData = _data;
    _listHttpRequestDelegate.viewClassName = @"IdeaView";
    _listHttpRequestDelegate.listViewController = self;
    
    UIImage* dividerImage = [UIImage imageNamed:DIVIDER_LINE_IMAGE];
    _segmentedControl = [[CustomSegmentedControl alloc] initWithSegmentCount:2 segmentsize:CGSizeMake(60, dividerImage.size.height) dividerImage:dividerImage tag:OrderTypeTime delegate:self];
    self.navigationItem.rightBarButtonItem = [[UIBarButtonItem alloc] initWithCustomView:_segmentedControl];
    
    //中央分类选择按钮
    _categoryDownLinkImg = [UIImage imageNamed:CATEGORY_DOWN_LINK_IMG];
    _categoryDownHoverImg = [UIImage imageNamed:CATEGORY_DOWN_HOVER_IMG];
    _categoryUpLinkImg = [UIImage imageNamed:CATEGORY_UP_LINK_IMG];
    _categoryUpHoverImg = [UIImage imageNamed:CATEGORY_up_HOVER_IMG];
    
    _categoryButton = [UIButton buttonWithType:UIButtonTypeCustom];
    _categoryButton.frame = CGRectMake(0, 0, _categoryDownLinkImg.size.width, _categoryDownLinkImg.size.height);
    [_categoryButton setBackgroundImage:_categoryDownLinkImg forState:UIControlStateNormal];
    [_categoryButton setBackgroundImage:_categoryDownHoverImg forState:UIControlStateHighlighted];
    _categoryButton.titleLabel.font = [UIFont fontWithName:DEFAULT_FONT_FAMILY size:13.0];
    [_categoryButton setTitle:@"全部分类" forState:UIControlStateNormal];
    [_categoryButton setTitleColor:[UIColor whiteColor] forState:UIControlStateNormal];
    [_categoryButton setTitleColor:[UIColor whiteColor] forState:UIControlStateHighlighted];
    [_categoryButton setTitleEdgeInsets:UIEdgeInsetsMake(0.0, -20.0, 0.0, 0.0)];
    _categoryButton.tag = ALL_CATEGORY_ID;
    [_categoryButton addTarget:self action:@selector(showCategory:) forControlEvents:UIControlEventTouchUpInside];
    self.navigationItem.leftBarButtonItem = [[UIBarButtonItem alloc] initWithCustomView:_categoryButton];
    
    //隐藏下方线条
    UIView *view = [UIView new];
    view.backgroundColor = [UIColor clearColor];
    [self.tableView setTableFooterView:view];
    
    //设置分割线
    self.tableView.separatorColor = [UIColor colorWithRed:0.71f green:0.71f blue:0.71f alpha:1.00f];
    self.tableView.backgroundColor = [UIColor colorWithPatternImage:[UIImage imageNamed:APP_BG_IMG]];
    
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
    if(page <= 0){
        page = 1;
    }
    NSInteger categoryId = _categoryButton.tag;
    NSString *orderType;
    if(_segmentedControl.tag == OrderTypeTime){
        orderType = @"time";
    }else {
        orderType = @"pop";
    }
    NSDictionary *params = [[NSDictionary alloc] initWithObjectsAndKeys:[NSNumber numberWithInt:categoryId], @"categoryId",orderType, @"orderType", [NSNumber numberWithInt:page], @"page", nil];
    ASIHTTPRequest *request = [HttpRequestSender getRequestWithUrl:[UrlUtils urlStringWithUri:@"idea/list"] withParams:params];
    if (request) {
        [request setDelegate:_listHttpRequestDelegate];
        [request startAsynchronous];
    }
}

#pragma mark -
#pragma mark CustomSegmentedControlDelegate
- (UIButton*) buttonFor:(CustomSegmentedControl*)segmentedControl atIndex:(NSUInteger)segmentIndex;
{
    CapLocation location;
    if (segmentIndex == 0)
        location = CapLeft;
    else if (segmentIndex == segmentedControl.buttons.count - 1)
        location = CapMiddle;
    else
        location = CapRight;
    
    NSString *buttonText;
    switch (segmentIndex) {
        case OrderTypeTime:
            buttonText = @"最新";
            break;
        case OrderTypeHot:
            buttonText = @"最热";
            break;
    }
    UIButton* button = [[MenuButton alloc] initWithWidth:60 buttonText:buttonText CapLocation:location];
    if (segmentIndex == 0)
        button.selected = YES;
    return button;
}

- (void) touchDownAtSegmentIndex:(NSUInteger)segmentIndex
{
    if (segmentIndex == _segmentedControl.tag) {
        return;
    }
    _segmentedControl.tag = segmentIndex;
    //reload data
    [_refreshHeaderView autoRefresh:self.tableView];
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
    [_categoryButton setBackgroundImage:_categoryDownLinkImg forState:UIControlStateNormal];
    [_categoryButton setBackgroundImage:_categoryDownHoverImg forState:UIControlStateHighlighted];
    [_categoryButton addTarget:self action:@selector(showCategory:) forControlEvents:UIControlEventTouchUpInside];
}

-(IBAction)showCategory:(id)sender{
    [_categoryButton setBackgroundImage:_categoryUpLinkImg forState:UIControlStateNormal];
    [_categoryButton setBackgroundImage:_categoryUpHoverImg forState:UIControlStateHighlighted];
    [self popover: sender];
    [_categoryButton removeTarget:self action:@selector(showCategory:) forControlEvents:UIControlEventTouchUpInside];
}

//-(IBAction)changeOrder:(id)sender{
//    switch (_orderButton.tag) {
//        case OrderTypeTime:
//            [self hotOrderButton];
//            break;
//        case OrderTypeHot:
//            [self timeOrderButton];
//            break;
//    }
//    //reload data
//    [_refreshHeaderView autoRefresh:self.tableView];
//}

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
        [self loadListDataWithPage:[_data.pager nextPage]];
    }
    [tableView deselectRowAtIndexPath:indexPath animated:YES];
}

@end
