package com.juzhai.msg.task;

import java.util.concurrent.Callable;

import com.juzhai.msg.bean.ActMsg.MsgType;
import com.juzhai.passport.bean.AuthInfo;
import com.juzhai.passport.model.Thirdparty;

public class SendSysMsgTask implements Callable<Boolean> {
	Thirdparty thirdparty;
	String receiverIdentity;
	MsgType type;
	AuthInfo authInfo;
	long actId;
	long uid;

//	public SendSysMsgTask(Thirdparty thirdparty, IAppService appService,
//			String receiverIdentity, AuthInfo authInfo, MsgType type,
//			long actId, long uid) {
//		this.appService = appService;
//		this.receiverIdentity = receiverIdentity;
//		this.authInfo = authInfo;
//		this.type = type;
//		this.actId = actId;
//		this.thirdparty = thirdparty;
//		this.uid = uid;
//
//	}

	@Override
	public Boolean call() throws Exception {
		try {
			// 发送匹配消息
			// appService.sendMatchMessage(uid, receiverIdentity, actId,
			// thirdparty.getAppUrl() + "?goUri=/app/showAct/" + actId,
			// type, authInfo);
		} catch (Throwable e) {
			return Boolean.FALSE;
		}
		return Boolean.TRUE;
	}

}