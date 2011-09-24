package com.juzhai.act.caculator.inbox.caculator;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.juzhai.act.caculator.IScoreCaculator;
import com.juzhai.act.caculator.annotation.ScorePoint;
import com.juzhai.act.caculator.annotation.ScorePoint.ScoreType;
import com.juzhai.act.service.IUserActService;

@Service
@ScorePoint({ ScoreType.INBOX })
public class InboxActScoreCaculator implements IScoreCaculator {

	private static final int ACT_REWARD_TIME = 3 * 24 * 60 * 60;

	@Autowired
	private IUserActService userActService;

	@Override
	public long calculate(long srcUid, long destUid, long actId, Date time) {
		if (userActService.isInterested(destUid, actId)) {
			return ACT_REWARD_TIME;
		} else {
			return 0;
		}
	}

}
