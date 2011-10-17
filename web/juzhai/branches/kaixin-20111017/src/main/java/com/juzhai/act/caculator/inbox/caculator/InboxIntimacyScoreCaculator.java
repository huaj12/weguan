package com.juzhai.act.caculator.inbox.caculator;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.juzhai.act.caculator.IScoreCaculator;
import com.juzhai.act.caculator.annotation.ScorePoint;
import com.juzhai.act.caculator.annotation.ScorePoint.ScoreType;
import com.juzhai.passport.service.IFriendService;

@Service
@ScorePoint({ ScoreType.INBOX })
public class InboxIntimacyScoreCaculator implements IScoreCaculator {

	@Autowired
	private IFriendService friendService;
	@Value("${per.intimacy.reward.time.seconds}")
	private long perIntimacyRewardTimeSeconds = 43200;

	@Override
	public long calculate(long srcUid, long destUid, long actId, Date time) {
		return friendService.getFriendIntimacy(srcUid, destUid)
				* perIntimacyRewardTimeSeconds;
	}

}
