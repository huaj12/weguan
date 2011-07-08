package com.weguan.passport.dao.impl;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.weguan.passport.dao.IPassportDao;
import com.weguan.passport.mapper.PassportMapper;
import com.weguan.passport.model.Passport;
import com.weguan.passport.model.PassportExample;

@Repository
public class PassportDao implements IPassportDao {

	@Autowired
	private PassportMapper passportMapper;

	@Override
	public Passport selectPassportByUserName(String userName) {
		PassportExample example = new PassportExample();
		example.createCriteria().andUserNameEqualTo(userName);
		List<Passport> list = passportMapper.selectByExample(example);
		if (CollectionUtils.isEmpty(list)) {
			return null;
		}
		return list.get(0);
	}

}
