//
//  SmsViewController.h
//  juzhai
//
//  Created by JiaJun Wu on 12-7-8.
//  Copyright (c) 2012年 __MyCompanyName__. All rights reserved.
//

#import <UIKit/UIKit.h>
#import "EGORefreshHeaderTableViewController.h"

@class JZData;
@class HomeViewController;
@class ListHttpRequestDelegate;

@interface SmsViewController : EGORefreshHeaderTableViewController
{
    JZData *_data;
    UIButton *_editButton;
    ListHttpRequestDelegate *_listHttpRequestDelegate;
}

@end
