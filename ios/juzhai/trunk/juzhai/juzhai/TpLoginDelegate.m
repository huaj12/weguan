//
//  TpLoginDelegate.m
//  juzhai
//
//  Created by JiaJun Wu on 12-6-18.
//  Copyright (c) 2012年 __MyCompanyName__. All rights reserved.
//

#import "TpLoginDelegate.h"

@implementation TpLoginDelegate

-(NSInteger)tableView:(UITableView *)tableView numberOfRowsInSection:(NSInteger)section{
    return 3;
}

-(UITableViewCell *)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath{
    static NSString *TpLoginCellIdentifier = @"TpLoginCellIdentifier";
    UITableViewCell *cell = [tableView dequeueReusableCellWithIdentifier:TpLoginCellIdentifier];
    if(cell == nil){
        cell = [[UITableViewCell alloc] initWithStyle:UITableViewCellStyleDefault reuseIdentifier:TpLoginCellIdentifier];
        cell.accessoryType = UITableViewCellAccessoryDisclosureIndicator;
    }
    cell.textLabel.text = @"新浪";
    return cell;
}

@end
