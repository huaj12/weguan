package com.weguan.passport.service.impl;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.weguan.passport.dao.IPassportDao;
import com.weguan.passport.exception.LoginException;
import com.weguan.passport.model.Passport;
import com.weguan.passport.service.ILoginService;

@Service
public class LoginService implements ILoginService {

	public static final Log log = LogFactory.getLog(LoginService.class);

	@Autowired
	private IPassportDao passportDao;

	@Override
	public Passport authenticate(String loginName, String password)
			throws LoginException {
		if (StringUtils.isEmpty(loginName) || StringUtils.isEmpty(password)) {
			throw new LoginException(LoginException.LOGIN_INVALID);
		}
		Passport passport = passportDao.selectPassportByUserName(loginName);
		if (null == passport) {
			if (log.isDebugEnabled()) {
				log.debug("The loginName is not exist.[loginName=" + loginName
						+ "]");
			}
			throw new LoginException(LoginException.LOGIN_NAME_INEXISTENCE);
		}
		if (!StringUtils.equals(passport.getPassword(), password)) {
			if (log.isDebugEnabled()) {
				log.debug("The password is error.[loginName=" + loginName + "]");
			}
			throw new LoginException(LoginException.LOGIN_PASSWORD_ERROR);
		}
		return passport;
	}

}
