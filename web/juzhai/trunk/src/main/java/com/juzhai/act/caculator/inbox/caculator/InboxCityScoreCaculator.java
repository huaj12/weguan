package com.juzhai.act.caculator.inbox.caculator;

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
	public long calculate(long srcUid, long destUid, long actId) {
		long srcUserCity = profileService.getUserCityFromCache(srcUid);
		long destUserCity = profileService.getUserCityFromCache(destUid);
		return srcUserCity == destUserCity ? CITY_REWARD_TIME : 0;
	}

}
