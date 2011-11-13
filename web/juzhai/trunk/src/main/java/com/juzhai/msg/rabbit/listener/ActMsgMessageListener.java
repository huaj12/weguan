package com.juzhai.msg.rabbit.listener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import com.juzhai.act.service.IUserActService;
import com.juzhai.core.cache.RedisKeyGenerator;
import com.juzhai.core.rabbit.listener.IRabbitMessageListener;
import com.juzhai.msg.bean.ActMsg;
import com.juzhai.msg.bean.ActMsg.MsgType;
import com.juzhai.msg.bean.MergerActMsg;
import com.juzhai.msg.rabbit.message.MsgMessage;
import com.juzhai.msg.service.IMsgService;
import com.juzhai.msg.service.ISendAppMsgService;
import com.juzhai.passport.model.TpUser;
import com.juzhai.passport.service.IFriendService;
import com.juzhai.passport.service.IProfileService;
import com.juzhai.passport.service.ITpUserService;
import com.juzhai.passport.service.IUserSetupService;

@Component
public class ActMsgMessageListener implements
		IRabbitMessageListener<MsgMessage<ActMsg>, Object> {

	private final Log log = LogFactory.getLog(getClass());

	@Autowired
	private IFriendService friendService;
	@Autowired
	private IMsgService<MergerActMsg> msgService;
	@Autowired
	private IUserActService userActService;
	@Autowired
	private ITpUserService tpUserService;
	@Autowired
	private IProfileService profileService;
	@Autowired
	private RedisTemplate<String, Long> redisTemplate;
	@Autowired
	private RedisTemplate<String, String> redisStringTemplate;
	ISendAppMsgService sendAppMsgService;
	@Autowired
	private IUserSetupService userSetupService;

	@Value("${friend.act.count}")
	private int friendActCount = 10;

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
		// 接受消息如果接受者UID为0则找出同城且同兴趣的所有好友
		if (msgMessage.getReceiverId() == 0
				&& MsgType.RECOMMEND.equals(msgMessage.getBody().getType())) {
			List<Long> fuids = getPushTargets(msgMessage.getSenderId(),
					msgMessage.getBody().getActId());
			for (Long fuid : fuids) {
				msgMessage.buildReceiverId(fuid);
				receiverActMsg(msgMessage);
			}
		} else {
			receiverActMsg(msgMessage);
		}

		return null;
	}

	private void receiverActMsg(MsgMessage<ActMsg> msgMessage) {
		try {
			TpUser tpUser = null;
			if (msgMessage.getReceiverId() > 0) {
				// // 是否延迟发送
				// if (msgMessage.getBody().isLazy()) {
				// String lazyMsgKey = RedisKeyGenerator.genLazyMessageKey(
				// msgMessage.getSenderId(), msgMessage
				// .getReceiverId(), msgMessage.getBody()
				// .getType(), MergerActMsg.class
				// .getSimpleName());
				// redisTemplate.opsForZSet().add(lazyMsgKey,
				// msgMessage.getBody().getActId(),
				// msgMessage.getBody().getDate().getTime());
				// redisStringTemplate.opsForSet().add(
				// RedisKeyGenerator.genMergerMsgKey(), lazyMsgKey);
				// } else {
				// msgService.sendMsg(msgMessage.getReceiverId(),
				// newMergerMsg(msgMessage));
				tpUser = tpUserService.getTpUserByUid(msgMessage
						.getReceiverId());
				// }
				// 消息不再合并
			} else if (msgMessage.getReceiverTpId() > 0
					&& StringUtils.isNotEmpty(msgMessage.getReceiverIdentity())) {
				// msgService.sendMsg(msgMessage.getReceiverTpId(),
				// msgMessage.getReceiverIdentity(),
				// newMergerMsg(msgMessage));
				tpUser = tpUserService.getTpUserByTpIdAndIdentity(
						msgMessage.getReceiverTpId(),
						msgMessage.getReceiverIdentity());

			}
			if (tpUser != null) {
				// 该用户是否发送第三方消息
				// if (userSetupService.isTpAdvise(msgMessage.getSenderId())) {
				// 12小时内只能给该用户发一条消息
				if (sendAppMsgService.checkTpMsgLimitAndAddCnt(msgMessage
						.getReceiverId(), msgMessage.getBody().getType())) {
					sendAppMsgService.threadSendAppMsg(tpUser, msgMessage
							.getSenderId(), msgMessage.getBody().getType(),
							msgMessage.getBody().getActId());
				}
				// }
			}
		} catch (Exception e) {
			log.error("receiverActMsg is error", e);
		}

	}

	private MergerActMsg newMergerMsg(MsgMessage<ActMsg> msgMessage) {
		MergerActMsg merger = new MergerActMsg();
		List<ActMsg> msgs = new ArrayList<ActMsg>();
		msgs.add(msgMessage.getBody());
		merger.setUid(msgMessage.getSenderId());
		merger.setMsgs(msgs);
		return merger;
	}

	/**
	 * 当该项目超过xx人想去找出同兴趣的好友
	 * 
	 * @param uid
	 * @param actId
	 * @return
	 */
	private List<Long> getPushTargets(long uid, long actId) {
		int count = userActService.countUserActByActId(actId);
		List<Long> targets = null;
		if (friendActCount < count) {
			List<Long> friendIds = friendService.getAppFriends(uid);
			targets = new ArrayList<Long>();
			for (Long friendUid : friendIds) {

				if (friendUid != null && friendUid > 0
						&& userActService.hasAct(friendUid, actId)) {
					targets.add(friendUid);
				}
			}
		} else {
			targets = Collections.emptyList();
		}
		return targets;
	}
}
