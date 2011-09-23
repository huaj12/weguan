/* 
 * IFilterLogService.java * 
 * Copyright 2007 Shanghai NaLi.  
 * All rights reserved. 
 */

package com.juzhai.wordfilter.dataservice;

/**
 * 文字过滤服务日志接口.
 * 
 * @author xiaolin
 * 
 *         2008-3-5 create
 */
public interface IFilterLogService {

	/**
	 * 初始化定时任务，该定时任务按照一定周期将内存中的数据写到数据库中。
	 */
	public void initTask();

	/**
	 * 获取允许缓存日志的最大数量。
	 */
	public int getMaxCacheSize();

	/**
	 * 添加没有通过的用户操作信息
	 * 
	 * @param ob
	 */
	public void addLog(Object[] ob);

	/**
	 * 获取当前缓存在内存中日志数量。
	 * 
	 * @return
	 */
	public int getLogCount();
}
