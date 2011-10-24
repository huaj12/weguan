package com.juzhai.act.caculator.inbox.caculator;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.juzhai.act.caculator.IScoreCaculator;
import com.juzhai.act.caculator.annotation.ScorePoint;
import com.juzhai.act.caculator.annotation.ScorePoint.ScoreType;
import com.juzhai.passport.bean.ProfileCache;
import com.juzhai.passport.service.IProfileService;

@Service
@ScorePoint({ ScoreType.INBOX })
public class InboxGenderScoreCaculator implements IScoreCaculator {

	@Autowired
	private IProfileService profileService;
	@Value("${female.reward.time.seconds}")
	private long femaleRewardTimeSeconds = 10800;

	@Override
	public long calculate(long srcUid, long destUid, long actId, Date time) {
		ProfileCache profile = profileService.getProfileCacheByUid(srcUid);
		if (null != profile && null != profile.getGender()
				&& profile.getGender() == 0) {
			return femaleRewardTimeSeconds;
		} else {
			return 0;
		}
	}

}
