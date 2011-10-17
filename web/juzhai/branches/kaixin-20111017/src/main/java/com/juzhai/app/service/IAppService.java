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
	
	/**
	 * 
	 * @param fuids TRUE
	 * @param linktext TRUE
	 * @param link TRUE 
	 * @param word
	 * @param text  TRUE 该文案可以有{_USER_} {_USER_TA_}变量，解析时会被替换为当前用户名字和他/她。
                                                                   例如，动态文案：{_USER_} 在做XX任务时遇到了强大的XX，快去帮帮{_USER_TA_}！
	 * @param picurl false
	 * @return
	 */
	boolean sendSysMessage(String fuids,String linktext,String link,String word,String text,String picurl,AuthInfo authInfo);
	
	/**
	 * 发送短消息
	 * @param fuids
	 * @param Content
	 * @return
	 */
	boolean sendMessage(String fuids,String word,AuthInfo authInfo);
}
