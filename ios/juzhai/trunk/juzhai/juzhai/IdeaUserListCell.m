//
//  IdeaUserListCell.m
//  juzhai
//
//  Created by JiaJun Wu on 12-7-11.
//  Copyright (c) 2012年 __MyCompanyName__. All rights reserved.
//

#import "IdeaUserListCell.h"
#import "UserView.h"
#import <QuartzCore/QuartzCore.h>
#import "SDWebImage/UIImageView+WebCache.h"
#import "BaseData.h"
#import "IdeaUserView.h"

@implementation IdeaUserListCell

@synthesize userLogoView;
@synthesize nicknameLabel;
@synthesize infoLabel;

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

+ (id) cellFromNib
{
    IdeaUserListCell *cell = nil;
    NSArray *nib = [[NSBundle mainBundle] loadNibNamed:@"IdeaUserListCell" owner:self options:nil];
    for(id oneObject in nib){
        if([oneObject isKindOfClass:[IdeaUserListCell class]]){
            cell = (IdeaUserListCell *) oneObject;
        }
    }
    [cell setBackground];
    return cell;
}

- (void) redrawn:(IdeaUserView *)ideaUserView
{
    _ideaUserView = ideaUserView;
//    userLogoView.image = [UIImage imageNamed:USER_DEFAULT_LOGO];
    SDWebImageManager *manager = [SDWebImageManager sharedManager];
    NSURL *imageURL = [NSURL URLWithString:ideaUserView.userView.smallLogo];
    [manager downloadWithURL:imageURL delegate:self options:0 success:^(UIImage *image) {
        userLogoView.image = image;
        userLogoView.layer.shouldRasterize = YES;
        userLogoView.layer.masksToBounds = YES;
        userLogoView.layer.cornerRadius = 3.0;
    } failure:nil];
    
    nicknameLabel.font = [UIFont fontWithName:DEFAULT_FONT_FAMILY size:12.0];
    if(ideaUserView.userView.gender.intValue == 0){
        nicknameLabel.textColor = [UIColor colorWithRed:1.00f green:0.40f blue:0.60f alpha:1.00f];
        nicknameLabel.highlightedTextColor = [UIColor colorWithRed:1.00f green:0.40f blue:0.60f alpha:1.00f];
    }else {
        nicknameLabel.textColor = [UIColor blueColor];
        nicknameLabel.highlightedTextColor = [UIColor blueColor];
    }
    nicknameLabel.text = ideaUserView.userView.nickname;
    
    infoLabel.font = [UIFont fontWithName:DEFAULT_FONT_FAMILY size:12.0];
    infoLabel.textColor = [UIColor colorWithRed:0.60f green:0.60f blue:0.60f alpha:1.00f];
    infoLabel.highlightedTextColor = [UIColor colorWithRed:0.60f green:0.60f blue:0.60f alpha:1.00f];
    infoLabel.text = [ideaUserView.userView basicInfo];
}

- (void) setBackground
{
    self.backgroundColor = [UIColor colorWithRed:0.93f green:0.93f blue:0.93f alpha:1.00f];
    UIView *selectBgColorView = [[UIView alloc] init];
    selectBgColorView.backgroundColor = [UIColor colorWithRed:0.96f green:0.96f blue:0.96f alpha:1.00f];
    self.selectedBackgroundView = selectBgColorView;
}
+ (CGFloat) heightForCell:()objView
{
    return 60.0;
}

- (IBAction)dateHim:(id)sender
{
    
}

@end
