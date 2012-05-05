package com.juzhai.index.service;

import java.util.Date;
import java.util.List;

import com.juzhai.act.model.Act;

public interface IActRankService {

	/**
	 * 给Act加分
	 * 
	 * @param actId
	 * @param date
	 */
	void incrScore(long actId, Date date);

	/**
	 * 删除某天的排行
	 * 
	 * @param date
	 */
	void removeActRank(Date date);

	/**
	 * 某日排行榜
	 * 
	 * @param count
	 * @return
	 */
	List<Act> listActRank(Date date, int count);
}
