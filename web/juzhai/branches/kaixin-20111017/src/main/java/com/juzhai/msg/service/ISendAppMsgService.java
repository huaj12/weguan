package com.juzhai.msg.service;

import com.juzhai.msg.bean.ActMsg.MsgType;
import com.juzhai.passport.model.TpUser;

public interface ISendAppMsgService {
	void threadSendAppMsg(TpUser tpUser,long uid,MsgType type,long sendCount);
}
