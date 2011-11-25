package com.juzhai.app.service;


public interface IWeiboService {
	
	void follow(long tpId, long uid);
	
	boolean  sendWeiboRequest(long tpId, long uid,String content);
	
	boolean sendFeed( long tpId, long uid,String content ,long actId);
}
