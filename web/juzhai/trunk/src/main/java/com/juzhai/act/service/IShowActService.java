package com.juzhai.act.service;

import java.util.List;

import com.juzhai.act.model.Act;
import com.juzhai.act.model.ShowAct;
import com.juzhai.index.bean.ShowActOrder;

public interface IShowActService {

	/**
	 * 更新ShowAct库
	 */
	void updateShowActs();

	/**
	 * 根据项目Id获取ShowAct
	 * 
	 * @param actId
	 * @return
	 */
	ShowAct getShowActByActId(long actId);

	/**
	 * showAct列表
	 * 
	 * @param cityId
	 * @param categoryId
	 * @param order
	 * @return
	 */
	List<Act> listShowActs(long cityId, long categoryId, ShowActOrder order,
			int firstResult, int maxResults);

	/**
	 * showAct个数
	 * 
	 * @param cityId
	 * @param categoryId
	 * @return
	 */
	int countShowActs(long cityId, long categoryId);
}
