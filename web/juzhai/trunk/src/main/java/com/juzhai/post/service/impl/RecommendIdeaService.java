package com.juzhai.post.service.impl;

import java.util.Calendar;
import java.util.Collections;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.juzhai.core.cache.RedisKeyGenerator;
import com.juzhai.core.dao.Limit;
import com.juzhai.post.mapper.IdeaMapper;
import com.juzhai.post.model.Idea;
import com.juzhai.post.model.IdeaExample;
import com.juzhai.post.service.IRecommendIdeaService;

@Service
public class RecommendIdeaService implements IRecommendIdeaService {
	@Autowired
	private IdeaMapper ideaMapper;
	@Autowired
	private RedisTemplate<String, List<Idea>> redisTemplate;

	@Value("${recommend.idea.max.rows}")
	private int recommendIdeaMaxRows;
	@Value("${recommend.idea.time.interval}")
	private int recommendIdeaTimeInterval;

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
		Calendar c = Calendar.getInstance();
		c.add(Calendar.DAY_OF_MONTH, -recommendIdeaTimeInterval);
		IdeaExample example = new IdeaExample();
		example.createCriteria().andCreateTimeGreaterThan(c.getTime())
				.andDefunctEqualTo(false);
		example.setOrderByClause("use_count desc,create_time desc");
		example.setLimit(new Limit(0, recommendIdeaMaxRows));
		List<Idea> ideas = ideaMapper.selectByExample(example);
		redisTemplate.opsForValue().set(
				RedisKeyGenerator.genIndexRecommendIdeaKey(), ideas);
	}

}
