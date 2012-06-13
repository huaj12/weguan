//
//  UserViewController.m
//  juzhai
//
//  Created by JiaJun Wu on 12-5-23.
//  Copyright (c) 2012年 __MyCompanyName__. All rights reserved.
//

#import "UserViewController.h"
#import "CustomSegmentedControl.h"
#import "CustomButton.h"
#import "UserListCell.h"
#import "UserView.h"
#import "ASIHTTPRequest.h"
#import "HttpRequestSender.h"
#import "SBJson.h"
#import "MBProgressHUD.h"
#import "PostDetailViewController.h"

@interface UserViewController (Private)

- (void) loadListDataWithPage:(NSInteger)page;

@end

@implementation UserViewController

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
    //中央切换按钮
    UIImage* dividerImage = [UIImage imageNamed:@"menu_line.png"];
    _segmentedControl = [[CustomSegmentedControl alloc] initWithSegmentCount:2 segmentsize:CGSizeMake(51, dividerImage.size.height) dividerImage:dividerImage tag:0 delegate:self];
    _segmentedControl.tag = ORDER_BY_TIME;
    self.navigationItem.titleView = _segmentedControl;
    
    //右侧性别按钮
    _genderButton = [UIButton buttonWithType:UIButtonTypeCustom];
    _genderButton.tag = QUERY_GENDER_ALL;
    UIImage *genderImage = [UIImage imageNamed:[NSString stringWithFormat:@"sex_%d_link.png", _genderButton.tag]];
    UIImage *activeGenderImage = [UIImage imageNamed:[NSString stringWithFormat:@"sex_%d_hover.png", _genderButton.tag]];
    _genderButton.frame = CGRectMake(0, 0, genderImage.size.width, genderImage.size.height);
    [_genderButton setBackgroundImage:genderImage forState:UIControlStateNormal];
    [_genderButton setBackgroundImage:activeGenderImage forState:UIControlStateHighlighted];
    [_genderButton addTarget:self action:@selector(selectGender:) forControlEvents:UIControlEventTouchUpInside];
    self.navigationItem.rightBarButtonItem = [[UIBarButtonItem alloc] initWithCustomView: _genderButton];
    
    //隐藏下方线条
    UIView *view = [UIView new];
    view.backgroundColor = [UIColor clearColor];
    [self.tableView setTableFooterView:view];
    
    //设置分组
//    self.tableView = [[UITableView alloc] initWithFrame:[UIScreen mainScreen].applicationFrame style:UITableViewStyleGrouped];
    self.tableView.separatorColor = [UIColor clearColor];
    
    //设置背景图
//    UIImageView *imageview = [[UIImageView alloc] initWithFrame:self.view.bounds]; 
//    [imageview setImage:[UIImage imageNamed:@"bg.png"]];
//    [self.tableView setBackgroundView:imageview];
    self.tableView.backgroundColor = [UIColor purpleColor];
    
    //load
    [self loadListDataWithPage:1];
}

- (void)viewDidUnload
{
    [super viewDidUnload];
    // Release any retained subviews of the main view.
    // e.g. self.myOutlet = nil;
    _data = nil;
}

- (BOOL)shouldAutorotateToInterfaceOrientation:(UIInterfaceOrientation)interfaceOrientation
{
    return (interfaceOrientation == UIInterfaceOrientationPortrait);
}

#pragma mark -
#pragma mark local method

-(IBAction)selectGender:(id)sender{
    //弹框选择性别
    UIActionSheet *actionSheet = [[UIActionSheet alloc] 
                                  initWithTitle:@"筛选" 
                                  delegate:self 
                                  cancelButtonTitle:@"取消"
                                  destructiveButtonTitle:nil
                                  otherButtonTitles:@"全部", @"宅男", @"宅女", nil];
    [actionSheet showInView:self.tabBarController.view];
}

- (void) loadListDataWithPage:(NSInteger)page{
    if(page <= 0)
        page = 1;
    MBProgressHUD *hud = [MBProgressHUD showHUDAddedTo:self.navigationController.view animated:YES];
    hud.labelText = @"加载中...";
    dispatch_async(dispatch_get_global_queue(DISPATCH_QUEUE_PRIORITY_HIGH, 0), ^{
        sleep(1);
        NSMutableDictionary *params = [[NSMutableDictionary alloc] initWithObjectsAndKeys:@"time", @"orderType", [NSNumber numberWithInt:page], @"page", nil];
        NSInteger gender = _genderButton.tag;
        if (gender <= 1) {
            [params setObject:[NSNumber numberWithInt:gender] forKey:@"gender"];
        }
        __block ASIHTTPRequest *_request = [HttpRequestSender initGetRequestWithUrl:@"http://test.51juzhai.com/app/ios/userList" withParams:params];
        __unsafe_unretained ASIHTTPRequest *request = _request;
        [request setCompletionBlock:^{
            // Use when fetching text data
            [MBProgressHUD hideHUDForView:self.navigationController.view animated:YES];
            NSString *responseString = [request responseString];
            NSMutableDictionary *jsonResult = [responseString JSONValue];
            if([jsonResult valueForKey:@"success"] == [NSNumber numberWithBool:YES]){
                //reload
                if(_data == nil){
                    _data = [[NSMutableArray alloc] init];
                }
                NSMutableArray *userViewList = [[jsonResult valueForKey:@"result"] valueForKey:@"userViewList"];
                
                NSNumber *currentPage = [[[jsonResult valueForKey:@"result"] valueForKey:@"pager"] valueForKey:@"currentPage"];
                if([currentPage intValue] == 1){
                    [_data removeAllObjects];
                }
                for (int i = 0; i < userViewList.count; i++) {
                    [_data addObject:[UserView userConvertFromDictionary:[userViewList objectAtIndex:i]]];
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

#pragma mark - 
#pragma mark Action Sheet Delegate
- (void)actionSheet:(UIActionSheet *)actionSheet
didDismissWithButtonIndex:(NSInteger)buttonIndex
{
    if (buttonIndex != [actionSheet cancelButtonIndex] && _genderButton.tag != 2 - buttonIndex)
    {
        _genderButton.tag = 2 - buttonIndex;
        UIImage *genderImage = [UIImage imageNamed:[NSString stringWithFormat:@"sex_%d_link.png", _genderButton.tag]];
        UIImage *activeGenderImage = [UIImage imageNamed:[NSString stringWithFormat:@"sex_%d_hover.png", _genderButton.tag]];
        [_genderButton setBackgroundImage:genderImage forState:UIControlStateNormal];
        [_genderButton setBackgroundImage:activeGenderImage forState:UIControlStateHighlighted];
        [self loadListDataWithPage:1];
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
        case 0:
            buttonText = @"最新";
            break;
        case 1:
            buttonText = @"推荐";
            break;    
        default:
            break;
    }
    UIButton* button = [[CustomButton alloc] initWithWidth:51 buttonText:buttonText CapLocation:location];
    if (segmentIndex == 0)
        button.selected = YES;
    return button;
}

- (void) touchDownAtSegmentIndex:(NSUInteger)segmentIndex
{
    switch (segmentIndex) {
        case 0:
            break;
        case 1:
            break;
    }
    [self loadListDataWithPage:1];
}

#pragma mark -
#pragma mark Table View Data Source

//- (NSInteger)numberOfSectionsInTableView:(UITableView *)tableView{
//    return _data.count;
//}

-(NSInteger)tableView:(UITableView *)tableView numberOfRowsInSection:(NSInteger)section{
    return _data.count;
}

- (UITableViewCell *)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath{
    static NSString *UserListCellIdentifier = @"UserListCellIdentifier";
    UserListCell * cell = (UserListCell *)[tableView dequeueReusableCellWithIdentifier:UserListCellIdentifier];
    if(cell == nil){
        NSArray *nib = [[NSBundle mainBundle] loadNibNamed:@"UserListCell" owner:self options:nil];
        for(id oneObject in nib){
            if([oneObject isKindOfClass:[UserListCell class]]){
                cell = (UserListCell *) oneObject;
            }
        }
        [cell setBackground];
    }
    
    UserView *userView = (UserView *)[_data objectAtIndex:indexPath.row];
    [cell redrawn:userView];
    [cell sizeToFit];
    return cell;
}

#pragma mark -
#pragma mark Table View Delegate methods

-(CGFloat)tableView:(UITableView *)tableView heightForRowAtIndexPath:(NSIndexPath *)indexPath{
    return [UserListCell heightForCell:[_data objectAtIndex:indexPath.row]];
}

//-(CGFloat)tableView:(UITableView *)tableView heightForFooterInSection:(NSInteger)section{
//    return 3;
//}
//
//-(CGFloat)tableView:(UITableView *)tableView heightForHeaderInSection:(NSInteger)section{
//    if(section == 0){
//        return 8.0f;
//    }
//    return 3;
//}
//
//-(UIView *)tableView:(UITableView *)tableView viewForHeaderInSection:(NSInteger)section{
//    return [[UIView alloc] initWithFrame:CGRectZero];
//}
//
//-(UIView *)tableView:(UITableView *)tableView viewForFooterInSection:(NSInteger)section{
//    return [[UIView alloc] initWithFrame:CGRectZero];
//}

-(void) tableView:(UITableView *)tableView didSelectRowAtIndexPath:(NSIndexPath *)indexPath{
    PostDetailViewController *postDetailViewController = [[PostDetailViewController alloc] initWithNibName:@"PostDetailViewController" bundle:nil];
    postDetailViewController.hidesBottomBarWhenPushed = YES;
    [self.navigationController pushViewController:postDetailViewController animated:YES];
}

@end
