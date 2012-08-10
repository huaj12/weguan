package com.juzhai.cms.task;

import com.juzhai.platform.service.IQplusPushService;

public class QplusSendTask implements Runnable {
	private String openid;
	private String text;
	private String link;
	private IQplusPushService qplusPushService;

	public QplusSendTask(String openid, String text, String link,
			IQplusPushService qplusPushService) {
		this.openid = openid;
		this.text = text;
		this.link = link;
		this.qplusPushService = qplusPushService;
	}

	@Override
	public void run() {
		qplusPushService.push(openid, text, link);
	}
}
