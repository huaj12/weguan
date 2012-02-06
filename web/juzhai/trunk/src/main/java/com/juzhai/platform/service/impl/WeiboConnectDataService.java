package com.juzhai.platform.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeoutException;

import net.rubyeye.xmemcached.MemcachedClient;
import net.rubyeye.xmemcached.exception.MemcachedException;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import weibo4j.Timeline;
import weibo4j.model.Status;

import com.juzhai.core.cache.MemcachedKeyGenerator;
import com.juzhai.passport.bean.AuthInfo;
import com.juzhai.platform.bean.UserWeibo;
import com.juzhai.platform.service.IDataService;

@Service
public class WeiboConnectDataService implements IDataService {
	private final Log log = LogFactory.getLog(getClass());
	@Value("${user.weibo.expire.time}")
	private int userWeiboExpireTime;
	@Value("${user.weibo.size}")
	private int userWeiboSize;
	@Autowired
	private MemcachedClient memcachedClient;

	@Override
	public List<UserWeibo> listWeibo(long uid, AuthInfo authInfo) {
		List<UserWeibo> userWeibos = null;
		try {
			userWeibos = memcachedClient.get(MemcachedKeyGenerator
					.genUserWeiboKey(uid));

			if (CollectionUtils.isEmpty(userWeibos)) {
				userWeibos = getWeibos(uid, authInfo);
			}
		} catch (Exception e) {
			log.error("listWeibo is error error message=" + e.getMessage());
		}
		return userWeibos;

	}

	private List<UserWeibo> getWeibos(long uid, AuthInfo authInfo)
			throws Exception {
		List<UserWeibo> userWeibos = new ArrayList<UserWeibo>();
		Timeline timeline = new Timeline(authInfo.getToken());
		List<Status> status = timeline.getUserTimeline(
				authInfo.getTpIdentity(), null, userWeiboSize, null, 0, 1);
		if (CollectionUtils.isNotEmpty(status)) {
			for (Status s : status) {
				UserWeibo userWeibo = new UserWeibo();
				userWeibo.setContent(s.getText());
				userWeibo.setTime(s.getCreatedAt());
				userWeibos.add(userWeibo);
			}
			memcachedClient.set(MemcachedKeyGenerator.genUserWeiboKey(uid),
					userWeiboExpireTime, userWeibos);
		}
		return userWeibos;
	}

	@Override
	public List<UserWeibo> refreshListWeibo(long uid, AuthInfo authInfo) {
		List<UserWeibo> userWeibos = null;
		try {
			userWeibos = getWeibos(uid, authInfo);
		} catch (Exception e) {
			log.error("refreshListWeibo is error error message="
					+ e.getMessage());
		}
		return userWeibos;
	}

}
