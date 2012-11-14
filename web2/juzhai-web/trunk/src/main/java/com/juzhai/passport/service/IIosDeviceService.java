package com.juzhai.passport.service;

import java.util.List;

public interface IIosDeviceService extends IIosDeviceRemoteService {

	/**
	 * 获取某一个用户的所有设备token
	 * 
	 * @param uid
	 * @return
	 */
	List<String> getDeviceTokenList(long uid);
}
