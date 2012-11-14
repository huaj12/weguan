package com.juzhai.passport.service.impl;

import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.juzhai.passport.exception.IosDeviceException;
import com.juzhai.passport.mapper.IosDeviceMapper;
import com.juzhai.passport.model.IosDevice;
import com.juzhai.passport.service.IIosDeviceService;

@Service
public class IosDeviceService implements IIosDeviceService {
	@Autowired
	private IosDeviceMapper iosDeviceMapper;

	@Override
	public void registerDevice(String deviceToken, Long uid)
			throws IosDeviceException {
		if (StringUtils.isEmpty(deviceToken)) {
			throw new IosDeviceException(
					IosDeviceException.IOS_DEVICE_TOKEN_IS_NULL);
		}
		IosDevice iosDevice = getIosDevice(deviceToken);
		if (iosDevice != null) {
			if (uid != null && uid > 0) {
				if (iosDevice.getUid() == null || iosDevice.getUid() == 0
						|| uid.longValue() != iosDevice.getUid()) {
					iosDevice.setLastModifyTime(new Date());
					iosDevice.setDeviceToken(deviceToken);
					iosDevice.setUid(uid);
					iosDeviceMapper.updateByPrimaryKeySelective(iosDevice);
				}
			}
		} else {
			iosDevice = new IosDevice();
			iosDevice.setCreateTime(new Date());
			iosDevice.setLastModifyTime(iosDevice.getCreateTime());
			iosDevice.setDeviceToken(deviceToken);
			iosDevice.setUid(uid);
			iosDeviceMapper.insertSelective(iosDevice);
		}

	}

	private IosDevice getIosDevice(String deviceToken) {
		return iosDeviceMapper.selectByPrimaryKey(deviceToken);
	}

	@Override
	public void removeDevice(String deviceToken) throws IosDeviceException {
		if (StringUtils.isEmpty(deviceToken)) {
			throw new IosDeviceException(
					IosDeviceException.IOS_DEVICE_TOKEN_IS_NULL);
		}
		IosDevice iosDevice = new IosDevice();
		iosDevice.setLastModifyTime(new Date());
		iosDevice.setDeviceToken(deviceToken);
		iosDevice.setUid(0l);
		iosDeviceMapper.updateByPrimaryKeySelective(iosDevice);
	}

}
