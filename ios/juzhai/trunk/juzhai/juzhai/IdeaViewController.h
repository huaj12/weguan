//
//  IdeaViewController.h
//  juzhai
//
//  Created by JiaJun Wu on 12-5-11.
//  Copyright (c) 2012年 __MyCompanyName__. All rights reserved.
//

#import <UIKit/UIKit.h>
#import "FPPopoverController.h"
#import "EGORefreshTableHeaderView.h"
#import "EGORefreshHeaderTableViewController.h"
#import "CustomSegmentedControl.h"

@class EGORefreshTableHeaderView;
@class IdeaDetailViewController;
@class JZData;

enum {
    OrderTypeTime = 0,
    OrderTypeHot = 1
};

#define ALL_CATEGORY_ID 0

#define HOT_ORDER_BTN_IMG @"zuire_btn"
#define TIME_ORDER_BTN_IMG @"zuixin_btn"

#define CATEGORY_DOWN_LINK_IMG @"select_down_link"
#define CATEGORY_DOWN_HOVER_IMG @"select_down_hover"
#define CATEGORY_UP_LINK_IMG @"select_up_link"
#define CATEGORY_up_HOVER_IMG @"select_up_hover"

@interface IdeaViewController : EGORefreshHeaderTableViewController <FPPopoverControllerDelegate, CustomSegmentedControlDelegate>
{	
	JZData *_data;
    FPPopoverController *_categoryPopver;
    CustomSegmentedControl *_segmentedControl;
    UIButton *_categoryButton;
//    UIButton *_orderButton;
//    UIButton *_categoryButton;
//    UIImage *_hotOrderBtnImg;
//    UIImage *_timeOrderBtnImg;
    UIImage *_categoryDownLinkImg;
    UIImage *_categoryDownHoverImg;
    UIImage *_categoryUpLinkImg;
    UIImage *_categoryUpHoverImg;
}

//- (void)reloadTableViewDataSource;
//- (void)doneLoadingTableViewData;

@end
