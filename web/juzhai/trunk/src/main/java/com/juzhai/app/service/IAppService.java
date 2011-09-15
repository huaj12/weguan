package com.juzhai.app.service;

import java.util.List;


import com.juzhai.app.model.AppUser;
import com.juzhai.core.exception.JuzhaiAppException;
import com.juzhai.passport.bean.AuthInfo;

public interface IAppService {
	/**
	 * 获取已安装应用的好友id
	 * @param authInfo
	 * @param start
	 * @param num
	 * @return String[] uids
	 * @throws JuzhaiAppException
	 */
	String[] appFriends(AuthInfo authInfo,int start,int num)throws JuzhaiAppException;
	/**
	 * 获取所有好友的信息
	 * @param authInfo
	 * @param fields
	 * @param start
	 * @param num
	 * @return
	 * @throws JuzhaiAppException
	 */
	List<AppUser>  getFriends(AuthInfo authInfo,String fields,int start,int num) throws JuzhaiAppException;
	
}
