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
import com.juzhai.post.bean.VerifyType;
import com.juzhai.post.mapper.PostMapper;
import com.juzhai.post.model.Post;
import com.juzhai.post.model.PostExample;
import com.juzhai.post.service.IRecommendPostService;

@Service
public class RecommendPostService implements IRecommendPostService {
	@Autowired
	private PostMapper postMapper;
	@Autowired
	private RedisTemplate<String, List<Post>> redisTemplate;

	@Value("${recommend.post.max.rows}")
	private int recommendPostMaxRows;
	@Value("${recommend.post.time.interval}")
	private int recommendPostTimeInterval;

	@Override
	public List<Post> listRecommendPost(int count) {
		List<Post> list = redisTemplate.opsForValue().get(
				RedisKeyGenerator.genIndexRecommendPostKey());
		if (CollectionUtils.isEmpty(list)) {
			return Collections.emptyList();
		}
		if (list.size() > count) {
			list.subList(0, count);
		}
		return list;
	}

	@Override
	public void updateRecommendPost() {
		Calendar c = Calendar.getInstance();
		c.add(Calendar.HOUR_OF_DAY, -recommendPostTimeInterval);
		PostExample example = new PostExample();
		example.createCriteria().andCreateTimeGreaterThan(c.getTime())
				.andVerifyTypeEqualTo(VerifyType.QUALIFIED.getType())
				.andDefunctEqualTo(false);
		example.setOrderByClause("response_cnt desc,comment_cnt desc,create_time desc");
		example.setLimit(new Limit(0, recommendPostMaxRows));
		List<Post> posts = postMapper.selectByExample(example);
		redisTemplate.opsForValue().set(
				RedisKeyGenerator.genIndexRecommendPostKey(), posts);

	}

}
