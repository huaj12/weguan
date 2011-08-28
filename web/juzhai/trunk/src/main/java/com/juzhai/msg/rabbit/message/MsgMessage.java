package com.juzhai.msg.rabbit.message;

import com.juzhai.core.rabbit.message.RabbitMessage;
import com.juzhai.msg.bean.Msg;

public class MsgMessage<B extends Msg> extends RabbitMessage<MsgMessage<B>, B> {

	private static final long serialVersionUID = -8341275455562455020L;

	private long receiverTpId;

	private String receiverIdentity;

	public MsgMessage<B> buildReceiverTpId(long receiverTpId) {
		this.receiverTpId = receiverTpId;
		return this;
	}

	public MsgMessage<B> buildReceiverIdentity(String receiverIdentity) {
		this.receiverIdentity = receiverIdentity;
		return this;
	}

	public long getReceiverTpId() {
		return receiverTpId;
	}

	public String getReceiverIdentity() {
		return receiverIdentity;
	}
}
