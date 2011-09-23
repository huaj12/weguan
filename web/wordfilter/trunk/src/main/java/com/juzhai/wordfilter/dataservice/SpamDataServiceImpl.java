/* 
 * SpamDataServiceImpl.java * 
 * Copyright 2007 Shanghai NaLi.  
 * All rights reserved. 
 */

package com.juzhai.wordfilter.dataservice;

import java.util.List;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;

import org.apache.log4j.Logger;

import com.juzhai.wordfilter.core.Filter;
import com.juzhai.wordfilter.core.SpamStruct;
import com.juzhai.wordfilter.dao.FilterDataDAO;
import com.juzhai.wordfilter.web.util.SpringContextUtil;

/**
 * 垃圾数据服务接口实现类。对于更新接口没有实现，目前还不需要.
 * 
 * @author xiaolin
 * 
 *         2008-3-3 create
 */
public class SpamDataServiceImpl implements ISpamDataService {
	private static final Logger logger = Logger
			.getLogger(SpamDataServiceImpl.class);

	private long period = 12 * 3600000;

	@Override
	public Set<String> getSpamIPs() {
		FilterDataDAO dao = (FilterDataDAO) SpringContextUtil
				.getBean("filterDataDAO");
		return dao.getSpamIPs();
	}

	@Override
	public Set<Integer> getSpamUsers() {
		FilterDataDAO dao = (FilterDataDAO) SpringContextUtil
				.getBean("filterDataDAO");
		return dao.getSpamUsers();
	}

	@Override
	public List<SpamStruct> getSpamWords() {
		FilterDataDAO dao = (FilterDataDAO) SpringContextUtil
				.getBean("filterDataDAO");
		return dao.getSpamWords();
	}

	@Override
	public boolean updateSpamData() {
		try {
			String[] spamAgentList = new String[0];
			Set<Integer> spamUsers = getSpamUsers();
			Set<String> spamIPs = getSpamIPs();
			List<SpamStruct> spamWords = getSpamWords();

			Filter.getInstance().updateSpamData(spamAgentList, spamUsers,
					spamIPs, spamWords);
			if (logger.isInfoEnabled()) {
				logger.info("System has finished updating spam data successfully!");
			}
			return true;
		} catch (Throwable e) {
			logger.error("updating spam data failed:" + e.getMessage(), e);
			return false;
		}
	}

	/**
	 * launch a task that updates spam data at regular intervals.
	 */
	@Override
	public void launchTask() {
		Timer timer = new Timer("update spam data", true);
		// long period=1000*3600*24; //one day
		timer.schedule(new TimerTask() {
			public void run() {
				updateSpamData();
			}
		}, period, period);
		if (logger.isInfoEnabled()) {
			logger.info("The task updating spam data every one day has been launched!");
		}

		Timer resourceCheck = new Timer("Resource Check", true);
		resourceCheck.schedule(new TimerTask() {
			public void run() {
				long totalMemory = Runtime.getRuntime().totalMemory();
				long freeMemory = Runtime.getRuntime().freeMemory();
				double rate = ((double) freeMemory) / totalMemory;
				if (rate < 0.1) {
					Filter.getInstance().releaseMemory();
				}
			}
		}, 0, 30 * 1000);
	}

	public long getPeriod() {
		return period;
	}

	public void setPeriod(long period) {
		this.period = period;
	}
}
