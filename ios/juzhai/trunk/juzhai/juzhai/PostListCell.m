//
//  PostListCell.m
//  juzhai
//
//  Created by JiaJun Wu on 12-6-16.
//  Copyright (c) 2012年 __MyCompanyName__. All rights reserved.
//

#import "PostListCell.h"
#import "PostView.h"
#import "BaseData.h"
#import "SDWebImage/UIImageView+WebCache.h"
#import <QuartzCore/QuartzCore.h>

@implementation PostListCell

@synthesize contentLabel;
@synthesize imageView;

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

- (void) redrawn:(PostView *)postView{
    _postView = postView; 

    contentLabel.text = [NSString stringWithFormat:@"%@：%@", postView.purpose, postView.content];
    contentLabel.font = [UIFont fontWithName:DEFAULT_FONT_FAMILY size:14.0];
    CGSize labelsize = [contentLabel.text sizeWithFont:contentLabel.font constrainedToSize:CGSizeMake(contentLabel.frame.size.width, 300.0) lineBreakMode:UILineBreakModeCharacterWrap];
    [contentLabel setFrame:CGRectMake(contentLabel.frame.origin.x, contentLabel.frame.origin.y, labelsize.width, labelsize.height)];
    
    SDWebImageManager *manager = [SDWebImageManager sharedManager];
    if(![postView.pic isEqual:[NSNull null]]){
        imageView.image = [UIImage imageNamed:POST_DEFAULT_PIC];
        NSURL *postImageURL = [NSURL URLWithString:postView.pic];
        [imageView setFrame:CGRectMake(imageView.frame.origin.x, contentLabel.frame.origin.y + labelsize.height + 10.0, imageView.frame.size.width, imageView.frame.size.height)];
        [manager downloadWithURL:postImageURL delegate:self options:0 success:^(UIImage *image) {
            UIGraphicsBeginImageContext(CGSizeMake(imageView.frame.size.width, imageView.frame.size.height));
            [image drawInRect:CGRectMake(0, 0, imageView.frame.size.width, image.size.height*(imageView.frame.size.width/image.size.width))];
            UIImage* resultImage = UIGraphicsGetImageFromCurrentImageContext();
            UIGraphicsEndImageContext();
            imageView.image = resultImage;
            imageView.layer.shouldRasterize = YES;
            imageView.layer.masksToBounds = YES;
            imageView.layer.cornerRadius = 5.0;
        } failure:nil];
        [imageView setHidden:NO];
    }else {
        [imageView setHidden:YES];
    }
}

+(CGFloat) heightForCell:(PostView *)postView{
    float height = 10.0;
    NSString *content = [NSString stringWithFormat:@"%@：%@", postView.purpose, postView.content];
    CGSize contentSize = [content sizeWithFont:[UIFont fontWithName:DEFAULT_FONT_FAMILY size:14.0] constrainedToSize:CGSizeMake(300, 300.0) lineBreakMode:UILineBreakModeCharacterWrap];
    height += contentSize.height + 10.0;
    if(![postView.pic isEqual:[NSNull null]]){
        height += 110.0;
    }
    return height;
}

@end
