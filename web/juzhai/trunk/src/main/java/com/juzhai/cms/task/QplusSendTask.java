package com.juzhai.cms.task;

import com.juzhai.notice.service.INoticeService;

public class QplusSendTask implements Runnable {
	private INoticeService noticeService;
	private String openid;
	private String text;
	private String link;

	public QplusSendTask(String openid, String text, String link,
			INoticeService noticeService) {
		this.noticeService = noticeService;
		this.openid = openid;
		this.text = text;
		this.link = link;
	}

	@Override
	public void run() {
		noticeService.noticeQplusUser(openid, text, link);
	}
}
