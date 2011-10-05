package com.juzhai.msg.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.juzhai.act.service.IUserActService;
import com.juzhai.msg.bean.ActMsg;
import com.juzhai.msg.bean.ActMsg.MsgType;
import com.juzhai.msg.bean.Msg;
import com.juzhai.msg.rabbit.message.MsgMessage;
import com.juzhai.msg.service.IMsgMessageService;
import com.juzhai.passport.bean.FriendsBean;
import com.juzhai.passport.bean.TpFriend;
import com.juzhai.passport.service.IAuthorizeService;
import com.juzhai.passport.service.IFriendService;
import com.juzhai.passport.service.IProfileService;

@Service
public class MsgMessageService implements IMsgMessageService {

	@Autowired
	private AmqpTemplate actMsgRabbitTemplate;
	@Autowired
	IFriendService friendService;
	@Override
	public void sendActMsg(long senderId, long receiverId, ActMsg actMsg) {
		Assert.notNull(actMsg, "ActMsg must not be null.");
		actMsg.setUid(senderId);
			MsgMessage<ActMsg> msgMessage = new MsgMessage<ActMsg>();
			msgMessage = msgMessage.buildSenderId(senderId)
					.buildReceiverId(receiverId).buildBody(actMsg);
			sendMsgMessage(msgMessage);
	}

	@Override
	public void sendActMsg(long senderId, long receiverTpId,
			String receiverIdentity, ActMsg actMsg) {
		Assert.notNull(actMsg, "ActMsg must not be null.");
		actMsg.setUid(senderId);
		MsgMessage<ActMsg> msgMessage = new MsgMessage<ActMsg>();
		msgMessage.buildSenderId(senderId).buildReceiverTpId(receiverTpId)
				.buildReceiverIdentity(receiverIdentity).buildBody(actMsg);
		sendMsgMessage(msgMessage);
	}

	// TODO 如果再有新的消息类型，则需要重构，建议用策略模式来设计
	private <T extends Msg> void sendMsgMessage(MsgMessage<T> msgMessage) {
		Assert.notNull(msgMessage, "message is null.");
		Assert.notNull(msgMessage.getBody(), "message's body must not bu null.");
		if (msgMessage.getBody() instanceof ActMsg) {
			// TODO 检查消息有效性，防止代码bug
			actMsgRabbitTemplate.convertAndSend(msgMessage);
		}
	}
}
