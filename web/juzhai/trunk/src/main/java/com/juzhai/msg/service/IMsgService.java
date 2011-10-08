package com.juzhai.msg.service;

import com.juzhai.msg.bean.Msg;

public interface IMsgService<T extends Msg> {

	/**
	 * 把消息放入未读箱
	 * 
	 * @param receiverId
	 * @param msg
	 */
	void sendMsg(long receiverId, T msg);

	/**
	 * 把消息放入预存未读箱
	 * 
	 * @param receiverTpId
	 * @param receiverIdentity
	 * @param msg
	 */
	void sendMsg(long receiverTpId, String receiverIdentity, T msg);
	
	/**
	 * 将该用户的所有预存消息存放到未读消息里
	 * 用户第一次登陆时调用
	 * @param receiverIdentity
	 * @param receiverTpId
	 * @param className
	 */
	void getPrestore(String receiverIdentity,long receiverTpId,long uid,T msg);
}
