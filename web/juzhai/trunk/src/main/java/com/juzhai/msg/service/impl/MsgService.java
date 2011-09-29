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
	public void getPrestore(String receiverIdentity, long receiverTpId,long uid,
			String className) {
		long len=redisTemplate.opsForList().size(RedisKeyGenerator.genPrestoreMsgsKey(receiverIdentity,
				receiverTpId, className));
		for(int i=0;i<len;i++){
			T msg=redisTemplate.opsForList().rightPop(
					RedisKeyGenerator.genPrestoreMsgsKey(receiverIdentity,
							receiverTpId, className));
			redisTemplate.opsForList().leftPush(RedisKeyGenerator.genUnreadMsgsKey(uid, msg.getClass()
					.getSimpleName()), msg);
		}
		
	}
}
