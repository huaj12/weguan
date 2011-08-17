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

import com.juzhai.passport.bean.AuthInfo;
import com.juzhai.passport.mapper.PassportMapper;
import com.juzhai.passport.mapper.ProfileMapper;
import com.juzhai.passport.mapper.TpUserMapper;
import com.juzhai.passport.model.Passport;
import com.juzhai.passport.model.Profile;
import com.juzhai.passport.model.Thirdparty;
import com.juzhai.passport.model.TpUser;
import com.juzhai.passport.service.IFriendService;
import com.juzhai.passport.service.IProfileService;
import com.juzhai.passport.service.IRegisterService;

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
		registerTpUser(tp, identity, passport);
		tpUserAuthService.updateTpUserAuth(passport.getId(), tp.getId(),
				authInfo);

		// 初始化数据
		// 1.所在城市
		profileService.cacheUserCity(profile);
		// 2.好友列表
		friendService.updateExpiredFriends(passport.getId(), tp.getId(),
				authInfo);
		// 3.发送rabbitmq消息进行拉数据

		return passport.getId();
	}

	private void registerProfile(Profile profile, Passport passport) {
		profile.setUid(passport.getId());
		profile.setCreateTime(passport.getCreateTime());
		profile.setLastModifyTime(passport.getLastModifyTime());
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
