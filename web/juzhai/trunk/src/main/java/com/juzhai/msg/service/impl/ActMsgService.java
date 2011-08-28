package com.juzhai.msg.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.juzhai.core.cache.RedisKeyGenerator;
import com.juzhai.msg.bean.ActMsg;
import com.juzhai.msg.service.IActMsgService;

@Service
public class ActMsgService implements IActMsgService {

	private RedisTemplate<String, ActMsg> redisTemplate;

	@Override
	public List<ActMsg> pageUnRead(long uid, int maxResults) {
		List<ActMsg> actMsgList = new ArrayList<ActMsg>();
		String unReadKey = RedisKeyGenerator.genUnreadMsgsKey(uid,
				ActMsg.class.getSimpleName());
		for (int i = 0; i < maxResults; i++) {
			ActMsg actMsg = redisTemplate.opsForList().leftPop(unReadKey);
			if (null != actMsg) {
				actMsgList.add(actMsg);
			} else {
				break;
			}
		}
		String readKey = RedisKeyGenerator.genReadMsgsKey(uid,
				ActMsg.class.getSimpleName());
		for (int i = actMsgList.size(); i > 0; i--) {
			redisTemplate.opsForList().leftPush(readKey, actMsgList.get(i - 1));
		}

		return actMsgList;
	}

	@Override
	public long countUnRead(long uid) {
		return redisTemplate.opsForList().size(
				RedisKeyGenerator.genUnreadMsgsKey(uid,
						ActMsg.class.getSimpleName()));
	}

	@Override
	public List<ActMsg> pageRead(long uid, int start, int maxResults) {
		if (maxResults <= 0) {
			return Collections.emptyList();
		}
		return redisTemplate.opsForList().range(
				RedisKeyGenerator.genReadMsgsKey(uid,
						ActMsg.class.getSimpleName()), start,
				start + maxResults - 1);
	}

	@Override
	public long countRead(long uid) {
		return redisTemplate.opsForList().size(
				RedisKeyGenerator.genReadMsgsKey(uid,
						ActMsg.class.getSimpleName()));
	}

}
