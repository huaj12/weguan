package com.juzhai.core.rabbit.message;

import java.io.Serializable;

public abstract class RabbitMessage<B> implements Serializable {

	private static final long serialVersionUID = 1556132308054492709L;

	/**
	 * 发送者id
	 */
	private long senderId;

	/**
	 * 接收者id，如果为0，则listener自己取接受者id
	 */
	private long receiverId;
	
	/**
	 * 消息实体内容
	 */
	private B body;

	@SuppressWarnings("unchecked")
	public <T extends RabbitMessage<B>> T buildReceiverId(long receiverId) {
		this.receiverId = receiverId;
		return (T) this;
	}

	@SuppressWarnings("unchecked")
	public <T extends RabbitMessage<B>> T buildSenderId(long senderId) {
		this.senderId = senderId;
		return (T) this;
	}

	@SuppressWarnings("unchecked")
	public <T extends RabbitMessage<B>> T buildBody(B body) {
		this.body = body;
		return (T) this;
	}

	public long getSenderId() {
		return senderId;
	}

	public long getReceiverId() {
		return receiverId;
	}

	public B getBody() {
		return body;
	}
}
