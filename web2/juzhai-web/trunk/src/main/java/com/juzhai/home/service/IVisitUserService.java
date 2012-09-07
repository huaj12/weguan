package com.juzhai.home.service;

import java.util.List;

import com.juzhai.home.controller.view.VisitorView;

public interface IVisitUserService {

	/**
	 * 添加访问者
	 * 
	 * @param uid
	 * @param visitUid
	 */
	void addVisitUser(long uid, long visitUid);

	/**
	 * 来访者列表
	 * 
	 * @param uid
	 * @param firstResult
	 * @param maxResults
	 * @return
	 */
	List<VisitorView> listVisitUsers(long uid, int firstResult, int maxResults);

	/**
	 * 来访者数量
	 * 
	 * @param uid
	 * @return
	 */
	int countVisitUsers(long uid);

	/**
	 * 用户互访
	 * 
	 * @param uid
	 */
	void autoExchangeVisits(long uid);
}
