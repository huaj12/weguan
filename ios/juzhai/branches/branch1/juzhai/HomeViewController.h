//
//  HomeViewController.h
//  juzhai
//
//  Created by JiaJun Wu on 12-6-14.
//  Copyright (c) 2012年 __MyCompanyName__. All rights reserved.
//

#import <UIKit/UIKit.h>

@class UserHomeView;
@class JZData;

@interface HomeViewController : UIViewController
{
    UserHomeView *_userHomeView;
    JZData *_data;
}
@end
