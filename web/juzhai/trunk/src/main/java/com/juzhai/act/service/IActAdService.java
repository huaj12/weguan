package com.juzhai.act.service;

import java.util.List;

import com.juzhai.act.exception.ActAdInputException;
import com.juzhai.act.model.Act;
import com.juzhai.act.model.ActAd;

public interface IActAdService {

	void createActAd(String actName, long rawAdId) throws ActAdInputException;

	boolean isUrlExist(String url, long actId);

	/**
	 * 移除项目的广告
	 * 
	 * @param actAdId
	 * @throws ActAdInputException
	 */
	void remove(long actAdId) throws ActAdInputException;

	/**
	 * 获取广告信息曾添加到的Act列表
	 * 
	 * @param id
	 * @return
	 */
	List<Act> getActByRawAd(long rawAdId);

	/**
	 * 获取所有广告
	 * 
	 * @param actId
	 * @return
	 */
	List<ActAd> getActAds(long actId);

	/**
	 * 项目的广告
	 * 
	 * @param actId
	 * @return
	 */
	List<ActAd> listActAdByActId(long actId, long cityId, int count);
}
