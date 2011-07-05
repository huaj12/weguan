package com.weguan.passport.service.impl;

import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.weguan.passport.exception.RegisterException;
import com.weguan.passport.form.RegisterForm;
import com.weguan.passport.mapper.PassportMapper;
import com.weguan.passport.model.Passport;
import com.weguan.passport.model.PassportExample;
import com.weguan.passport.service.IRegisterService;

@Service
public class RegisterService implements IRegisterService {

	@Autowired
	private PassportMapper passportMapper;

	@Override
	public long register(RegisterForm registerForm) throws RegisterException {
		Passport passport = new Passport();
		passport.setUserName(registerForm.getLoginName());
		passport.setPassword(registerForm.getPassword());
		passport.setEmail(registerForm.getEmailAddress());
		Date date = new Date();
		passport.setLastModifyTime(date);
		passport.setCreateTime(date);

		passportMapper.insertSelective(passport);

		PassportExample example = new PassportExample();
		example.createCriteria()
				.andUserNameEqualTo(registerForm.getLoginName());
		List<Passport> list = passportMapper.selectByExample(example);
		if (CollectionUtils.isEmpty(list)) {
			return 0L;
		} else {
			return list.get(0).getId();
		}
	}
}
