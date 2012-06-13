//
//  PostDetailViewController.m
//  juzhai
//
//  Created by JiaJun Wu on 12-6-7.
//  Copyright (c) 2012年 __MyCompanyName__. All rights reserved.
//

#import "PostDetailViewController.h"
#import "SDWebImage/UIImageView+WebCache.h"
#import <QuartzCore/QuartzCore.h>
#import "UserView.h"
#import "PostView.h"
#import "BaseData.h"

@interface PostDetailViewController ()
- (CGFloat) getViewOriginY:(UIView *)view byUpperView:(UIView *)upperView heightGap:(float)heightGap;
- (void) resetViewFrame;
@end

@implementation PostDetailViewController

@synthesize userView;
@synthesize logoView;
@synthesize nicknameLabel;
@synthesize userInfoLabel;
@synthesize postScrollView;
@synthesize postInfoView;
@synthesize postImageView;
@synthesize contentLabel;
@synthesize addressLabel;
@synthesize categoryLabel;
@synthesize timeLabel;
@synthesize timeIconView;
@synthesize addressIconView;
@synthesize categoryIconView;


- (id)initWithNibName:(NSString *)nibNameOrNil bundle:(NSBundle *)nibBundleOrNil
{
    self = [super initWithNibName:nibNameOrNil bundle:nibBundleOrNil];
    if (self) {
        // Custom initialization
        self.title = @"拒宅详情";
    }
    return self;
}

- (void)viewDidLoad
{
    [super viewDidLoad];
    // Do any additional setup after loading the view from its nib.
//    logoView.image = [UIImage imageNamed:USER_DEFAULT_LOGO];
    SDWebImageManager *manager = [SDWebImageManager sharedManager];
    NSURL *imageURL = [NSURL URLWithString:userView.logo];
    [manager downloadWithURL:imageURL delegate:self options:0 success:^(UIImage *image) {
        logoView.image = image;
        logoView.layer.shouldRasterize = YES;
        logoView.layer.masksToBounds = YES;
        logoView.layer.cornerRadius = 3.0;
    } failure:nil];
    
    nicknameLabel.font = [UIFont fontWithName:DEFAULT_FONT_FAMILY size:11.0];
    if(userView.gender.intValue == 0){
        nicknameLabel.textColor = [UIColor redColor];
    }else {
        nicknameLabel.textColor = [UIColor blueColor];
    }
    nicknameLabel.text = userView.nickname;
    
    userInfoLabel.font = [UIFont fontWithName:DEFAULT_FONT_FAMILY size:11.0];
    userInfoLabel.textColor = [UIColor grayColor];
    NSMutableString *info = [NSMutableString stringWithCapacity:0];
    if(![userView.birthYear isEqual:[NSNull null]]){
        NSDate *now = [NSDate date];
        NSCalendar *cal = [NSCalendar currentCalendar];
        unsigned int unitFlags = NSYearCalendarUnit;
        NSDateComponents *dd = [cal components:unitFlags fromDate:now];
        int age = [dd year] - userView.birthYear.intValue;
        [info appendFormat:@"%d岁 ", age];
    }
    if(![userView.constellation isEqual:[NSNull null]]){
        [info appendFormat:@"%@", userView.constellation];
    }
    userInfoLabel.text = info;
    
    contentLabel.font = [UIFont fontWithName:DEFAULT_FONT_FAMILY size:15.0];
    contentLabel.textColor = [UIColor whiteColor];
    contentLabel.text = self.userView.post.content;
    CGSize contentSize = [contentLabel.text sizeWithFont:contentLabel.font constrainedToSize:CGSizeMake(contentLabel.frame.size.width, 200.0) lineBreakMode:UILineBreakModeCharacterWrap];
    [contentLabel setFrame:CGRectMake(contentLabel.frame.origin.x, [self getViewOriginY:contentLabel byUpperView:nil heightGap:POST_DEFAULT_HEIGHT_GAP], contentSize.width, contentSize.height)];
    
//    postImageView.image = [UIImage imageNamed:IDEA_DEFAULT_PIC];
    [postImageView setFrame:CGRectMake(postImageView.frame.origin.x, [self getViewOriginY:postImageView byUpperView:contentLabel heightGap:POST_DEFAULT_HEIGHT_GAP], postImageView.frame.size.width, postImageView.frame.size.height)];
    [postImageView setHidden:userView.post.bigPic == nil || [userView.post.bigPic isEqual:[NSNull null]] || [userView.post.bigPic isEqualToString:@""]];
    
    [timeIconView setFrame:CGRectMake(timeIconView.frame.origin.x, [self getViewOriginY:timeIconView byUpperView:nil heightGap:POST_INFO_ICON_HEIGHT_GAP], timeIconView.frame.size.width, timeIconView.frame.size.height)];
    timeIconView.hidden = ![userView.post hasTime];
    timeLabel.hidden = timeIconView.hidden;
    if(!timeLabel.hidden){
        timeLabel.font = [UIFont fontWithName:DEFAULT_FONT_FAMILY size:11.0];
        timeLabel.text = userView.post.date;
        [timeLabel setFrame:CGRectMake(timeLabel.frame.origin.x, timeIconView.frame.origin.y, timeLabel.frame.size.width, timeLabel.frame.size.height)];
    }
    [addressIconView setFrame:CGRectMake(addressIconView.frame.origin.x, [self getViewOriginY:addressIconView byUpperView:timeIconView heightGap:POST_INFO_ICON_HEIGHT_GAP], addressIconView.frame.size.width, addressIconView.frame.size.height)];
    addressIconView.hidden = ![userView.post hasPlace];
    addressLabel.hidden = addressIconView.hidden;
    if(!addressLabel.hidden){
        addressLabel.font = [UIFont fontWithName:DEFAULT_FONT_FAMILY size:11.0];
        addressLabel.text = userView.post.place;
        [addressLabel setFrame:CGRectMake(addressLabel.frame.origin.x, addressIconView.frame.origin.y, addressLabel.frame.size.width, addressLabel.frame.size.height)];
    }
    [categoryIconView setFrame:CGRectMake(categoryIconView.frame.origin.x, [self getViewOriginY:categoryIconView byUpperView:addressIconView heightGap:POST_INFO_ICON_HEIGHT_GAP], categoryIconView.frame.size.width, categoryIconView.frame.size.height)];
    categoryIconView.hidden = ![userView.post hasCategory];
    categoryLabel.hidden = categoryIconView.hidden;
    if(!categoryLabel.hidden){
        categoryLabel.font = [UIFont fontWithName:DEFAULT_FONT_FAMILY size:11.0];
        categoryLabel.text = userView.post.categoryName;
        [categoryLabel setFrame:CGRectMake(categoryLabel.frame.origin.x, categoryIconView.frame.origin.y, categoryLabel.frame.size.width, categoryLabel.frame.size.height)];
    }
    
    if(!postImageView.hidden){
        SDWebImageManager *manager = [SDWebImageManager sharedManager];
        NSURL *imageURL = [NSURL URLWithString:userView.post.bigPic];
        [manager downloadWithURL:imageURL delegate:self options:0 success:^(UIImage *image) {
            float height = image.size.height;
            postImageView.image = image;
            [postImageView setFrame:CGRectMake(postImageView.frame.origin.x, postImageView.frame.origin.y, postImageView.frame.size.width, height/2)];
            postImageView.layer.shouldRasterize = YES;
            postImageView.layer.masksToBounds = YES;
            postImageView.layer.cornerRadius = 5.0;
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
    NSLog(@"%f", categoryIconView.frame.origin.y);
    [postInfoView setFrame:CGRectMake(postInfoView.frame.origin.x, [self getViewOriginY:postInfoView byUpperView:postImageView heightGap:POST_DEFAULT_HEIGHT_GAP], postInfoView.frame.size.width, categoryIconView.frame.origin.y + (categoryIconView.hidden ? 0.0 : categoryIconView.frame.size.height + POST_DEFAULT_HEIGHT_GAP))];
    
    [postScrollView setContentSize:CGSizeMake([[UIScreen mainScreen] bounds].size.width, postInfoView.frame.origin.y + postInfoView.frame.size.height)];
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

@end
