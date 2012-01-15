package com.juzhai.act.service;

import java.util.List;

import com.juzhai.act.exception.ActInputException;
import com.juzhai.act.model.Act;
import com.juzhai.act.model.SynonymAct;

public interface ISynonymActService {
	/**
	 * 
	 * @param name指向词
	 * 
	 * @param actName同义词
	 */
	boolean synonymAct(String name, String actName);

	boolean synonymAct(String name, long actId);

	List<SynonymAct> getSysonymActs(int firstResult, int maxResults);

	int countSysonymActs();

	boolean updateSynonymAct(Long id, long actId);

	boolean isExist(String name);

	/**
	 * 近义词ActId列表
	 * 
	 * @param actId
	 * @return
	 */
	@Deprecated
	List<Long> listSynonymIds(long actId);

	/**
	 * 近义词Actl列表
	 * 
	 * @param actId
	 * @return
	 */
	@Deprecated
	List<Act> listSynonymActs(long actId);

	/**
	 * 设置两个近义词
	 * 
	 * @param actId1
	 * @param actId2
	 */
	@Deprecated
	void addSynonym(long actId1, long actId2);

	/**
	 * 设置两个近义词
	 * 
	 * @param actId1
	 * @param actName2
	 * @throws ActInputException
	 */
	@Deprecated
	void addSynonym(long actId1, String actName2) throws ActInputException;

	/**
	 * 取消近义词
	 * 
	 * @param actId
	 * @param removeActId
	 */
	@Deprecated
	void removeSynonym(long actId, long removeActId);

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

}
