package com.juzhai.post.schedule;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.juzhai.core.schedule.AbstractScheduleHandler;
import com.juzhai.post.service.IIdeaService;

@Component
public class IdeaShowHandler extends AbstractScheduleHandler {

	@Autowired
	private IIdeaService ideaService;

	@Override
	protected void doHandle() {
		ideaService.updateRecentTopIdeas();
	}

}
