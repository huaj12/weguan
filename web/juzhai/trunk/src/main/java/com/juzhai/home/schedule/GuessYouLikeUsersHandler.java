package com.juzhai.home.schedule;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.juzhai.core.dao.Limit;
import com.juzhai.core.schedule.AbstractScheduleHandler;
import com.juzhai.home.service.IGuessYouService;
import com.juzhai.passport.mapper.ProfileMapper;
import com.juzhai.passport.model.Profile;
import com.juzhai.passport.model.ProfileExample;

@Component
public class GuessYouLikeUsersHandler extends AbstractScheduleHandler {

	@Autowired
	private ProfileMapper profileMapper;
	@Autowired
	private IGuessYouService guessYouService;

	@Override
	protected void doHandle() {
		int firstResult = 0;
		int maxResults = 200;
		ProfileExample example = new ProfileExample();
		example.createCriteria().andCityGreaterThan(0L);
		example.setOrderByClause("create_time asc");
		while (true) {
			example.setLimit(new Limit(firstResult, maxResults));
			List<Profile> list = profileMapper.selectByExample(example);
			if (CollectionUtils.isEmpty(list)) {
				break;
			}
			for (Profile profile : list) {
				guessYouService.clearRescueUsers(profile.getUid());
				guessYouService.updateLikeUsers(profile.getUid());
			}
			firstResult += maxResults;
		}
	}
}
