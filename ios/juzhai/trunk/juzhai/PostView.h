//
//  Post.h
//  juzhai
//
//  Created by JiaJun Wu on 12-5-30.
//  Copyright (c) 2012年 __MyCompanyName__. All rights reserved.
//

#import <Foundation/Foundation.h>

@interface PostView : NSObject

@property (nonatomic, strong) NSDecimalNumber *postId;
@property (nonatomic, strong) NSString *purpose;
@property (nonatomic, strong) NSString *content;
@property (nonatomic, strong) NSString *place;
@property (nonatomic, strong) NSString *pic;
@property (nonatomic, strong) NSString *categoryName;
@property (nonatomic, strong) NSString *date;
@property (nonatomic, strong) NSNumber *respCnt;
@property (nonatomic, strong) NSNumber *hasResp;

+ (id) postConvertFromDictionary:(NSDictionary *)info;

@end
