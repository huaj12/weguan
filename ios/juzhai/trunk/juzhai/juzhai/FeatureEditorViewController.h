//
//  FeatureEditorViewController.h
//  juzhai
//
//  Created by JiaJun Wu on 12-6-21.
//  Copyright (c) 2012年 __MyCompanyName__. All rights reserved.
//

#import <UIKit/UIKit.h>
#import "DetailEditorViewController.h"

#define FEATURE_MIN_LENGTH 1
#define FEATURE_MAX_LENGTH 140
#define FEATURE_MIN_ERROR_TEXT @"自我评价不能为空"
#define FEATURE_MAX_ERROR_TEXT @"自我评价请不要超过70个字"
@interface FeatureEditorViewController : DetailEditorViewController

@property (strong, nonatomic) NSString *textValue;
@property (strong, nonatomic) NSString *cellIdentifier;

@property (strong, nonatomic) IBOutlet UITextView *textView;
@property (strong, nonatomic) IBOutlet UILabel *tipsLabel;
@property (strong, nonatomic) IBOutlet UIImageView *backgroundView;

@end
