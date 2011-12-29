package com.juzhai.notice.service;

import java.util.List;

import com.juzhai.core.exception.JuzhaiException;
import com.juzhai.notice.bean.SysNoticeType;
import com.juzhai.notice.model.SysNotice;

public interface ISysNoticeService {

	/**
	 * 发送系统通知
	 * 
	 * @param uid
	 * @param sysNoticeType
	 * @param params
	 */
	void sendSysNotice(long uid, SysNoticeType sysNoticeType, Object... params);

	/**
	 * 列表系统通知
	 * 
	 * @param uid
	 * @param firstResult
	 * @param maxResults
	 * @return
	 */
	List<SysNotice> listSysNoticeByUid(long uid, int firstResult, int maxResults);

	/**
	 * 系统通知数量
	 * 
	 * @param uid
	 * @return
	 */
	int countSysNoticeByUid(long uid);

	/**
	 * 删除系统通知
	 * 
	 * @param sysNoticeId
	 * @throws JuzhaiException
	 */
	void delSysNotice(long uid, long sysNoticeId) throws JuzhaiException;
}
