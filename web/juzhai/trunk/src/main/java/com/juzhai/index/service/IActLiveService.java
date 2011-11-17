package com.juzhai.index.service;

import java.util.Date;
import java.util.List;

import com.juzhai.index.controller.view.ActLiveView;

public interface IActLiveService {

	/**
	 * 添加新的直播
	 * 
	 * @param uid
	 * @param actId
	 */
	void addNewLive(long uid, long tpId, long actId, Date time);

	/**
	 * 删除直播
	 * 
	 * @param uid
	 * @param actId
	 */
	void removeLive(long uid, long tpId, long actId);

	/**
	 * 直播列表
	 * 
	 * @param firstResult
	 * @param maxResults
	 * @return
	 */
	List<ActLiveView> listActivists(long tpId, int firstResult, int maxResults);

	/**
	 * 直播数量
	 * 
	 * @return
	 */
	int countActivists(long tpId);
}
