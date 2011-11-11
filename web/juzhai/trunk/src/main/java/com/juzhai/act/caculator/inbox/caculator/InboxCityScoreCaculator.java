package com.juzhai.act.caculator.inbox.caculator;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import com.juzhai.act.caculator.IScoreCaculator;
import com.juzhai.passport.service.IProfileService;

//@Service
//@ScorePoint({ ScoreType.INBOX })
public class InboxCityScoreCaculator implements IScoreCaculator {

	@Autowired
	private IProfileService profileService;
	@Value("${city.reward.time.seconds}")
	private long cityRewardTimeSeconds = 259200;

	@Override
	public long calculate(long srcUid, long destUid, long actId, Date time) {
		if (profileService.isMaybeSameCity(srcUid, destUid) > 0) {
			return cityRewardTimeSeconds;
		} else {
			return 0;
		}
	}

}
