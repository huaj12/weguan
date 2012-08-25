package com.juzhai.home.service.impl;

import java.util.concurrent.TimeoutException;

import net.rubyeye.xmemcached.MemcachedClient;
import net.rubyeye.xmemcached.exception.MemcachedException;

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
		Idea idea = ideaService.getRandomIdea(0l);
		if (idea == null) {
			return;
		}
		for (int i = 0; i < rescueBoySendPeopleCount; i++) {
			Long receiveUid = redisTemplate.opsForSet().pop(
					RedisKeyGenerator.genWaitInviteGirlKey(city));
			if (receiveUid == null) {
				break;
			}
			try {
				Long num = memcachedClient.get(MemcachedKeyGenerator
						.genUserReceiveCountKey(receiveUid));
				if (num == null) {
					num = 0l;
				}
				num++;
				if (num > rescueBoyReceiveCount) {
					continue;
				} else {
					redisTemplate.opsForSet().add(
							RedisKeyGenerator.genWaitInviteGirlKey(city),
							receiveUid);
					dialogService.sendSMS(uid, receiveUid,
							DialogContentTemplate.PRIVATE_DATE,
							idea.getContent());
					int exp = DateUtil.getNextDayTime();
					memcachedClient.set(MemcachedKeyGenerator
							.genUserReceiveCountKey(receiveUid), exp, num);
				}

			} catch (DialogException e) {
				log.error("rescueboy uid=" + uid + "receiveUid=" + receiveUid
						+ " send msg is error", e);
			} catch (TimeoutException e) {
				log.error(e.getMessage(), e);
			} catch (InterruptedException e) {
				log.error(e.getMessage(), e);
			} catch (MemcachedException e) {
				log.error(e.getMessage(), e);
			}
			setSendRescueboy(uid);
		}

	}

	public void setSendRescueboy(long uid) {
		int exp = DateUtil.getNextDayTime();
		try {
			memcachedClient.set(
					MemcachedKeyGenerator.genIsCanSendRescueBoyKey(uid), exp,
					true);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}

	@Override
	public boolean isCanSend(long uid) {
		try {
			Object obj = memcachedClient.get(MemcachedKeyGenerator
					.genIsCanSendRescueBoyKey(uid));
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
