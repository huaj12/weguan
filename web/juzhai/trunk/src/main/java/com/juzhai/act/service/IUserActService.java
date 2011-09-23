/*
 *The code is written by 51juzhai, All rights is reserved
 */
package com.juzhai.act.service;

import java.util.Collection;
import java.util.Date;
import java.util.List;

import com.juzhai.act.bean.ActDealType;
import com.juzhai.act.controller.view.UserActView;
import com.juzhai.act.exception.ActInputException;
import com.juzhai.act.model.Act;
import com.juzhai.act.model.UserAct;
import com.juzhai.home.exception.IndexException;

public interface IUserActService {

	/**
	 * 处理Act
	 * 
	 * @param uid
	 *            处理者ID
	 * @param actId
	 *            处理的ActId
	 * @param friendId
	 *            好友ID
	 * @param type
	 *            处理方式
	 * @throws IndexException
	 */
	void respFeed(long uid, long actId, long friendId, ActDealType type)
			throws IndexException;

	/**
	 * 处理Act
	 * 
	 * @param uid
	 *            处理者ID
	 * @param actId
	 *            处理的ActId
	 * @param tpFriendId
	 *            第三方好友ID
	 * @param tpId
	 *            第三方id
	 * @param type
	 *            处理方式
	 * @throws IndexException
	 */
	void respRandom(long uid, long actId, String tpFriendId, long tpId,
			ActDealType type) throws IndexException;

	/**
	 * 添加拒宅活动（用户可以自定义，但是不会立即对其他用户生效，通过验证后，会被其他人使用）
	 * 
	 * @param uid
	 *            用户ID
	 * @param actName
	 *            活动名称
	 * @param isSyn
	 *            是否同步
	 * @throws ActInputException
	 */
	void addAct(long uid, String actName, boolean isSyn)
			throws ActInputException;

	/**
	 * 添加已经存在的拒宅活动
	 * 
	 * @param uid
	 *            用户ID
	 * @param actId
	 *            活动名称
	 * @param isSyn
	 *            是否同步
	 * @throws ActInputException
	 */
	void addAct(long uid, long actId, boolean isSyn) throws ActInputException;

	/**
	 * 删除我的拒宅活动
	 * 
	 * @param uid
	 * @param actId
	 */
	void removeAct(long uid, long actId);

	/**
	 * 从缓存根据用户id获取ActId(不带热度),根据热度排序
	 * 
	 * @param uid
	 *            用户ID
	 * @param count
	 *            获取的数量 。<code>Integer.MIN_VALUE</code>表示获取全部
	 * @return
	 */
	List<Long> getUserActIdsFromCache(long uid, int count);

	/**
	 * 从缓存根据用户id获取Act
	 * 
	 * @param uid
	 *            用户ID
	 * @param count
	 *            获取的数量 。<code>Integer.MIN_VALUE</code>表示获取全部
	 * @return
	 */
	List<Act> getUserActFromCache(long uid, int count);

	/**
	 * 根据页码和每页显示数量，列出某用户的Act
	 * 
	 * @param uid
	 * @param page
	 * @param maxRows
	 * @return
	 */
	List<UserActView> pageUserActView(long uid, int page, int maxRows);

	/**
	 * 某用户的Act数量
	 * 
	 * @param uid
	 * @return
	 */
	int countUserActByUid(long uid);

	/**
	 * 判断用户有没有Act
	 * 
	 * @param uid
	 * @param actId
	 * @return
	 */
	boolean hasAct(long uid, long actId);

	/**
	 * 根据用户ID和ActId获取UserAct
	 * 
	 * @param uid
	 * @param actId
	 * @return
	 */
	UserAct getUserAct(long uid, long actId);

	/**
	 * 判断某个用户是否对某个拒宅兴趣感兴趣，包括近义词兴趣
	 * 
	 * @param uid
	 * @param actId
	 * @return
	 */
	boolean isInterested(long uid, long actId);

	/**
	 * 列出最近好友更新的兴趣
	 * 
	 * @param friendIds
	 * @param startDate
	 * @return
	 */
	List<UserAct> listFriendsRecentAct(Collection<Long> friendIds,
			Date startDate, int firstResult, int maxResult);

	/**
	 * 统计最近好友更新的兴趣的数量
	 * 
	 * @param friendIds
	 * @param startDate
	 * @return
	 */
	int countFriendsRecentAct(Collection<Long> friendIds, Date startDate);
}
