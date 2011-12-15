/*
 *The code is written by 51juzhai, All rights is reserved
 */
package com.juzhai.passport.service.impl;

import java.util.Date;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.juzhai.account.service.IAccountService;
import com.juzhai.msg.bean.MergerActMsg;
import com.juzhai.msg.service.IMsgService;
import com.juzhai.passport.InitData;
import com.juzhai.passport.bean.AuthInfo;
import com.juzhai.passport.mapper.PassportMapper;
import com.juzhai.passport.mapper.ProfileMapper;
import com.juzhai.passport.mapper.TpUserMapper;
import com.juzhai.passport.model.Constellation;
import com.juzhai.passport.model.Passport;
import com.juzhai.passport.model.Profile;
import com.juzhai.passport.model.Thirdparty;
import com.juzhai.passport.model.TpUser;
import com.juzhai.passport.service.IFriendService;
import com.juzhai.passport.service.IProfileService;
import com.juzhai.passport.service.IRegisterService;
import com.juzhai.passport.service.IUserGuideService;

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
	private IFriendService friendService;
	@Autowired
	private IProfileService profileService;
	@Autowired
	private IAccountService accountService;
	@Autowired
	private IUserGuideService userGuideService;
	@Autowired
	private IMsgService<MergerActMsg> msgService;

	@Override
	public long autoRegister(Thirdparty tp, String identity, AuthInfo authInfo,
			Profile profile) {
		Passport passport = registerPassport("@" + tp.getName() + "_"
				+ identity, "", "");
		if (null == passport || null == passport.getId()
				|| passport.getId() <= 0) {
			log.error("Register passport failed by DB.");
			return 0L;
		}
		registerProfile(profile, passport);
		registerAccount(passport);
		registerUserGuide(passport);
		registerTpUser(tp, identity, passport);
		tpUserAuthService.updateTpUserAuth(passport.getId(), tp.getId(),
				authInfo);

		// 初始化数据

		// 1.缓存profile
		profileService.cacheProfile(profile, identity);
		// 2.所在城市
		profileService.cacheUserCity(profile);
		// 3.好友列表
		friendService.updateExpiredFriends(passport.getId(), tp.getId(),
				authInfo);
		// // 4.拉数据
		// inboxService.syncInboxByTask(passport.getId());
		// 5.预存消息转正
		msgService.getPrestore(identity, tp.getId(), passport.getId(),
				MergerActMsg.class);

		return passport.getId();
	}

	private void registerUserGuide(Passport passport) {
		userGuideService.craeteUserGuide(passport.getId());
	}

	private void registerAccount(Passport passport) {
		accountService.createAccount(passport.getId());
	}

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
		profile.setLastUpdateTime(passport.getCreateTime());
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
			String password) {
		Passport passport = new Passport();
		passport.setLoginName(loginName);
		passport.setPassword(DigestUtils.md5Hex(password));
		passport.setEmail(email);
		passport.setCreateTime(new Date());
		passport.setLastModifyTime(passport.getCreateTime());
		if (passportMapper.insertSelective(passport) == 1) {
			return passport;
		}
		return null;
	}

}
