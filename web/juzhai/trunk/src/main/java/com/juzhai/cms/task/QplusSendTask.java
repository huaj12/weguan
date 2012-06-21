package com.juzhai.cms.task;

import com.juzhai.notice.service.INoticeService;

public class QplusSendTask implements Runnable {
	private INoticeService noticeService;
	private String openid;
	private String text;

	public QplusSendTask(String openid, String text,
			INoticeService noticeService) {
		this.noticeService = noticeService;
		this.openid = openid;
		this.text = text;
	}

	@Override
	public void run() {
		noticeService.noticeQplusUser(openid, text);
	}
}
