package com.juzhai.notice.service;

import java.util.Map;

import com.juzhai.notice.bean.NoticeType;
import com.juzhai.passport.bean.ThirdpartyNameEnum;
import com.juzhai.platform.exception.AdminException;

public interface INoticeService {

	/**
	 * 通知数+1
	 * 
	 * @param uid
	 * @param noticeType
	 * @return
	 */
	long incrNotice(long uid, NoticeType noticeType);

	/**
	 * 清空通知数
	 * 
	 * @param uid
	 * @param noticeType
	 */
	void emptyNotice(long uid, NoticeType noticeType);

	/**
	 * 获取所有通知数
	 * 
	 * @param uid
	 * @return
	 */
	Map<Integer, Long> getAllNoticeNum(long uid);

	/**
	 * 获取特定通知的通知数
	 * 
	 * @param uid
	 * @param noticeType
	 * @return
	 */
	Long getNoticeNum(long uid, NoticeType noticeType);

	/**
	 * 通知用户未读消息数
	 * 
	 * @param uid
	 * @param tpId
	 * @return
	 */
	boolean noticeUserUnReadNum(
			long receiver, int num) throws AdminException;
}
