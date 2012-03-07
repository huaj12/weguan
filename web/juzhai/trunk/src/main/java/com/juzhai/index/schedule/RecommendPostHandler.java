package com.juzhai.index.schedule;

import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import com.juzhai.core.cache.RedisKeyGenerator;
import com.juzhai.core.dao.Limit;
import com.juzhai.core.schedule.AbstractScheduleHandler;
import com.juzhai.post.mapper.PostMapper;
import com.juzhai.post.model.Post;
import com.juzhai.post.model.PostExample;

@Component
public class RecommendPostHandler extends AbstractScheduleHandler {
	@Autowired
	private PostMapper postMapper;
	@Autowired
	private RedisTemplate<String, Post> redisTemplate;

	@Value("{recommend.post.max.rows}")
	private int recommendPostMaxRows;
	@Value("{recommend.post.time.interval}")
	private int recommendPostTimeInterval;

	@Override
	protected void doHandle() {
		Calendar c = Calendar.getInstance();
		c.add(Calendar.HOUR_OF_DAY, -(recommendPostTimeInterval));
		PostExample example = new PostExample();
		example.createCriteria().andCreateTimeGreaterThan(c.getTime());
		example.setOrderByClause("response_cnt desc,comment_cnt desc,create_time desc");
		example.setLimit(new Limit(0, recommendPostMaxRows));
		List<Post> posts = postMapper.selectByExample(example);
		for (Post post : posts) {
			redisTemplate.opsForList().leftPush(
					RedisKeyGenerator.genIndexRecommendPostKey(), post);
		}

	}

}
