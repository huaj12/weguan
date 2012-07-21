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
