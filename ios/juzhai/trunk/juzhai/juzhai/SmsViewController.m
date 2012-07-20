//
//  SmsViewController.m
//  juzhai
//
//  Created by JiaJun Wu on 12-7-8.
//  Copyright (c) 2012年 __MyCompanyName__. All rights reserved.
//

#import "SmsViewController.h"
#import "JZData.h"
#import "SmsListCell.h"
#import "PagerCell.h"
#import "DialogView.h"
#import "Constant.h"
#import "HttpRequestSender.h"
#import "SBJson.h"
#import "TaHomeViewController.h"
#import "RectButton.h"
#import "MessageShow.h"
#import "UrlUtils.h"
#import "DialogContentViewController.h"

@interface SmsViewController ()

@end

@implementation SmsViewController

- (id)initWithStyle:(UITableViewStyle)style
{
    self = [super initWithStyle:style];
    if (self) {
        // Custom initialization
    }
    return self;
}

- (void) loadListDataWithPage:(NSInteger)page
{
    if(page <= 0)
        page = 1;
    NSMutableDictionary *params = [[NSMutableDictionary alloc] initWithObjectsAndKeys:[NSNumber numberWithInt:page], @"page", nil];
    __unsafe_unretained __block ASIHTTPRequest *request = [HttpRequestSender getRequestWithUrl:[UrlUtils urlStringWithUri:@"dialogList"] withParams:params];
    if (request) {
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
                NSMutableArray *dialogViewList = [[jsonResult valueForKey:@"result"] valueForKey:@"dialogViewList"];
                for (int i = 0; i < dialogViewList.count; i++) {
                    DialogView *dialogView = [DialogView convertFromDictionary:[dialogViewList objectAtIndex:i]];
                    [_data addObject:dialogView withIdentity:[NSNumber numberWithInt:dialogView.dialogId]];
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

- (IBAction)toggleEdit:(id)sender{
    [self.tableView setEditing:!self.tableView.editing animated:YES];
    if(self.tableView.editing)
        [_editButton setTitle:@"完成" forState:UIControlStateNormal];
    else
        [_editButton setTitle:@"删除" forState:UIControlStateNormal];
}

- (void)viewDidLoad
{    
    self.title = @"我的消息";
    
    _editButton = [[RectButton alloc] initWithWidth:45.0 buttonText:@"删除" CapLocation:CapLeftAndRight];
    [_editButton addTarget:self action:@selector(toggleEdit:) forControlEvents:UIControlEventTouchUpInside];
    self.navigationItem.rightBarButtonItem = [[UIBarButtonItem alloc] initWithCustomView:_editButton];
    
    UIView *view = [UIView new];
    view.backgroundColor = [UIColor clearColor];
    [self.tableView setTableFooterView:view];
    
    self.tableView.separatorColor = [UIColor colorWithRed:0.71f green:0.71f blue:0.71f alpha:1.00f];
    self.tableView.backgroundColor = [UIColor colorWithPatternImage:[UIImage imageNamed:APP_BG_IMG]];
    
    [super viewDidLoad];
}


- (void)viewDidUnload
{
    [super viewDidUnload];
    _data = nil;
}

- (void)viewWillAppear:(BOOL)animated{
    [self.tableView reloadData];
}

- (BOOL)shouldAutorotateToInterfaceOrientation:(UIInterfaceOrientation)interfaceOrientation
{
    return (interfaceOrientation == UIInterfaceOrientationPortrait);
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
    static NSString *SmsListCellIdentifier = @"SmsListCellIdentifier";
    SmsListCell *cell = (SmsListCell *)[tableView dequeueReusableCellWithIdentifier:SmsListCellIdentifier];
    if(cell == nil){
        cell = [SmsListCell cellFromNib];
    }
    DialogView *dialogView = (DialogView *)[_data objectAtIndex:indexPath.row];
    [cell redrawn:dialogView];
    return cell;
}

// Override to support conditional editing of the table view.
- (BOOL)tableView:(UITableView *)tableView canEditRowAtIndexPath:(NSIndexPath *)indexPath
{
    if (indexPath.row < [_data count])
        return YES;
    else
        return NO;
}

// Override to support editing the table view.
- (void)tableView:(UITableView *)tableView commitEditingStyle:(UITableViewCellEditingStyle)editingStyle forRowAtIndexPath:(NSIndexPath *)indexPath
{
    if (editingStyle == UITableViewCellEditingStyleDelete) {
        DialogView *dialogView = (DialogView *)[_data objectAtIndex:indexPath.row];
        NSDictionary *params = [[NSDictionary alloc] initWithObjectsAndKeys:[NSNumber numberWithInt:dialogView.dialogId], @"dialogId", nil];
        __block ASIFormDataRequest *_request = [HttpRequestSender postRequestWithUrl:[UrlUtils urlStringWithUri:@"deleteDialog"] withParams:params];
        __unsafe_unretained ASIHTTPRequest *request = _request;
        [request setCompletionBlock:^{
            NSString *responseString = [request responseString];
            NSMutableDictionary *jsonResult = [responseString JSONValue];
            if([jsonResult valueForKey:@"success"] == [NSNumber numberWithBool:YES]){
                NSUInteger row = [indexPath row];
                [_data removeObjectAtIndex:row];
                // Delete the row from the data source
                [tableView deleteRowsAtIndexPaths:[NSArray arrayWithObject:indexPath] withRowAnimation:UITableViewRowAnimationFade];
                return;
            }
            NSString *errorInfo = [jsonResult valueForKey:@"errorInfo"];
            NSLog(@"%@", errorInfo);
            if (errorInfo == nil || [errorInfo isEqual:[NSNull null]] || [errorInfo isEqualToString:@""]) {
                errorInfo = SERVER_ERROR_INFO;
            }
            [MessageShow error:errorInfo onView:self.view];
        }];
        [request setFailedBlock:^{
            [MessageShow error:SERVER_ERROR_INFO onView:self.view];
        }];
        [request startAsynchronous];
    }   
}

#pragma mark - Table view delegate

- (CGFloat) tableView:(UITableView *)tableView heightForRowAtIndexPath:(NSIndexPath *)indexPath
{
    if (_data.pager.hasNext && indexPath.row == [_data count]) {
        return PAGER_CELL_HEIGHT;
    }else {
        return [SmsListCell heightForCell:[_data objectAtIndex:indexPath.row]];
    }
}

- (void)tableView:(UITableView *)tableView didSelectRowAtIndexPath:(NSIndexPath *)indexPath
{
    if (indexPath.row < [_data count]) {
        DialogView *dialogView = (DialogView *)[_data objectAtIndex:indexPath.row];
        DialogContentViewController *dialogContentViewController = [[DialogContentViewController alloc] initWithNibName:@"DialogContentViewController" bundle:nil];
        dialogContentViewController.hidesBottomBarWhenPushed = YES;
        dialogContentViewController.targetUser = dialogView.targetUser;
        [self.navigationController pushViewController:dialogContentViewController animated:YES];
    } else {
        [self loadListDataWithPage:_data.pager.currentPage + 1];
    }
    [tableView deselectRowAtIndexPath:indexPath animated:YES];
}

@end
