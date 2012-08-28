package com.juzhai.platform.service;

import java.io.UnsupportedEncodingException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.juzhai.passport.bean.AuthInfo;
import com.juzhai.passport.bean.DeviceName;
import com.juzhai.passport.exception.TokenAuthorizeException;
import com.juzhai.passport.model.Thirdparty;
import com.juzhai.platform.bean.Terminal;

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
			HttpServletResponse response, AuthInfo authInfo, Thirdparty tp,
			long inviterUid, DeviceName deviceName);

	/**
	 * 登录授权地址
	 * 
	 * @param tp
	 * @param turnTo
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	String getLoginAuthorizeURLforCode(HttpServletRequest request,
			HttpServletResponse response, Thirdparty tp, Terminal terminal,
			String turnTo, String incode) throws UnsupportedEncodingException;

	/**
	 * 过期授权地址
	 * 
	 * @param tp
	 * @param turnTo
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	// TODO (review) turnTo和incode这两个参数还需要？
	String getExpiredAuthorizeURLforCode(HttpServletRequest request,
			HttpServletResponse response, Thirdparty tp, Terminal terminal,
			String turnTo, String incode) throws UnsupportedEncodingException;

	/**
	 * 获取用户名字
	 * 
	 * @param authInfo
	 * @param uids
	 * @return
	 */
	List<String> getUserNames(AuthInfo authInfo, List<String> fuids);

	/**
	 * 过期授权
	 * 
	 * @param request
	 * @param tp
	 * @param uid
	 * @param userTpId
	 */
	void expireAccess(HttpServletRequest request, Thirdparty tp, long uid)
			throws TokenAuthorizeException;

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
	// TODO (review) turnTo和incode这两个参数还需要？
	String getBindAuthorizeURLforCode(HttpServletRequest request,
			HttpServletResponse response, Thirdparty tp, Terminal terminal,
			String turnTo, String incode) throws UnsupportedEncodingException;

	/**
	 * 本地注册绑定第三方账户
	 * 
	 * @param request
	 * @param tp
	 * @param uid
	 * @throws TokenAuthorizeException
	 */
	void bindAccess(HttpServletRequest request, Thirdparty tp, long uid)
			throws TokenAuthorizeException;

}
