/* 
 * ISpamDataService.java * 
 * Copyright 2007 Shanghai NaLi.  
 * All rights reserved. 
 */

package com.juzhai.wordfilter.dataservice;

import java.util.List;
import java.util.Set;

import com.juzhai.wordfilter.core.SpamStruct;

/**
 * 垃圾数据服务接口，定义了垃圾数据的获取接口
 * 
 * @author xiaolin
 * 
 *         2008-3-3 create
 */
public interface ISpamDataService {

	/**
	 * 获取需要被过滤的屏蔽词
	 * 
	 * @return
	 */
	public List<SpamStruct> getSpamWords();

	/**
	 * 获取需要被屏蔽的ip集合
	 * 
	 * @return
	 */
	public Set<String> getSpamIPs();

	/**
	 * 获取需要被屏蔽的用户集合
	 * 
	 * @return
	 */
	public Set<Integer> getSpamUsers();

	/**
	 * 更新垃圾参考数据.
	 * 
	 * @return
	 */
	public boolean updateSpamData();

	/**
	 * 启动定时更新垃圾数据的任务
	 */
	public void launchTask();
}
