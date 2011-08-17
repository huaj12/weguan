/*
 *The code is written by 51juzhai, All rights is reserved
 */
package com.juzhai.passport.service.login;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import com.juzhai.passport.service.IFriendService;

public abstract class AbstractLoginService implements ILoginService {

	@Autowired
	private ThreadPoolTaskExecutor taskExecutor;
	@Autowired
	private IFriendService friendService;

	@Override
	public void login(HttpServletRequest request, final long uid,
			final long tpId) {
		doLogin(request, uid, tpId);
		// TODO 用AOP
		// 启动一个线程来获取和保存
		taskExecutor.execute(new Runnable() {
			@Override
			public void run() {
				friendService.updateExpiredFriends(uid, tpId);
			}
		});
	}

	protected abstract void doLogin(HttpServletRequest request, long uid,
			long tpId);

}
