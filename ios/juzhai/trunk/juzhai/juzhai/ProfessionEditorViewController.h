//
//  ProfessionEditorViewController.h
//  juzhai
//
//  Created by JiaJun Wu on 12-6-21.
//  Copyright (c) 2012年 __MyCompanyName__. All rights reserved.
//

#import <UIKit/UIKit.h>

@class ProfileSettingViewController;

@interface ProfessionEditorViewController : UIViewController <UIPickerViewDelegate, UIPickerViewDataSource>
{
    NSArray *_professionArray;
}
@property (strong, nonatomic) ProfileSettingViewController *profileSettingViewController;
@property (strong, nonatomic) NSString *textValue;
@property (nonatomic) NSInteger valueId;
@property (nonatomic) NSInteger tag;

@property (strong, nonatomic) IBOutlet UIPickerView *professionPicker;
@property (strong, nonatomic) IBOutlet UILabel *tipLabel;
@property (strong, nonatomic) IBOutlet UITextField *professionField;

- (IBAction)save:(id)sender;

@end
