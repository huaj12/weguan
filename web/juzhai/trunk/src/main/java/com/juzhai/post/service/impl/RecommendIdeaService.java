package com.juzhai.post.service.impl;

import java.util.Collections;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.juzhai.core.cache.RedisKeyGenerator;
import com.juzhai.post.model.Idea;
import com.juzhai.post.service.IIdeaService;
import com.juzhai.post.service.IRecommendIdeaService;

@Service
public class RecommendIdeaService implements IRecommendIdeaService {
	@Autowired
	private IIdeaService ideaService;
	@Autowired
	private RedisTemplate<String, List<Idea>> redisTemplate;
	@Value("${recommend.idea.max.rows}")
	private int recommendIdeaMaxRows;

	@Override
	public List<Idea> listRecommendIdea() {
		List<Idea> list = redisTemplate.opsForValue().get(
				RedisKeyGenerator.genIndexRecommendIdeaKey());
		if (CollectionUtils.isEmpty(list)) {
			return Collections.emptyList();
		}
		return list;
	}

	@Override
	public void updateRecommendIdea() {
		List<Idea> ideas = ideaService.listRecentIdeas(0, null, null, 0,
				recommendIdeaMaxRows);
		redisTemplate.opsForValue().set(
				RedisKeyGenerator.genIndexRecommendIdeaKey(), ideas);
	}

}
