package com.juzhai.home.schedule;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.juzhai.core.schedule.AbstractScheduleHandler;
import com.juzhai.home.bean.ReadFeed;
import com.juzhai.home.service.IInboxService;

@Component
public class GetBackFeedHandler extends AbstractScheduleHandler {

	@Autowired
	private IInboxService inboxService;
	@Value("${nill.feed.back.punish.time.seconds}")
	private long nillFeedBackPunishTimeSeconds = 259200;

	@Override
	protected void doHandle() {
		List<ReadFeed> backFeedList = inboxService.listGetBackFeed();
		for (ReadFeed readFeed : backFeedList) {
			// TODO 判断senderId还有没有这个兴趣
			// inboxService.push(receiverId, senderId, actId);
		}
	}
}
