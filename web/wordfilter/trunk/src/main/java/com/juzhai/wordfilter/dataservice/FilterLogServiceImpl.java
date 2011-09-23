/* 
 * FilterLogService.java * 
 * Copyright 2007 Shanghai NaLi.  
 * All rights reserved. 
 */

package com.juzhai.wordfilter.dataservice;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.juzhai.wordfilter.core.Config;
import com.juzhai.wordfilter.dao.FilterLogDAO;

/**
 * 过滤日志服务
 * 
 * @author xiaolin
 * 
 *         2008-3-5 create
 */
public class FilterLogServiceImpl implements IFilterLogService {
	private static final Log log = LogFactory
			.getLog(FilterLogServiceImpl.class);

	/**
	 * 缓存的最大数据量.
	 */
	private int maxCache = 2000;

	/**
	 * 用户批量插入过滤日志的dao对象。
	 */
	private FilterLogDAO filterLogdao = null;

	/**
	 * 临时缓存过滤日志数据的集合对象，考虑到如果出现特殊情况，导致日志量过多，而使得内存占用过大，特设置了一个门槛值，对应变量:
	 * {@link #maxCache}
	 */
	private List<Object[]> logData = null;
	/**
	 * 垃圾评论总数
	 */
	private long count = 0;

	public FilterLogServiceImpl() {
		logData = new ArrayList<Object[]>();

	}

	@Override
	public void initTask() {
		Timer timer = new Timer("filter", true); // 将其设置为守护线程，防止没有必要的任务调度。
		timer.schedule(new TimerTask() {
			@Override
			public void run() {
				List<Object[]> tmpData = logData;
				logData = new ArrayList<Object[]>();
				count = count + ((long) tmpData.size());
				try {
					if (Config.isRecordLog) {
						filterLogdao.batchUpdate(tmpData);
					} else {
						if (log.isInfoEnabled()) {
							log.info("本次拦截非法评论数量：" + tmpData.size()
									+ "\n当前所有拦截数量:" + count);
						}
					}
				} catch (Exception e) {
					log.error("执行过滤日志批量插入出错：" + e.getMessage(), e);
					// filterLogdao.resetCachedStatement();
				}
			}

		}, new Date(), 30 * 1000); // 30秒执行一次。
		if (log.isInfoEnabled()) {
			log.info("Task has been initialized ,the regular period is 30 seconds");
		}
	}

	@Override
	public int getMaxCacheSize() {
		return maxCache;
	}

	@Override
	public void addLog(Object[] ob) {
		if (ob == null) {
			return;
		}
		if (logData.size() >= getMaxCacheSize()) {
			logData.remove(logData.size() - 1);// 如果超出最大缓存数量，将最旧的数据删除。
		}
		logData.add(ob);
	}

	@Override
	public int getLogCount() {
		return logData.size();
	}

	public void setMaxCacheSize(int size) {
		maxCache = size;
		if (logData.size() > size) {
			for (int i = 0; i < logData.size() - size; i++) {
				logData.remove(i);
			}
		}
		if (log.isInfoEnabled()) {
			log.info("最大缓存数据值设置为:" + size);
		}
	}

	public FilterLogDAO getFilterLogdao() {
		return filterLogdao;
	}

	public void setFilterLogdao(FilterLogDAO filterLogdao) {
		this.filterLogdao = filterLogdao;
		if (filterLogdao == null) {
			log.error("Task to save all illegal data into database isn't initialized at all,because no filterlogdao is gived ");
			return;
		}
		initTask();
	}
}