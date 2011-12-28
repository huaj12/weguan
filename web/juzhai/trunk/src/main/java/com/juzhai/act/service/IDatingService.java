package com.juzhai.act.service;

import java.util.List;

import com.juzhai.act.bean.ConsumeType;
import com.juzhai.act.bean.ContactType;
import com.juzhai.act.exception.DatingInputException;
import com.juzhai.act.model.Dating;

public interface IDatingService {

	/**
	 * 验证是否有资格
	 * 
	 * @param uid
	 * @throws DatingInputException
	 */
	void checkCanDate(long uid, long targetId, long datingId)
			throws DatingInputException;

	/**
	 * 验证是否能接受邀请
	 * 
	 * @param uid
	 * @param datingId
	 * @return
	 * @throws DatingInputException
	 */
	Dating checkCanRespDating(long uid, long datingId)
			throws DatingInputException;

	/**
	 * 约人
	 * 
	 * @param uid
	 * @param targetId
	 * @param actId
	 * @param consumeType
	 * @param contactType
	 * @param contactValue
	 * @throws DatingInputException
	 */
	long date(long uid, long targetId, long actId, ConsumeType consumeType,
			ContactType contactType, String contactValue)
			throws DatingInputException;

	/**
	 * 修改约人记录
	 * 
	 * @param uid
	 * @param datingId
	 * @param actId
	 * @param consumeType
	 * @param contactType
	 * @param contactValue
	 * @throws DatingInputException
	 */
	long modifyDating(long uid, long datingId, long actId,
			ConsumeType consumeType, ContactType contactType,
			String contactValue) throws DatingInputException;

	/**
	 * 删除约人记录
	 * 
	 * @param uid
	 * @param datingId
	 */
	void deleteDating(long uid, long datingId);

	/**
	 * 回应约人记录
	 * 
	 * @param uid
	 * @param datingId
	 * @param accept
	 * @throws DatingInputException
	 */
	void acceptDating(long uid, long datingId, ContactType contactType,
			String contactValue) throws DatingInputException;

	/**
	 * 我约的列表
	 * 
	 * @param uid
	 * @param firstResult
	 * @param maxResults
	 * @return
	 */
	List<Dating> listDating(long uid, Integer response, int firstResult,
			int maxResults);

	/**
	 * 我约的数量
	 * 
	 * @param uid
	 * @return
	 */
	int countDating(long uid, Integer response);

	/**
	 * 被约的列表
	 * 
	 * @param uid
	 * @param firstResult
	 * @param maxResults
	 * @return
	 */
	List<Dating> listDatingMe(long uid, int firstResult, int maxResults);

	/**
	 * 被约的数量
	 * 
	 * @param uid
	 * @return
	 */
	int countDatingMe(long uid);

	/**
	 * 获取约会记录
	 * 
	 * @param uid
	 * @param targetUid
	 * @return
	 */
	Dating fetchDating(long uid, long targetUid);

	/**
	 * 获取dating
	 * 
	 * @param datingId
	 * @return
	 */
	Dating getDatingByDatingId(long datingId);
}
