//
//  JZData.m
//  juzhai
//
//  Created by JiaJun Wu on 12-6-14.
//  Copyright (c) 2012年 __MyCompanyName__. All rights reserved.
//

#import "JZData.h"
#import "Pager.h"

@implementation JZData

@synthesize pager = _pager;

- (id) init{
    self = [super init];
    if(self){
        _data = [[NSMutableArray alloc] init];
        _identitySet = [[NSMutableSet alloc] init];
    }
    return self;
}

- (id) initWithPager:(Pager *)pager{
    self = [self init];
    if(self){
        _pager = pager;
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

- (NSInteger) cellRows
{
    int count = [self count];
    if (_pager.hasNext) {
        count += 1;
    }
    return count;
}

- (void)removeObjectAtIndex:(NSUInteger)index
{
    [_data removeObjectAtIndex:index];
}

@end
