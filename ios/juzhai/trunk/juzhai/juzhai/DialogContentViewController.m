//
//  DialogContentViewController.m
//  juzhai
//
//  Created by JiaJun Wu on 12-7-12.
//  Copyright (c) 2012年 __MyCompanyName__. All rights reserved.
//

#import "DialogContentViewController.h"
#import "JZData.h"
#import "Pager.h"
#import "PagerCell.h"
#import "DialogContentListCell.h"
#import "DialogContentView.h"
#import "MBProgressHUD.h"
#import "HttpRequestSender.h"
#import "UrlUtils.h"
#import "UserView.h"
#import "SBJson.h"
#import "DialogService.h"
#import "ListHttpRequestDelegate.h"

@interface DialogContentViewController ()

@end

@implementation DialogContentViewController

@synthesize targetUser;
@synthesize inputAreaView;
@synthesize dialogContentTableView;
@synthesize inputField;

- (id)initWithNibName:(NSString *)nibNameOrNil bundle:(NSBundle *)nibBundleOrNil
{
    self = [super initWithNibName:nibNameOrNil bundle:nibBundleOrNil];
    if (self) {
        [[NSNotificationCenter defaultCenter] addObserver:self selector:@selector(keyboardWillShow:) name:UIKeyboardWillShowNotification object:nil];
        [[NSNotificationCenter defaultCenter] addObserver:self selector:@selector(keyboardWillHide:) name:UIKeyboardWillHideNotification object:nil];
        _singlePan = [[UIPanGestureRecognizer alloc] initWithTarget:self action:@selector(hideKeyboard)];
        _singleTap = [[UITapGestureRecognizer alloc] initWithTarget:self action:@selector(hideKeyboard)];
    }
    return self;
}

- (void)keyboardWillShow:(NSNotification *)note
{
    CGRect keyboardBounds;
    [[note.userInfo valueForKey:UIKeyboardFrameEndUserInfoKey] getValue: &keyboardBounds];
    NSNumber *duration = [note.userInfo objectForKey:UIKeyboardAnimationDurationUserInfoKey];
    NSNumber *curve = [note.userInfo objectForKey:UIKeyboardAnimationCurveUserInfoKey];
    
    // Need to translate the bounds to account for rotation.
    keyboardBounds = [self.view convertRect:keyboardBounds toView:nil];
    
	// get a rect for the textView frame
	CGRect inputAreaViewFrame = inputAreaView.frame;
    inputAreaViewFrame.origin.y = self.view.bounds.size.height - (keyboardBounds.size.height + inputAreaViewFrame.size.height);
    
	// animations settings
	[UIView beginAnimations:nil context:NULL];
	[UIView setAnimationBeginsFromCurrentState:YES];
    [UIView setAnimationDuration:[duration doubleValue]];
    [UIView setAnimationCurve:[curve intValue]];
	
	// set views with new info
	inputAreaView.frame = inputAreaViewFrame;
    
    CGRect tableViewFrame = dialogContentTableView.frame;
    if (self.dialogContentTableView.contentOffset.y == self.dialogContentTableView.contentSize.height - self.dialogContentTableView.frame.size.height)
    {
        tableViewFrame.origin.y = -keyboardBounds.size.height;
        dialogContentTableView.frame = tableViewFrame;
    } else if (self.dialogContentTableView.contentSize.height < self.dialogContentTableView.frame.size.height)  {
        tableViewFrame.size.height = inputAreaViewFrame.origin.y;
        dialogContentTableView.frame = tableViewFrame;
        [self doneLoadingTableViewData];
    }
	
	// commit animations
	[UIView commitAnimations];
    
    [self.dialogContentTableView addGestureRecognizer:_singlePan];
    [self.dialogContentTableView addGestureRecognizer:_singleTap];
}

- (void)keyboardWillHide:(NSNotification *)note
{
    NSNumber *duration = [note.userInfo objectForKey:UIKeyboardAnimationDurationUserInfoKey];
    NSNumber *curve = [note.userInfo objectForKey:UIKeyboardAnimationCurveUserInfoKey];
	
	// get a rect for the textView frame
	CGRect inputAreaViewFrame = inputAreaView.frame;
    inputAreaViewFrame.origin.y = self.view.bounds.size.height - inputAreaViewFrame.size.height;
    
    CGRect tableViewFrame = dialogContentTableView.frame;
    tableViewFrame.origin.y = 0;
    tableViewFrame.size.height = inputAreaViewFrame.origin.y;
	
	// animations settings
	[UIView beginAnimations:nil context:NULL];
	[UIView setAnimationBeginsFromCurrentState:YES];
    [UIView setAnimationDuration:[duration doubleValue]];
    [UIView setAnimationCurve:[curve intValue]];
    
	// set views with new info
	inputAreaView.frame = inputAreaViewFrame;
    dialogContentTableView.frame = tableViewFrame;
	
	// commit animations
	[UIView commitAnimations];
    
    [self.dialogContentTableView removeGestureRecognizer:_singlePan];
    [self.dialogContentTableView removeGestureRecognizer:_singleTap];
}

- (void) loadListDataWithPage:(NSInteger)page
{
    if(page <= 0)
        page = 1;
    MBProgressHUD *hud = [MBProgressHUD showHUDAddedTo:self.navigationController.view animated:YES];
    hud.labelText = @"加载中...";
    dispatch_async(dispatch_get_global_queue(DISPATCH_QUEUE_PRIORITY_HIGH, 0), ^{
        NSMutableDictionary *params = [[NSMutableDictionary alloc] initWithObjectsAndKeys:self.targetUser.uid, @"uid", [NSNumber numberWithInt:page], @"page", nil];
        ASIHTTPRequest *request = [HttpRequestSender getRequestWithUrl:[UrlUtils urlStringWithUri:@"dialog/dialogContentList"] withParams:params];
        if (request) {
            [request setDelegate:_listHttpRequestDelegate];
            [request startAsynchronous];
        }
    });
}

- (void)viewDidLoad
{
    [super viewDidLoad];
    
    _data = [[JZData alloc] init];
    _listHttpRequestDelegate = [[ListHttpRequestDelegate alloc] init];
    _listHttpRequestDelegate.jzData = _data;
    _listHttpRequestDelegate.viewClassName = @"DialogContentView";
    _listHttpRequestDelegate.listViewController = self;
    _listHttpRequestDelegate.addToHead = YES;
    
    self.title = [NSString stringWithFormat:@"与 %@ 对话", self.targetUser.nickname];
    
    self.inputAreaView.backgroundColor =  [UIColor colorWithPatternImage:[UIImage imageNamed:@"send_area_bg.png"]];
    //隐藏下方线条
    UIView *view = [UIView new];
    view.backgroundColor = [UIColor clearColor];
    [self.dialogContentTableView setTableFooterView:view];

    self.dialogContentTableView.separatorColor = [UIColor clearColor];

    self.dialogContentTableView.backgroundColor = [UIColor colorWithRed:0.93f green:0.93f blue:0.93f alpha:1.00f];
    
    //load
    [self loadListDataWithPage:1];
}

- (void)viewDidAppear:(BOOL)animated
{
    _timer = [NSTimer scheduledTimerWithTimeInterval:TIMER_INTERVAL target:self selector:@selector(refresh) userInfo:nil repeats:YES];
}

- (void)viewDidDisappear:(BOOL)animated
{
    [_timer invalidate];
}

- (void)hideKeyboard{  
    [self.inputField resignFirstResponder];
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

- (void)refresh
{
    NSMutableDictionary *params = [[NSMutableDictionary alloc] initWithObjectsAndKeys:self.targetUser.uid, @"uid", nil];
    __unsafe_unretained __block ASIHTTPRequest *request = [HttpRequestSender getRequestWithUrl:[UrlUtils urlStringWithUri:@"dialog/refreshDialogContent"] withParams:params];
    [request setCompletionBlock:^{
        NSString *responseString = [request responseString];
        NSMutableDictionary *jsonResult = [responseString JSONValue];
        if([jsonResult valueForKey:@"success"] == [NSNumber numberWithBool:YES]){
            NSMutableArray *dialogContentViewList = [[jsonResult valueForKey:@"result"] valueForKey:@"list"];
            for (int i = 0; i < dialogContentViewList.count; i++) {
                DialogContentView *dialogContentView = [DialogContentView convertFromDictionary:[dialogContentViewList objectAtIndex:i]];
                [_data addObject:dialogContentView withIdentity:[NSNumber numberWithInt:dialogContentView.dialogContentId]];
            }
            [self doneLoadingTableViewData];
        }
    }];
    [request setFailedBlock:^{
        [HttpRequestDelegate requestFailedHandle:request];
    }];
    [request startAsynchronous];
}

- (void)doneLoadingTableViewData
{
    [MBProgressHUD hideHUDForView:self.navigationController.view animated:YES];
    [self.dialogContentTableView reloadData];
    if ([_data count] > 0) {
        [self.dialogContentTableView scrollToRowAtIndexPath:[NSIndexPath indexPathForRow:[_data count]-1 inSection:0] atScrollPosition:UITableViewScrollPositionBottom animated:NO];
    }
}

- (IBAction)sendSms:(id)sender
{
    if (_dialogService == nil) {
        _dialogService = [[DialogService alloc] init];
    }
    [_dialogService sendSms:inputField.text toUser:self.targetUser.uid.intValue onSuccess:^(NSDictionary *info) {
        DialogContentView *dialogContentView = [DialogContentView convertFromDictionary:info];
        [_data addObject:dialogContentView withIdentity:[NSNumber numberWithInt:dialogContentView.dialogContentId]];
        inputField.text = @"";
        [inputField resignFirstResponder];
        [self doneLoadingTableViewData];
    }];
}

#pragma mark -
#pragma mark Table View Data Source & Delegate

- (NSInteger) tableView:(UITableView *)tableView numberOfRowsInSection:(NSInteger)section
{
    return [_data count];
}

- (UITableViewCell *)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath
{
    static NSString *DialogContentListCellIdentifier = @"DialogContentListCellIdentifier";
    DialogContentListCell *cell = (DialogContentListCell *)[tableView dequeueReusableCellWithIdentifier:DialogContentListCellIdentifier];
    if(cell == nil){
        cell = [DialogContentListCell cellFromNib];
    }
    cell.targetUser = self.targetUser;
    if (indexPath.row < [_data count]) {
        DialogContentView *dialogContentView = (DialogContentView *)[_data objectAtIndex:indexPath.row];
        [cell redrawn:dialogContentView];
    }
    return cell;
}

- (CGFloat) tableView:(UITableView *)tableView heightForRowAtIndexPath:(NSIndexPath *)indexPath
{
    return [DialogContentListCell heightForCell:[_data objectAtIndex:indexPath.row]];
}

@end
