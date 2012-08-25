package com.juzhai.home.service;

public interface IRescueboyService {
	/**
	 * 是否开启宅男自救
	 * 
	 * @param uid
	 * @return
	 */
	boolean isOpenRescueboy(long uid);

	/**
	 * 打开自救器
	 * 
	 * @param uid
	 */
	void open(long uid);

	/**
	 * 关闭自救器
	 * 
	 * @param uid
	 */
	void close(long uid);

	/**
	 * 获取发送列表
	 */
	void rescueboy(long uid, long city);

	/**
	 * 是否可以发送
	 * 
	 * @param uid
	 * @return
	 */
	boolean isSend(long uid);

}
