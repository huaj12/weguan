package com.juzhai.app.service;

import java.util.List;

import com.juzhai.core.exception.JuzhaiAppException;
import com.juzhai.msg.bean.ActMsg.MsgType;
import com.juzhai.passport.bean.AuthInfo;
import com.juzhai.passport.bean.TpFriend;

public interface IAppService {

	/**
	 * 发送匹配消息
	 * 
	 * @param fuids
	 * @param sendAct
	 * @param link
	 * @param type
	 * @param authInfo
	 * @return
	 */
	boolean sendMatchMessage(long sendId, String fuids, long actId,
			String link, MsgType type, AuthInfo authInfo);

	/**
	 * 约他短消息fuids上限30
	 * 
	 * @param fuids
	 * @param content
	 * @param sendId
	 * @param tpId
	 * @return
	 */
	boolean aboutFriends(List<String> fuids, String content, long sendId,
			long tpId, long actId);

	/**
	 * 发送feed
	 * 
	 * @param word
	 * @param authInfo
	 * @return
	 */
	boolean sendFeed(long actId, long uid, long tpId);

	/**
	 * 发送答题消息
	 * 
	 * @param fuids
	 * @param linktext
	 * @param link
	 * @param word
	 * @param text
	 * @return
	 */
	boolean sendQuestionMssage(long uid, long tpId, long questionId,
			String identity, int answer);

}
