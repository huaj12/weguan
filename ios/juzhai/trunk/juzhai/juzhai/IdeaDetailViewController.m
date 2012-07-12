//
//  IdeaDetailViewController.m
//  juzhai
//
//  Created by JiaJun Wu on 12-6-7.
//  Copyright (c) 2012年 __MyCompanyName__. All rights reserved.
//

#import "IdeaDetailViewController.h"
#import "Constant.h"
#import "CustomNavigationController.h"
#import "IdeaView.h"
#import "SDWebImage/UIImageView+WebCache.h"
#import <QuartzCore/QuartzCore.h>
#import "SBJson.h"
#import "HttpRequestSender.h"
#import "MBProgressHUD.h"
#import "MessageShow.h"
#import "IdeaUsersViewController.h"
#import "UrlUtils.h"

@interface IdeaDetailViewController ()
- (CGFloat) getViewOriginY:(UIView *)view byUpperView:(UIView *)upperView heightGap:(float)heightGap;
- (void) resetViewFrame;
@end

@implementation IdeaDetailViewController

@synthesize separatorView;
@synthesize ideaView;
@synthesize contentView;
@synthesize infoView;
@synthesize imageView;
@synthesize contentLabel;
@synthesize addressLabel;
@synthesize categoryLabel;
@synthesize timeLabel;
@synthesize timeIconView;
@synthesize addressIconView;
@synthesize categoryIconView;
@synthesize moreButton;
@synthesize postIdeaButton;
@synthesize shareButton;
@synthesize separatorView2;
@synthesize showUsersButton;

- (id)initWithNibName:(NSString *)nibNameOrNil bundle:(NSBundle *)nibBundleOrNil
{
    self = [super initWithNibName:nibNameOrNil bundle:nibBundleOrNil];
    if (self) {
        // Custom initialization
        self.title = @"拒宅好主意";
    }
    return self;
}

- (void)viewDidLoad
{
    [super viewDidLoad];
    // Do any additional setup after loading the view from its nib.
    self.view.backgroundColor = [UIColor colorWithRed:0.93f green:0.93f blue:0.93f alpha:1.00f];
    infoView.backgroundColor = [UIColor colorWithRed:0.93f green:0.93f blue:0.93f alpha:1.00f];
    
    contentLabel.font = [UIFont fontWithName:DEFAULT_FONT_FAMILY size:15.0];
    contentLabel.textColor = [UIColor colorWithRed:0.40f green:0.40f blue:0.40f alpha:1.00f];
    contentLabel.text = self.ideaView.content;
    CGSize contentSize = [contentLabel.text sizeWithFont:contentLabel.font constrainedToSize:CGSizeMake(300.0, 300.0) lineBreakMode:UILineBreakModeCharacterWrap];
    [contentLabel setFrame:CGRectMake(contentLabel.frame.origin.x, [self getViewOriginY:contentLabel byUpperView:nil heightGap:IDEA_DEFAULT_HEIGHT_GAP], contentSize.width, contentSize.height)];
    
    imageView.image = [UIImage imageNamed:@""];
    [imageView setFrame:CGRectMake(imageView.frame.origin.x, [self getViewOriginY:imageView byUpperView:contentLabel heightGap:IDEA_DEFAULT_HEIGHT_GAP], imageView.frame.size.width, imageView.frame.size.height)];
    [imageView setHidden:[ideaView.bigPic isEqual:[NSNull null]]];
    
    [timeIconView setFrame:CGRectMake(timeIconView.frame.origin.x, [self getViewOriginY:timeIconView byUpperView:nil heightGap:IDEA_INFO_ICON_HEIGHT_GAP], timeIconView.frame.size.width, timeIconView.frame.size.height)];
    timeIconView.hidden = ![ideaView hasTime];
    timeLabel.hidden = timeIconView.hidden;
    if(!timeLabel.hidden){
        timeLabel.font = [UIFont fontWithName:DEFAULT_FONT_FAMILY size:12.0];
        timeLabel.text = [NSString stringWithFormat:@"%@ - %@", ideaView.startTime, ideaView.endTime];
        timeLabel.textColor = [UIColor colorWithRed:0.53f green:0.53f blue:0.53f alpha:1.00f];
        [timeLabel setFrame:CGRectMake(timeLabel.frame.origin.x, timeIconView.frame.origin.y, timeLabel.frame.size.width, timeLabel.frame.size.height)];
    }
    [addressIconView setFrame:CGRectMake(addressIconView.frame.origin.x, [self getViewOriginY:addressIconView byUpperView:timeIconView heightGap:IDEA_INFO_ICON_HEIGHT_GAP], addressIconView.frame.size.width, addressIconView.frame.size.height)];
    addressIconView.hidden = ![ideaView hasPlace];
    addressLabel.hidden = addressIconView.hidden;
    if(!addressLabel.hidden){
        addressLabel.font = [UIFont fontWithName:DEFAULT_FONT_FAMILY size:12.0];
        addressLabel.text = ideaView.place;
        addressLabel.textColor = [UIColor colorWithRed:0.53f green:0.53f blue:0.53f alpha:1.00f];
        [addressLabel setFrame:CGRectMake(addressLabel.frame.origin.x, addressIconView.frame.origin.y, addressLabel.frame.size.width, addressLabel.frame.size.height)];
    }
    [categoryIconView setFrame:CGRectMake(categoryIconView.frame.origin.x, [self getViewOriginY:categoryIconView byUpperView:addressIconView heightGap:IDEA_INFO_ICON_HEIGHT_GAP], categoryIconView.frame.size.width, categoryIconView.frame.size.height)];
    categoryIconView.hidden = ![ideaView hasCategory];
    categoryLabel.hidden = categoryIconView.hidden;
    if(!categoryLabel.hidden){
        categoryLabel.font = [UIFont fontWithName:DEFAULT_FONT_FAMILY size:12.0];
        categoryLabel.text = ideaView.categoryName;
        categoryLabel.textColor = [UIColor colorWithRed:0.53f green:0.53f blue:0.53f alpha:1.00f];
        [categoryLabel setFrame:CGRectMake(categoryLabel.frame.origin.x, categoryIconView.frame.origin.y, categoryLabel.frame.size.width, categoryLabel.frame.size.height)];
    }
    
    [moreButton setFrame:CGRectMake(moreButton.frame.origin.x, [self getViewOriginY:moreButton byUpperView:categoryIconView heightGap:IDEA_DEFAULT_HEIGHT_GAP], moreButton.frame.size.width, moreButton.frame.size.height)];
    
    separatorView.backgroundColor = [UIColor colorWithRed:0.87f green:0.87f blue:0.87f alpha:1.00f];
    [separatorView setFrame:CGRectMake(separatorView.frame.origin.x, [self getViewOriginY:separatorView byUpperView:moreButton heightGap:IDEA_DEFAULT_HEIGHT_GAP], separatorView.frame.size.width, separatorView.frame.size.height)];
    
    [postIdeaButton setFrame:CGRectMake(postIdeaButton.frame.origin.x, [self getViewOriginY:postIdeaButton byUpperView:separatorView heightGap:IDEA_DEFAULT_HEIGHT_GAP], postIdeaButton.frame.size.width, postIdeaButton.frame.size.height)];
    postIdeaButton.enabled = ![ideaView.hasUsed boolValue];
    
    [shareButton setFrame:CGRectMake(shareButton.frame.origin.x, [self getViewOriginY:shareButton byUpperView:separatorView heightGap:IDEA_DEFAULT_HEIGHT_GAP], shareButton.frame.size.width, shareButton.frame.size.height)];
    
    if (ideaView.useCount.intValue > 0) {
        //有xxx人想去
        self.separatorView2.hidden = NO;
        self.showUsersButton.hidden = NO;
        separatorView2.backgroundColor = [UIColor colorWithRed:0.87f green:0.87f blue:0.87f alpha:1.00f];
        [separatorView2 setFrame:CGRectMake(separatorView2.frame.origin.x, [self getViewOriginY:separatorView2 byUpperView:postIdeaButton heightGap:IDEA_DEFAULT_HEIGHT_GAP], separatorView2.frame.size.width, separatorView2.frame.size.height)];
        [showUsersButton setFrame:CGRectMake(showUsersButton.frame.origin.x, [self getViewOriginY:showUsersButton byUpperView:separatorView2 heightGap:IDEA_DEFAULT_HEIGHT_GAP], showUsersButton.frame.size.width, showUsersButton.frame.size.height)];
        [showUsersButton setTitle:[NSString stringWithFormat:@"共%d人想去", ideaView.useCount.intValue] forState:UIControlStateNormal];
        [showUsersButton setTitleColor:[UIColor colorWithRed:0.40f green:0.40f blue:0.40f alpha:1.00f] forState:UIControlStateNormal];
        [showUsersButton setTitleColor:[UIColor colorWithRed:0.40f green:0.40f blue:0.40f alpha:1.00f] forState:UIControlStateHighlighted];
        showUsersButton.titleLabel.font = [UIFont fontWithName:DEFAULT_FONT_FAMILY size:13.0];
    } else {
        self.separatorView2.hidden = YES;
        self.showUsersButton.hidden = YES;
    }
    
    if(!imageView.hidden){
        SDWebImageManager *manager = [SDWebImageManager sharedManager];
        NSURL *imageURL = [NSURL URLWithString:ideaView.bigPic];
        [manager downloadWithURL:imageURL delegate:self options:0 success:^(UIImage *image) {
            NSInteger height = image.size.height;
            [imageView setFrame:CGRectMake(imageView.frame.origin.x, [self getViewOriginY:imageView byUpperView:contentLabel heightGap:IDEA_DEFAULT_HEIGHT_GAP], imageView.frame.size.width, height/2)];
            imageView.image = image;
            imageView.layer.shouldRasterize = YES;
            imageView.layer.masksToBounds = YES;
            imageView.layer.cornerRadius = 5.0;
            //重新定位以下元素
            [self resetViewFrame];
            
        } failure:nil];
    }
    [self resetViewFrame];
}

- (CGFloat) getViewOriginY:(UIView *)view byUpperView:(UIView *)upperView heightGap:(float)heightGap{
    if(upperView == nil){
        return view.frame.origin.y;
    }else {
        float y = upperView.frame.origin.y;
        return y + (upperView.hidden ? 0.0 : (upperView.frame.size.height + heightGap));
    }
}

- (void) resetViewFrame{
    CGFloat infoViewHeight = (showUsersButton.hidden ? (postIdeaButton.frame.origin.y + postIdeaButton.frame.size.height) : (showUsersButton.frame.origin.y + showUsersButton.frame.size.height)) + IDEA_DEFAULT_HEIGHT_GAP;
    
    [infoView setFrame:CGRectMake(infoView.frame.origin.x, [self getViewOriginY:infoView byUpperView:imageView heightGap:IDEA_DEFAULT_HEIGHT_GAP], infoView.frame.size.width, infoViewHeight)];
    
    [contentView setContentSize:CGSizeMake([[UIScreen mainScreen] bounds].size.width, infoView.frame.origin.y + infoView.frame.size.height)];
}

- (void)viewDidUnload
{
    [super viewDidUnload];
    // Release any retained subviews of the main view.
    // e.g. self.myOutlet = nil;
    self.contentView = nil;
    self.contentLabel = nil;
    self.addressLabel = nil;
    self.categoryLabel = nil;
    self.timeLabel = nil;
    self.ideaView = nil;
}

- (BOOL)shouldAutorotateToInterfaceOrientation:(UIInterfaceOrientation)interfaceOrientation
{
    return (interfaceOrientation == UIInterfaceOrientationPortrait);
}

- (IBAction)moreIdea:(id)sender{
    [self.navigationController popViewControllerAnimated:YES];
}

- (IBAction)postIdea:(id)sender{
    MBProgressHUD *hud = [MBProgressHUD showHUDAddedTo:self.contentView animated:YES];
    hud.labelText = @"操作中...";
    dispatch_async(dispatch_get_global_queue(DISPATCH_QUEUE_PRIORITY_HIGH, 0), ^{
        NSDictionary *params = [[NSDictionary alloc] initWithObjectsAndKeys:ideaView.ideaId, @"ideaId", nil];
        __block ASIFormDataRequest *_request = [HttpRequestSender postRequestWithUrl:[UrlUtils urlStringWithUri:@"sendPost"] withParams:params];
        __unsafe_unretained ASIHTTPRequest *request = _request;
        [request setCompletionBlock:^{
            [MBProgressHUD hideHUDForView:self.contentView animated:YES];
            NSString *responseString = [request responseString];
            NSMutableDictionary *jsonResult = [responseString JSONValue];
            if([jsonResult valueForKey:@"success"] == [NSNumber numberWithBool:YES]){
                ideaView.hasUsed = [NSNumber numberWithInt:1];
                ideaView.useCount = [NSNumber numberWithInt:(ideaView.useCount.intValue + 1)];
                UIButton *wantToButton = (UIButton *)sender;
                wantToButton.enabled = NO;
                hud.customView = [[UIImageView alloc] initWithImage:[UIImage imageNamed:@"37x-Checkmark.png"]];
                hud.mode = MBProgressHUDModeCustomView;
                hud.labelText = @"保存成功";
                [hud hide:YES afterDelay:1];
                return;
            }
            NSString *errorInfo = [jsonResult valueForKey:@"errorInfo"];
            NSLog(@"%@", errorInfo);
            if (errorInfo == nil || [errorInfo isEqual:[NSNull null]] || [errorInfo isEqualToString:@""]) {
                errorInfo = SERVER_ERROR_INFO;
            }
            [MBProgressHUD hideHUDForView:self.contentView animated:YES];
            [MessageShow error:errorInfo onView:self.contentView];
        }];
        [request setFailedBlock:^{
            [MBProgressHUD hideHUDForView:self.contentView animated:YES];
            [MessageShow error:SERVER_ERROR_INFO onView:self.contentView];
        }];
        [request startAsynchronous];
    });
}

- (IBAction)showUsedUsers:(id)sender
{
    IdeaUsersViewController *ideaUsersViewController = [[IdeaUsersViewController alloc] initWithStyle:UITableViewStylePlain];
    ideaUsersViewController.ideaView = ideaView;
    [self.navigationController pushViewController:ideaUsersViewController animated:YES];
}

@end
