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
	 * @param uid 登录者id
	 * @param fuid 被查看者id
	 * @param tpId 登录者tpid
	 * @return
	 */
	List<UserWeibo> listWeibo(long uid,long fuid, long tpId);

	/**
	 * 强制刷新用户最近的3条微博
	 * 
	  * @param uid 登录者id
	 * @param fuid 被查看者id
	 * @param tpId 登录者tpid
	 * @return
	 */
	List<UserWeibo> refreshListWeibo(long uid,long fuid, long tpId);

}
