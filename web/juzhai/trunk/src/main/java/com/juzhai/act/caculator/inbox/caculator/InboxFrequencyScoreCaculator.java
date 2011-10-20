package com.juzhai.act.caculator.inbox.caculator;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.juzhai.act.caculator.IScoreCaculator;
import com.juzhai.act.caculator.annotation.ScorePoint;
import com.juzhai.act.caculator.annotation.ScorePoint.ScoreType;
import com.juzhai.home.service.IInboxService;

@Service
@ScorePoint({ ScoreType.INBOX })
public class InboxFrequencyScoreCaculator implements IScoreCaculator {

	@Autowired
	private IInboxService inboxService;
	@Value("${per.punish.time.seconds}")
	private long perPunishTimeSeconds = 3600;

	@Override
	public long calculate(long srcUid, long destUid, long actId, Date time) {
		long lastTime = inboxService.getLastPushTime(srcUid, destUid);
		if (0 >= lastTime) {
			inboxService.clearPunishTimes(srcUid, destUid);
			return 0L;
		} else {
			long punishTimes = inboxService
					.increasePunishTimes(srcUid, destUid);
			return -punishTimes * perPunishTimeSeconds;
		}
	}
}
