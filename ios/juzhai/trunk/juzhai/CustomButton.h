//
//  CustomButton.h
//  juzhai
//
//  Created by JiaJun Wu on 12-5-25.
//  Copyright (c) 2012年 __MyCompanyName__. All rights reserved.
//

#import <UIKit/UIKit.h>

typedef enum {
    CapLeft          = 0,
    CapMiddle        = 1,
    CapRight         = 2,
    CapLeftAndRight  = 3
} CapLocation;

#define BUTTON_IMAGE @"menu_link_bg.png"
#define BUTTON_ACTIVE_IMAGE @"menu_active_bg.png"
#define CAP_WIDTH 5

@interface CustomButton : UIButton

- (id)initWithWidth:(NSUInteger)width buttonText:(NSString *)buttonText CapLocation:(CapLocation)location;

- (id)initWithFrame:(CGRect)frame buttonText:(NSString *)buttonText buttonImage:(UIImage *)image buttonPressedImage:(UIImage *)pressedImage;

@end
