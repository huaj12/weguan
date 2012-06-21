//
//  BaseData.h
//  juzhai
//
//  Created by JiaJun Wu on 12-6-2.
//  Copyright (c) 2012年 __MyCompanyName__. All rights reserved.
//

#import <Foundation/Foundation.h>

#define DEFAULT_FONT_FAMILY @"MicrosoftYaHei"

@interface BaseData : NSObject

@property (strong,nonatomic) NSMutableArray *categoryArray;
@property (strong,nonatomic) NSMutableArray *professionArray;
+ (NSArray *)getCategories;
+ (NSArray *)getProfessions;
@end
