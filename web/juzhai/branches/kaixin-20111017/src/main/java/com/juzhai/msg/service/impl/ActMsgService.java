package com.juzhai.msg.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.juzhai.core.cache.RedisKeyGenerator;
import com.juzhai.msg.bean.ActMsg;
import com.juzhai.msg.service.IActMsgService;

@Service
public class ActMsgService implements IActMsgService {
	@Autowired
	private RedisTemplate<String, ActMsg> redisTemplate;

	@Override
	public List<ActMsg> pageUnRead(long uid,int start,int maxResults) {
		List<ActMsg> actMsgList = new ArrayList<ActMsg>();
		String unReadKey = RedisKeyGenerator.genUnreadMsgsKey(uid,
				ActMsg.class.getSimpleName());
		actMsgList=redisTemplate.opsForList().range(unReadKey,start ,start + maxResults - 1);
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

	@Override
	public void openMessage(long uid,int index) {
		String unReadKey = RedisKeyGenerator.genUnreadMsgsKey(uid,
				ActMsg.class.getSimpleName());
		ActMsg msg=redisTemplate.opsForList().index(unReadKey, index);
		redisTemplate.opsForList().remove(unReadKey, 0, msg);
		String readKey=RedisKeyGenerator.genReadMsgsKey(uid,
				ActMsg.class.getSimpleName());
		redisTemplate.opsForList().leftPush(readKey, msg);
	}

	@Override
	public void removeUnRead(long uid, int index) {
		String unReadKey = RedisKeyGenerator.genUnreadMsgsKey(uid,
				ActMsg.class.getSimpleName());
		ActMsg msg=redisTemplate.opsForList().index(unReadKey, index);
		redisTemplate.opsForList().remove(unReadKey, 0, msg);
	}

	@Override
	public void removeRead(long uid, int index) {
		String readKey = RedisKeyGenerator.genReadMsgsKey(uid,
				ActMsg.class.getSimpleName());
		ActMsg msg=redisTemplate.opsForList().index(readKey, index);
		redisTemplate.opsForList().remove(readKey, 0, msg);
	}

	@Override
	public void updateMsgStuts(long uid, int index) {
		String readKey = RedisKeyGenerator.genReadMsgsKey(uid,
				ActMsg.class.getSimpleName());
		ActMsg msg=redisTemplate.opsForList().index(readKey, index);
		msg.setStuts(true);
		redisTemplate.opsForList().set(readKey, index, msg);
	}

	
	
	
}
