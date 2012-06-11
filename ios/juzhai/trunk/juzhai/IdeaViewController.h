//
//  IdeaViewController.h
//  juzhai
//
//  Created by JiaJun Wu on 12-5-11.
//  Copyright (c) 2012年 __MyCompanyName__. All rights reserved.
//

#import <UIKit/UIKit.h>
#import "FPPopoverController.h"

@class EGORefreshTableHeaderView;
@class CustomButton;
@class IdeaDetailViewController;

#define ORDER_BY_TIME 0
#define ORDER_BY_HOT 1
#define ALL_CATEGORY_ID 0

@interface IdeaViewController : UITableViewController <
//EGORefreshTableHeaderDelegate
FPPopoverControllerDelegate>
{
	
//	EGORefreshTableHeaderView *_refreshHeaderView;
    
//	BOOL _reloading;
	
	NSMutableArray *_data;
    FPPopoverController *_categoryPopver;
    UIButton *_orderButton;
    UIButton *_categoryButton;
    IdeaDetailViewController *_ideaDetailViewController;
}

//- (void)reloadTableViewDataSource;
//- (void)doneLoadingTableViewData;

@end
