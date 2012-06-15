//
//  JZData.h
//  juzhai
//
//  Created by JiaJun Wu on 12-6-14.
//  Copyright (c) 2012年 __MyCompanyName__. All rights reserved.
//

#import <Foundation/Foundation.h>

@interface JZData : NSObject
{
    NSMutableSet *_identitySet;
    NSMutableArray *_data;
}

-(NSInteger) count;
-(void) addObject:(id)object withIdentity:(id)identity;
-(void) clear;
- (id)objectAtIndex:(NSUInteger)index;
@end
