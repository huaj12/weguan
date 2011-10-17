package com.juzhai.act.caculator.inbox.caculator;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.juzhai.act.caculator.IScoreCaculator;
import com.juzhai.act.caculator.annotation.ScorePoint;
import com.juzhai.act.caculator.annotation.ScorePoint.ScoreType;
import com.juzhai.act.service.IUserActService;

@Service
@ScorePoint({ ScoreType.INBOX })
public class InboxActScoreCaculator implements IScoreCaculator {

	@Autowired
	private IUserActService userActService;
	@Value("${act.reward.time.seconds}")
	private long actRewardTimeSeconds = 259200;

	@Override
	public long calculate(long srcUid, long destUid, long actId, Date time) {
		if (userActService.isInterested(destUid, actId)) {
			return actRewardTimeSeconds;
		} else {
			return 0;
		}
	}

}
