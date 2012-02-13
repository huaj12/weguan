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
import com.juzhai.core.cache.MemcachedKeyGenerator;
import com.juzhai.msg.bean.ActMsg.MsgType;
import com.juzhai.msg.service.ISendAppMsgService;
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
	@Value("${recommend.tp.msg.limit}")
	private int recommendTpMsgLimit = 1;
	@Value("${invite.tp.msg.limit}")
	private int inviteTpMsgLimit = 3;

	@Override
	public void threadSendAppMsg(TpUser tpUser, long uid, MsgType type,
			long actId) {
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
		// taskExecutor.submit(new SendSysMsgTask(thirdparty, appService,
		// receiverIdentity, authInfo, type, actId,uid));

	}

	@Override
	public boolean checkTpMsgLimitAndAddCnt(long receiverId, MsgType msgType) {
		int limit = Integer.MAX_VALUE;
		if (MsgType.RECOMMEND.equals(msgType)) {
			limit = recommendTpMsgLimit;
		} else if (MsgType.INVITE.equals(msgType)) {
			limit = inviteTpMsgLimit;
		}
		try {
			long result = memcachedClient.incr(MemcachedKeyGenerator
					.genTpMsgReceiveCnt(receiverId, msgType), 1L, 1L, 1000L,
					tpMsgReceiveCntExpireTime);

			// Counter counter =
			// memcachedClient.getCounter(MemcachedKeyGenerator
			// .genTpMsgReceiveCnt(receiverId, msgType));
			// System.out.println(receiverId + ": count is " + counter.get());
			// if (null == counter || counter.get() == 0) {
			// System.out.println(receiverId + ": result is null. msgType: "
			// + msgType.name());
			//
			// return true;
			// } else
			if (result <= limit) {
				// counter.incrementAndGet();
				return true;
			} else {
				return false;
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return true;
	}
}
