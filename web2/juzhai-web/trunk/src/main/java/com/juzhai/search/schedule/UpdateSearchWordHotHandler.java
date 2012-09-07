package com.juzhai.search.schedule;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.juzhai.core.schedule.AbstractScheduleHandler;
import com.juzhai.search.service.ISearchHotService;

@Component
public class UpdateSearchWordHotHandler extends AbstractScheduleHandler {
	@Autowired
	private ISearchHotService searchHotService;

	@Override
	protected void doHandle() {
		try {
			searchHotService.updateWordHot();
		} catch (Exception e) {
			log.error("update search word hot iserror", e);
		}
	}
}
