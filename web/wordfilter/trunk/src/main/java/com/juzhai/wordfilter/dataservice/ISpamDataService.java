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
 * �������ݷ���ӿڣ��������������ݵĻ�ȡ�ӿ�
 * 
 * @author xiaolin
 * 
 *         2008-3-3 create
 */
public interface ISpamDataService {

	/**
	 * ��ȡ��Ҫ�����˵����δ�
	 * 
	 * @return
	 */
	public List<SpamStruct> getSpamWords();

	/**
	 * ��ȡ��Ҫ�����ε�ip����
	 * 
	 * @return
	 */
	public Set<String> getSpamIPs();

	/**
	 * ��ȡ��Ҫ�����ε��û�����
	 * 
	 * @return
	 */
	public Set<Integer> getSpamUsers();

	/**
	 * ���������ο�����.
	 * 
	 * @return
	 */
	public boolean updateSpamData();

	/**
	 * ������ʱ�����������ݵ�����
	 */
	public void launchTask();
}
