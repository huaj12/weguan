//
//  FeatureEditorViewController.h
//  juzhai
//
//  Created by JiaJun Wu on 12-6-21.
//  Copyright (c) 2012年 __MyCompanyName__. All rights reserved.
//

#import <UIKit/UIKit.h>
#import "DetailEditorViewController.h"

@interface FeatureEditorViewController : DetailEditorViewController

@property (strong, nonatomic) NSString *textValue;
@property (strong, nonatomic) NSString *cellIdentifier;

@property (strong, nonatomic) IBOutlet UITextView *textView;
@property (strong, nonatomic) IBOutlet UILabel *tipsLabel;
@property (strong, nonatomic) IBOutlet UIImageView *backgroundView;

@end
