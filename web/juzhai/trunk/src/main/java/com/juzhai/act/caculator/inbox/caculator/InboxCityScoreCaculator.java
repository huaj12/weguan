package com.juzhai.act.caculator.inbox.caculator;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.juzhai.act.caculator.IScoreCaculator;
import com.juzhai.act.caculator.annotation.ScorePoint;
import com.juzhai.act.caculator.annotation.ScorePoint.ScoreType;
import com.juzhai.passport.service.IProfileService;

@Service
@ScorePoint({ ScoreType.INBOX })
public class InboxCityScoreCaculator implements IScoreCaculator {

	private static final int CITY_REWARD_TIME = 3 * 24 * 60 * 60;

	@Autowired
	private IProfileService profileService;

	@Override
	public long calculate(long srcUid, long destUid, long actId, Date time) {
		if (profileService.isMaybeSameCity(srcUid, destUid) > 0) {
			return CITY_REWARD_TIME;
		} else {
			return 0;
		}
	}

}
