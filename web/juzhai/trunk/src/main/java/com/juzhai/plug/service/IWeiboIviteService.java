package com.juzhai.plug.service;

import java.util.List;

public interface IWeiboIviteService {
	boolean sendWeiboIvite(String content,long actId,long tpId,long uid);
	
	List<String> getInviteReceiverName(String uids,long tpId,
			long uid);
}
