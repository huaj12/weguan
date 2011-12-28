package com.juzhai.notice.service;

import java.util.Map;

import com.juzhai.notice.bean.NoticeType;

public interface INoticeService {

	long incrNotice(long uid, NoticeType noticeType);

	void emptyNotice(long uid, NoticeType noticeType);

	Map<Integer, Long> getAllNoticeNum(long uid);

	Long getNoticeNum(long uid, NoticeType noticeType);
}
