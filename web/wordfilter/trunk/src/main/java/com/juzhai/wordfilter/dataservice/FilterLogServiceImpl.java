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
 * ������־����
 * 
 * @author xiaolin
 * 
 *         2008-3-5 create
 */
public class FilterLogServiceImpl implements IFilterLogService {
	private static final Log log = LogFactory
			.getLog(FilterLogServiceImpl.class);

	/**
	 * ��������������.
	 */
	private int maxCache = 2000;

	/**
	 * �û��������������־��dao����
	 */
	private FilterLogDAO filterLogdao = null;

	/**
	 * ��ʱ���������־���ݵļ��϶��󣬿��ǵ�����������������������־�����࣬��ʹ���ڴ�ռ�ù�����������һ���ż�ֵ����Ӧ����:
	 * {@link #maxCache}
	 */
	private List<Object[]> logData = null;
	/**
	 * ������������
	 */
	private long count = 0;

	public FilterLogServiceImpl() {
		logData = new ArrayList<Object[]>();

	}

	@Override
	public void initTask() {
		Timer timer = new Timer("filter", true); // ��������Ϊ�ػ��̣߳���ֹû�б�Ҫ��������ȡ�
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
							log.info("�������طǷ�����������" + tmpData.size()
									+ "\n��ǰ������������:" + count);
						}
					}
				} catch (Exception e) {
					log.error("ִ�й�����־�����������" + e.getMessage(), e);
					// filterLogdao.resetCachedStatement();
				}
			}

		}, new Date(), 30 * 1000); // 30��ִ��һ�Ρ�
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
			logData.remove(logData.size() - 1);// ���������󻺴�����������ɵ�����ɾ����
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
			log.info("��󻺴�����ֵ����Ϊ:" + size);
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