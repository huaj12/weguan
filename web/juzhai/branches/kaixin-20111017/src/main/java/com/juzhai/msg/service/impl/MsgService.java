package com.juzhai.msg.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.juzhai.core.cache.RedisKeyGenerator;
import com.juzhai.msg.bean.Msg;
import com.juzhai.msg.service.IMsgService;

@Service
public class MsgService<T extends Msg> implements IMsgService<T> {

	@Autowired
	private RedisTemplate<String, T> redisTemplate;

	@Override
	public void sendMsg(long receiverId, T msg) {
		redisTemplate.opsForList().leftPush(
				RedisKeyGenerator.genUnreadMsgsKey(receiverId, msg.getClass()
						.getSimpleName()), msg);
	}

	@Override
	public void sendMsg(long receiverTpId, String receiverIdentity, T msg) {
		redisTemplate.opsForList().leftPush(
				RedisKeyGenerator.genPrestoreMsgsKey(receiverIdentity,
						receiverTpId, msg.getClass().getSimpleName()), msg);
	}

	@Override
	public void getPrestore(String receiverIdentity, long receiverTpId,
			long uid, Class<T> clazz) {
		T msg = null;
		while ((msg = redisTemplate.opsForList().rightPop(
				RedisKeyGenerator.genPrestoreMsgsKey(receiverIdentity,
						receiverTpId, clazz.getSimpleName()))) != null) {
			redisTemplate.opsForList().leftPush(
					RedisKeyGenerator.genUnreadMsgsKey(uid, msg.getClass()
							.getSimpleName()), msg);
		}
	}
}
