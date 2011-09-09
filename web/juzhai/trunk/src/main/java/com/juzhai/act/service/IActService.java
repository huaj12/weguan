/*
 *The code is written by 51juzhai, All rights is reserved
 */
package com.juzhai.act.service;

import java.util.List;

import com.juzhai.act.exception.ActInputException;
import com.juzhai.act.model.Act;

public interface IActService {

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
	 * @param actId1
	 * @param actId2
	 */
	void removeSynonym(long actId1, long actId2);
}
