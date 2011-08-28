package com.juzhai.msg.service;

import java.util.List;

import com.juzhai.msg.bean.ActMsg;

public interface IActMsgService {

	/**
	 * 获取一页未读ActMsg并放入已读列表
	 * 
	 * @param uid
	 * @param maxResults
	 * @return
	 */
	List<ActMsg> pageUnRead(long uid, int maxResults);

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
	List<ActMsg> pageRead(long uid, int start, int maxResults);

	/**
	 * 已读列表数
	 * 
	 * @param uid
	 * @return
	 */
	long countRead(long uid);
}
