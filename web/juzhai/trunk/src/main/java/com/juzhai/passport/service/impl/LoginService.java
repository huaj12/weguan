/*
 *The code is written by 51juzhai, All rights is reserved
 */
package com.juzhai.passport.service.impl;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import net.rubyeye.xmemcached.MemcachedClient;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

import com.juzhai.core.cache.MemcachedKeyGenerator;
import com.juzhai.core.exception.NeedLoginException.RunType;
import com.juzhai.core.web.session.LoginSessionManager;
import com.juzhai.home.service.IUserStatusService;
import com.juzhai.passport.InitData;
import com.juzhai.passport.bean.JoinTypeEnum;
import com.juzhai.passport.exception.PassportAccountException;
import com.juzhai.passport.mapper.PassportMapper;
import com.juzhai.passport.mapper.ProfileMapper;
import com.juzhai.passport.model.Passport;
import com.juzhai.passport.model.Profile;
import com.juzhai.passport.model.Thirdparty;
import com.juzhai.passport.model.TpUser;
import com.juzhai.passport.service.ILoginService;
import com.juzhai.passport.service.IPassportService;
import com.juzhai.passport.service.ITpUserService;
import com.juzhai.stats.counter.service.ICounter;

@Service
public class LoginService implements ILoginService {

	private final Log log = LogFactory.getLog(getClass());

	protected static final String DAY_FIRST_LOGIN = "dfl";

	@Autowired
	private LoginSessionManager loginSessionManager;
	@Autowired
	private ThreadPoolTaskExecutor taskExecutor;
	@Autowired
	private IUserStatusService userStatusService;
	@Autowired
	private PassportMapper passportMapper;
	@Autowired
	private IPassportService passportService;
	@Autowired
	private ProfileMapper profileMapper;
	@Autowired
	private MemcachedClient memcachedClient;
	@Autowired
	private ITpUserService tpUserService;
	@Autowired
	private ICounter loginCounter;
	@Autowired
	private ICounter nativeLoginCounter;
	@Value(value = "${user.online.expire.time}")
	private int userOnlineExpireTime;

	@Override
	public void login(HttpServletRequest request, final long uid,
			final long tpId, RunType runType) throws PassportAccountException {
		doLogin(request, uid, tpId, runType);
		loginCounter.incr(null, 1);
	}

	private void doLogin(HttpServletRequest request, final long uid,
			final long tpId, RunType runType) throws PassportAccountException {
		// 判断是不是当天第一次登陆
		Passport passport = passportMapper.selectByPrimaryKey(uid);
		if (null == passport) {
			log.error("Login error. Can not find passport[id=" + uid + "].");
		}
		Date shield = passport.getShieldTime();
		if (shield != null && shield.getTime() > System.currentTimeMillis()) {
			throw new PassportAccountException(
					PassportAccountException.USER_IS_SHIELD, shield.getTime());
		}
		loginSessionManager.login(request, uid, tpId, false);
		// 更新最后登录时间
		updateLastLoginTime(uid, runType);
		updateOnlineState(uid);
		// 启动一个线程来获取和保存
		if (tpId > 0) {
			taskExecutor.execute(new Runnable() {
				@Override
				public void run() {
					// friendService.updateExpiredFriends(uid, tpId);
					userStatusService.updateUserStatus(uid, tpId);
				}
			});
		}
	}

	@Override
	public void cmsLogin(HttpServletRequest request, final long uid,
			final long tpId, boolean admin) {
		loginSessionManager.login(request, uid, tpId, admin);
	}

	@Override
	public boolean isOnline(long uid) {
		try {
			Boolean online = memcachedClient.get(MemcachedKeyGenerator
					.genUserOnlineKey(uid));
			return online == null ? false : online;
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return false;
		}
	}

	@Override
	public void updateOnlineState(long uid) {
		try {
			memcachedClient.set(MemcachedKeyGenerator.genUserOnlineKey(uid),
					userOnlineExpireTime, true);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}

	@Override
	public void logout(HttpServletRequest request, long uid) {
		loginSessionManager.logout(request);
		try {
			memcachedClient.delete(MemcachedKeyGenerator.genUserOnlineKey(uid));
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}

	private void updateLastLoginTime(long uid, RunType runType) {
		Date cDate = new Date();
		Passport updatePassport = new Passport();
		updatePassport.setId(uid);
		updatePassport.setLastLoginTime(cDate);
		passportMapper.updateByPrimaryKeySelective(updatePassport);

		Profile updateProfile = new Profile();
		updateProfile.setUid(uid);
		if (RunType.CONNET == runType || RunType.WEB == runType) {
			updateProfile.setLastWebLoginTime(cDate);
			profileMapper.updateByPrimaryKeySelective(updateProfile);
		}
	}

	@Override
	public void login(HttpServletRequest request, String loginName, String pwd)
			throws PassportAccountException {
		loginName = StringUtils.trim(loginName);
		pwd = StringUtils.trim(pwd);
		if (StringUtils.isEmpty(loginName) || StringUtils.isEmpty(pwd)) {
			throw new PassportAccountException(
					PassportAccountException.ACCOUNT_OR_PWD_ERROR);
		}
		Passport passport = passportService.getPassportByLoginName(loginName);
		if (null == passport
				|| !StringUtils.equals(passport.getPassword(),
						DigestUtils.md5Hex(pwd))) {
			throw new PassportAccountException(
					PassportAccountException.ACCOUNT_OR_PWD_ERROR);
		}
		Thirdparty tp = null;
		TpUser tpUser = tpUserService.getTpUserByUid(passport.getId());
		if (null != tpUser) {
			tp = InitData.getTpByTpNameAndJoinType(tpUser.getTpName(),
					JoinTypeEnum.CONNECT);
		}
		doLogin(request, passport.getId(), tp != null ? tp.getId() : 0L,
				RunType.WEB);
		nativeLoginCounter.incr(null, 1);
	}

	@Override
	public void autoLogin(HttpServletRequest request, long uid) {
		try {
			doLogin(request, uid, 0L, RunType.WEB);
		} catch (PassportAccountException e) {
			log.error(e.getMessage(), e);
		}
	}
}
