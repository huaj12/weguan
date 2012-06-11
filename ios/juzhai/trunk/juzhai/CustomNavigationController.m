//
//  CustomNavigationController.m
//  juzhai
//
//  Created by JiaJun Wu on 12-5-25.
//  Copyright (c) 2012年 __MyCompanyName__. All rights reserved.
//

#import "CustomNavigationController.h"

@interface CustomNavigationController ()

@end

@implementation CustomNavigationController

- (id)initWithNibName:(NSString *)nibNameOrNil bundle:(NSBundle *)nibBundleOrNil
{
    self = [super initWithNibName:nibNameOrNil bundle:nibBundleOrNil];
    if (self) {
        // Custom initialization
    }
    return self;
}

- (void)viewDidLoad
{
    [super viewDidLoad];
	// Do any additional setup after loading the view.
    if ([[[UIDevice currentDevice] systemVersion] floatValue] >= 5.0){
        UIImage *navbgpic = [[UIImage imageNamed:TOP_BG_PIC_NAME] resizableImageWithCapInsets:UIEdgeInsetsMake(0,0,0,0)];
        [self.navigationBar setBackgroundImage:navbgpic forBarMetrics:UIBarMetricsDefault];
    }else{
        // Override point for customization after application launch.
//        UIImageView* imageView = [[UIImageView alloc] initWithFrame:self.navigationBar.frame];
//        imageView.contentMode = UIViewContentModeLeft;
//        imageView.image = [UIImage imageNamed:TOP_BG_PIC_NAME];
//        [self.navigationBar insertSubview:imageView atIndex:0];
//        UIImageView *backgroundView = [[UIImageView alloc] initWithImage:[UIImage imageNamed:TOP_BG_PIC_NAME]]; 
//        [self.navigationBar insertSubview:backgroundView atIndex:0];
//        self.navigationBar.barStyle. = backgroundView;
    }
}

- (void)viewDidUnload
{
    [super viewDidUnload];
    // Release any retained subviews of the main view.
}

- (BOOL)shouldAutorotateToInterfaceOrientation:(UIInterfaceOrientation)interfaceOrientation
{
    return (interfaceOrientation == UIInterfaceOrientationPortrait);
}

@end

@implementation UINavigationBar (CustomImage2)   
- (void)drawRect:(CGRect)rect {   
    UIImage *image = [UIImage imageNamed:TOP_BG_PIC_NAME];    
    [image drawInRect:CGRectMake(0, 0, self.frame.size.width, self.frame.size.height)];   
}   
@end