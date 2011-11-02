package com.juzhai.app.service;

import java.util.List;

import com.juzhai.app.model.AppUser;
import com.juzhai.core.exception.JuzhaiAppException;
import com.juzhai.msg.bean.ActMsg.MsgType;
import com.juzhai.passport.bean.AuthInfo;

public interface IAppService {

	/**
	 * 发送系统消息
	 * 
	 * @param fuids
	 * @param sendAct
	 * @param link
	 * @param type
	 * @param authInfo
	 * @return
	 */
	boolean sendSysMessage(String fuids, String sendAct, String link,
			MsgType type, AuthInfo authInfo);

	/**
	 * 发送短消息
	 * 
	 * @param fuids
	 * @param Content
	 * @return
	 */
	boolean sendMessage(String fuids, String word, AuthInfo authInfo);

	/**
	 * 发送feed
	 * 
	 * @param word
	 * @param authInfo
	 * @return
	 */
	boolean sendFeed(long actId, long uid,long tpId);

}
