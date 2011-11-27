package com.juzhai.index.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.juzhai.act.model.Act;
import com.juzhai.act.service.IActService;
import com.juzhai.core.cache.RedisKeyGenerator;
import com.juzhai.index.service.IActRankService;

@Service
public class ActRankService implements IActRankService {

	@Autowired
	private RedisTemplate<String, Long> redisTemplate;
	@Autowired
	private IActService actService;

	@Override
	public void incrScore(long actId, Date date) {
		if (null == date) {
			date = new Date();
		}
		redisTemplate.opsForZSet().incrementScore(
				RedisKeyGenerator.genActDayRankKey(date), actId, 1D);
	}

	@Override
	public void removeActRank(Date date) {
		if (null != date) {
			redisTemplate.delete(RedisKeyGenerator.genActDayRankKey(date));
		}
	}

	@Override
	public List<Act> listActRank(Date date, int count) {
		if (null == date) {
			date = new Date();
		}
		Set<Long> acts = redisTemplate.opsForZSet().reverseRange(
				RedisKeyGenerator.genActDayRankKey(date), 0, count - 1);
		List<Act> actList = new ArrayList<Act>();
		for (long actId : acts) {
			actList.add(actService.getActById(actId));
		}
		return actList;
	}
}
