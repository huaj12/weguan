package com.juzhai.antiad.service;

import com.juzhai.core.bean.Function;
import com.juzhai.core.web.session.UserContext;

/**
 * 犯规service
 * 
 * @author Administrator
 * 
 */
public interface IFoulService {
	/**
	 * 计算犯规次数
	 * 
	 * @param sendUid
	 *            发送者
	 * @param uid
	 *            接收者
	 * @param content
	 *            发送内容
	 * @param function
	 *            模块
	 */
	void foul(UserContext context, long targetUid, String content,
			Function function);

	/**
	 * 获取该用户的犯规数
	 * 
	 * @param uid
	 * @return
	 */
	int getUserFoul(long uid);

	/**
	 * 获取该ip的犯规数
	 * 
	 * @param ip
	 * @return
	 */
	int getIpFoul(String ip);

	/**
	 * 重置用户犯规数
	 * 
	 * @param uid
	 */
	void resetFoul(long uid);

	/**
	 * 重置ip犯规数
	 * 
	 * @param ip
	 */
	void resetFoul(String ip);

}
