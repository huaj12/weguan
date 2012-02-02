package com.juzhai.post.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.juzhai.core.cache.RedisKeyGenerator;
import com.juzhai.post.dao.IIdeaDao;
import com.juzhai.post.mapper.IdeaMapper;
import com.juzhai.post.model.Idea;
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
}
