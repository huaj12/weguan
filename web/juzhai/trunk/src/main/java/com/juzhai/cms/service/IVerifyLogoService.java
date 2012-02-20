package com.juzhai.cms.service;

import java.util.List;

import com.juzhai.passport.bean.LogoVerifyState;
import com.juzhai.passport.model.Profile;

public interface IVerifyLogoService {

	/**
	 * 用户头像状态列表
	 * 
	 * @param logoVerifyState
	 * @return
	 */
	List<Profile> listVerifyLogoProfile(LogoVerifyState logoVerifyState,
			int firstResult, int maxResult);

	/**
	 * 头像状态数量
	 * 
	 * @param logoVerifyState
	 * @return
	 */
	int countVerifyLogoProfile(LogoVerifyState logoVerifyState);

	/**
	 * 通过头像
	 * 
	 * @param uid
	 */
	void passLogo(long uid);

	/**
	 * 拒绝头像
	 * 
	 * @param uid
	 */
	void denyLogo(long uid);
}
