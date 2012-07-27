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
#import "TaHomeViewController.h"
#import "Constant.h"
#import "NSDate+BeforeShowType.h"

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
    TaHomeViewController *taHomeViewController = [[TaHomeViewController alloc] initWithNibName:@"TaHomeViewController" bundle:nil];
    taHomeViewController.hidesBottomBarWhenPushed = YES;
    taHomeViewController.userView = _dialogView.targetUser;
    UIViewController *viewController = (UIViewController *)self.nextResponder.nextResponder;
    [viewController.navigationController pushViewController:taHomeViewController animated:YES];
}


- (void)setBackground{
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
//        userLogoView.layer.borderWidth = 1;
//        userLogoView.layer.borderColor = [UIColor grayColor].CGColor;
    } failure:nil];
    UITapGestureRecognizer *singleTap = [[UITapGestureRecognizer alloc] initWithTarget:self action:@selector(logoClick:)];
    [userLogoView addGestureRecognizer:singleTap];
    
    if ([dialogView isSendToMe]) {
        targetImageView.image = [UIImage imageNamed:@"tasaytome.png"];
    }else {
        targetImageView.image = [UIImage imageNamed:@"isaytota.png"];
    }
    
    //昵称
    nicknameLabel.font = [UIFont fontWithName:DEFAULT_FONT_FAMILY size:14.0];
    if(_dialogView.targetUser.gender.intValue == 0){
        nicknameLabel.textColor = FEMALE_NICKNAME_COLOR;
        nicknameLabel.highlightedTextColor = FEMALE_NICKNAME_COLOR;
    }else {
        nicknameLabel.textColor = MALE_NICKNAME_COLOR;
        nicknameLabel.highlightedTextColor = MALE_NICKNAME_COLOR;
    }
    CGSize nicknameSize = [_dialogView.targetUser.nickname sizeWithFont:nicknameLabel.font constrainedToSize:CGSizeMake(140.0f, nicknameLabel.frame.size.height) lineBreakMode:UILineBreakModeTailTruncation];
    [nicknameLabel setFrame:CGRectMake(nicknameLabel.frame.origin.x, nicknameLabel.frame.origin.y, nicknameSize.width, nicknameSize.height)];
    nicknameLabel.text = _dialogView.targetUser.nickname;
    
    //时间
    timeLabel.font = [UIFont fontWithName:DEFAULT_FONT_FAMILY size:12.0];
    timeLabel.textColor = [UIColor colorWithRed:0.71f green:0.71f blue:0.71f alpha:1.00f];
    timeLabel.highlightedTextColor = [UIColor colorWithRed:0.71f green:0.71f blue:0.71f alpha:1.00f];
    timeLabel.text = [[NSDate dateWithTimeIntervalSince1970:_dialogView.createTime] showBefore];
    CGSize timeSize = [timeLabel.text sizeWithFont:timeLabel.font constrainedToSize:CGSizeMake(72, infoLabel.frame.size.height) lineBreakMode:UILineBreakModeTailTruncation];
    [timeLabel setFrame:CGRectMake(310 - timeSize.width, timeLabel.frame.origin.y, timeSize.width, timeSize.height)];
    
    //基本资料
    infoLabel.font = [UIFont fontWithName:DEFAULT_FONT_FAMILY size:14.0];
    infoLabel.textColor = [UIColor colorWithRed:0.60f green:0.60f blue:0.60f alpha:1.00f];
    infoLabel.highlightedTextColor = [UIColor colorWithRed:0.60f green:0.60f blue:0.60f alpha:1.00f];
    infoLabel.text = [_dialogView.targetUser basicInfo];
    CGSize infoSize = [infoLabel.text sizeWithFont:infoLabel.font constrainedToSize:CGSizeMake(230 - nicknameSize.width - timeSize.width, infoLabel.frame.size.height) lineBreakMode:UILineBreakModeTailTruncation];
    [infoLabel setFrame:CGRectMake(nicknameLabel.frame.origin.x + nicknameLabel.frame.size.width + 8.0, infoLabel.frame.origin.y, infoSize.width, infoSize.height)];
    
    //最新一条内容
    latestContentLabel.font = [UIFont fontWithName:DEFAULT_FONT_FAMILY size:12.0];
    latestContentLabel.textColor = [UIColor blackColor];
    latestContentLabel.highlightedTextColor = [UIColor blackColor];
    latestContentLabel.text = [NSString stringWithFormat:@"\"%@\"" ,_dialogView.latestContent];
}

+ (CGFloat) heightForCell:(id)objView
{
    return 60;
}

@end
