//
//  FeatureEditorViewController.h
//  juzhai
//
//  Created by JiaJun Wu on 12-6-21.
//  Copyright (c) 2012年 __MyCompanyName__. All rights reserved.
//

#import <UIKit/UIKit.h>

@class ProfileSettingViewController;

@interface FeatureEditorViewController : UIViewController

@property (strong, nonatomic) ProfileSettingViewController *profileSettingViewController;
@property (strong, nonatomic) NSString *textValue;
@property (nonatomic) NSInteger tag;

@property (strong, nonatomic) IBOutlet UITextView *textView;

-(IBAction)save:(id)sender;
@end
