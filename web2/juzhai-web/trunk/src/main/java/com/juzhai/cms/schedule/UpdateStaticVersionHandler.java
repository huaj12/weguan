package com.juzhai.cms.schedule;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.juzhai.cms.service.IStaticVersionService;
import com.juzhai.core.schedule.AbstractScheduleHandler;

@Component
public class UpdateStaticVersionHandler extends AbstractScheduleHandler {
	@Autowired
	private IStaticVersionService staticVersionService;

	@Override
	protected void doHandle() {
		staticVersionService.updateVersion();
	}

}
