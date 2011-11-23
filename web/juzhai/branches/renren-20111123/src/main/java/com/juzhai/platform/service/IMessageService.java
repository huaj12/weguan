package com.juzhai.platform.service;

import java.util.List;

import com.juzhai.passport.bean.AuthInfo;

public interface IMessageService {
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
	boolean sendSysMessage(List<String> fuids,String linktext,String link,String word,String text,String picurl,AuthInfo authInfo);
	/**
	 * 发送消息
	 * @param fuids
	 * @param content
	 * @param authInfo
	 * @return
	 */
	boolean sendMessage(long sendId,String fuids, String content, AuthInfo authInfo,long actId,String link);
	/**
	 * 发送feed无弹筐
	 * @param linktext
	 * @param link
	 * @param word
	 * @param text
	 * @param picurl
	 * @param authInfo
	 * @return
	 */
	boolean sendFeed(String linktext,String link,String word,String text,String picurl,AuthInfo authInfo,String name);
	/**
	 * 发送答题消息
	 * @param fuids
	 * @param linktext
	 * @param link
	 * @param word
	 * @param text
	 * @return
	 */
	boolean sendQuestionMessage(AuthInfo authInfo,List<String> fuids,long sendId, String linktext,
			String  link,String  word,String  text);
	/**
	 * 发送匹配消息
	 * @param sendId
	 * @param fuids
	 * @param linktext
	 * @param link
	 * @param word
	 * @param text
	 * @param picurl
	 * @param authInfo
	 * @param actId
	 * @return
	 */
	boolean sendMatchMessage(long sendId,List<String> fuids,String linktext,String link,String word,String text,String picurl,AuthInfo authInfo,long actId);
}
