package com.juzhai.home.schedule;

import org.springframework.beans.factory.annotation.Autowired;

import com.juzhai.core.schedule.AbstractScheduleHandler;
import com.juzhai.passport.mapper.ProfileMapper;

public class GuessYouLikeUsersHandler extends AbstractScheduleHandler {

	@Autowired
	private ProfileMapper profileMapper;

	@Override
	protected void doHandle() {
		// TODO Auto-generated method stub

	}

}
