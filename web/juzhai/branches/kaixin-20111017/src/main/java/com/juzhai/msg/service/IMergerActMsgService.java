package com.juzhai.msg.service;

import java.util.List;

import com.juzhai.msg.bean.ActMsg;
import com.juzhai.msg.bean.MergerActMsg;

public interface IMergerActMsgService {

	/**
	 * 获取一页未读ActMsg
	 * 
	 * @param uid
	 * @param maxResults
	 * @return
	 */
	List<MergerActMsg<ActMsg>> pageUnRead(long uid, int start, int maxResults);

	/**
	 * 未读数
	 * 
	 * @param uid
	 * @return
	 */
	long countUnRead(long uid);

	/**
	 * 获取已读ActMsg
	 * 
	 * @param uid
	 * @param start
	 * @param maxResults
	 * @return
	 */
	List<MergerActMsg<ActMsg>> pageRead(long uid, int start, int maxResults);

	/**
	 * 已读列表数
	 * 
	 * @param uid
	 * @return
	 */
	long countRead(long uid);

	/**
	 * 将未读消息放到已读消息里
	 * 
	 * @param uid
	 * @param index
	 *            未读消息所在位置
	 */
	void openMessage(long uid, int index);

	/**
	 * 删除未读消息
	 * 
	 * @param uid
	 * @param index
	 */
	void removeUnRead(long uid, int index);

	/**
	 * 删除已读消息
	 * 
	 * @param uid
	 * @param index
	 */
	void removeRead(long uid, int index);

	/**
	 * 改变消息状态 已经发出立即响应
	 * 
	 * @param uid
	 * @param index
	 */
	void updateMsgStuts(long uid, int index);

}
