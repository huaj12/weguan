package com.juzhai.notice.schedule;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import com.juzhai.core.cache.RedisKeyGenerator;
import com.juzhai.core.schedule.AbstractScheduleHandler;
import com.juzhai.notice.bean.NoticeType;
import com.juzhai.notice.service.INoticeService;
import com.juzhai.platform.exception.AdminException;
import com.juzhai.platform.service.ISynchronizeService;

@Component
public class NoticeUnreadMessageHandler extends AbstractScheduleHandler {
	@Autowired
	private INoticeService noticeService;
	@Autowired
	private RedisTemplate<String, Long> redisTemplate;

	@Override
	protected void doHandle() {
		List<Long> uids = new ArrayList<Long>();
		for (long uid : uids) {
			int num = 0;
			for (NoticeType noticeType : NoticeType.values()){
				num += redisTemplate.opsForValue().get(
						RedisKeyGenerator.genUserNoticeNumKey(uid, noticeType));
			}
			if(num>0){
				try {
					noticeService.noticeUserUnReadNum(uid, num);
				} catch (AdminException e) {
					return ;
				}
			}
		}

	}

}
