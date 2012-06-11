//
//  IdeaListCell.h
//  juzhai
//
//  Created by JiaJun Wu on 12-5-27.
//  Copyright (c) 2012年 __MyCompanyName__. All rights reserved.
//

#import <UIKit/UIKit.h>
@class IdeaView;

#define IDEA_IMAGE_TAG 1
#define IDEA_CONTENT_TAG 2
#define IDEA_WANT_TO_TAG 3

#define IDEA_DEFAULT_PIC @"none_img.png"
#define NORMAL_WANT_BUTTON_IMAGE @"idea_wgo_btn_link.png"
#define HIGHLIGHT_WANT_BUTTON_IMAGE @"idea_wgo_btn_hover.png"
#define DISABLE_WANT_BUTTON_IMAGE @"idea_wgo_btn_done.png"
#define WANT_BUTTON_CAP_WIDTH 26.0
@interface IdeaListCell : UITableViewCell
{
    IdeaView *_ideaView;
}

- (void) redrawn:(IdeaView *)ideaView;
- (void) setBackground;
+ (CGFloat) heightForCell:(IdeaView *)IdeaView;

- (IBAction)wantGo:(id)sender;

@end
