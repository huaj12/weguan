package com.juzhai.home.service;

import java.util.Date;
import java.util.List;

import com.juzhai.home.bean.Feed;
import com.juzhai.home.bean.ReadFeed;
import com.juzhai.home.bean.ReadFeedType;

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
	 * 向收件箱推数据
	 * 
	 * @param receiverId
	 * @param senderId
	 * @param actId
	 * @param time
	 */
	void push(long receiverId, long senderId, long actId, Date time);

	/**
	 * 收件箱里feed的数量
	 * 
	 * @param uid
	 * @return
	 */
	long inboxCount(long uid);

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
	 * @param readFeedType
	 */
	void shiftRead(long uid, long senderId, long actId,
			ReadFeedType readFeedType);

	/**
	 * 获取需要恢复的Feed
	 * 
	 * @return
	 */
	List<ReadFeed> listGetBackFeed();

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
	Feed showFirst(long uid);

	/**
	 * 显示一个随机
	 * 
	 * @param uid
	 * @return
	 */
	Feed showYue(long uid);

	/**
	 * 显示打分feed
	 * 
	 * @param uid
	 * @return
	 */
	Feed showQuestion(long uid);

	/**
	 * 做题目
	 * 
	 * @param uid
	 * @param tpId
	 * @param questionId
	 * @param identity
	 * @param answer
	 */
	void answer(long uid, long tpId, long questionId, String identity,
			int answer);

	/**
	 * 获取最后推送时间
	 * 
	 * @param senderId
	 * @param receiverId
	 * @return
	 */
	long getLastPushTime(long senderId, long receiverId);

	/**
	 * 清空惩罚次数
	 * 
	 * @param senderId
	 * @param receiverId
	 */
	void clearPunishTimes(long senderId, long receiverId);

	/**
	 * 增加惩罚次数
	 * 
	 * @param senderId
	 * @param reveiverId
	 * @return
	 */
	long increasePunishTimes(long senderId, long reveiverId);
}
