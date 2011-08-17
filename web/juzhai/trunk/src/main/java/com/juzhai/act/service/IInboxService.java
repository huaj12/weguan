package com.juzhai.act.service;

public interface IInboxService {

	/**
	 * 向收件箱推数据
	 * 
	 * @param receiverId
	 * @param senderId
	 * @param actId
	 */
	void push(long receiverId, long senderId, long actId);
}
