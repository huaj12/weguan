//
//  IdeaUsersViewController.h
//  juzhai
//
//  Created by JiaJun Wu on 12-7-10.
//  Copyright (c) 2012年 __MyCompanyName__. All rights reserved.
//

#import <UIKit/UIKit.h>

@class IdeaView;
@class JZData;
@class HomeViewController;

#define TABLE_HEAD_HEIGHT 35

@interface IdeaUsersViewController : UITableViewController
{
    JZData *_data;
}

@property (strong, nonatomic) IdeaView *ideaView;

@end
