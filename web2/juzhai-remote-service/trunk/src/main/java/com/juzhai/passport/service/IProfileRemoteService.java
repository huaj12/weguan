package com.juzhai.passport.service;

import com.juzhai.core.exception.UploadImageException;
import com.juzhai.passport.bean.ProfileCache;
import com.juzhai.passport.exception.ProfileInputException;
import com.juzhai.passport.model.Profile;

public interface IProfileRemoteService {

	/**
	 * 根据用户ID获取Profile，先查缓存
	 * 
	 * @param uid
	 * @return
	 */
	ProfileCache getProfileCacheByUid(long uid);

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
	 * @throws ProfileInputException
	 */
	boolean isExistNickname(String nickname, long uid) throws ProfileInputException;

	/**
	 * 保存头像和个人资料（用于手机应用）
	 * 
	 * @param uid
	 * @param profileForm
	 * @throws UploadImageException
	 * @throws ProfileInputException
	 */
	void updateLogoAndProfile(long uid, Profile profile, String logoPath)
			throws UploadImageException, ProfileInputException;

	/**
	 * 引导保存头像和个人资料（用于手机应用）
	 * 
	 * @param uid
	 * @param profileForm
	 * @throws UploadImageException
	 * @throws ProfileInputException
	 */
	void guideLogoAndProfile(long uid, Profile profile, String logoPath)
			throws UploadImageException, ProfileInputException;

	/**
	 * 是否是有效用户（上传头像且通过引导）
	 * 
	 * @param uid
	 * @return
	 */
	boolean isValidUser(long uid);
}
