//
//  NSDate+BeforeShowType.m
//  juzhai
//
//  Created by JiaJun Wu on 12-7-16.
//  Copyright (c) 2012年 __MyCompanyName__. All rights reserved.
//

#import "NSDate+BeforeShowType.h"

@implementation NSDate (BeforeShowType)

- (NSString *)showBefore
{
    NSTimeInterval interval = - [self timeIntervalSinceNow];
    NSString *timeText;
    if (interval < 60) {
        NSInteger beforeSec = interval;
        timeText = [NSString stringWithFormat:@"%d秒前", beforeSec];
    } else if (0 < interval/60 && interval/60 < 60) {
        NSInteger beforeMin = interval/60;
        timeText = [NSString stringWithFormat:@"%d分钟前", beforeMin];
    } else if (0 < interval/3600 && interval/3600 < 24) {
        NSInteger beforeHour = interval/3600;
        timeText = [NSString stringWithFormat:@"%d小时前", beforeHour];
    } else if (interval/3600/24 == 1) {
        timeText = @"昨天";
    } else if (interval/3600/24 == 2) {
        timeText = @"前天";
    } else {
        NSDateFormatter *formatter = [[NSDateFormatter alloc] init];
        [formatter setDateFormat:@"yyyy-MM-dd"];
        timeText = [formatter stringFromDate:self];
    }
    return timeText;
}

@end
