package com.juzhai.act.service;

import com.juzhai.act.model.ActDetail;

public interface IActDetailService {

	/**
	 * 更新项目详情
	 * 
	 * @param actId
	 * @param detail
	 */
	void updateActDetail(long actId, String detail);

	/**
	 * 获取项目详情
	 * 
	 * @param actId
	 * @return
	 */
	ActDetail getActDetail(long actId);

}
