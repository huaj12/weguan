//
//  ProfessionEditorViewController.h
//  juzhai
//
//  Created by JiaJun Wu on 12-6-21.
//  Copyright (c) 2012年 __MyCompanyName__. All rights reserved.
//

#import <UIKit/UIKit.h>
#import "SettingViewController.h"

@class ProfileSettingViewController;

#define PROFESSION_MIN_LENGTH 2
#define PROFESSION_MAX_LENGTH 20
#define PROFESSION_MIN_ERROR_TEXT @"职业描述不能为空"
#define PROFESSION_MAX_ERROR_TEXT @"职业描述请不要超过10个字"

@interface ProfessionEditorViewController : UIViewController <UIPickerViewDelegate, UIPickerViewDataSource>
{
    NSArray *_professionArray;
}
@property (strong, nonatomic) SettingViewController *settingViewController;
@property (strong, nonatomic) NSString *textValue;
@property (nonatomic) NSInteger valueId;
@property (strong, nonatomic) NSString *cellIdentifier;

@property (strong, nonatomic) IBOutlet UIPickerView *professionPicker;
@property (strong, nonatomic) IBOutlet UILabel *tipLabel;
@property (strong, nonatomic) IBOutlet UITextField *professionField;

- (IBAction)save:(id)sender;

@end
