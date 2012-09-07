package com.juzhai.notice.service;

import com.juzhai.notice.bean.NoticeType;

public interface INoticeRemoteService {

	/**
	 * 获取特定通知的通知数
	 * 
	 * @param uid
	 * @param noticeType
	 * @return
	 */
	Long getNoticeNum(long uid, NoticeType noticeType);

	/**
	 * 清空通知数
	 * 
	 * @param uid
	 * @param noticeType
	 */
	void emptyNotice(long uid, NoticeType noticeType);
}
