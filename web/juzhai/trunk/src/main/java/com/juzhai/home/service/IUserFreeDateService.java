package com.juzhai.home.service;

import java.util.Date;
import java.util.List;

public interface IUserFreeDateService {

	/**
	 * 设置空闲日期
	 * 
	 * @param uid
	 * @param dateList
	 */
	void setFreeDate(long uid, Date freeDate);

	/**
	 * 取消空闲日期
	 * 
	 * @param id
	 * @param date
	 */
	void unSetFreeDate(long uid, Date date);

	/**
	 * 用户空闲日期列表
	 * 
	 * @param uid
	 * @return
	 */
	List<Date> userFreeDateList(long uid);

	/**
	 * 空闲日期数量
	 * 
	 * @param uid
	 * @return
	 */
	int countFreeDate(long uid);
}
