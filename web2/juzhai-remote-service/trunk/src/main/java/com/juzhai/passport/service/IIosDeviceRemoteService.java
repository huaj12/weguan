package com.juzhai.passport.service;

import com.juzhai.passport.exception.IosDeviceException;

public interface IIosDeviceRemoteService {
	/**
	 * 注册ios设备
	 * 
	 * @param deviceToken
	 * @param uid
	 */
	void registerDevice(String deviceToken, Long uid)
			throws IosDeviceException;
}
