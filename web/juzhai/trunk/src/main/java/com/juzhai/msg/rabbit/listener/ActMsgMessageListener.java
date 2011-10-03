package com.juzhai.msg.rabbit.listener;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.concurrent.Callable;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

import com.juzhai.account.InitData;
import com.juzhai.account.bean.ProfitAction;
import com.juzhai.account.service.IAccountService;
import com.juzhai.act.service.IUserActService;
import com.juzhai.app.bean.TpMessageKey;
import com.juzhai.app.service.IAppService;
import com.juzhai.core.rabbit.listener.IRabbitMessageListener;
import com.juzhai.msg.bean.ActMsg;
import com.juzhai.msg.bean.ActMsg.MsgType;
import com.juzhai.msg.rabbit.message.MsgMessage;
import com.juzhai.msg.service.IMsgService;
import com.juzhai.passport.bean.AuthInfo;
import com.juzhai.passport.bean.JoinTypeEnum;
import com.juzhai.passport.bean.ProfileCache;
import com.juzhai.passport.bean.TpFriend;
import com.juzhai.passport.model.Thirdparty;
import com.juzhai.passport.model.TpUser;
import com.juzhai.passport.service.IAuthorizeService;
import com.juzhai.passport.service.IFriendService;
import com.juzhai.passport.service.IProfileService;
import com.juzhai.passport.service.ITpUserAuthService;
import com.juzhai.passport.service.ITpUserService;

@Component
public class ActMsgMessageListener implements
		IRabbitMessageListener<MsgMessage<ActMsg>, Object> {

	private final Log log = LogFactory.getLog(getClass());

	@Autowired
	private IFriendService friendService;
	@Autowired
	private IMsgService<ActMsg> msgService;
	@Autowired
	private IUserActService userActService;
	@Autowired
	private ThreadPoolTaskExecutor taskExecutor;
	@Autowired
	IAppService kaiXinService;
	@Autowired
	ITpUserService tpUserService;
	@Autowired
	IAccountService accountService;
	@Autowired
	private ITpUserAuthService tpUserAuthService;
	@Autowired
	private MessageSource messageSource;
	@Autowired
	private IProfileService profileService;

	@Override
	public Object handleMessage(MsgMessage<ActMsg> msgMessage) {
		if (null == msgMessage) {
			log.error("MsgMessage must not bu null.");
			return null;
		}
		if (null == msgMessage.getBody()) {
			log.error("Object actMsg must not be null.");
			return null;
		}
		if (null == msgMessage.getBody().getType()) {
			log.error("ActMsg's type must not be null.");
			return null;
		}
		//接受消息
		ReceiverActMsg(msgMessage);
	
		return null;
	}

	private void ReceiverActMsg(MsgMessage<ActMsg> msgMessage) {
		try{
		TpUser tpUser=null;
		if (msgMessage.getReceiverId() > 0) {
			msgService
					.sendMsg(msgMessage.getReceiverId(), msgMessage.getBody());
			 tpUser=tpUserService.getTpUserByUid(msgMessage.getReceiverId());
		} else if (msgMessage.getReceiverTpId() > 0
				&& StringUtils.isNotEmpty(msgMessage.getReceiverIdentity())) {
			msgService.sendMsg(msgMessage.getReceiverTpId(),
					msgMessage.getReceiverIdentity(), msgMessage.getBody());
			tpUser=tpUserService.getTpUserByTpIdAndIdentity(msgMessage.getReceiverTpId(), msgMessage.getReceiverIdentity());
		}
		if(tpUser==null){
			log.error("send message find tpUser is null");
			return ;
		}
		MsgType type=msgMessage.getBody().getType();
		if(type==null){
			log.error("send message find MsgType is null");
			return ;
		}
		String receiverIdentity=tpUser.getTpIdentity();
		String tpName=tpUser.getTpName();
		long uid=msgMessage.getSenderId();
		ProfileCache profileCache=profileService.getProfileCacheByUid(uid);
		if(profileCache==null){
			log.error("send message profileCache is null");
			return ;
		}
		String sendName=profileCache.getNickname();
		// TODO 目前就app 以后要重构
		Thirdparty thirdparty=com.juzhai.passport.InitData.getTpByTpNameAndJoinType(tpName, JoinTypeEnum.APP);
		if(thirdparty==null){
			log.error("send message find thirdparty is null");
			return ;
		}
		AuthInfo authInfo=tpUserAuthService.getAuthInfo(uid, thirdparty.getId());
		if(authInfo==null){
			log.error("send message find authInfo is null");
			return ;
		}
		IAppService appService=null;
		if("kaixin001".equals(tpName)){
			appService=kaiXinService;
		}
		if(appService==null){
			log.error("send message appService is null");
			return ;
		}
			taskExecutor.submit(new SendSysMsgTask(accountService, appService, uid,receiverIdentity,authInfo,type,messageSource,sendName));	
		}catch (Exception e) {
			e.printStackTrace();
		}	
		
	}

	private void broadcastActMsg(MsgMessage<ActMsg> msgMessage) {
		long senderId = msgMessage.getSenderId();
		List<Long> targets = getPushTargets(senderId, msgMessage.getBody()
				.getActId());
		for (Long targetUid : targets) {
			// TODO 使用piple
			msgService.sendMsg(targetUid, msgMessage.getBody());
		}

		// TODO 启动一个线程进行第三方消息发送
	}

	private List<Long> getPushTargets(long uid, long actId) {
		Set<Long> friendIds = friendService.getAppFriends(uid);
		List<Long> targets = new ArrayList<Long>();
		for (Long friendUid : friendIds) {
			if (friendUid != null && friendUid > 0
					&& userActService.hasAct(friendUid, actId)) {
				targets.add(friendUid);
			}
		}
		return targets;
	}
}

 class SendSysMsgTask implements Callable<Boolean>{
	 IAccountService accountService;
	 IAppService appService;
	 long uid;
	 String sendName;
	 String receiverIdentity;
	 MsgType type;
	 AuthInfo authInfo;
	 MessageSource messageSource;
	 public SendSysMsgTask(IAccountService accoutService,IAppService appService,long uid,String receiverIdentity,AuthInfo authInfo,MsgType type, MessageSource messageSource,String sendName) {
		 this.accountService=accoutService;
		 this.appService=appService;
		 this.uid=uid;
		 this.receiverIdentity=receiverIdentity;
		 this.authInfo=authInfo;
		 this.type=type;
		 this.messageSource=messageSource;
		 this.sendName=sendName;
	}
	@Override
	public Boolean call() throws Exception {
		try{
		//内容
		String text="";
		//附言
		String word="";
		if(MsgType.INVITE.equals(type)){
			text=messageSource.getMessage(TpMessageKey.INVITE_FRIEND, new Object[]{sendName},
					Locale.SIMPLIFIED_CHINESE);
			word=messageSource.getMessage(TpMessageKey.INVITE_FRIEND_WORD, new Object[]{sendName},
					Locale.SIMPLIFIED_CHINESE);
		}else if (MsgType.RECOMMEND.equals(type)){
			text=messageSource.getMessage(TpMessageKey.RECOMMEND_FRIEND, new Object[]{sendName},
					Locale.SIMPLIFIED_CHINESE);
			word=messageSource.getMessage(TpMessageKey.RECOMMEND_FRIEND_WORD, new Object[]{sendName},
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

