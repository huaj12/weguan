//
//  UserViewController.h
//  juzhai
//
//  Created by JiaJun Wu on 12-5-23.
//  Copyright (c) 2012年 __MyCompanyName__. All rights reserved.
//

#import <UIKit/UIKit.h>
#import "CustomSegmentedControl.h"

@class JZData;
@class PostDetailViewController;

#define ORDER_BY_TIME 1
#define ORDER_BY_RECOMMEND 2
#define QUERY_GENDER_GIRL 0
#define QUERY_GENDER_BOY 1
#define QUERY_GENDER_ALL 2

@interface UserViewController : UITableViewController <UIActionSheetDelegate,CustomSegmentedControlDelegate>
{
    JZData *_data;
    CustomSegmentedControl *_segmentedControl;
    UIButton *_genderButton;
    PostDetailViewController *_postDetailViewController;
}

@end
