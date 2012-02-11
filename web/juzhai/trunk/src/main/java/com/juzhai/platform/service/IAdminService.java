package com.juzhai.platform.service;


/**
 * 系统管理接口
 * 
 * @author Administrator
 * 
 */
public interface IAdminService {
	/**
	 * 是否还有发送通知配额。
	 * 
	 * @param authInfo
	 * @return
	 */
	boolean isAllocation(long uid, long tpId);
}
