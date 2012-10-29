package com.juzhai.platform.service;

import java.io.UnsupportedEncodingException;

import com.juzhai.core.bean.DeviceName;
import com.juzhai.core.web.bean.RequestParameter;
import com.juzhai.passport.bean.AuthInfo;
import com.juzhai.passport.model.Thirdparty;
import com.juzhai.platform.bean.Terminal;
import com.juzhai.platform.exception.TokenAuthorizeException;

public interface IUserRemoteService {

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
	long access(RequestParameter requestParameter, AuthInfo authInfo,
			Thirdparty tp, long inviterUid, DeviceName deviceName);

	/**
	 * 登录授权地址
	 * 
	 * @param tp
	 * @param turnTo
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	String getLoginAuthorizeURLforCode(Thirdparty tp, Terminal terminal,
			String turnTo, String incode) throws UnsupportedEncodingException;

	/**
	 * 过期授权地址
	 * 
	 * @param tp
	 * @param turnTo
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	String getExpiredAuthorizeURLforCode(Thirdparty tp, Terminal terminal)
			throws UnsupportedEncodingException;

	/**
	 * 本地账户绑定第三方帐号
	 * 
	 * @param request
	 * @param response
	 * @param tp
	 * @param terminal
	 * @param turnTo
	 * @param incode
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	String getBindAuthorizeURLforCode(Thirdparty tp, Terminal terminal)
			throws UnsupportedEncodingException;

	/**
	 * 过期授权
	 * 
	 * @param request
	 * @param tp
	 * @param uid
	 * @param userTpId
	 */
	void expireAccess(RequestParameter requestParameter, Thirdparty tp, long uid)
			throws TokenAuthorizeException;

	/**
	 * 本地注册绑定第三方账户
	 * 
	 * @param request
	 * @param tp
	 * @param uid
	 * @throws TokenAuthorizeException
	 */
	void bindAccess(RequestParameter requestParameter, Thirdparty tp, long uid)
			throws TokenAuthorizeException;

}
