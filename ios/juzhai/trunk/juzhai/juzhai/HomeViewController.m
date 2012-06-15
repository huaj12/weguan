//
//  HomeViewController.m
//  juzhai
//
//  Created by JiaJun Wu on 12-6-14.
//  Copyright (c) 2012年 __MyCompanyName__. All rights reserved.
//

#import "HomeViewController.h"
#import "BaseData.h"
#import <QuartzCore/QuartzCore.h>

@interface HomeViewController ()

@end

@implementation HomeViewController

@synthesize logoImageView;
@synthesize nicknameLabel;
@synthesize interestUserListButton;
@synthesize interestMeListButton;
@synthesize sendPostButton;
@synthesize postTableView;

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
    // Do any additional setup after loading the view from its nib.
    postTableView.delegate = self;
    postTableView.dataSource = self;
    [postTableView reloadData];
//    postTableView.hidden = YES;
    postTableView.separatorColor = [UIColor greenColor];
    
    
    
//    UILabel * label = [[UILabel alloc] init];
//    label.frame = CGRectMake(10, 7, postTableView.bounds.size.width, 11);
//    label.backgroundColor = [UIColor clearColor];
//    label.font=[UIFont fontWithName:DEFAULT_FONT_FAMILY size:11];
//    label.textColor = [UIColor grayColor];
//    label.text = @"共 12 条拒宅";
//    
//    UIView * sectionView = [[UIView alloc] initWithFrame:CGRectMake(0, 0, postTableView.bounds.size.width, 25)];
//    [sectionView setBackgroundColor: [UIColor colorWithPatternImage: [UIImage imageNamed: @"my_jz_title_bg.png"]]];
//    [sectionView addSubview:label];
//    
//    postTableView.tableHeaderView = sectionView;
}

- (void)viewDidUnload
{
    [super viewDidUnload];
    // Release any retained subviews of the main view.
    // e.g. self.myOutlet = nil;
}

- (BOOL)shouldAutorotateToInterfaceOrientation:(UIInterfaceOrientation)interfaceOrientation
{
    return (interfaceOrientation == UIInterfaceOrientationPortrait);
}

#pragma mark -
#pragma mark Table View Data Source

- (NSInteger) tableView:(UITableView *)tableView numberOfRowsInSection:(NSInteger)section{
    return 3;
}

- (UITableViewCell *)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath{
    NSString *simpleTableIdentifier = @"SimpleTableIdentifier";
    
    UITableViewCell *cell = [tableView dequeueReusableCellWithIdentifier:simpleTableIdentifier];
    if(cell == nil){
        cell = [[UITableViewCell alloc] initWithStyle:UITableViewCellStyleDefault reuseIdentifier:simpleTableIdentifier];
    }
    cell.textLabel.text = @"abc";
    return cell;
}

- (NSString *) tableView:(UITableView *)tableView titleForHeaderInSection:(NSInteger)section{
    return @"共 12 条拒宅";
}

- (UIView *) tableView:(UITableView *)tableView viewForHeaderInSection:(NSInteger)section{
    
    NSString *sectionTitle = [self tableView:tableView titleForHeaderInSection:section];
    if (sectionTitle == nil) {
        return  nil;
    }
    
    UILabel * label = [[UILabel alloc] init];
    label.frame = CGRectMake(10, 7, tableView.bounds.size.width, 11);
    label.backgroundColor = [UIColor clearColor];
    label.font=[UIFont fontWithName:DEFAULT_FONT_FAMILY size:11];
    label.textColor = [UIColor grayColor];
    label.text = sectionTitle;
    
    UIView * sectionView = [[UIView alloc] initWithFrame:CGRectMake(0, 0, tableView.bounds.size.width, 25)];
    [sectionView setBackgroundColor: [UIColor colorWithPatternImage: [UIImage imageNamed: @"my_jz_title_bg.png"]]];
    [sectionView addSubview:label];
    return sectionView;
}

- (CGFloat) tableView:(UITableView *)tableView heightForHeaderInSection:(NSInteger)section{
    return 25.0;
}

#pragma mark -
#pragma mark Table View Deletage


@end
