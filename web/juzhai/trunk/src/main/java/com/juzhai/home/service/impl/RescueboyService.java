package com.juzhai.home.service.impl;

import java.util.HashMap;
import java.util.Map;

import net.rubyeye.xmemcached.MemcachedClient;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.juzhai.core.cache.MemcachedKeyGenerator;
import com.juzhai.core.cache.RedisKeyGenerator;
import com.juzhai.core.util.DateUtil;
import com.juzhai.home.bean.DialogContentTemplate;
import com.juzhai.home.exception.DialogException;
import com.juzhai.home.service.IDialogService;
import com.juzhai.home.service.IRescueboyService;
import com.juzhai.post.model.Idea;
import com.juzhai.post.service.IIdeaService;

@Service
public class RescueboyService implements IRescueboyService {
	Log log = LogFactory.getLog(this.getClass());
	@Autowired
	private RedisTemplate<String, Long> redisTemplate;
	@Autowired
	private IDialogService dialogService;
	@Autowired
	private IIdeaService ideaService;
	@Autowired
	private MemcachedClient memcachedClient;
	public static Map<Long, Long> citySendNum = new HashMap<Long, Long>();
	@Value("${rescue.boy.send.people.count}")
	private long rescueBoySendPeopleCount;
	@Value("${rescue.boy.receive.count}")
	private int rescueBoyReceiveCount;

	@Override
	public boolean isOpenRescueboy(long uid) {
		return redisTemplate.opsForSet().isMember(
				RedisKeyGenerator.genRescueboyStatusKey(), uid);
	}

	@Override
	public void open(long uid) {
		redisTemplate.opsForSet().add(
				RedisKeyGenerator.genRescueboyStatusKey(), uid);
	}

	@Override
	public void close(long uid) {
		redisTemplate.opsForSet().remove(
				RedisKeyGenerator.genRescueboyStatusKey(), uid);

	}

	@Override
	public void rescueboy(long uid, long city) {
		Long size = redisTemplate.opsForSet().pop(
				RedisKeyGenerator.genWaitInviteGirlCitySizeKey(city));
		if (size != null) {
			redisTemplate.opsForSet().add(
					RedisKeyGenerator.genWaitInviteGirlCitySizeKey(city), size);
		}
		Idea idea = ideaService.getRandomIdea(0l);
		if (idea == null) {
			return;
		}
		rescueBoySendPeopleCount = rescueBoySendPeopleCount > size ? size
				: rescueBoySendPeopleCount;
		for (int i = 0; i < rescueBoySendPeopleCount; i++) {
			Long receiveUid = redisTemplate.opsForList().leftPop(
					RedisKeyGenerator.genWaitInviteGirlKey(city));
			if (receiveUid == null) {
				break;
			}
			try {
				synchronized (citySendNum) {
					long num = citySendNum.get(city) == null ? 0 : citySendNum
							.get(city);
					num++;
					if (num > (size * rescueBoyReceiveCount)) {
						return;
					}
					dialogService.sendSMS(uid, receiveUid,
							DialogContentTemplate.PRIVATE_DATE,
							idea.getContent());
					citySendNum.put(city, num);
				}
			} catch (DialogException e) {
				log.error("rescueboy uid=" + uid + "receiveUid=" + receiveUid
						+ " send msg is error", e);
			}
			redisTemplate.opsForList().rightPush(
					RedisKeyGenerator.genWaitInviteGirlKey(city), receiveUid);
			setSendRescueboy(uid);
		}

	}

	public void setSendRescueboy(long uid) {
		int exp = DateUtil.getNextDayTime();
		try {
			memcachedClient.set(MemcachedKeyGenerator.genSendRescueBoyKey(uid),
					exp, true);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}

	@Override
	public boolean isSend(long uid) {
		try {
			Object obj = memcachedClient.get(MemcachedKeyGenerator
					.genSendRescueBoyKey(uid));
			if (obj == null) {
				return true;
			} else {
				return false;
			}
		} catch (Exception e) {
			return false;
		}
	}
}
