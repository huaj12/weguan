package com.juzhai.passport.service;

import java.util.List;

import com.juzhai.passport.bean.ProfileCache;
import com.juzhai.passport.exception.InterestUserException;

public interface IInterestUserService {

	/**
	 * 收藏感兴趣的人
	 * 
	 * @param uid
	 * @param targetUid
	 * @throws InterestUserException
	 */
	void interestUser(long uid, long targetUid) throws InterestUserException;

	/**
	 * 收藏感兴趣的人
	 * 
	 * @param uid
	 * @param targetUid
	 * @throws InterestUserException
	 */
	void interestUser(long uid, long targetUid, String content)
			throws InterestUserException;

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
	List<ProfileCache> listInterestMeUser(long uid, int firstResult,
			int maxResults);

	/**
	 * 对我感兴趣的人数量
	 * 
	 * @param uid
	 * @return
	 */
	int countInterestMeUser(long uid);

	/**
	 * 是否感兴趣
	 * 
	 * @param uid
	 * @param targetUid
	 * @return
	 */
	boolean isInterest(long uid, long targetUid);

	/**
	 * 感兴趣的人Id列表
	 * 
	 * @param uid
	 * @return
	 */
	List<Long> interestUids(long uid);
}
