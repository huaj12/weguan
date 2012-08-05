package com.juzhai.post.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.juzhai.core.cache.RedisKeyGenerator;
import com.juzhai.post.InitData;
import com.juzhai.post.dao.IIdeaDao;
import com.juzhai.post.exception.InputRecommendException;
import com.juzhai.post.mapper.IdeaMapper;
import com.juzhai.post.model.Idea;
import com.juzhai.post.service.IIdeaService;
import com.juzhai.post.service.IRecommendIdeaService;

@Service
public class RecommendIdeaService implements IRecommendIdeaService {
	@Autowired
	private IIdeaService ideaService;
	@Autowired
	private RedisTemplate<String, List<Idea>> redisTemplate;
	@Autowired
	private RedisTemplate<String, Idea> redisIdeaTemplate;
	@Autowired
	private IIdeaDao ideaDao;
	@Autowired
	private IdeaMapper ideaMapper;
	@Value("${recommend.idea.max.rows}")
	private int recommendIdeaMaxRows;
	@Value("${top.idea.recent.day.before}")
	private int topIdeaRecentDayBefore;
	@Value("${recommend.index.idea.max.length}")
	private int recommendIndexIdeaMaxLength;

	@Override
	public List<Idea> listRecommendIdea(int count) {
		List<Idea> list = redisTemplate.opsForValue().get(
				RedisKeyGenerator.genIndexRecommendIdeaKey());
		if (CollectionUtils.isEmpty(list)) {
			return Collections.emptyList();
		}
		if (list.size() > count) {
			list = list.subList(0, count);
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

	@Override
	public void updateRecentTopIdeas() {
		Date endTime = new Date();
		Date startTime = DateUtils.addDays(endTime, -topIdeaRecentDayBefore);

		List<Map<Idea, Integer>> resultList = new ArrayList<Map<Idea, Integer>>(
				InitData.CATEGORY_MAP.size());
		for (Long categoryId : InitData.CATEGORY_MAP.keySet()) {
			Map<Long, Integer> ideaCntMap = ideaDao.getRecentPopIdeaId(
					categoryId, startTime, endTime);
			if (MapUtils.isNotEmpty(ideaCntMap)) {
				Idea idea = null;
				for (Map.Entry<Long, Integer> entry : ideaCntMap.entrySet()) {
					idea = ideaMapper.selectByPrimaryKey(entry.getKey());
					if (null != idea) {
						Map<Idea, Integer> map = new HashMap<Idea, Integer>();
						map.put(idea, entry.getValue());
						resultList.add(map);
					}
				}
			}
		}
		Collections.sort(resultList, new Comparator<Map<Idea, Integer>>() {
			@Override
			public int compare(Map<Idea, Integer> map1, Map<Idea, Integer> map2) {
				Idea idea1 = null;
				Idea idea2 = null;
				int count1 = 0;
				int count2 = 0;
				for (Map.Entry<Idea, Integer> entry : map1.entrySet()) {
					idea1 = entry.getKey();
					count1 = entry.getValue();
					break;
				}
				for (Map.Entry<Idea, Integer> entry : map2.entrySet()) {
					idea2 = entry.getKey();
					count2 = entry.getValue();
					break;
				}
				if (count1 != count2) {
					return count2 - count1;
				} else if (idea2.getCreateTime().after(idea1.getCreateTime())) {
					return 1;
				} else {
					return -1;
				}
			}
		});
		List<Idea> ideaList = new ArrayList<Idea>(resultList.size());
		for (Map<Idea, Integer> map : resultList) {
			for (Idea idea : map.keySet()) {
				ideaList.add(idea);
			}
		}
		if (CollectionUtils.isNotEmpty(ideaList)) {
			redisTemplate.opsForValue().set(
					RedisKeyGenerator.genRecentTopIdeasKey(), ideaList);
		}
	}

	@Override
	public List<Idea> listRecentTopIdeas() {
		return redisTemplate.opsForValue().get(
				RedisKeyGenerator.genRecentTopIdeasKey());
	}

	@Override
	public Set<Idea> listIndexIdeas() {
		return redisIdeaTemplate.opsForSet().members(
				RedisKeyGenerator.genIndexIdeaKey());
	}

	@Override
	public void addIndexIdea(long ideaId) throws InputRecommendException {
		Idea idea = ideaService.getIdeaById(ideaId);
		if (null == idea) {
			throw new InputRecommendException(
					InputRecommendException.ADD_IDEA_ID_NOT_EXIST);
		}
		Long size = redisIdeaTemplate.opsForSet().size(
				RedisKeyGenerator.genIndexIdeaKey());
		if (size != null && size >= recommendIndexIdeaMaxLength) {
			throw new InputRecommendException(
					InputRecommendException.ADD_IDEA_IS_TOO_MORE);
		}
		redisIdeaTemplate.opsForSet().add(RedisKeyGenerator.genIndexIdeaKey(),
				idea);

	}

	@Override
	public Idea getIndexIdea() {
		String key = RedisKeyGenerator.genIndexIdeaKey();
		Idea idea = redisIdeaTemplate.opsForSet().pop(key);
		if (null != idea) {
			redisIdeaTemplate.opsForSet().add(
					RedisKeyGenerator.genIndexIdeaKey(), idea);
		}
		return idea;
	}

	@Override
	public void removeIndexIdea(long ideaId) {
		Idea idea = ideaService.getIdeaById(ideaId);
		if (null != idea) {
			redisIdeaTemplate.opsForSet().remove(
					RedisKeyGenerator.genIndexIdeaKey(), idea);
		}
	}
}
