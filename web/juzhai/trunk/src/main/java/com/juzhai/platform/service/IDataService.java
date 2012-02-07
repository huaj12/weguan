package com.juzhai.platform.service;

import java.util.List;

import com.juzhai.passport.bean.AuthInfo;
import com.juzhai.platform.bean.UserWeibo;

/**
 * 获取微博的各项数据
 * 
 * @author Administrator
 * 
 */
public interface IDataService {

	/**
	 * 获取用户最近的3条微博缓存24小时
	 * 
	 * @return
	 */
	List<UserWeibo> listWeibo(long uid, long tpId);

	/**
	 * 强制刷新用户最近的3条微博
	 * 
	 * @param uid
	 * @param authInfo
	 * @return
	 */
	List<UserWeibo> refreshListWeibo(long uid, long tpId);

}
