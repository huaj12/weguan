/*
 *The code is written by 51juzhai, All rights is reserved
 */
package com.juzhai.act.service;

import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.juzhai.act.exception.ActInputException;
import com.juzhai.act.exception.UploadImageException;
import com.juzhai.act.model.Act;
import com.juzhai.act.model.ActLink;
import com.juzhai.cms.controller.form.AddActForm;

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
	 * 增加或者减少人气
	 * 
	 * @param actId
	 * @param p
	 *            正数加，负数减
	 */
	void inOrDeTpActPopularity(String tpName, long actId, int p);

	/**
	 * 获取Act在平台上的流行度
	 * 
	 * @param tpId
	 * @param actId
	 * @return
	 */
	long getTpActPopularity(long tpId, long actId);

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
	 * 根据搜索条件找出acts
	 * 
	 * @param beginDate
	 * @param endDate
	 * @param name
	 * @param active
	 * @param catId
	 * @return
	 */
	List<Act> queryActs(Date beginDate, Date endDate, String name,
			int firstResult, int maxResults);

	/**
	 * 根据条件统计数量
	 * 
	 * @param beginDate
	 * @param endDate
	 * @param name
	 * @return
	 */
	int queryActsCount(Date beginDate, Date endDate, String name);

	/**
	 * 项目的相关链接
	 * 
	 * @param actId
	 * @return
	 */
	List<ActLink> listActLinkByActId(long actId, int count);

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
	 * 更新项目
	 * 
	 * @param uid
	 * @param addActForm
	 * @throws UploadImageException
	 * @throws ParseException
	 */
	void cmsUpdateAct(long uid, AddActForm addActForm)
			throws ActInputException, UploadImageException, ParseException;

	/**
	 * 后台添加项目
	 * 
	 * @param uid
	 * @param addActForm
	 * @throws UploadImageException
	 * @throws ActInputException
	 * @throws ParseException
	 */
	void cmsCreateAct(long uid, AddActForm addActForm)
			throws UploadImageException, ActInputException, ParseException;
}
