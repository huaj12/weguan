package com.juzhai.act.caculator.inbox;

import org.springframework.stereotype.Service;

import com.juzhai.act.annotation.ScorePoint;
import com.juzhai.act.annotation.ScorePoint.ScoreType;
import com.juzhai.act.caculator.IScoreCaculator;

@Service
@ScorePoint({ ScoreType.INBOX })
public class InboxCityScoreCaculator implements IScoreCaculator {

	@Override
	public int calculate(long srcUid, long destUid, long actId) {
		// TODO Auto-generated method stub
		return 0;
	}

}
