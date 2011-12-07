package com.juzhai.passport.service;

import java.util.List;

import com.juzhai.core.exception.JuzhaiException;
import com.juzhai.passport.bean.ProfileCache;

public interface IInterestUserService {

	/**
	 * 收藏感兴趣的人
	 * 
	 * @param uid
	 * @param targetUid
	 * @throws JuzhaiException
	 */
	void interestUser(long uid, long targetUid) throws JuzhaiException;

	/**
	 * 删除我感兴趣的人
	 * 
	 * @param uid
	 * @param targetUid
	 */
	void removeInterestUser(long uid, long targetUid);

	/**
	 * 感兴趣的人列表
	 * 
	 * @param uid
	 * @param firstResult
	 * @param maxResults
	 * @return
	 */
	List<ProfileCache> listInterestUser(long uid, int firstResult,
			int maxResults);

	/**
	 * 感兴趣的人数量
	 * 
	 * @param uid
	 * @return
	 */
	int countInterestUser(long uid);

	/**
	 * 对我感兴趣的人
	 * 
	 * @param uid
	 * @param firstResult
	 * @param maxResult
	 * @return
	 */
	List<ProfileCache> listInteresteMeUser(long uid, int firstResult,
			int maxResults);

	/**
	 * 对我感兴趣的人数量
	 * 
	 * @param uid
	 * @return
	 */
	int countInteresteMeUser(long uid);
}
