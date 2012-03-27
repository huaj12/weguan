package com.juzhai.passport.service.impl;

import java.util.Date;

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
	public void lockUser(long uid, long time) {
		Passport passport = getPassportByUid(uid);
		if (time > 0) {
			Date date = new Date(time);
			passport.setShieldTime(date);
		} else {
			passport.setShieldTime(null);
		}
		passport.setLastModifyTime(new Date());
		passportMapper.updateByPrimaryKey(passport);

	}
	@Override
	public int totalCount() {
		return passportMapper.countByExample(new PassportExample());
	}

}
