package com.juzhai.antiad.service.impl;

import java.util.concurrent.TimeUnit;

import net.rubyeye.xmemcached.MemcachedClient;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;

import com.juzhai.antiad.service.IFoulService;
import com.juzhai.core.bean.Function;
import com.juzhai.core.cache.MemcachedKeyGenerator;
import com.juzhai.core.cache.RedisKeyGenerator;
import com.juzhai.core.web.session.UserContext;
import com.juzhai.platform.service.impl.SynchronizeService;

public abstract class AbstractFoulservice implements IFoulService {
	private final Log log = LogFactory.getLog(SynchronizeService.class);
	@Autowired
	private RedisTemplate<String, Long> redisTemplate;
	@Autowired
	private MemcachedClient memcachedClient;
	@Value("${ad.send.user.count}")
	private long adSendUserCount;
	@Value("${user.foul.save.expire.time}")
	private int userFoulSaveExpireTime;
	@Value("${ip.foul.save.expire.time}")
	private int ipFoulSaveExpireTime;
	@Value("${ad.send.user.time}")
	private long adSendUserTime;

	@Override
	public void foul(UserContext context, long targetUid, String content,
			Function function) {
		// 发送成功记录该用户在固定时间内发送的人次
		String key = RedisKeyGenerator.genSendCount(function.getType(),
				context.getUid());
		redisTemplate.opsForSet().add(key, targetUid);
		// 防止重复设置过期时间
		Long count = redisTemplate.opsForSet().size(key);
		if (count == 1) {
			redisTemplate.expire(key, adSendUserTime, TimeUnit.MINUTES);
		}

		long sendUid = context.getUid();
		String ip = context.getRemoteAddress();
		Integer uidCount = 0;
		Integer ipCount = 0;
		String foulIpKey = MemcachedKeyGenerator.genFoulIpCountKey(ip);
		String foulUserKey = MemcachedKeyGenerator.genFoulUserCountKey(sendUid);
		try {
			uidCount = memcachedClient.get(foulUserKey);
			ipCount = memcachedClient.get(foulIpKey);
		} catch (Exception e) {
			log.error("  get foul count is error");
		}
		ipCount = ipCount == null ? 0 : ipCount;
		uidCount = uidCount == null ? 0 : uidCount;
		// 是否犯规
		if (contentIsFoul(content, sendUid)) {
			// 犯规++
			try {
				memcachedClient.set(foulUserKey, userFoulSaveExpireTime,
						++uidCount);
				memcachedClient.set(foulIpKey, ipFoulSaveExpireTime, ++ipCount);
			} catch (Exception e) {
				log.error("set foul count is error");
			}
		}
		if (count > adSendUserCount) {
			// 犯规++
			try {
				memcachedClient.set(foulUserKey, userFoulSaveExpireTime,
						++uidCount);
				memcachedClient.set(foulIpKey, ipFoulSaveExpireTime, ++ipCount);
			} catch (Exception e) {
				log.error("  set foul count is error");
			}
		}

	}

	protected abstract boolean contentIsFoul(String content, long sendUid);

	@Override
	public int getUserFoul(long uid) {
		return 0;
	}

	@Override
	public int getIpFoul(String ip) {
		return 0;
	}

	@Override
	public void resetFoul(long uid) {

	}

	@Override
	public void resetFoul(String ip) {

	}

}
