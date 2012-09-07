package com.juzhai.antiad.service.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.juzhai.antiad.mapper.AdLockIpMapper;
import com.juzhai.antiad.model.AdLockIp;
import com.juzhai.antiad.service.IAdLockIpService;

@Service
public class AdLockIpService implements IAdLockIpService {
	@Autowired
	private AdLockIpMapper adLockIpMapper;

	@Override
	public void save(String ip) {
		AdLockIp adLockIp = new AdLockIp();
		adLockIp.setCreateTime(new Date());
		adLockIp.setIp(ip);
		adLockIpMapper.insert(adLockIp);
	}

}
