/*
 *The code is written by 51juzhai, All rights is reserved
 */
package com.juzhai.act.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.juzhai.act.exception.ActInputException;
import com.juzhai.act.model.Act;

public interface IActService {

	/**
	 * act是否存在
	 * 
	 * @param actId
	 * @return
	 */
	boolean actExist(long actId);

	/**
	 * 根据ActId获取Act
	 * 
	 * @param actId
	 */
	Act getActById(long actId);

	/**
	 * 获取多个Act
	 * 
	 * @param actIds
	 * @return
	 */
	Map<Long, Act> getMultiActByIds(List<Long> actIds);

	/**
	 * 获取多个Act
	 * 
	 * @param actIds
	 * @return
	 */
	List<Act> getActListByIds(List<Long> actIds);

	/**
	 * 审核用户自定义的Act
	 * 
	 * @param rawActId
	 * @param actCategoryIds
	 *            用逗号隔开的行为分类ID
	 */
	void verifyAct(long rawActId, String actCategoryIds);

	/**
	 * 创建Act
	 * 
	 * @param uid
	 *            创建者
	 * @param actName
	 *            名称
	 * @param categoryIds
	 *            类别Id列表
	 * @return 带有id的Act实体
	 * @throws ActInputException
	 */
	Act createAct(long uid, String actName, List<Long> categoryIds)
			throws ActInputException;

	/**
	 * 创建Act
	 * 
	 * @param uid
	 *            创建者
	 * @param actName
	 *            名称
	 * @param categoryIds
	 *            类别Id列表
	 * @return 带有id的Act实体
	 * @throws ActInputException
	 */
	Act createAct(Act act, List<Long> categoryIds) throws ActInputException;

	/**
	 * 根据name查询
	 * 
	 * @param name
	 * @return
	 */
	Act getActByName(String name);

	/**
	 * 增加或者减少人气
	 * 
	 * @param actId
	 * @param p
	 *            正数加，负数减
	 */
	void inOrDePopularity(long actId, int p);

	/**
	 * 索引查询
	 * 
	 * @param queryString
	 *            查询内容
	 * @param count
	 *            查询数量
	 * @return
	 */
	List<String> indexSearchName(String queryString, int count);

	/**
	 * 近义词ActId列表
	 * 
	 * @param actId
	 * @return
	 */
	List<Long> listSynonymIds(long actId);

	/**
	 * 近义词Actl列表
	 * 
	 * @param actId
	 * @return
	 */
	List<Act> listSynonymActs(long actId);

	/**
	 * 设置两个近义词
	 * 
	 * @param actId1
	 * @param actId2
	 */
	void addSynonym(long actId1, long actId2);

	/**
	 * 设置两个近义词
	 * 
	 * @param actId1
	 * @param actName2
	 * @throws ActInputException
	 */
	void addSynonym(long actId1, String actName2) throws ActInputException;

	/**
	 * 取消近义词
	 * 
	 * @param actId
	 * @param removeActId
	 */
	void removeSynonym(long actId, long removeActId);

	/**
	 * 查询新的兴趣
	 * 
	 * @param startDate
	 * @param endDate
	 * @param order
	 *            0.热度 1.时间
	 * @return
	 */
	List<Act> searchNewActs(Date startDate, Date endDate, int order,
			int firstResult, int maxResults);

	/**
	 * 新的兴趣数量
	 * 
	 * @param startDate
	 * @param endDate
	 *            0.热度 1.时间
	 * @return
	 */
	int countNewActs(Date startDate, Date endDate);

	/**
	 * 添加屏蔽词
	 * 
	 * @param actId
	 */
	void addActShield(long actId);

	/**
	 * 删除屏蔽词
	 * 
	 * @param actId
	 */
	void removeActShield(long actId);

	/**
	 * 判断是否是屏蔽词
	 * 
	 * @param actId
	 * @return
	 */
	boolean isShieldAct(long actId);

	/**
	 * 屏蔽词ActId列表
	 * 
	 * @param actId
	 * @return
	 */
	List<Long> listShieldActIds();

	/**
	 * 屏蔽词Act列表
	 * 
	 * @param actId
	 * @return
	 */
	List<Act> listShieldActs();

	/**
	 * 根据搜索条件找出acts
	 * 
	 * @param beginDate
	 * @param endDate
	 * @param name
	 * @param active
	 * @param catId
	 * @return
	 */
	List<Act> searchActs(Date beginDate, Date endDate, String name,
			int firstResult, int maxResults);

	/**
	 * 根据条件统计数量
	 * 
	 * @param beginDate
	 * @param endDate
	 * @param name
	 * @return
	 */
	int searchActsCount(Date beginDate, Date endDate, String name);

	void updateAct(Act act, List<Long> categoryIds);
}
