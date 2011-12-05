package com.juzhai.platform.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.juzhai.passport.bean.AuthInfo;
import com.juzhai.passport.bean.TpFriend;
import com.juzhai.passport.model.Thirdparty;

public interface IUserService {
	/**
	 * 获取授权
	 * 
	 * @param request
	 *            请求
	 * @param response
	 *            回应
	 * @param authInfo
	 *            临时授权信息
	 * @param tp
	 *            授权服务商对象
	 * @return 授权成功返回用户ID,否则返回0
	 */
	public long access(HttpServletRequest request,
			HttpServletResponse response, AuthInfo authInfo, Thirdparty tp);

	/**
	 * 获取好友列表
	 * 
	 * @param authInfo
	 * @return
	 */
	public List<TpFriend> getAllFriends(AuthInfo authInfo);

	/**
	 * 获取安装了应用的好友列表
	 * 
	 * @param authInfo
	 * @return
	 */
	public List<String> getAppFriends(AuthInfo authInfo);
	/**
	 * Connect获取accessToken  
	 * @param code
	 * @return
	 */
	public String getOAuthAccessTokenFromCode(Thirdparty tp,String code);
}
