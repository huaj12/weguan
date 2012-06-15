//
//  JZData.m
//  juzhai
//
//  Created by JiaJun Wu on 12-6-14.
//  Copyright (c) 2012年 __MyCompanyName__. All rights reserved.
//

#import "JZData.h"

@implementation JZData

- (id) init{
    self = [super init];
    if(self){
        _data = [[NSMutableArray alloc] init];
        _identitySet = [[NSMutableSet alloc] init];
    }
    return self;
}

- (NSInteger) count
{
    return _data.count;
}

- (void) clear
{
    [_data removeAllObjects];
    [_identitySet removeAllObjects];
}

- (void) addObject:(id)object withIdentity:(id)identity
{
    if(![_identitySet containsObject:identity]){
        [_data addObject:object];
        [_identitySet addObject:identity];
    }
}

- (id)objectAtIndex:(NSUInteger)index
{
    return [_data objectAtIndex:index];
}

@end
