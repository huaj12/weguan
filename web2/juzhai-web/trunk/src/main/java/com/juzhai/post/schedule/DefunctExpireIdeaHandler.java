package com.juzhai.post.schedule;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.juzhai.core.schedule.AbstractScheduleHandler;
import com.juzhai.post.service.IIdeaService;

/**
 * 好主意橱窗排序
 * 
 * @author kooks
 * 
 */
@Component
public class DefunctExpireIdeaHandler extends AbstractScheduleHandler {
	@Autowired
	private IIdeaService ideaService;

	@Override
	protected void doHandle() {
		ideaService.defunctExpireIdea();
	}

}
