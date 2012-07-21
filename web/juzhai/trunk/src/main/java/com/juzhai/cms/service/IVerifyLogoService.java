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

	/**
	 * 删除头像
	 * 
	 * @param uid
	 */
	void removeLogo(long uid);

	/**
	 * 是否是真实图片
	 * 
	 * @param imgUrl
	 * @return boolean(如果返回null 传入url为null或请求google服务器失败)
	 */
	Boolean realPic(String imgUrl);

	/**
	 * 忽略
	 * 
	 * @param uid
	 */
	void ignoreLogo(long uid);
}
