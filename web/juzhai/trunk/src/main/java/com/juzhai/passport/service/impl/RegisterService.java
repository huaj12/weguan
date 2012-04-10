/*
 *The code is written by 51juzhai, All rights is reserved
 */
package com.juzhai.passport.service.impl;

import java.util.Date;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.juzhai.common.bean.ActiveCodeType;
import com.juzhai.common.service.IActiveCodeService;
import com.juzhai.core.util.StringUtil;
import com.juzhai.passport.InitData;
import com.juzhai.passport.bean.AuthInfo;
import com.juzhai.passport.exception.PassportAccountException;
import com.juzhai.passport.exception.ProfileInputException;
import com.juzhai.passport.mapper.PassportMapper;
import com.juzhai.passport.mapper.ProfileMapper;
import com.juzhai.passport.mapper.TpUserMapper;
import com.juzhai.passport.model.Constellation;
import com.juzhai.passport.model.Passport;
import com.juzhai.passport.model.Profile;
import com.juzhai.passport.model.Thirdparty;
import com.juzhai.passport.model.TpUser;
import com.juzhai.passport.service.IPassportService;
import com.juzhai.passport.service.IProfileService;
import com.juzhai.passport.service.IRegisterService;
import com.juzhai.stats.counter.service.ICounter;

@Service
public class RegisterService implements IRegisterService {

	private final Log log = LogFactory.getLog(getClass());

	@Autowired
	private PassportMapper passportMapper;
	@Autowired
	private ProfileMapper profileMapper;
	@Autowired
	private TpUserMapper tpUserMapper;
	@Autowired
	private TpUserAuthService tpUserAuthService;
	@Autowired
	private IProfileService profileService;
	@Autowired
	private IPassportService passportService;
	@Autowired
	private ICounter registerCounter;
	@Autowired
	private ICounter nativeRegisterCounter;
	@Autowired
	private IActiveCodeService activeCodeService;
	@Value("${register.email.min}")
	private int registerEmailMin;
	@Value("${register.email.max}")
	private int registerEmailMax;
	@Value("${nickname.length.max}")
	private int nickNameLengthMax;
	@Value("${register.password.min}")
	private int registerPasswordMin;
	@Value("${register.password.max}")
	private int registerPasswordMax;

	@Override
	public long autoRegister(Thirdparty tp, String identity, AuthInfo authInfo,
			Profile profile, long inviterUid) {
		Passport passport = registerPassport("@" + tp.getName() + "_"
				+ identity, "", "", inviterUid);
		if (null == passport || null == passport.getId()
				|| passport.getId() <= 0) {
			log.error("Register passport failed by DB.");
			return 0L;
		}
		registerProfile(profile, passport);
		// registerAccount(passport);
		// registerUserGuide(passport);
		registerTpUser(tp, identity, passport);
		tpUserAuthService.updateTpUserAuth(passport.getId(), tp.getId(),
				authInfo);

		// 初始化数据

		// 1.缓存profile
		profileService.cacheProfile(profile, identity);
		// 2.所在城市
		profileService.cacheUserCity(profile);
		// 3.好友列表
		// friendService.updateExpiredFriends(passport.getId(), tp.getId(),
		// authInfo);
		// // 4.拉数据
		// inboxService.syncInboxByTask(passport.getId());
		// 5.预存消息转正
		// msgService.getPrestore(identity, tp.getId(), passport.getId(),
		// MergerActMsg.class);

		// 统计注册数
		registerCounter.incr(null, 1);
		return passport.getId();
	}

	// private void registerUserGuide(Passport passport) {
	// userGuideService.craeteUserGuide(passport.getId());
	// }
	//
	// private void registerAccount(Passport passport) {
	// accountService.createAccount(passport.getId());
	// }

	private void registerProfile(Profile profile, Passport passport) {
		profile.setUid(passport.getId());
		if (null != profile.getBirthMonth() && profile.getBirthMonth() > 0
				&& null != profile.getBirthDay() && profile.getBirthDay() > 0) {
			Constellation c = InitData.getConstellation(
					profile.getBirthMonth(), profile.getBirthDay());
			if (null != c) {
				profile.setConstellationId(c.getId());
			}
		}
		profile.setSubEmail(false);
		profile.setCreateTime(passport.getCreateTime());
		profile.setLastModifyTime(passport.getLastModifyTime());
		// profile.setLastUpdateTime(passport.getCreateTime());
		profileMapper.insertSelective(profile);
	}

	private void registerTpUser(Thirdparty tp, String identity,
			Passport passport) {
		TpUser tpUser = new TpUser();
		tpUser.setUid(passport.getId());
		tpUser.setTpName(tp.getName());
		tpUser.setTpIdentity(identity);
		tpUser.setCreateTime(passport.getCreateTime());
		tpUser.setLastModifyTime(passport.getLastModifyTime());
		tpUserMapper.insertSelective(tpUser);
	}

	private Passport registerPassport(String loginName, String email,
			String password, long inviterUid) {
		Passport passport = new Passport();
		passport.setLoginName(loginName);
		passport.setPassword(DigestUtils.md5Hex(password));
		passport.setEmail(email);
		passport.setCreateTime(new Date());
		passport.setLastModifyTime(passport.getCreateTime());
		passport.setInviterUid(inviterUid);
		if (passportMapper.insertSelective(passport) == 1) {
			return passport;
		}
		return null;
	}

	@Override
	public long register(String email, String nickname, String pwd,
			String confirmPwd, long inviterUid)
			throws PassportAccountException, ProfileInputException {
		email = StringUtils.trim(email);
		nickname = StringUtils.trim(nickname);

		validateEmail(email);
		validateNickname(nickname);
		validatePwd(pwd, confirmPwd);
		// 创建passport
		Passport passport = registerPassport(email, email, pwd, inviterUid);
		if (null == passport) {
			throw new PassportAccountException(
					PassportAccountException.SYSTEM_ERROR);
		}
		// 创建profile
		Profile profile = new Profile();
		profile.setUid(passport.getId());
		profile.setEmail(email);
		profile.setNickname(nickname);
		profile.setCreateTime(new Date());
		profile.setLastModifyTime(profile.getCreateTime());
		if (0 == profileMapper.insertSelective(profile)) {
			throw new PassportAccountException(
					PassportAccountException.SYSTEM_ERROR);
		}
		// 统计注册数
		nativeRegisterCounter.incr(null, 1);
		return passport.getId();
	}

	private void validatePwd(String pwd, String confirmPwd)
			throws PassportAccountException {
		// 验证密码
		int pwdLength = pwd.length();
		if (pwdLength < registerPasswordMin || pwdLength > registerPasswordMax) {
			throw new PassportAccountException(
					PassportAccountException.PWD_LENGTH_ERROR);
		}
		if (!StringUtils.equals(pwd, confirmPwd)) {
			throw new PassportAccountException(
					PassportAccountException.CONFIRM_PWD_ERROR);
		}
	}

	private void validateNickname(String nickname) throws ProfileInputException {
		if (StringUtils.isEmpty(nickname)) {
			throw new ProfileInputException(
					ProfileInputException.PROFILE_NICKNAME_IS_NULL);
		}
		int nicknameLength = StringUtil.chineseLength(nickname);
		if (nicknameLength > nickNameLengthMax) {
			throw new ProfileInputException(
					ProfileInputException.PROFILE_NICKNAME_IS_TOO_LONG);
		}
		if (profileService.isExistNickname(nickname, 0)) {
			throw new ProfileInputException(
					ProfileInputException.PROFILE_NICKNAME_IS_EXIST);
		}
	}

	private void validateEmail(String email) throws PassportAccountException {
		// 验证邮箱
		int emailLength = StringUtil.chineseLength(email);
		if (emailLength < registerEmailMin || emailLength > registerEmailMax
				|| !StringUtil.checkMailFormat(email)) {
			throw new PassportAccountException(
					PassportAccountException.EMAIL_ACCOUNT_INVALID);
		}
	}

	@Override
	public void setAccount(long uid, String email, String pwd, String confirmPwd)
			throws PassportAccountException {
		email = StringUtils.trim(email);
		validateEmail(email);
		validatePwd(pwd, confirmPwd);
		Passport passport = passportService.getPassportByUid(uid);
		if (null == passport
				|| (StringUtils.isNotEmpty(passport.getLoginName()) && !StringUtils
						.startsWith(passport.getLoginName(), "@"))) {
			throw new PassportAccountException(
					PassportAccountException.ILLEGAL_OPERATION);
		}
		passport.setLoginName(email);
		passport.setEmail(email);
		passport.setPassword(DigestUtils.md5Hex(pwd));
		passport.setLastLoginTime(new Date());
		passportMapper.updateByPrimaryKey(passport);

		Profile profile = new Profile();
		profile.setUid(passport.getId());
		profile.setEmail(email);
		profile.setLastModifyTime(new Date());
		profileMapper.updateByPrimaryKeySelective(profile);
	}

	@Override
	public void modifyPwd(long uid, String oldPwd, String newPwd,
			String confirmPwd) throws PassportAccountException {
		Passport passport = passportService.getPassportByUid(uid);
		if (null == passport || !passport.getEmailActive()) {
			throw new PassportAccountException(
					PassportAccountException.ILLEGAL_OPERATION);
		}
		if (!StringUtils.equals(passport.getPassword(),
				DigestUtils.md5Hex(oldPwd))) {
			throw new PassportAccountException(
					PassportAccountException.PWD_ERROR);
		}
		validatePwd(newPwd, confirmPwd);
		passport.setPassword(DigestUtils.md5Hex(newPwd));
		passport.setLastModifyTime(new Date());
		passportMapper.updateByPrimaryKey(passport);
	}

	@Override
	public void resetPwd(long uid, String pwd, String confirmPwd, String code)
			throws PassportAccountException {
		// check
		if (!activeCodeService.check(uid, code, ActiveCodeType.RESET_PWD)) {
			throw new PassportAccountException(
					PassportAccountException.ACTIVE_CODE_ERROR);
		}
		Passport passport = passportService.getPassportByUid(uid);
		if (null == passport || !passport.getEmailActive()) {
			throw new PassportAccountException(
					PassportAccountException.ILLEGAL_OPERATION);
		}
		validatePwd(pwd, confirmPwd);
		passport.setPassword(DigestUtils.md5Hex(pwd));
		passport.setLastModifyTime(new Date());
		passportMapper.updateByPrimaryKey(passport);

		// 删除code
		activeCodeService.del(code);
	}

	@Override
	public boolean hasAccount(long uid) throws PassportAccountException {
		Passport passport = passportService.getPassportByUid(uid);
		if (null == passport) {
			throw new PassportAccountException(
					PassportAccountException.ILLEGAL_OPERATION);
		}
		if (StringUtils.isEmpty(passport.getLoginName())
				|| StringUtils.startsWith(passport.getLoginName(), "@")) {
			return false;
		}
		return true;
	}

	@Override
	public boolean hasActiveEmail(long uid) throws PassportAccountException {
		Passport passport = passportService.getPassportByUid(uid);
		if (null == passport) {
			throw new PassportAccountException(
					PassportAccountException.ILLEGAL_OPERATION);
		}
		return passport.getEmailActive();
	}
}
