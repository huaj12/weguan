package com.juzhai.passport.schedule;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import com.juzhai.core.cache.RedisKeyGenerator;
import com.juzhai.core.schedule.AbstractScheduleHandler;
import com.juzhai.passport.bean.TodayVisit;

@Component
public class CleanTodayVisitHandler extends AbstractScheduleHandler {
	@Autowired
	private RedisTemplate<String, Long> redisTemplate;
	private final Log log = LogFactory.getLog(getClass());

	//TODO (review) 考虑用过期时间来删除。另外考虑一下memcached能不能做，如果考虑过，说一下原因
	@Override
	protected void doHandle() {
		try {
			for (TodayVisit todayVisit : TodayVisit.values()) {
				redisTemplate.delete(RedisKeyGenerator
						.genTodayVisitKey(todayVisit.getType()));
			}
		} catch (Exception e) {
			log.error("delete user today visit Handler is error");
		}
	}

}
