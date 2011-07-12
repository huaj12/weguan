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
import com.weguan.passport.form.ModifyForm;
import com.weguan.passport.form.RegisterForm;
import com.weguan.passport.mapper.BlogMapper;
import com.weguan.passport.mapper.PassportMapper;
import com.weguan.passport.model.Blog;
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
	private BlogMapper blogMapper;
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
		long uid = 0L;
		if (CollectionUtils.isNotEmpty(list)) {
			uid = list.get(0).getId();
		}
		if (uid > 0) {
			passport.setId(uid);
			_initBlog(passport);
		}
		return uid;
	}

	private void _initBlog(Passport passport) {
		Blog blog = new Blog();
		blog.setUid(passport.getId());
		blog.setTitle(passport.getUserName() + "的微观世界！");
		blog.setAbout("简单灵感点燃微观生活。");
		blog.setStyleTemplate("1");
		blog.setCreateTime(passport.getCreateTime());
		blog.setLastModifyTime(passport.getLastModifyTime());
		blogMapper.insertSelective(blog);
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

		_checkPasswordLength(registerForm.getPassword());
		_checkEmail(registerForm.getEmailAddress());
	}

	private void _checkPasswordLength(String password) throws RegisterException {
		int passwordLth = StringUtil.chineseLength(password);
		if (passwordLth < registerPasswordMin
				|| passwordLth > registerPasswordMax) {
			throw new RegisterException(
					RegisterException.REGISTER_PASSWORD_INVALID);
		}
	}

	private void _checkEmail(String email) throws RegisterException {
		if (!EMAIL_PATTERN.matcher(email).matches()) {
			throw new RegisterException(
					RegisterException.REGISTER_EMAIL_INVALID);
		}
	}

	@Override
	public void modify(long uid, ModifyForm modifyForm)
			throws RegisterException {
		boolean isUpdate = false;
		Passport passport = passportMapper.selectByPrimaryKey(uid);
		if (null == passport) {
			throw new RegisterException(RegisterException.REGISTER_INVALID);
		}
		if (StringUtils.isNotEmpty(modifyForm.getPassword())
				&& StringUtils.isNotEmpty(modifyForm.getPwdConf())) {
			// check password
			if (!StringUtils.equals(DigestUtils.md5Hex(modifyForm.getOldPwd()),
					passport.getPassword())) {
				throw new RegisterException(
						RegisterException.REGISTER_OLD_PASSWORD_ERROR);
			}
			_checkPasswordLength(modifyForm.getPassword());
			if (!StringUtils.equals(modifyForm.getPassword(),
					modifyForm.getPwdConf())) {
				throw new RegisterException(
						RegisterException.REGISTER_PASSWORD_CONFIRM_ERROR);
			}
			if (!StringUtils.equals(modifyForm.getOldPwd(),
					modifyForm.getPassword())) {
				isUpdate = true;
				passport.setPassword(DigestUtils.md5Hex(modifyForm
						.getPassword()));
			}
		}
		if (StringUtils.isNotEmpty(modifyForm.getEmailAddress())
				&& !StringUtils.equals(passport.getEmail(),
						modifyForm.getEmailAddress())) {
			// check email
			_checkEmail(modifyForm.getEmailAddress());
			isUpdate = true;
			passport.setEmail(modifyForm.getEmailAddress());
		}
		if (isUpdate) {
			passportMapper.updateByPrimaryKeySelective(passport);
		}
	}
}
