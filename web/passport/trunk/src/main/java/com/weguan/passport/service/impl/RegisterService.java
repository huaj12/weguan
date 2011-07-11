package com.weguan.passport.service.impl;

import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.weguan.passport.core.util.StringUtil;
import com.weguan.passport.dao.IPassportDao;
import com.weguan.passport.exception.RegisterException;
import com.weguan.passport.form.RegisterForm;
import com.weguan.passport.mapper.PassportMapper;
import com.weguan.passport.model.Passport;
import com.weguan.passport.model.PassportExample;
import com.weguan.passport.service.IRegisterService;

@Service
public class RegisterService implements IRegisterService {

	private static final Log log = LogFactory.getLog(RegisterService.class);

	public static final String EMAIL_PATTERN_STRING = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*$";
	public static final Pattern EMAIL_PATTERN = Pattern
			.compile(EMAIL_PATTERN_STRING);

	public static final String ACCOUNT_PATTERN_STRING = "^\\b[A-Za-z][A-za-z0-9]{3,15}$";
	public static final Pattern ACCOUNT_PATTERN = Pattern
			.compile(ACCOUNT_PATTERN_STRING);

	@Autowired
	private PassportMapper passportMapper;
	@Autowired
	private IPassportDao passportDao;
	@Value("${register.password.min}")
	private int registerPasswordMin = 6;
	@Value("${register.password.max}")
	private int registerPasswordMax = 12;

	@Override
	public long register(RegisterForm registerForm) throws RegisterException {
		_checkRegisterForm(registerForm);
		Passport passport = new Passport();
		passport.setUserName(StringUtils.lowerCase(registerForm.getLoginName()));
		passport.setPassword(DigestUtils.md5Hex(registerForm.getPassword()));
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

	private void _checkRegisterForm(RegisterForm registerForm)
			throws RegisterException {
		if (StringUtils.isEmpty(registerForm.getEmailAddress())
				|| StringUtils.isEmpty(registerForm.getLoginName())
				|| StringUtils.isEmpty(registerForm.getPassword())) {
			if (log.isDebugEnabled()) {
				log.debug("Register input invalid.");
			}
			throw new RegisterException(RegisterException.REGISTER_INVALID);
		}
		if (!ACCOUNT_PATTERN.matcher(registerForm.getLoginName()).matches()) {
			if (log.isDebugEnabled()) {
				log.debug("User name is invalid.["
						+ registerForm.getLoginName() + "]");
			}
			throw new RegisterException(
					RegisterException.REGISTER_USERNAME_INVALID);
		}
		if (passportDao.countPassportByUserName(registerForm.getLoginName()) > 0) {
			throw new RegisterException(
					RegisterException.REGISTER_USERNAME_EXISTENCE);
		}

		int passwordLth = StringUtil.chineseLength(registerForm.getPassword());
		if (passwordLth < registerPasswordMin
				|| passwordLth > registerPasswordMax) {
			throw new RegisterException(
					RegisterException.REGISTER_PASSWORD_INVALID);
		}
		if (!EMAIL_PATTERN.matcher(registerForm.getEmailAddress()).matches()) {
			throw new RegisterException(
					RegisterException.REGISTER_EMAIL_INVALID);
		}
	}
}
