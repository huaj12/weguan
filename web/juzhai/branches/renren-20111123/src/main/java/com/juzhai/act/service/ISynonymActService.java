package com.juzhai.act.service;

import java.util.List;

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

}
