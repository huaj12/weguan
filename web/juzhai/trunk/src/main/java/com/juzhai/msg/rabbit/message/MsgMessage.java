package com.juzhai.msg.rabbit.message;

import com.juzhai.core.rabbit.message.RabbitMessage;
import com.juzhai.msg.bean.Msg;

public class MsgMessage<B extends Msg> extends RabbitMessage<B> {

	private static final long serialVersionUID = -8341275455562455020L;

	private long receiverTpId;

	private String receiverIdentity;

	@SuppressWarnings("unchecked")
	public <T extends RabbitMessage<B>> T buildReceiverTpId(long receiverTpId) {
		this.receiverTpId = receiverTpId;
		return (T) this;
	}

	@SuppressWarnings("unchecked")
	public <T extends RabbitMessage<B>> T buildReceiverIdentity(
			String receiverIdentity) {
		this.receiverIdentity = receiverIdentity;
		return (T) this;
	}

	public long getReceiverTpId() {
		return receiverTpId;
	}

	public String getReceiverIdentity() {
		return receiverIdentity;
	}
}
