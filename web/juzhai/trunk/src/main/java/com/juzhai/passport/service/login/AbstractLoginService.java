/*
 *The code is written by 51juzhai, All rights is reserved
 */
package com.juzhai.passport.service.login;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import com.juzhai.core.cache.KeyGenerator;
import com.juzhai.passport.mapper.ProfileMapper;
import com.juzhai.passport.model.Profile;
import com.juzhai.passport.service.IFriendService;

public abstract class AbstractLoginService implements ILoginService {

	@Autowired
	private RedisTemplate<String, Long> redisTemplate;
	@Autowired
	private ProfileMapper profileMapper;
	@Autowired
	private ThreadPoolTaskExecutor taskExecutor;
	@Autowired
	private IFriendService friendService;

	@Override
	public void login(HttpServletRequest request, final long uid,
			final long tpId) {
		doLogin(request, uid, tpId);
		// TODO 用AOP
		// 初始化安装的好友列表和没安装的好友列表（memcached）
		// 启动一个线程来获取和保存
		taskExecutor.execute(new Runnable() {
			@Override
			public void run() {
				friendService.getFriends(uid, tpId);
			}
		});
		// 初始化所在地等需要匹配的信息（redis）
		Profile profile = profileMapper.selectByPrimaryKey(uid);
		if (null != profile && profile.getCity() != null) {
			redisTemplate.opsForValue().set(KeyGenerator.genUserCityKey(uid),
					profile.getCity());
		}
	}

	protected abstract void doLogin(HttpServletRequest request, long uid,
			long tpId);

}
