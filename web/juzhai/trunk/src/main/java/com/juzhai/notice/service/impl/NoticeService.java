package com.juzhai.notice.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.juzhai.core.cache.RedisKeyGenerator;
import com.juzhai.notice.bean.NoticeType;
import com.juzhai.notice.service.INoticeService;

@Service
public class NoticeService implements INoticeService {

	@Autowired
	private RedisTemplate<String, Long> redisTemplate;

	@Override
	public long incrNotice(long uid, NoticeType noticeType) {
		return redisTemplate.opsForValue().increment(
				RedisKeyGenerator.genUserNoticeNumKey(uid, noticeType), 1);
	}

	@Override
	public void emptyNotice(long uid, NoticeType noticeType) {
		redisTemplate.delete(RedisKeyGenerator.genUserNoticeNumKey(uid,
				noticeType));
	}

	@Override
	public Map<Integer, Long> getAllNoticeNum(long uid) {
		Map<Integer, Long> map = new HashMap<Integer, Long>();
		for (NoticeType noticeType : NoticeType.values()) {
			map.put(noticeType.getType(), getNoticeNum(uid, noticeType));
		}
		return map;
	}

	@Override
	public Long getNoticeNum(long uid, NoticeType noticeType) {
		return redisTemplate.opsForValue().increment(
				RedisKeyGenerator.genUserNoticeNumKey(uid, noticeType), 0);
	}
}
