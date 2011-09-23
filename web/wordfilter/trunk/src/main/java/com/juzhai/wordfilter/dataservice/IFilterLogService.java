/* 
 * IFilterLogService.java * 
 * Copyright 2007 Shanghai NaLi.  
 * All rights reserved. 
 */

package com.juzhai.wordfilter.dataservice;

/**
 * ���ֹ��˷�����־�ӿ�.
 * 
 * @author xiaolin
 * 
 *         2008-3-5 create
 */
public interface IFilterLogService {

	/**
	 * ��ʼ����ʱ���񣬸ö�ʱ������һ�����ڽ��ڴ��е�����д�����ݿ��С�
	 */
	public void initTask();

	/**
	 * ��ȡ��������־�����������
	 */
	public int getMaxCacheSize();

	/**
	 * ���û��ͨ�����û�������Ϣ
	 * 
	 * @param ob
	 */
	public void addLog(Object[] ob);

	/**
	 * ��ȡ��ǰ�������ڴ�����־������
	 * 
	 * @return
	 */
	public int getLogCount();
}
