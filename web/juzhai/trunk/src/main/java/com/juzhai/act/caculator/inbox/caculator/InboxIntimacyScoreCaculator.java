package com.juzhai.act.caculator.inbox.caculator;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.juzhai.act.caculator.IScoreCaculator;
import com.juzhai.act.caculator.annotation.ScorePoint;
import com.juzhai.act.caculator.annotation.ScorePoint.ScoreType;
import com.juzhai.passport.service.IFriendService;

@Service
@ScorePoint({ ScoreType.INBOX })
public class InboxIntimacyScoreCaculator implements IScoreCaculator {

	private static final int PER_INTIMACY_REWARD_TIME = 12 * 60 * 60;
	@Autowired
	private IFriendService friendService;

	@Override
	public long calculate(long srcUid, long destUid, long actId, Date time) {
		return friendService.getFriendIntimacy(srcUid, destUid)
				* PER_INTIMACY_REWARD_TIME;
	}

}
