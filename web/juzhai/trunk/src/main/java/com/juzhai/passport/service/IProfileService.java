/*
 *The code is written by 51juzhai, All rights is reserved
 */
package com.juzhai.passport.service;

import java.util.Date;
import java.util.List;

import com.juzhai.core.exception.UploadImageException;
import com.juzhai.lab.controller.form.ProfileMForm;
import com.juzhai.passport.bean.ProfileCache;
import com.juzhai.passport.exception.ProfileInputException;
import com.juzhai.passport.model.Profile;

public interface IProfileService {

	/**
	 * 清除缓存
	 * 
	 * @param uid
	 */
	void clearProfileCache(long uid);

	/**
	 * 根据城市，找出所有在这个城市中的人
	 * 
	 * @param cityId
	 * @return
	 */
	List<Profile> getProfilesByCityId(long cityId);

	/**
	 * 根据用户ID获取Profile，先查缓存
	 * 
	 * @param uid
	 * @return
	 */
	ProfileCache getProfileCacheByUid(long uid);

	/**
	 * 缓存profile
	 * 
	 * @param profile
	 */
	ProfileCache cacheProfile(Profile profile, String tpIdentity);

	/**
	 * 缓存ProfileCache
	 * 
	 * @param uid
	 * @param profileCache
	 */
	void cacheProfileCache(long uid, ProfileCache profileCache);

	/**
	 * 获取订阅邮箱的用户资料对象
	 * 
	 * @param firstResult
	 * @param maxResults
	 * @return
	 */
	List<Profile> getSubEmailProfiles(int firstResult, int maxResults);

	/**
	 * 取用户密钥，当不存在，则生成一个并保存
	 * 
	 * @param uid
	 */
	byte[] getUserSecretKey(long uid);

	/**
	 * 更新最后更新时间
	 * 
	 * @param uid
	 */
	void updateLastUpdateTime(long uid, Date date);

	/**
	 * 更新最后更新时间
	 * 
	 * @param uid
	 */
	void delLastUpdateTime(long uid);

	/**
	 * 根据给出的ID列表，根据最后更新时间排序
	 * 
	 * @param uids
	 * @return
	 */
	List<Profile> listProfileByIdsOrderByLastUpdateTime(List<Long> uids,
			int firstResult, int maxResults);

	/**
	 * 查询Profile(用户资料设置使用)
	 * 
	 * @param uid
	 * @return
	 */
	Profile getProfile(long uid);

	/**
	 * 昵称是否存在排除自己
	 * 
	 * @param nickname
	 * @return
	 */
	boolean isExistNickname(String nickname, long uid);

	/**
	 * 更新性别
	 * 
	 * @param uid
	 * @param gender
	 * @throws ProfileInputException
	 */
	void setGender(long uid, Integer gender) throws ProfileInputException;

	/**
	 * 更新昵称
	 * 
	 * @param uid
	 * @param nickName
	 * @throws ProfileInputException
	 */
	void setNickName(long uid, String nickName) throws ProfileInputException;

	/**
	 * 跟新用户信息
	 * 
	 * @param profile
	 * @throws ProfileInputException
	 */
	void updateProfile(Profile profile) throws ProfileInputException;

	/**
	 * 更新用户头像
	 * 
	 * @param logo
	 * @param uid
	 * @throws UploadImageException
	 */
	void updateLogo(long uid, String filePath, int scaledW, int scaledH, int x,
			int y, int w, int h) throws UploadImageException;

	/**
	 * 保存头像和个人资料（用户手机应用）
	 * 
	 * @param uid
	 * @param profileForm
	 * @throws UploadImageException
	 * @throws ProfileInputException
	 */
	void updateLogoAndProfile(long uid, ProfileMForm profileForm) throws UploadImageException, ProfileInputException;

	/**
	 * web登录用户倒序列表
	 * 
	 * @param gender
	 * @param city
	 * @param firstResult
	 * @param maxResults
	 * @return
	 */
	List<Profile> listProfileOrderByLoginWebTime(List<Long> uids,
			Integer gender, Long city, List<Long> exceptUids, int firstResult,
			int maxResults);

	/**
	 * 数量
	 * 
	 * @param gender
	 * @param city
	 * @return
	 */
	int countProfile(List<Long> uids, Integer gender, Long city,
			List<Long> exceptUids);

	/**
	 * 找伴搜索数量(排除默认头像的人)
	 * 
	 * @param gender
	 * @param city
	 * @param minYear
	 * @param maxYear
	 * @return
	 */
	int countQueryProfile(long excludeUid, Integer gender, Long city,
			Long townId, int minYear, int maxYear);

	/**
	 * 找伴搜索(排除默认头像的人)
	 * 
	 * @param gender
	 * @param city
	 * @param minYear
	 * @param maxYear
	 * @return
	 */
	List<Profile> queryProfile(long excludeUid, Integer gender, Long city,
			Long townId, int minYear, int maxYear, int firstResult,
			int maxResults);

	/**
	 * 根据cityId筛选最新注册的人(排除没有头像的)
	 * 
	 * @param cityId
	 * @param firstResult
	 * @param maxResults
	 * @return
	 */
	List<Profile> listProfileByCityIdOrderCreateTime(Long cityId,
			int firstResult, int maxResults);

	/**
	 * 个人资料完成度，满分100分
	 * 
	 * @return
	 */
	int getProfileCompletion(long uid);

	/**
	 * 是否是有效用户（上传头像且通过引导）
	 * 
	 * @param uid
	 * @return
	 */
	boolean isValidUser(long uid);

	/**
	 * 完成引导
	 * 
	 * @param profile
	 * @throws ProfileInputException
	 */
	void nextGuide(Profile profile) throws ProfileInputException;

	/**
	 * 头像是否通过
	 * 
	 * @param uid
	 * @return
	 */
	boolean isValidLogo(long uid);

}
