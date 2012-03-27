/*
 *The code is written by 51juzhai, All rights is reserved
 */
package com.juzhai.passport.service.login;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import net.rubyeye.xmemcached.MemcachedClient;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import com.juzhai.core.cache.MemcachedKeyGenerator;
import com.juzhai.core.exception.NeedLoginException.RunType;
import com.juzhai.home.service.IUserStatusService;
import com.juzhai.passport.exception.LoginException;
import com.juzhai.passport.mapper.PassportMapper;
import com.juzhai.passport.mapper.ProfileMapper;
import com.juzhai.passport.model.Passport;
import com.juzhai.passport.model.Profile;
import com.juzhai.passport.service.IFriendService;
import com.juzhai.stats.counter.service.ICounter;

public abstract class AbstractLoginService implements ILoginService {

	private final Log log = LogFactory.getLog(getClass());

	protected static final String DAY_FIRST_LOGIN = "dfl";

	@Autowired
	private ThreadPoolTaskExecutor taskExecutor;
	@Autowired
	private IFriendService friendService;
	@Autowired
	private IUserStatusService iUserStatusService;
	@Autowired
	private PassportMapper passportMapper;
	@Autowired
	private ProfileMapper profileMapper;
	@Autowired
	private MemcachedClient memcachedClient;
	@Autowired
	private ICounter loginCounter;
	@Value(value = "${user.online.expire.time}")
	private int userOnlineExpireTime;

	@Override
	public void login(HttpServletRequest request, final long uid,
			final long tpId, RunType runType) throws LoginException {
		// 判断是不是当天第一次登陆
		Passport passport = passportMapper.selectByPrimaryKey(uid);
		if (null == passport) {
			log.error("Login error. Can not find passport[id=" + uid + "].");
		}
		// if (passport.getLastLoginTime() == null
		// || !DateUtils
		// .isSameDay(new Date(), passport.getLastLoginTime())) {
		// accountService.profitPoint(uid, ProfitAction.DAY_LOGIN);
		// HttpRequestUtil.setSessionAttribute(request, DAY_FIRST_LOGIN, true);
		// }
		Date shield = passport.getShieldTime();
		if (shield != null && shield.getTime() > new Date().getTime()) {
			throw new LoginException(LoginException.USER_IS_SHIELD,
					shield.getTime());
		}
		doLogin(request, uid, tpId, false);
		// 更新最后登录时间
		updateLastLoginTime(uid, runType);
		updateOnlineState(uid);
		// TODO 用AOP
		// 启动一个线程来获取和保存
		taskExecutor.execute(new Runnable() {
			@Override
			public void run() {
				// friendService.updateExpiredFriends(uid, tpId);
				iUserStatusService.updateUserStatus(uid, tpId);
			}
		});
		loginCounter.incr(null, 1);
	}

	@Override
	public void cmsLogin(HttpServletRequest request, final long uid,
			final long tpId, boolean admin) {
		doLogin(request, uid, tpId, admin);
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
		doLogout(request);
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

	protected abstract void doLogin(HttpServletRequest request, long uid,
			long tpId, boolean admin);

	protected abstract void doLogout(HttpServletRequest request);

}
