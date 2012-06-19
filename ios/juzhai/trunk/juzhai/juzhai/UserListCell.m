//
//  UserListCell.m
//  juzhai
//
//  Created by JiaJun Wu on 12-5-29.
//  Copyright (c) 2012年 __MyCompanyName__. All rights reserved.
//

#import <QuartzCore/QuartzCore.h>
#import "UserListCell.h"
#import "SDWebImage/UIImageView+WebCache.h"
#import "UserView.h"
#import "PostView.h"
#import "BaseData.h"
#import "MBProgressHUD.h"
#import "ASIHTTPRequest.h"
#import "HttpRequestSender.h"
#import "SBJson.h"

@implementation UserListCell

//@synthesize userView;

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

- (void) setBackground{
    UIImage *image = [UIImage imageNamed:BG_PNG];
    UIGraphicsBeginImageContextWithOptions(CGSizeMake(self.frame.size.width, image.size.height + 30.0), NO, 0.0);
    [image drawInRect:CGRectMake(0.0, 0 + 30.0, image.size.width, image.size.height)];
    UIImage* resultImage = UIGraphicsGetImageFromCurrentImageContext();
    UIGraphicsEndImageContext();
    
    UIImageView *imageview = [[UIImageView alloc] initWithFrame:CGRectMake(0, 0, resultImage.size.width, self.frame.size.height)]; 
    [imageview setImage:[resultImage stretchableImageWithLeftCapWidth:0 topCapHeight:BG_CAP_HEIHGT + 30]];
    [self setBackgroundView: imageview];
}

- (void) redrawn:(UserView *)userView{
    _userView = userView;
    UIImageView *imageView = (UIImageView *)[self viewWithTag:USER_LOGO_TAG];
    imageView.image = [UIImage imageNamed:USER_DEFAULT_LOGO];
    SDWebImageManager *manager = [SDWebImageManager sharedManager];
    NSURL *imageURL = [NSURL URLWithString:userView.logo];
    [manager downloadWithURL:imageURL delegate:self options:0 success:^(UIImage *image) {
        imageView.image = image;
        imageView.layer.shouldRasterize = YES;
        imageView.layer.masksToBounds = YES;
        imageView.layer.cornerRadius = 3.0;
    } failure:nil];
    
    UILabel *nicknameLabel = (UILabel *)[self viewWithTag:USER_NICKNAME_TAG];
    nicknameLabel.font = [UIFont fontWithName:DEFAULT_FONT_FAMILY size:11.0];
    if(userView.gender.intValue == 0){
        nicknameLabel.textColor = [UIColor redColor];
    }else {
        nicknameLabel.textColor = [UIColor blueColor];
    }
    CGSize nicknameSize = [userView.nickname sizeWithFont:nicknameLabel.font constrainedToSize:CGSizeMake(100.0f, nicknameLabel.frame.size.height) lineBreakMode:UILineBreakModeTailTruncation];
    [nicknameLabel setFrame:CGRectMake(nicknameLabel.frame.origin.x, nicknameLabel.frame.origin.y, nicknameSize.width, nicknameSize.height)];
    nicknameLabel.text = userView.nickname;
    
    UILabel *infoLabel = (UILabel *)[self viewWithTag:USER_INFO_TAG];
    infoLabel.font = [UIFont fontWithName:DEFAULT_FONT_FAMILY size:11.0];
    infoLabel.textColor = [UIColor grayColor];
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
    CGSize infoSize = [info sizeWithFont:infoLabel.font constrainedToSize:infoLabel.frame.size lineBreakMode:UILineBreakModeTailTruncation];
    [infoLabel setFrame:CGRectMake(nicknameLabel.frame.origin.x + nicknameLabel.frame.size.width + 10.0, infoLabel.frame.origin.y, infoSize.width, infoSize.height)];
    infoLabel.text = info;
    
    UILabel *contentLabel = (UILabel *)[self viewWithTag:POST_CONTENT_TAG];
    contentLabel.font = [UIFont fontWithName:DEFAULT_FONT_FAMILY size:14.0];
    contentLabel.textColor = [UIColor grayColor];
    contentLabel.text = [NSString stringWithFormat:@"%@：%@", userView.post.purpose, userView.post.content];
    CGSize contentSize = [contentLabel.text sizeWithFont:contentLabel.font constrainedToSize:CGSizeMake(contentLabel.frame.size.width, 200.0) lineBreakMode:UILineBreakModeCharacterWrap];
    [contentLabel setFrame:CGRectMake(contentLabel.frame.origin.x, contentLabel.frame.origin.y, contentSize.width, contentSize.height)];
    
    UIImageView *postImageView = (UIImageView *)[self viewWithTag:POST_IMAGE_TAG];
    if(![userView.post.pic isEqual:[NSNull null]]){
        postImageView.image = [UIImage imageNamed:POST_DEFAULT_PIC];
        NSURL *postImageURL = [NSURL URLWithString:userView.post.pic];
        [postImageView setFrame:CGRectMake(postImageView.frame.origin.x, contentLabel.frame.origin.y + contentSize.height + 10.0, postImageView.frame.size.width, postImageView.frame.size.height)];
        [manager downloadWithURL:postImageURL delegate:self options:0 success:^(UIImage *image) {
            UIGraphicsBeginImageContext(CGSizeMake(postImageView.frame.size.width, postImageView.frame.size.height));
            [image drawInRect:CGRectMake(0, 0, postImageView.frame.size.width, image.size.height*(postImageView.frame.size.width/image.size.width))];
            UIImage* resultImage = UIGraphicsGetImageFromCurrentImageContext();
            UIGraphicsEndImageContext();
            postImageView.image = resultImage;
            postImageView.layer.shouldRasterize = YES;
            postImageView.layer.masksToBounds = YES;
            postImageView.layer.cornerRadius = 5.0;
        } failure:nil];
        [postImageView setHidden:NO];
    }else {
        [postImageView setHidden:YES];
    }
    
    UIButton *respButton = (UIButton *)[self viewWithTag:RESPONSE_BUTTON_TAG];
    NSString *buttonTitle = [NSString stringWithFormat:@"%d", userView.post.respCnt.intValue];
    CGSize respButtonTitleSize = [buttonTitle sizeWithFont:[UIFont fontWithName:DEFAULT_FONT_FAMILY size:11.0] constrainedToSize:CGSizeMake(100.0f, 25.0f)lineBreakMode:UILineBreakModeHeadTruncation];
    [respButton setTitle:buttonTitle forState:UIControlStateNormal];
    [respButton setTitleColor:[UIColor blackColor] forState:UIControlStateNormal];
    respButton.titleLabel.font = [UIFont fontWithName:DEFAULT_FONT_FAMILY size:11.0];
    
    respButton.enabled = ![userView.post.hasResp boolValue];
    UIImage *normalImg = [[UIImage imageNamed:NORMAL_RESP_BUTTON_IMAGE] stretchableImageWithLeftCapWidth:WANT_BUTTON_CAP_WIDTH topCapHeight:0.0];
    UIImage *highlightedImg = [[UIImage imageNamed:HIGHLIGHT_RESP_BUTTON_IMAGE] stretchableImageWithLeftCapWidth:WANT_BUTTON_CAP_WIDTH topCapHeight:0.0];
    UIImage *disabledImg = [[UIImage imageNamed:DISABLE_RESP_BUTTON_IMAGE] stretchableImageWithLeftCapWidth:WANT_BUTTON_CAP_WIDTH topCapHeight:0.0];
    
    [respButton setBackgroundImage:normalImg forState:UIControlStateNormal];
    [respButton setBackgroundImage:highlightedImg forState:UIControlStateHighlighted];
    [respButton setBackgroundImage:disabledImg forState:UIControlStateDisabled];
    
    float respButtonX = 320.0 - 20.0 - (normalImg.size.width + respButtonTitleSize.width);
    float respButtonY = 0.0;
    if(postImageView.hidden){
        respButtonY = contentLabel.frame.origin.y + contentSize.height + 10;
    }else {
        respButtonY = postImageView.frame.origin.y + postImageView.frame.size.height + 10;
    }
    respButton.frame = CGRectMake(respButtonX, respButtonY, normalImg.size.width + respButtonTitleSize.width, respButton.frame.size.height);
    [respButton setTitleEdgeInsets:UIEdgeInsetsMake(0.0, 20.0, 0.0, 0.0)];
}

+(CGFloat) heightForCell:(UserView *)userView{
    float height = 85.0;
    NSString *content = [NSString stringWithFormat:@"%@：%@", userView.post.purpose, userView.post.content];
    CGSize contentSize = [content sizeWithFont:[UIFont fontWithName:DEFAULT_FONT_FAMILY size:14.0] constrainedToSize:CGSizeMake(220, 200.0) lineBreakMode:UILineBreakModeCharacterWrap];
    height += contentSize.height;
    if(![userView.post.pic isEqual:[NSNull null]]){
        height += 110.0;
    }
    return height;
}

-(IBAction)respPost:(id)sender{
    UIView *coverView = self.superview.superview.superview.superview;
    MBProgressHUD *hud = [MBProgressHUD showHUDAddedTo:coverView animated:YES];
    hud.labelText = @"操作中...";
    dispatch_async(dispatch_get_global_queue(DISPATCH_QUEUE_PRIORITY_HIGH, 0), ^{
        NSDictionary *params = [[NSDictionary alloc] initWithObjectsAndKeys:_userView.post.postId, @"postId", nil];
        __block ASIHTTPRequest *_request = [HttpRequestSender getRequestWithUrl:@"http://test.51juzhai.com/app/ios/resppost" withParams:params];
        __unsafe_unretained ASIHTTPRequest *request = _request;
        [request setCompletionBlock:^{
            [MBProgressHUD hideHUDForView:coverView animated:YES];
            NSString *responseString = [request responseString];
            NSMutableDictionary *jsonResult = [responseString JSONValue];
            MBProgressHUD *hud2 = [MBProgressHUD showHUDAddedTo:coverView animated:YES];
            hud2.mode = MBProgressHUDModeText;
            hud2.margin = 10.f;
            hud2.yOffset = 150.f;
            hud2.removeFromSuperViewOnHide = YES;
            [hud2 hide:YES afterDelay:1];
            if([jsonResult valueForKey:@"success"] == [NSNumber numberWithBool:YES]){
                UIButton *respButton = (UIButton *)[self viewWithTag:RESPONSE_BUTTON_TAG];
                respButton.enabled = NO;
                [respButton setTitle:[NSString stringWithFormat:@"%d", respButton.titleLabel.text.intValue + 1] forState:UIControlStateNormal];
                hud2.labelText = @"操作成功";
            }else {
                hud2.labelText = [jsonResult valueForKey:@"errorInfo"];
            }
        }];
        [request setFailedBlock:^{
            [MBProgressHUD hideHUDForView:coverView animated:YES];
        }];
        [request startAsynchronous];
    });
}

@end
