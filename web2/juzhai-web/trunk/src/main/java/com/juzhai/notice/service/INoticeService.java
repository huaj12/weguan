package com.juzhai.notice.service;

import java.util.List;
import java.util.Map;

import com.juzhai.notice.bean.NoticeType;
import com.juzhai.platform.exception.AdminException;

public interface INoticeService extends INoticeRemoteService {

	/**
	 * 通知数+1
	 * 
	 * @param uid
	 * @param noticeType
	 * @return
	 */
	long incrNotice(long uid, NoticeType noticeType);

	/**
	 * 获取所有通知数
	 * 
	 * @param uid
	 * @return
	 */
	Map<Integer, Long> getAllNoticeNum(long uid);

	/**
	 * 获取前N个需要被通知的人
	 * 
	 * @param count
	 * @return
	 */
	List<Long> getNoticUserList(int count);

	/**
	 * 从通知人列表里移除用户
	 * 
	 * @param uid
	 */
	void removeFromNoticeUsers(long uid);

	/**
	 * 通知用户未读消息数
	 * 
	 * @param uid
	 * @param tpId
	 * @return
	 */
	void noticeUserUnReadNum(long receiver, long num) throws AdminException;

}
