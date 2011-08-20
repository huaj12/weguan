package com.juzhai.act.caculator.inbox;

import org.springframework.stereotype.Service;

import com.juzhai.act.caculator.IScoreCaculator;
import com.juzhai.act.caculator.annotation.ScorePoint;
import com.juzhai.act.caculator.annotation.ScorePoint.ScoreType;

@Service
@ScorePoint({ ScoreType.INBOX })
public class InboxCityScoreCaculator implements IScoreCaculator {

	@Override
	public int calculate(long srcUid, long destUid, long actId) {
		// TODO Auto-generated method stub
		return 0;
	}

}
