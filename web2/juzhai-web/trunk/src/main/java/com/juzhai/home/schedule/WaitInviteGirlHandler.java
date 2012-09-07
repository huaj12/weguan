package com.juzhai.home.schedule;

import java.util.Date;
import java.util.List;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import com.juzhai.core.cache.RedisKeyGenerator;
import com.juzhai.core.dao.Limit;
import com.juzhai.core.schedule.AbstractScheduleHandler;
import com.juzhai.passport.mapper.ProfileMapper;
import com.juzhai.passport.model.Profile;
import com.juzhai.passport.model.ProfileExample;

@Component
public class WaitInviteGirlHandler extends AbstractScheduleHandler {
	@Autowired
	private RedisTemplate<String, Long> redisTemplate;
	@Autowired
	private ProfileMapper profileMapper;
	private int num = 200;

	@Override
	protected void doHandle() {
		Set<Long> citys = redisTemplate.opsForSet().members(
				RedisKeyGenerator.genWaitInviteGirlAllCityKey());
		redisTemplate.delete(RedisKeyGenerator.genWaitInviteGirlAllCityKey());
		if (CollectionUtils.isNotEmpty(citys)) {
			for (Long city : citys) {
				redisTemplate.delete(RedisKeyGenerator
						.genWaitInviteGirlKey(city));
			}
		}
		Date cdate = new Date();
		ProfileExample example = new ProfileExample();
		ProfileExample.Criteria c = example.createCriteria();
		c.andLogoPicIsNotNull();
		c.andGenderEqualTo(0);
		c.andCityNotEqualTo(0l);
		c.andLastWebLoginTimeGreaterThanOrEqualTo(DateUtils
				.addHours(cdate, -24));
		c.andLastWebLoginTimeLessThanOrEqualTo(cdate);
		example.setOrderByClause("create_time desc");
		int i = 0;
		while (true) {
			example.setLimit(new Limit(i, num));
			List<Profile> list = profileMapper.selectByExample(example);
			if (CollectionUtils.isNotEmpty(list)) {
				for (Profile profile : list) {
					redisTemplate.opsForSet().add(
							RedisKeyGenerator.genWaitInviteGirlKey(profile
									.getCity()), profile.getUid());
					redisTemplate.opsForSet().add(
							RedisKeyGenerator.genWaitInviteGirlAllCityKey(),
							profile.getCity());
				}
			}
			i += num;
			if (list.size() < num) {
				break;
			}
		}
	}
}
