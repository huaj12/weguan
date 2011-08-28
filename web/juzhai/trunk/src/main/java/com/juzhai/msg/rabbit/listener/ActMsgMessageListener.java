package com.juzhai.msg.rabbit.listener;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import com.juzhai.act.service.IUserActService;
import com.juzhai.core.rabbit.listener.IRabbitMessageListener;
import com.juzhai.msg.bean.ActMsg;
import com.juzhai.msg.rabbit.message.MsgMessage;
import com.juzhai.msg.service.IMsgService;
import com.juzhai.passport.service.IFriendService;

@Component
public class ActMsgMessageListener implements
		IRabbitMessageListener<MsgMessage<ActMsg>, Object> {

	@Autowired
	private IFriendService friendService;
	@Autowired
	private IMsgService<ActMsg> msgService;
	@Autowired
	private IUserActService userActService;

	@Override
	public Object handleMessage(MsgMessage<ActMsg> msgMessage) {
		Assert.notNull(msgMessage, "MsgMessage must not bu null.");
		Assert.notNull(msgMessage.getBody(), "Object actMsg must not be null.");
		Assert.notNull(msgMessage.getBody().getType(),
				"ActMsg's type must not be null.");

		if (msgMessage.getBody().getType().equals(ActMsg.MsgType.FIND_YOU_ACT)) {
			findYouActMsg(msgMessage);
		} else if (msgMessage.getBody().getType()
				.equals(ActMsg.MsgType.BROADCAST_ACT)) {
			broadcastActMsg(msgMessage);
		}
		return null;
	}

	private void findYouActMsg(MsgMessage<ActMsg> msgMessage) {
		if (msgMessage.getReceiverId() > 0) {
			msgService
					.sendMsg(msgMessage.getReceiverId(), msgMessage.getBody());
		} else if (msgMessage.getReceiverTpId() > 0
				&& StringUtils.isNotEmpty(msgMessage.getReceiverIdentity())) {
			msgService.sendMsg(msgMessage.getReceiverTpId(),
					msgMessage.getReceiverIdentity(), msgMessage.getBody());
		}

		// TODO 发送第三方系统消息
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
