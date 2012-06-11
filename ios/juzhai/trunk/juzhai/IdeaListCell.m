//
//  IdeaListCell.m
//  juzhai
//
//  Created by JiaJun Wu on 12-5-27.
//  Copyright (c) 2012年 __MyCompanyName__. All rights reserved.
//

#import <QuartzCore/QuartzCore.h>
#import "IdeaListCell.h"
#import "SDWebImage/UIImageView+WebCache.h"
#import "IdeaView.h"
#import "BaseData.h"
#import "MBProgressHUD.h"
#import "ASIHTTPRequest.h"
#import "HttpRequestSender.h"
#import "SBJson.h"

@implementation IdeaListCell

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

-(void) setBackground{
//    UIImageView *imageview = [[UIImageView alloc] initWithFrame:self.bounds]; 
//    [imageview setImage:[UIImage imageNamed:BG_PNG]];
//    [self setBackgroundView: imageview];
}

- (void) redrawn:(IdeaView *)ideaView{
    _ideaView = ideaView;
    UIImageView *imageView = (UIImageView *)[self viewWithTag:IDEA_IMAGE_TAG];
    imageView.image = [UIImage imageNamed:IDEA_DEFAULT_PIC];
    SDWebImageManager *manager = [SDWebImageManager sharedManager];
    if(![ideaView.pic isEqual:[NSNull null]]){
        NSURL *imageURL = [NSURL URLWithString:ideaView.pic];
        [manager downloadWithURL:imageURL delegate:self options:0 success:^(UIImage *image) {
            UIGraphicsBeginImageContext(CGSizeMake(imageView.frame.size.width, imageView.frame.size.height));
            [image drawInRect:CGRectMake(0, 0, imageView.frame.size.width, image.size.height*(imageView.frame.size.width/image.size.width))];
            UIImage* resultImage = UIGraphicsGetImageFromCurrentImageContext();
            UIGraphicsEndImageContext();
            imageView.image = resultImage;
            
            imageView.layer.shouldRasterize = YES;
            imageView.layer.masksToBounds = YES;
            imageView.layer.cornerRadius = 5.0;
        } failure:nil];
    }
    
    UILabel *contentLabel = (UILabel *)[self viewWithTag:IDEA_CONTENT_TAG];
    contentLabel.font = [UIFont fontWithName:DEFAULT_FONT_FAMILY size:14.0];
    CGSize labelsize = [ideaView.content sizeWithFont:contentLabel.font constrainedToSize:contentLabel.frame.size lineBreakMode:UILineBreakModeCharacterWrap];
    [contentLabel setFrame:CGRectMake(contentLabel.frame.origin.x, contentLabel.frame.origin.y, labelsize.width, labelsize.height)];
    contentLabel.text = ideaView.content;
    
    UIButton *wantToButton = (UIButton *)[self viewWithTag:IDEA_WANT_TO_TAG];
    NSString *buttonTitle = [NSString stringWithFormat:@"%d", ideaView.useCount.intValue];
    CGSize wgoButtonTitleSize = [buttonTitle sizeWithFont:[UIFont fontWithName:DEFAULT_FONT_FAMILY size:11.0] constrainedToSize:CGSizeMake(100.0f, 25.0f)lineBreakMode:UILineBreakModeHeadTruncation];
    [wantToButton setTitle:buttonTitle forState:UIControlStateNormal];
    [wantToButton setTitleColor:[UIColor blackColor] forState:UIControlStateNormal];
    wantToButton.titleLabel.font = [UIFont fontWithName:DEFAULT_FONT_FAMILY size:11.0];
    
    wantToButton.enabled = ![ideaView.hasUsed boolValue];
    UIImage *normalImg = [[UIImage imageNamed:NORMAL_WANT_BUTTON_IMAGE] stretchableImageWithLeftCapWidth:WANT_BUTTON_CAP_WIDTH topCapHeight:0.0];
    UIImage *highlightedImg = [[UIImage imageNamed:HIGHLIGHT_WANT_BUTTON_IMAGE] stretchableImageWithLeftCapWidth:WANT_BUTTON_CAP_WIDTH topCapHeight:0.0];
    UIImage *disabledImg = [[UIImage imageNamed:DISABLE_WANT_BUTTON_IMAGE] stretchableImageWithLeftCapWidth:WANT_BUTTON_CAP_WIDTH topCapHeight:0.0];
    [wantToButton setBackgroundImage:normalImg forState:UIControlStateNormal];
    [wantToButton setBackgroundImage:highlightedImg forState:UIControlStateHighlighted];
    [wantToButton setBackgroundImage:disabledImg forState:UIControlStateDisabled];
    
    wantToButton.frame = CGRectMake(320.0 - 10.0 - (normalImg.size.width + wgoButtonTitleSize.width), wantToButton.frame.origin.y, normalImg.size.width + wgoButtonTitleSize.width, wantToButton.frame.size.height);
    [wantToButton setTitleEdgeInsets:UIEdgeInsetsMake(0.0, 20.0, 0.0, 0.0)];
}

+(CGFloat) heightForCell:(IdeaView *)IdeaView{
    return 90.0f;
}

-(IBAction)wantGo:(id)sender{
    UIView *coverView = self.superview.superview.superview.superview;
    MBProgressHUD *hud = [MBProgressHUD showHUDAddedTo:coverView animated:YES];
    hud.labelText = @"操作中...";
    dispatch_async(dispatch_get_global_queue(DISPATCH_QUEUE_PRIORITY_HIGH, 0), ^{
        NSDictionary *params = [[NSDictionary alloc] initWithObjectsAndKeys:_ideaView.ideaId, @"ideaId", nil];
        __block ASIHTTPRequest *_request = [HttpRequestSender initGetRequestWithUrl:@"http://test.51juzhai.com/app/ios/postidea" withParams:params];
        __unsafe_unretained ASIHTTPRequest *request = _request;
        [request setCompletionBlock:^{
            // Use when fetching text data
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
                UIButton *wantToButton = (UIButton *)[self viewWithTag:IDEA_WANT_TO_TAG];
                wantToButton.enabled = NO;
                [wantToButton setTitle:[NSString stringWithFormat:@"%d", wantToButton.titleLabel.text.intValue + 1] forState:UIControlStateNormal];
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
