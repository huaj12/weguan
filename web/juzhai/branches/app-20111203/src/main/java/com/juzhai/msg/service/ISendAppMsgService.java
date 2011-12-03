package com.juzhai.msg.service;

import com.juzhai.msg.bean.ActMsg.MsgType;
import com.juzhai.passport.model.TpUser;

public interface ISendAppMsgService {
	void threadSendAppMsg(TpUser tpUser, long uid, MsgType type, long actId);

	/**
	 * 检查第三方消息接收方限制数是否到达，如果没到达，则+1
	 * 
	 * @param receiverId
	 * @param msgType
	 * @return
	 */
	boolean checkTpMsgLimitAndAddCnt(long receiverId, MsgType msgType);
}
