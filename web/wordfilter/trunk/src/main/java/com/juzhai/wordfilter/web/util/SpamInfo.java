/* 
 * SpamInfo.java * 
 * Copyright 2008 Shanghai NaLi.  
 * All rights reserved. 
 */

package com.juzhai.wordfilter.web.util;

import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import org.apache.log4j.Logger;

import com.juzhai.wordfilter.core.SpamStruct;
import com.juzhai.wordfilter.dataservice.ISpamDataService;

/**
 * 
 * @author xiaolin
 * 
 *         2009-8-6 create
 */
public class SpamInfo {

	private static final Logger logger = Logger.getLogger(SpamInfo.class);
	private static SpamInfo info;

	private String spamWordString = "";

	private SpamInfo() {
		Timer timer = new Timer("spam loader", true); // 将其设置为守护线程，防止没有必要的任务调度。
		timer.schedule(new TimerTask() {
			@Override
			public void run() {
				updateSpamWords();
				logger.info("Update spam words successfully!");
			}

		}, new Date(), 60 * 1000); // 1分钟执行一次。
	}

	private void updateSpamWords() {
		List<SpamStruct> list = ((ISpamDataService) SpringContextUtil
				.getBean("spamDataService")).getSpamWords();
		if (list != null && list.size() > 0) {
			StringBuilder sb = new StringBuilder();
			for (SpamStruct ss : list) {
				ss.getText();
				sb.append(ss.getScore()).append("\t").append(ss.getText())
						.append("\n");
			}
			sb.deleteCharAt(sb.length() - 1);

			spamWordString = sb.toString();
		}
	}

	public static SpamInfo getInstance() {
		if (info == null) {
			info = new SpamInfo();
		}
		return info;
	}

	public String getSpamWordString() {
		return spamWordString;
	}

}