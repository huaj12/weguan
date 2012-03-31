package com.juzhai.passport.service;

import java.util.Date;
import java.util.List;

import com.juzhai.passport.model.Passport;

public interface IPassportService {

	/**
	 * 根据Uid搜索Passport
	 * 
	 * @param uid
	 * @return
	 */
	Passport getPassportByUid(long uid);

	/**
	 * 锁定用户
	 * 
	 * @param uid
	 * @param time锁定时间time
	 *            ==0则解锁
	 */
	void lockUser(long uid, Date time);

	/**
	 * 获取所有锁定用户
	 * 
	 * @param firstResult
	 * @param maxResults
	 * @return
	 */
	List<Passport> listLockUser(int firstResult, int maxResults);

	/**
	 * 获取锁定用户数量
	 * 
	 * @return
	 */
	int countLockUser();

	/**
	 * 总数
	 * 
	 * @return
	 */
	int totalCount();

	/**
	 * 获取邀请的人
	 * 
	 * @param inviterUid
	 * @return
	 */
	List<Long> listInviteUsers(long inviterUid);
}
