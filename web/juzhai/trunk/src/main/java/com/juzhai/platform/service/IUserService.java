package com.juzhai.platform.service;

import java.io.UnsupportedEncodingException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.juzhai.passport.bean.AuthInfo;
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
			HttpServletResponse response, AuthInfo authInfo, Thirdparty tp,
			long inviterUid);

	/**
	 * 授权地址
	 * 
	 * @param tp
	 * @param turnTo
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	String getAuthorizeURLforCode(Thirdparty tp, String turnTo, String incode)
			throws UnsupportedEncodingException;

	/**
	 * 获取用户名字
	 * 
	 * @param authInfo
	 * @param uids
	 * @return
	 */
	List<String> getUserNames(AuthInfo authInfo, List<String> fuids);

}
