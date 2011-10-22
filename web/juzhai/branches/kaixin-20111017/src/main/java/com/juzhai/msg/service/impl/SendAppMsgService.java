package com.juzhai.msg.service.impl;

import net.rubyeye.xmemcached.MemcachedClient;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

import com.juzhai.account.service.IAccountService;
import com.juzhai.app.service.IAppService;
import com.juzhai.core.cache.MemcachedKeyGenerator;
import com.juzhai.msg.bean.ActMsg.MsgType;
import com.juzhai.msg.service.ISendAppMsgService;
import com.juzhai.msg.task.SendSysMsgTask;
import com.juzhai.passport.bean.AuthInfo;
import com.juzhai.passport.bean.JoinTypeEnum;
import com.juzhai.passport.bean.ProfileCache;
import com.juzhai.passport.model.Thirdparty;
import com.juzhai.passport.model.TpUser;
import com.juzhai.passport.service.IProfileService;
import com.juzhai.passport.service.ITpUserAuthService;

@Service
public class SendAppMsgService implements ISendAppMsgService {
	private final Log log = LogFactory.getLog(getClass());
	@Autowired
	private ThreadPoolTaskExecutor taskExecutor;
	@Autowired
	private IAppService kaiXinService;
	@Autowired
	private IAccountService accountService;
	@Autowired
	private ITpUserAuthService tpUserAuthService;
	@Autowired
	private MessageSource messageSource;
	@Autowired
	private IProfileService profileService;
	@Autowired
	private MemcachedClient memcachedClient;
	@Value("${tp.msg.receive.cnt.expire.time}")
	private int tpMsgReceiveCntExpireTime;

	@Override
	public void threadSendAppMsg(TpUser tpUser, long uid, MsgType type,
			long sendCount) {
		if (tpUser == null) {
			log.error("send message find tpUser is null");
			return;
		}
		if (type == null) {
			log.error("send message find MsgType is null");
			return;
		}
		String receiverIdentity = tpUser.getTpIdentity();
		String tpName = tpUser.getTpName();
		ProfileCache profileCache = profileService.getProfileCacheByUid(uid);
		if (profileCache == null) {
			log.error("send message profileCache is null");
			return;
		}
		String sendName = profileCache.getNickname();
		// TODO 目前就app 以后要重构
		Thirdparty thirdparty = com.juzhai.passport.InitData
				.getTpByTpNameAndJoinType(tpName, JoinTypeEnum.APP);
		if (thirdparty == null) {
			log.error("send message find thirdparty is null");
			return;
		}
		AuthInfo authInfo = tpUserAuthService.getAuthInfo(uid,
				thirdparty.getId());
		if (authInfo == null) {
			log.error("send message find authInfo is null");
			return;
		}
		IAppService appService = null;
		if ("kaixin001".equals(tpName)) {
			appService = kaiXinService;
		}
		if (appService == null) {
			log.error("send message appService is null");
			return;
		}
		taskExecutor.submit(new SendSysMsgTask(thirdparty, accountService,
				appService, uid, receiverIdentity, authInfo, type,
				messageSource, sendName, sendCount));

	}

	@Override
	public boolean checkTpMsgLimitAndAddCnt(long receiverId, MsgType msgType) {
		if (!msgType.equals(MsgType.RECOMMEND)) {
			return true;
		}
		try {
			Long result = memcachedClient.get(MemcachedKeyGenerator
					.genTpMsgReceiveCnt(receiverId, msgType));
			if (null != result && result >= 1) {
				return false;
			} else {
				memcachedClient.set(MemcachedKeyGenerator.genTpMsgReceiveCnt(
						receiverId, msgType), tpMsgReceiveCntExpireTime, 1L);
				return true;
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return true;
	}
}
