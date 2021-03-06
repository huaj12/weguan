/*
 *The code is written by 51juzhai, All rights is reserved.
 */
package com.juzhai.passport.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.juzhai.passport.bean.AuthInfo;
import com.juzhai.passport.bean.TpFriend;
import com.juzhai.passport.model.Thirdparty;

/**
 * @author wujiajun Created on 2011-2-15
 */
public interface IAuthorizeService {

	// /**
	// * 获取临时授权信息和地址
	// *
	// * @param request
	// * 获取授权回调的request
	// * @param tp
	// * 授权服务商对象
	// * @param authorizeInfo
	// * 授权的参数信息
	// * @return 授权地址(如果是null或者空字符串，表示未获得)
	// */
	// public AuthInfo requestAuthInfo(Thirdparty tp, AuthorizeInfo
	// authorizeInfo);

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
	 * 发送系统消息
	 * 
	 * @param authInfo
	 * @param fuids
	 * @param linktext
	 * @param link
	 * @param word
	 * @param text
	 * @param picurl
	 */
	public void sendSystemMessage(AuthInfo authInfo, List<String> fuids,
			String linktext, String link, String word, String text,
			String picurl);
}
