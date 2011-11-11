package com.juzhai.home.schedule;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.juzhai.act.service.IUserActService;
import com.juzhai.core.schedule.AbstractScheduleHandler;
import com.juzhai.home.bean.ReadFeed;
import com.juzhai.home.service.IInboxService;

@Component
public class GetBackFeedHandler extends AbstractScheduleHandler {

	@Autowired
	private IInboxService inboxService;
	@Autowired
	private IUserActService userActService;
	@Value("${nill.back.inbox.count.limit}")
	private int nillBackInboxCountLimit = 100;
	@Value("${nill.feed.back.punish.time.seconds}")
	private long nillFeedBackPunishTimeSeconds = 259200;

	@Override
	protected void doHandle() {
		List<ReadFeed> backFeedList = inboxService.listGetBackFeed();
		for (ReadFeed readFeed : backFeedList) {
			if (userActService.hasAct(readFeed.getSenderId(),
					readFeed.getActId())) {
				Date date = null;
				switch (readFeed.getType()) {
				case WANT:
					date = new Date();
					break;
				case NILL:
					if (inboxService.inboxCount(readFeed.getReaderUid()) < nillBackInboxCountLimit) {
						date = new Date(System.currentTimeMillis()
								- nillFeedBackPunishTimeSeconds);
					}
					break;
				}
				inboxService.push(readFeed.getReaderUid(),
						readFeed.getSenderId(), readFeed.getActId(), date);
			}
		}
	}
}
