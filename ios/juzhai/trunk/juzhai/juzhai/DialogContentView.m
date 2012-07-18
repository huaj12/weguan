//
//  DialogContentView.m
//  juzhai
//
//  Created by JiaJun Wu on 12-7-15.
//  Copyright (c) 2012年 __MyCompanyName__. All rights reserved.
//

#import "DialogContentView.h"

@implementation DialogContentView

@synthesize dialogContentId;
@synthesize content;
@synthesize senderUid;
@synthesize receiverUid;
@synthesize createTime;

+ (id)convertFromDictionary:(NSDictionary *)info
{
    DialogContentView *dialogContentView = [[DialogContentView alloc] init];
    if (dialogContentView) {
        dialogContentView.dialogContentId = [[info objectForKey:@"dialogContentId"] intValue];
        dialogContentView.content = [info objectForKey:@"content"];
        dialogContentView.senderUid = [[info objectForKey:@"senderUid"] intValue];
        dialogContentView.receiverUid = [[info objectForKey:@"receiverUid"] intValue];
        dialogContentView.createTime = [[info objectForKey:@"createTime"] doubleValue] / 1000;
    }
    return dialogContentView;
}

@end
