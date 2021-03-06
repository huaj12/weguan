/*
 *The code is written by 51juzhai, All rights is reserved
 */
package com.juzhai.passport.service.impl;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
import com.juzhai.core.web.util.HttpRequestUtil;
import com.juzhai.home.service.IUserStatusService;
import com.juzhai.passport.InitData;
import com.juzhai.passport.bean.JoinTypeEnum;
import com.juzhai.passport.exception.PassportAccountException;
import com.juzhai.passport.mapper.LoginLogMapper;
import com.juzhai.passport.mapper.PassportMapper;
import com.juzhai.passport.mapper.ProfileMapper;
import com.juzhai.passport.model.LoginLog;
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
	@Autowired
	private LoginLogMapper loginLogMapper;
	@Value(value = "${user.online.expire.time}")
	private int userOnlineExpireTime;
	@Value("${use.verify.login.count}")
	private int useVerifyLoginCount;
	@Value("${login.count.expire.time}")
	private int loginCountExpireTime;

	@Override
	public void login(HttpServletRequest request, HttpServletResponse response,
			final long uid, final long tpId, RunType runType)
			throws PassportAccountException {
		doLogin(request, response, uid, tpId, runType, false);
		loginCounter.incr(null, 1);
	}

	private void doLogin(HttpServletRequest request,
			HttpServletResponse response, final long uid, final long tpId,
			RunType runType, boolean persistent)
			throws PassportAccountException {
		Passport passport = passportMapper.selectByPrimaryKey(uid);
		if (null == passport) {
			log.error("Login error. Can not find passport[id=" + uid + "].");
		}
		Date shield = passport.getShieldTime();
		if (shield != null && shield.getTime() > System.currentTimeMillis()) {
			throw new PassportAccountException(
					PassportAccountException.USER_IS_SHIELD, shield.getTime());
		}
		loginSessionManager.login(request, response, uid, tpId, false,
				persistent);
		// 更新最后登录时间
		updateLastLoginTime(uid, runType);
		// updateOnlineState(uid);
		addLoginLog(request, uid);
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
	public void logout(HttpServletRequest request,
			HttpServletResponse response, long uid) {
		loginSessionManager.logout(request, response);
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
	public long login(HttpServletRequest request, HttpServletResponse response,
			String loginName, String pwd, boolean persistent)
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
		doLogin(request, response, passport.getId(), tp != null ? tp.getId()
				: 0L, RunType.WEB, persistent);
		nativeLoginCounter.incr(null, 1);
		return passport.getId();
	}

	@Override
	public void autoLogin(HttpServletRequest request,
			HttpServletResponse response, long uid) {
		try {
			doLogin(request, response, uid, 0L, RunType.WEB, false);
		} catch (PassportAccountException e) {
			log.error(e.getMessage(), e);
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

	private void addLoginLog(HttpServletRequest request, long uid) {
		String ip = HttpRequestUtil.getRemoteIp(request);
		LoginLog loginLog = new LoginLog();
		loginLog.setUid(uid);
		loginLog.setIp(ip);
		loginLog.setLoginTime(new Date());
		loginLog.setAutoLogin(false);
		loginLogMapper.insertSelective(loginLog);
	}
}
