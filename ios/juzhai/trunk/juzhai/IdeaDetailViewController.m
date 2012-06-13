//
//  IdeaDetailViewController.m
//  juzhai
//
//  Created by JiaJun Wu on 12-6-7.
//  Copyright (c) 2012年 __MyCompanyName__. All rights reserved.
//

#import "IdeaDetailViewController.h"
#import "BaseData.h"
#import "CustomNavigationController.h"
#import "IdeaView.h"
#import "SDWebImage/UIImageView+WebCache.h"

@interface IdeaDetailViewController ()
- (float) getViewOriginY:(UIView *)view byUpperView:(UIView *)upperView heightGap:(float)heightGap;
@end

@implementation IdeaDetailViewController

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
    contentLabel.font = [UIFont fontWithName:DEFAULT_FONT_FAMILY size:14.0];
    contentLabel.textColor = [UIColor grayColor];
    contentLabel.text = self.ideaView.content;
    CGSize contentSize = [contentLabel.text sizeWithFont:contentLabel.font constrainedToSize:CGSizeMake(contentLabel.frame.size.width, 200.0) lineBreakMode:UILineBreakModeCharacterWrap];
    [contentLabel setFrame:CGRectMake(contentLabel.frame.origin.x, [self getViewOriginY:contentLabel byUpperView:nil heightGap:DEFAULT_HEIGHT_GAP], contentSize.width, contentSize.height)];
    
//    imageView.image = [UIImage imageNamed:IDEA_DEFAULT_PIC];
    [imageView setFrame:CGRectMake(imageView.frame.origin.x, [self getViewOriginY:imageView byUpperView:contentLabel heightGap:DEFAULT_HEIGHT_GAP], imageView.frame.size.width, imageView.frame.size.height)];
    [imageView setHidden:[ideaView.bigPic isEqual:[NSNull null]]];
    if(!imageView.hidden){
        SDWebImageManager *manager = [SDWebImageManager sharedManager];
        NSURL *imageURL = [NSURL URLWithString:ideaView.bigPic];
        [manager downloadWithURL:imageURL delegate:self options:0 success:^(UIImage *image) {
            float height = image.size.height;
            imageView.image = image;
            [imageView setFrame:CGRectMake(imageView.frame.origin.x, imageView.frame.origin.y, imageView.frame.size.width, height/2)];
            //重新定位以下元素
            [infoView setFrame:CGRectMake(infoView.frame.origin.x, [self getViewOriginY:infoView byUpperView:imageView heightGap:DEFAULT_HEIGHT_GAP], infoView.frame.size.width, infoView.frame.size.height)];
        } failure:nil];
    }
    [infoView setFrame:CGRectMake(infoView.frame.origin.x, [self getViewOriginY:infoView byUpperView:imageView heightGap:DEFAULT_HEIGHT_GAP], infoView.frame.size.width, infoView.frame.size.height)];
    
    [timeIconView setFrame:CGRectMake(timeIconView.frame.origin.x, [self getViewOriginY:timeIconView byUpperView:nil heightGap:INFO_ICON_HEIGHT_GAP], timeIconView.frame.size.width, timeIconView.frame.size.height)];
    timeIconView.hidden = ![ideaView hasTime];
    timeLabel.hidden = timeIconView.hidden;
    if(!timeLabel.hidden){
        timeLabel.font = [UIFont fontWithName:DEFAULT_FONT_FAMILY size:10.0];
        timeLabel.text = [NSString stringWithFormat:@"%@ - %@", ideaView.startTime, ideaView.endTime];
        [timeLabel setFrame:CGRectMake(timeLabel.frame.origin.x, timeIconView.frame.origin.y, timeLabel.frame.size.width, timeLabel.frame.size.height)];
    }
    [addressIconView setFrame:CGRectMake(addressIconView.frame.origin.x, [self getViewOriginY:addressIconView byUpperView:timeIconView heightGap:INFO_ICON_HEIGHT_GAP], addressIconView.frame.size.width, addressIconView.frame.size.height)];
    addressIconView.hidden = ![ideaView hasPlace];
    addressLabel.hidden = addressIconView.hidden;
    if(!addressLabel.hidden){
        addressLabel.font = [UIFont fontWithName:DEFAULT_FONT_FAMILY size:10.0];
        addressLabel.text = ideaView.place;
        [addressLabel setFrame:CGRectMake(addressLabel.frame.origin.x, addressIconView.frame.origin.y, addressLabel.frame.size.width, addressLabel.frame.size.height)];
    }
    [categoryIconView setFrame:CGRectMake(categoryIconView.frame.origin.x, [self getViewOriginY:categoryIconView byUpperView:addressIconView heightGap:INFO_ICON_HEIGHT_GAP], categoryIconView.frame.size.width, categoryIconView.frame.size.height)];
    categoryIconView.hidden = ![ideaView hasCategory];
    categoryLabel.hidden = categoryIconView.hidden;
    if(!categoryLabel.hidden){
        categoryLabel.font = [UIFont fontWithName:DEFAULT_FONT_FAMILY size:10.0];
        categoryLabel.text = ideaView.categoryName;
        [categoryLabel setFrame:CGRectMake(categoryLabel.frame.origin.x, categoryIconView.frame.origin.y, categoryLabel.frame.size.width, categoryLabel.frame.size.height)];
    }
    
    [moreButton setFrame:CGRectMake(moreButton.frame.origin.x, [self getViewOriginY:moreButton byUpperView:categoryIconView heightGap:DEFAULT_HEIGHT_GAP], moreButton.frame.size.width, moreButton.frame.size.height)];
}

- (float) getViewOriginY:(UIView *)view byUpperView:(UIView *)upperView heightGap:(float)heightGap{
    if(upperView == nil){
        return view.frame.origin.y;
    }else {
        float y = upperView.frame.origin.y;
        return y + (upperView.hidden ? 0.0 : (upperView.frame.size.height + heightGap));
    }
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

@end
