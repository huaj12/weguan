//
//  Idea.h
//  juzhai
//
//  Created by JiaJun Wu on 12-5-12.
//  Copyright (c) 2012年 __MyCompanyName__. All rights reserved.
//

#import <Foundation/Foundation.h>
#import "DataView.h"

@interface IdeaView : NSObject <DataView>

@property (strong,nonatomic) NSDecimalNumber *ideaId;
@property (strong,nonatomic) NSString *content;
@property (strong,nonatomic) NSString *place;
@property (strong,nonatomic) NSString *startTime;
@property (strong,nonatomic) NSString *endTime;
@property (strong,nonatomic) NSString *categoryName;
@property (strong,nonatomic) NSString *cityName;
@property (strong,nonatomic) NSString *townName;
@property (strong,nonatomic) NSNumber *useCount;
@property (strong,nonatomic) NSNumber *charge;
@property (strong,nonatomic) NSString *pic;
@property (strong,nonatomic) NSString *bigPic;
@property (strong,nonatomic) NSNumber *hasUsed;

- (BOOL) hasPlace;
- (BOOL) hasTime;
- (BOOL) hasCategory;

@end
