//
//  JZData.h
//  juzhai
//
//  Created by JiaJun Wu on 12-6-14.
//  Copyright (c) 2012年 __MyCompanyName__. All rights reserved.
//

#import <Foundation/Foundation.h>
#import "Pager.h"

@interface JZData : NSObject
{
    NSMutableSet *_identitySet;
    NSMutableArray *_data;
}

@property (strong, nonatomic) Pager *pager;

- (id)initWithPager:(Pager *)pager;
- (NSInteger)count;
- (void)addObject:(id)object withIdentity:(id)identity;
- (void)clear;
- (id)objectAtIndex:(NSUInteger)index;
- (NSInteger) cellRows;
@end
