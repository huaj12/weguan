package com.juzhai.msg.task;

import java.util.Locale;
import java.util.concurrent.Callable;

import org.springframework.context.MessageSource;

import com.juzhai.account.service.IAccountService;
import com.juzhai.app.bean.TpMessageKey;
import com.juzhai.app.service.IAppService;
import com.juzhai.msg.bean.ActMsg.MsgType;
import com.juzhai.passport.bean.AuthInfo;
import com.juzhai.passport.model.Thirdparty;

public class SendSysMsgTask implements Callable<Boolean> {
	Thirdparty thirdparty;
	IAppService appService;
	String receiverIdentity;
	MsgType type;
	AuthInfo authInfo;
	long actId;

	public SendSysMsgTask(Thirdparty thirdparty, IAppService appService,
			String receiverIdentity, AuthInfo authInfo, MsgType type, long actId) {
		this.appService = appService;
		this.receiverIdentity = receiverIdentity;
		this.authInfo = authInfo;
		this.type = type;
		this.actId = actId;
		this.thirdparty = thirdparty;
	}

	@Override
	public Boolean call() throws Exception {
		try {
			// 发送系统消息
			appService.sendSysMessage(receiverIdentity, actId,
					thirdparty.getAppUrl() + "?goUri=/msg/showRead", type,
					authInfo);
		} catch (Throwable e) {
			return Boolean.FALSE;
		}
		return Boolean.TRUE;
	}

}