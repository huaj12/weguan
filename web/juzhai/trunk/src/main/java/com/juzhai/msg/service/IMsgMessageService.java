package com.juzhai.msg.service;

import com.juzhai.msg.bean.ActMsg;

public interface IMsgMessageService {

	/**
	 * 发送ActMsg消息给站内用户
	 * 
	 * @param senderId
	 * @param receiverId
	 * @param actMsg
	 */
	void sendActMsg(long senderId, long receiverId, ActMsg actMsg);

	/**
	 * 发送ActMsg消息给站外用户
	 * 
	 * @param senderId
	 * @param receiverId
	 * @param actMsg
	 */
	@Deprecated
	void sendActMsg(long senderId, long receiverTpId, String receiverIdentity,
			ActMsg actMsg);
}
