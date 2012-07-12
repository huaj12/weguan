//
//  SmsViewController.h
//  juzhai
//
//  Created by JiaJun Wu on 12-7-8.
//  Copyright (c) 2012年 __MyCompanyName__. All rights reserved.
//

#import <UIKit/UIKit.h>

@class JZData;
@class HomeViewController;

@interface SmsViewController : UITableViewController
{
    JZData *_data;
    UIButton *_editButton;
}

@end
