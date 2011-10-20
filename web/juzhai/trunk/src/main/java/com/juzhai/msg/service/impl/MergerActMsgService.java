package com.juzhai.msg.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.juzhai.core.cache.RedisKeyGenerator;
import com.juzhai.msg.bean.ActMsg;
import com.juzhai.msg.bean.MergerActMsg;
import com.juzhai.msg.service.IMergerActMsgService;

@Service
public class MergerActMsgService implements IMergerActMsgService {
	@Autowired
	private RedisTemplate<String, MergerActMsg> redisTemplate;

	@Override
	public List<MergerActMsg> pageUnRead(long uid, int start,
			int maxResults) {
		List<MergerActMsg> actMsgList = new ArrayList<MergerActMsg>();
		String unReadKey = RedisKeyGenerator.genUnreadMsgsKey(uid,
				MergerActMsg.class.getSimpleName());
		actMsgList = redisTemplate.opsForList().range(unReadKey, start,
				start + maxResults - 1);
		return actMsgList;
	}

	@Override
	public long countUnRead(long uid) {
		return redisTemplate.opsForList().size(
				RedisKeyGenerator.genUnreadMsgsKey(uid,
						MergerActMsg.class.getSimpleName()));
	}

	@Override
	public List<MergerActMsg> pageRead(long uid, int start,
			int maxResults) {
		if (maxResults <= 0) {
			return Collections.emptyList();
		}
		return redisTemplate.opsForList().range(
				RedisKeyGenerator.genReadMsgsKey(uid,
						MergerActMsg.class.getSimpleName()), start,
				start + maxResults - 1);
	}

	@Override
	public long countRead(long uid) {
		return redisTemplate.opsForList().size(
				RedisKeyGenerator.genReadMsgsKey(uid,
						MergerActMsg.class.getSimpleName()));
	}

	@Override
	public void openMessage(long uid, int index) {
		String unReadKey = RedisKeyGenerator.genUnreadMsgsKey(uid,
				MergerActMsg.class.getSimpleName());
		MergerActMsg msg = redisTemplate.opsForList().index(unReadKey,
				index);
		redisTemplate.opsForList().remove(unReadKey, 0, msg);
		String readKey = RedisKeyGenerator.genReadMsgsKey(uid,
				MergerActMsg.class.getSimpleName());
		redisTemplate.opsForList().leftPush(readKey, msg);
	}

	@Override
	public void removeUnRead(long uid, int index) {
		String unReadKey = RedisKeyGenerator.genUnreadMsgsKey(uid,
				MergerActMsg.class.getSimpleName());
		MergerActMsg msg = redisTemplate.opsForList().index(unReadKey,
				index);
		redisTemplate.opsForList().remove(unReadKey, 0, msg);
	}

	@Override
	public void removeRead(long uid, int index) {
		String readKey = RedisKeyGenerator.genReadMsgsKey(uid,
				MergerActMsg.class.getSimpleName());
		MergerActMsg msg = redisTemplate.opsForList().index(readKey,
				index);
		redisTemplate.opsForList().remove(readKey, 0, msg);
	}

	@Override
	public void updateMsgStuts(long uid, int index) {
		String readKey = RedisKeyGenerator.genReadMsgsKey(uid,
				MergerActMsg.class.getSimpleName());
		MergerActMsg msg = redisTemplate.opsForList().index(readKey,
				index);
		msg.setStuts(true);
		redisTemplate.opsForList().set(readKey, index, msg);
	}

}
