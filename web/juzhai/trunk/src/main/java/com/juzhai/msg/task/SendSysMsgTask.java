package com.juzhai.msg.task;

import java.util.Locale;
import java.util.concurrent.Callable;

import org.springframework.context.MessageSource;

import com.juzhai.account.bean.ProfitAction;
import com.juzhai.account.service.IAccountService;
import com.juzhai.app.bean.TpMessageKey;
import com.juzhai.app.service.IAppService;
import com.juzhai.msg.bean.ActMsg.MsgType;
import com.juzhai.passport.bean.AuthInfo;

public class SendSysMsgTask implements Callable<Boolean>{
	 IAccountService accountService;
	 IAppService appService;
	 long uid;
	 String sendName;
	 String receiverIdentity;
	 MsgType type;
	 AuthInfo authInfo;
	 long sendCount;
	 MessageSource messageSource;
	 public SendSysMsgTask(IAccountService accoutService,IAppService appService,long uid,String receiverIdentity,AuthInfo authInfo,MsgType type, MessageSource messageSource,String sendName,long sendCount) {
		 this.accountService=accoutService;
		 this.appService=appService;
		 this.uid=uid;
		 this.receiverIdentity=receiverIdentity;
		 this.authInfo=authInfo;
		 this.type=type;
		 this.messageSource=messageSource;
		 this.sendName=sendName;
		 this.sendCount=sendCount;
	}
	@Override
	public Boolean call() throws Exception {
		try{
		//内容
		String text="";
		//附言
		String word="";
		if(MsgType.INVITE.equals(type)){
			text=messageSource.getMessage(TpMessageKey.INVITE_FRIEND, new Object[]{sendName,sendCount},
					Locale.SIMPLIFIED_CHINESE);
			word=messageSource.getMessage(TpMessageKey.INVITE_FRIEND_WORD, new Object[]{null},
					Locale.SIMPLIFIED_CHINESE);
		}else if (MsgType.RECOMMEND.equals(type)){
			text=messageSource.getMessage(TpMessageKey.RECOMMEND_FRIEND, new Object[]{sendName},
					Locale.SIMPLIFIED_CHINESE);
			word=messageSource.getMessage(TpMessageKey.RECOMMEND_FRIEND_WORD, new Object[]{sendCount},
					Locale.SIMPLIFIED_CHINESE);
		}
		//发送系统消息
		appService.sendSysMessage(receiverIdentity, messageSource.getMessage(
				TpMessageKey.FEED_LINKTEXT, null, Locale.SIMPLIFIED_CHINESE), messageSource.getMessage(
						TpMessageKey.FEED_LINK, null, Locale.SIMPLIFIED_CHINESE),word, text, null, authInfo);
		//发送成功增加积分
		accountService.profitPoint(uid, ProfitAction.IMMEDIATE_RESPONSE);
		}catch (Throwable  e) {
			return Boolean.FALSE;
		}
		return Boolean.TRUE;
	}
	 
}