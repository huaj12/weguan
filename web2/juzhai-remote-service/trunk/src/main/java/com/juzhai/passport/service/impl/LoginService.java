/*
 *The code is written by 51juzhai, All rights is reserved
 */
package com.juzhai.passport.service.impl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.rubyeye.xmemcached.MemcachedClient;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.juzhai.core.cache.MemcachedKeyGenerator;
import com.juzhai.core.exception.NeedLoginException.RunType;
import com.juzhai.core.stats.counter.service.ICounter;
import com.juzhai.core.web.session.LoginSessionManager;
import com.juzhai.core.web.util.HttpRequestUtil;
import com.juzhai.passport.bean.JoinTypeEnum;
import com.juzhai.passport.exception.PassportAccountException;
import com.juzhai.passport.exception.ReportAccountException;
import com.juzhai.passport.model.Passport;
import com.juzhai.passport.model.Thirdparty;
import com.juzhai.passport.service.ILoginService;
import com.juzhai.passport.service.IPassportRemoteService;
import com.juzhai.passport.service.IProfileRemoteService;
import com.juzhai.passport.service.IReportRemoteService;
import com.juzhai.passport.service.ITpUserRemoteService;

@Service
public class LoginService implements ILoginService {

	private final Log log = LogFactory.getLog(getClass());

	protected static final String DAY_FIRST_LOGIN = "dfl";

	@Autowired
	private LoginSessionManager loginSessionManager;
	@Autowired
	private IPassportRemoteService passportService;
	@Autowired
	private MemcachedClient memcachedClient;
	@Autowired
	private ITpUserRemoteService tpUserService;
	@Autowired
	private ICounter loginCounter;
	@Autowired
	private ICounter nativeLoginCounter;
	@Autowired
	private IProfileRemoteService profileService;
	@Autowired
	private IReportRemoteService reportService;

	@Override
	public void login(HttpServletRequest request, HttpServletResponse response,
			final long uid, final long tpId, RunType runType, boolean persistent)
			throws PassportAccountException, ReportAccountException {
		doLogin(request, response, uid, tpId, runType, persistent);
		loginCounter.incr(null, 1);
	}

	private void doLogin(HttpServletRequest request,
			HttpServletResponse response, final long uid, final long tpId,
			RunType runType, boolean persistent) throws ReportAccountException {
		Passport passport = passportService.getPassportByUid(uid);
		if (null == passport) {
			log.error("Login error. Can not find passport[id=" + uid + "].");
		}
		isShield(passport.getId(), request, response);
		loginSessionManager.login(request, response, uid, tpId, false,
				persistent);
		passportService.loginProcess(passport, tpId,
				HttpRequestUtil.getRemoteIp(request), runType);
	}

	@Override
	public void cmsLogin(HttpServletRequest request,
			HttpServletResponse response, final long uid, final long tpId) {
		loginSessionManager.login(request, response, uid, tpId, true, false);
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

	// @Override
	// public void updateOnlineState(long uid) {
	// try {
	// memcachedClient.set(MemcachedKeyGenerator.genUserOnlineKey(uid),
	// userOnlineExpireTime, true);
	// } catch (Exception e) {
	// log.error(e.getMessage(), e);
	// }
	// }

	@Override
	public void logout(HttpServletRequest request,
			HttpServletResponse response, long uid) {
		loginSessionManager.logout(request, response);
		try {
			memcachedClient.delete(MemcachedKeyGenerator.genUserOnlineKey(uid));
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}

	@Override
	public long login(HttpServletRequest request, HttpServletResponse response,
			String loginName, String pwd, boolean persistent)
			throws PassportAccountException, ReportAccountException {
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
		Thirdparty tp = tpUserService.getTpByUidAndJoinType(passport.getId(),
				JoinTypeEnum.CONNECT);
		doLogin(request, response, passport.getId(), tp != null ? tp.getId()
				: 0L, RunType.WEB, persistent);
		nativeLoginCounter.incr(null, 1);
		return passport.getId();
	}

	@Override
	public void autoLogin(HttpServletRequest request,
			HttpServletResponse response, long uid, boolean persistent) {
		try {
			doLogin(request, response, uid, 0L, RunType.WEB, persistent);
		} catch (ReportAccountException e) {
		}
	}

	@Override
	public boolean useVerifyCode(String ip) {
		// String count = null;
		// try {
		// count = memcachedClient.get(MemcachedKeyGenerator
		// .genLoginCountKey(ip));
		// } catch (Exception e) {
		// log.error(e.getMessage(), e);
		// }
		// if (null != count && Long.valueOf(count.trim()) >
		// useVerifyLoginCount) {
		// return true;
		// }
		return false;
	}

	@Override
	public long incrLoginCount(String ip) {
		// String key = MemcachedKeyGenerator.genLoginCountKey(ip);
		// long count = 0;
		// try {
		// count = memcachedClient.incr(key, 1L, 1L, 1000L,
		// loginCountExpireTime);
		// } catch (Exception e) {
		// log.error(e.getMessage(), e);
		// }
		// return count;
		return 0;
	}

	@Override
	public long persistentAutoLogin(HttpServletRequest request,
			HttpServletResponse response) throws PassportAccountException,
			ReportAccountException {
		long uid = loginSessionManager.persistentLoginUid(request, response);
		if (uid > 0 && null != profileService.getProfileCacheByUid(uid)) {
			Thirdparty tp = tpUserService.getTpByUidAndJoinType(uid,
					JoinTypeEnum.CONNECT);
			doLogin(request, response, uid, tp != null ? tp.getId() : 0L,
					RunType.WEB, false);
			nativeLoginCounter.incr(null, 1);
			return uid;
		} else {
			return 0;
		}
	}

	@Override
	public void isShield(long uid, HttpServletRequest request,
			HttpServletResponse response) throws ReportAccountException {
		long shieldTime = reportService.isShield(uid);
		if (shieldTime > 0) {
			logout(request, response, uid);
			throw new ReportAccountException(
					ReportAccountException.USER_IS_SHIELD, shieldTime);
		}
	}

	@Override
	public void updateTpId(HttpServletRequest request,
			HttpServletResponse response, long uid, long tpId) {
		loginSessionManager.updateTpId(request, response, uid, tpId);
	}
}
