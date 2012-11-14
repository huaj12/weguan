package com.juzhai.passport.service.impl;

import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.juzhai.passport.exception.IosDeviceException;
import com.juzhai.passport.mapper.IosDeviceMapper;
import com.juzhai.passport.model.IosDevice;
import com.juzhai.passport.model.IosDeviceExample;
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
		if (exist(deviceToken)) {
			if (uid != null && uid > 0) {
				IosDevice iosDevice = new IosDevice();
				iosDevice.setLastModifyTime(new Date());
				iosDevice.setDeviceToken(deviceToken);
				iosDevice.setUid(uid);
				iosDeviceMapper.updateByPrimaryKeySelective(iosDevice);
			}
		} else {
			IosDevice iosDevice = new IosDevice();
			iosDevice.setCreateTime(new Date());
			iosDevice.setLastModifyTime(iosDevice.getCreateTime());
			iosDevice.setDeviceToken(deviceToken);
			iosDevice.setUid(uid);
			iosDeviceMapper.insertSelective(iosDevice);
		}

	}

	private boolean exist(String deviceToken) {
		IosDeviceExample example = new IosDeviceExample();
		example.createCriteria().andDeviceTokenEqualTo(deviceToken);
		return iosDeviceMapper.countByExample(example) > 0;
	}

}
