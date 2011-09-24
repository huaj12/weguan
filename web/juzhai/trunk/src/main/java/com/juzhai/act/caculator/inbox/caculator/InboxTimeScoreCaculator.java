package com.juzhai.act.caculator.inbox.caculator;

import java.util.Date;

import org.springframework.stereotype.Service;

import com.juzhai.act.caculator.IScoreCaculator;
import com.juzhai.act.caculator.annotation.ScorePoint;
import com.juzhai.act.caculator.annotation.ScorePoint.ScoreType;

@Service
@ScorePoint({ ScoreType.INBOX })
public class InboxTimeScoreCaculator implements IScoreCaculator {

	@Override
	public long calculate(long srcUid, long destUid, long actId, Date time) {
		return (null == time ? System.currentTimeMillis() : time.getTime()) / 1000;
	}
}
