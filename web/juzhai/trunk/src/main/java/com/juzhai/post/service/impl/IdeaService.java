package com.juzhai.post.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.juzhai.core.cache.RedisKeyGenerator;
import com.juzhai.core.dao.Limit;
import com.juzhai.index.bean.ShowIdeaOrder;
import com.juzhai.post.dao.IIdeaDao;
import com.juzhai.post.mapper.IdeaMapper;
import com.juzhai.post.model.Idea;
import com.juzhai.post.model.IdeaExample;
import com.juzhai.post.service.IIdeaService;

@Service
public class IdeaService implements IIdeaService {

	@Autowired
	private IdeaMapper ideaMapper;
	@Autowired
	private IIdeaDao ideaDao;
	@Autowired
	private RedisTemplate<String, Long> redisTemplate;

	@Override
	public Idea getIdeaById(long ideaId) {
		return ideaMapper.selectByPrimaryKey(ideaId);
	}

	@Override
	public void addFirstUser(long ideaId, long uid) {
		ideaDao.addFirstUser(ideaId, uid);
		redisTemplate.opsForSet().add(
				RedisKeyGenerator.genIdeaUsersKey(ideaId), uid);
	}

	@Override
	public void addUser(long ideaId, long uid) {
		incrUseCount(ideaId);
		redisTemplate.opsForSet().add(
				RedisKeyGenerator.genIdeaUsersKey(ideaId), uid);
	}

	@Override
	public void removeUser(long ideaId, long uid) {
		decrUseCount(ideaId);
		redisTemplate.opsForSet().remove(
				RedisKeyGenerator.genIdeaUsersKey(ideaId), uid);
	}

	private void incrUseCount(long ideaId) {
		ideaDao.incrOrDecrUseCount(ideaId, 1);
	}

	private void decrUseCount(long ideaId) {
		ideaDao.incrOrDecrUseCount(ideaId, -1);
	}

	@Override
	public boolean isUseIdea(long uid, long ideaId) {
		return redisTemplate.opsForSet().isMember(
				RedisKeyGenerator.genIdeaUsersKey(ideaId), uid);
	}

	@Override
	public List<Idea> listIdeaByCity(Long cityId, ShowIdeaOrder orderType,
			int firstResult, int maxResults) {
		IdeaExample example = new IdeaExample();
		if (null != cityId && cityId > 0) {
			example.createCriteria().andCityEqualTo(cityId);
		}
		example.setOrderByClause(orderType.getColumn() + " desc");
		example.setLimit(new Limit(firstResult, maxResults));
		return ideaMapper.selectByExample(example);
	}

	@Override
	public int countIdeaByCity(Long cityId) {
		IdeaExample example = new IdeaExample();
		if (null != cityId && cityId > 0) {
			example.createCriteria().andCityEqualTo(cityId);
		}
		return ideaMapper.countByExample(example);
	}
}
