package com.juzhai.passport.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
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
		IosDevice iosDevice = getIosDevice(deviceToken);
		if (iosDevice != null) {
			if (uid != null && uid > 0) {
				if (iosDevice.getUid() == 0
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
	public void clearUserDevice(String deviceToken) {
		if (StringUtils.isEmpty(deviceToken)) {
			return;
		}
		IosDevice iosDevice = new IosDevice();
		iosDevice.setLastModifyTime(new Date());
		iosDevice.setDeviceToken(deviceToken);
		iosDevice.setUid(0l);
		iosDeviceMapper.updateByPrimaryKeySelective(iosDevice);
	}

	@Override
	public List<String> getDeviceTokenList(long uid) {
		IosDeviceExample example = new IosDeviceExample();
		example.createCriteria().andUidEqualTo(uid);
		List<IosDevice> iosDeviceList = iosDeviceMapper
				.selectByExample(example);
		if (CollectionUtils.isEmpty(iosDeviceList)) {
			return Collections.emptyList();
		}
		List<String> deviceTokenList = new ArrayList<String>(
				iosDeviceList.size());
		for (IosDevice device : iosDeviceList) {
			deviceTokenList.add(device.getDeviceToken());
		}
		return deviceTokenList;
	}
}
