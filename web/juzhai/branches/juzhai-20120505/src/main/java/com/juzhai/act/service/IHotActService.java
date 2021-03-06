package com.juzhai.act.service;

import java.util.Date;
import java.util.List;

import com.juzhai.act.model.Act;
import com.juzhai.act.model.HotAct;

public interface IHotActService {

	/**
	 * 推荐兴趣
	 * 
	 * @param actId
	 */
	void activeHotAct(long actId);

	/**
	 * 推荐兴趣
	 * 
	 * @param actName
	 * @return false表示actName的act不存在
	 */
	boolean activeHotAct(String actName);

	/**
	 * 取消推荐兴趣
	 * 
	 * @param actId
	 */
	void cancelHotAct(long actId);

	/**
	 * 删除推荐兴趣
	 * 
	 * @param actId
	 */
	void deleteHotAct(long actId);

	/**
	 * 列出推荐的Act
	 * 
	 * @param active
	 *            是否当前有效
	 * @return
	 */
	List<Act> listHotAct(boolean active, int firstResult, int maxResults);

	/**
	 * 列出推荐的Act
	 * 
	 * @param active
	 *            是否当前有效
	 * @return
	 */
	List<Act> listHotActByCreateTime(Date lastUpdateTime, boolean active,
			int firstResult, int maxResults);

	/**
	 * 列出推荐的Act
	 * 
	 * @param active
	 *            是否当前有效
	 * @return
	 */
	List<HotAct> hotActList(boolean active, int firstResult, int maxResults);

	/**
	 * 推荐的Act数量
	 * 
	 * @param active
	 *            是否当前有效
	 * @return
	 */
	int countHotAct(boolean active);

	/**
	 * 判断特定兴趣是否在推荐列表里面存在
	 * 
	 * @param actId
	 * @return
	 */
	boolean isExistHotAct(long actId);
}
