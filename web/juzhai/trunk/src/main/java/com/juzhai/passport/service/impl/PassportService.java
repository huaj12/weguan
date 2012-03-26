package com.juzhai.passport.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.juzhai.passport.mapper.PassportMapper;
import com.juzhai.passport.model.Passport;
import com.juzhai.passport.model.PassportExample;
import com.juzhai.passport.service.IPassportService;

@Service
public class PassportService implements IPassportService {

	@Autowired
	private PassportMapper passportMapper;

	@Override
	public Passport getPassportByUid(long uid) {
		return passportMapper.selectByPrimaryKey(uid);
	}

	@Override
	public int totalCount() {
		return passportMapper.countByExample(new PassportExample());
	}

}
