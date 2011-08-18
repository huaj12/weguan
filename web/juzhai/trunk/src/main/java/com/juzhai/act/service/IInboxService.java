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

	/**
	 * 同步收件箱，前提是好友信息已经缓存，小心使用，建议使用方法<code>syncInboxByTask</code>
	 * 
	 * @param uid
	 *            用户ID
	 */
	void syncInbox(long uid);

	/**
	 * 启动一个子线程进行同步收件箱，前提是好友信息已经缓存
	 * 
	 * @param uid
	 */
	void syncInboxByTask(long uid);
}
