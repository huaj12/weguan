/*
 *The code is written by 51juzhai, All rights is reserved
 */
package com.juzhai.passport.service.login;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.time.DateUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import com.juzhai.account.bean.ProfitAction;
import com.juzhai.account.service.IAccountService;
import com.juzhai.passport.mapper.PassportMapper;
import com.juzhai.passport.model.Passport;
import com.juzhai.passport.service.IFriendService;

public abstract class AbstractLoginService implements ILoginService {

	private final Log log = LogFactory.getLog(getClass());

	@Autowired
	private ThreadPoolTaskExecutor taskExecutor;
	@Autowired
	private IFriendService friendService;
	@Autowired
	private PassportMapper passportMapper;
	@Autowired
	private IAccountService accountService;

	@Override
	public void login(HttpServletRequest request, final long uid,
			final long tpId) {
		// 判断是不是当天第一次登陆
		Passport passport = passportMapper.selectByPrimaryKey(uid);
		if (null == passport) {
			log.error("Login error. Can not find passport[id=" + uid + "].");
		}
		if (passport.getLastLoginTime() == null
				|| !DateUtils
						.isSameDay(new Date(), passport.getLastLoginTime())) {
			accountService.profitPoint(uid, ProfitAction.DAY_LOGIN);
		}

		doLogin(request, uid, tpId, false);
		// 更新最后登录时间
		updateLastLoginTime(uid);
		// TODO 用AOP
		// 启动一个线程来获取和保存
		taskExecutor.execute(new Runnable() {
			@Override
			public void run() {
				friendService.updateExpiredFriends(uid, tpId);
			}
		});
	}

	@Override
	public void cmsLogin(HttpServletRequest request, final long uid,
			final long tpId, boolean admin) {
		doLogin(request, uid, tpId, admin);
	}

	private void updateLastLoginTime(long uid) {
		Passport updatePassport = new Passport();
		updatePassport.setId(uid);
		updatePassport.setLastLoginTime(new Date());
		passportMapper.updateByPrimaryKeySelective(updatePassport);
	}

	protected abstract void doLogin(HttpServletRequest request, long uid,
			long tpId, boolean admin);

}
