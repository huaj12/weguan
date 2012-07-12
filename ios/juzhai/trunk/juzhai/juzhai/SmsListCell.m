//
//  SmsListCell.m
//  juzhai
//
//  Created by JiaJun Wu on 12-7-9.
//  Copyright (c) 2012年 __MyCompanyName__. All rights reserved.
//

#import "SmsListCell.h"
#import "DialogView.h"
#import "SDWebImage/UIImageView+WebCache.h"
#import <QuartzCore/QuartzCore.h>
#import "UserView.h"
#import "HomeViewController.h"
#import "Constant.h"

@implementation SmsListCell

@synthesize userLogoView;
@synthesize nicknameLabel;
@synthesize infoLabel;
@synthesize targetImageView;
@synthesize latestContentLabel;
@synthesize timeLabel;

- (id)initWithStyle:(UITableViewCellStyle)style reuseIdentifier:(NSString *)reuseIdentifier
{
    self = [super initWithStyle:style reuseIdentifier:reuseIdentifier];
    if (self) {
        // Initialization code
    }
    return self;
}

- (void)setSelected:(BOOL)selected animated:(BOOL)animated
{
    [super setSelected:selected animated:animated];

    // Configure the view for the selected state
}

+ (id)cellFromNib
{
    SmsListCell *cell = nil;
    NSArray *nib = [[NSBundle mainBundle] loadNibNamed:@"SmsListCell" owner:self options:nil];
    for(id oneObject in nib){
        if([oneObject isKindOfClass:[SmsListCell class]]){
            cell = (SmsListCell *) oneObject;
        }
    }
    [cell setBackground];
    return cell;
}

- (void)logoClick:(UIGestureRecognizer *)gestureRecognizer {  
    HomeViewController *homeViewController = [[HomeViewController alloc] initWithNibName:@"HomeViewController" bundle:nil];
    homeViewController.hidesBottomBarWhenPushed = YES;
    homeViewController.userView = _dialogView.targetUser;
    UIViewController *viewController = (UIViewController *)self.nextResponder.nextResponder;
    [viewController.navigationController pushViewController:homeViewController animated:YES];
}


- (void)setBackground{
    self.backgroundColor = [UIColor colorWithRed:0.93f green:0.93f blue:0.93f alpha:1.00f];
    UIView *selectBgColorView = [[UIView alloc] init];
    selectBgColorView.backgroundColor = [UIColor colorWithRed:0.96f green:0.96f blue:0.96f alpha:1.00f];
    self.selectedBackgroundView = selectBgColorView;
}


- (void)redrawn:(DialogView *)dialogView{
    _dialogView = dialogView;
    
    //头像
    userLogoView.image = [UIImage imageNamed:FACE_LOADING_IMG];
    SDWebImageManager *manager = [SDWebImageManager sharedManager];
    NSURL *imageURL = [NSURL URLWithString:_dialogView.targetUser.smallLogo];
    [manager downloadWithURL:imageURL delegate:self options:0 success:^(UIImage *image) {
        userLogoView.image = image;
        userLogoView.layer.shouldRasterize = YES;
        userLogoView.layer.masksToBounds = YES;
        userLogoView.layer.cornerRadius = 5.0;
    } failure:nil];
    UITapGestureRecognizer *singleTap = [[UITapGestureRecognizer alloc] initWithTarget:self action:@selector(logoClick:)];
    [userLogoView addGestureRecognizer:singleTap];
    
    //昵称
    nicknameLabel.font = [UIFont fontWithName:DEFAULT_FONT_FAMILY size:12.0];
    if(_dialogView.targetUser.gender.intValue == 0){
        nicknameLabel.textColor = [UIColor colorWithRed:1.00f green:0.40f blue:0.60f alpha:1.00f];
        nicknameLabel.highlightedTextColor = [UIColor colorWithRed:1.00f green:0.40f blue:0.60f alpha:1.00f];
    }else {
        nicknameLabel.textColor = [UIColor blueColor];
        nicknameLabel.highlightedTextColor = [UIColor blueColor];
    }
    CGSize nicknameSize = [_dialogView.targetUser.nickname sizeWithFont:nicknameLabel.font constrainedToSize:CGSizeMake(120.0f, nicknameLabel.frame.size.height) lineBreakMode:UILineBreakModeTailTruncation];
    [nicknameLabel setFrame:CGRectMake(nicknameLabel.frame.origin.x, nicknameLabel.frame.origin.y, nicknameSize.width, nicknameSize.height)];
    nicknameLabel.text = _dialogView.targetUser.nickname;
    
    //基本资料
    infoLabel.font = [UIFont fontWithName:DEFAULT_FONT_FAMILY size:12.0];
    infoLabel.textColor = [UIColor colorWithRed:0.60f green:0.60f blue:0.60f alpha:1.00f];
    infoLabel.highlightedTextColor = [UIColor colorWithRed:0.60f green:0.60f blue:0.60f alpha:1.00f];
    infoLabel.text = [_dialogView.targetUser basicInfo];
    CGSize infoSize = [infoLabel.text sizeWithFont:infoLabel.font constrainedToSize:CGSizeMake(240 - nicknameSize.width - 72, infoLabel.frame.size.height) lineBreakMode:UILineBreakModeTailTruncation];
    [infoLabel setFrame:CGRectMake(nicknameLabel.frame.origin.x + nicknameLabel.frame.size.width + 10.0, infoLabel.frame.origin.y, infoSize.width, infoSize.height)];
    
    //时间
    timeLabel.font = [UIFont fontWithName:DEFAULT_FONT_FAMILY size:12.0];
    timeLabel.textColor = [UIColor colorWithRed:0.71f green:0.71f blue:0.71f alpha:1.00f];
    timeLabel.highlightedTextColor = [UIColor colorWithRed:0.71f green:0.71f blue:0.71f alpha:1.00f];
    NSDate *createTime = [NSDate dateWithTimeIntervalSince1970:_dialogView.createTime];
    NSTimeInterval interval = - [createTime timeIntervalSinceNow];
    NSString *timeText;
    if (interval < 60) {
        NSInteger beforeSec = interval;
        timeText = [NSString stringWithFormat:@"%d秒前", beforeSec];
    } else if (0 < interval/60 && interval/60 < 60) {
        NSInteger beforeMin = interval/60;
        timeText = [NSString stringWithFormat:@"%d分钟前", beforeMin];
    } else if (0 < interval/3600 && interval/3600 < 24) {
        NSInteger beforeHour = interval/3600;
        timeText = [NSString stringWithFormat:@"%d小时前", beforeHour];
    } else if (interval/3600/24 == 1) {
        timeText = @"昨天";
    } else if (interval/3600/24 == 2) {
        timeText = @"前天";
    } else {
        NSDateFormatter *formatter = [[NSDateFormatter alloc] init];
        [formatter setDateFormat:@"yyyy-MM-dd"];
        timeText = [formatter stringFromDate:createTime];
    }
    timeLabel.text = timeText;
    CGSize timeSize = [timeLabel.text sizeWithFont:timeLabel.font constrainedToSize:CGSizeMake(72, infoLabel.frame.size.height) lineBreakMode:UILineBreakModeTailTruncation];
    [timeLabel setFrame:CGRectMake(310 - timeSize.width, timeLabel.frame.origin.y, timeSize.width, timeSize.height)];
    
    if ([dialogView isSendToMe]) {
        targetImageView.image = [UIImage imageNamed:@"tasaytome.png"];
    }else {
        targetImageView.image = [UIImage imageNamed:@"isaytota.png"];
    }
    
    //最新一条内容
    latestContentLabel.font = [UIFont fontWithName:DEFAULT_FONT_FAMILY size:12.0];
    latestContentLabel.textColor = [UIColor colorWithRed:0.40f green:0.40f blue:0.40f alpha:1.00f];
    latestContentLabel.highlightedTextColor = [UIColor colorWithRed:0.40f green:0.40f blue:0.40f alpha:1.00f];
    latestContentLabel.text = _dialogView.latestContent;
    CGSize latestContentSize = [latestContentLabel.text sizeWithFont:latestContentLabel.font constrainedToSize:CGSizeMake(320 - userLogoView.frame.size.width - targetImageView.frame.size.width - 36, latestContentLabel.frame.size.height) lineBreakMode:UILineBreakModeTailTruncation];
    [latestContentLabel setFrame:CGRectMake(latestContentLabel.frame.origin.x, latestContentLabel.frame.origin.y, latestContentSize.width, latestContentSize.height)];
}

+ (CGFloat) heightForCell:(id)objView
{
    return 60;
}

@end
