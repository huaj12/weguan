package com.juzhai.act.service;

import java.util.SortedMap;

import com.juzhai.act.bean.ActDealType;
import com.juzhai.act.model.Act;
import com.juzhai.passport.bean.TpFriend;
import com.juzhai.passport.model.Profile;

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

	/**
	 * 转移已读
	 * 
	 * @param uid
	 * @param actId
	 * @param friendId
	 * @param actDealType
	 */
	void shiftRead(long uid, long senderId, long actId, ActDealType actDealType);

	/**
	 * 从收件箱中是删除
	 * 
	 * @param uid
	 *            收件箱主人ID
	 * @param senderId
	 *            内容发送者ID
	 * @param actId
	 *            actId
	 * @return true if exist
	 */
	boolean remove(long uid, long senderId, long actId);

	/**
	 * 显示第一个
	 * 
	 * @param uid
	 *            收件箱用户ID
	 * @return
	 */
	SortedMap<Profile, Act> showFirst(long uid);

	/**
	 * 显示一个随机
	 * 
	 * @param uid
	 * @return
	 */
	SortedMap<TpFriend, Act> showRandam(long uid);
}
