package com.juzhai.act.schedule;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.juzhai.act.service.ICategoryService;
import com.juzhai.core.schedule.AbstractScheduleHandler;

@Component
public class ShowCategoryActListHandler extends AbstractScheduleHandler {

	@Autowired
	private ICategoryService categoryService;

	@Override
	protected void doHandle() {
		categoryService.updateCategoryActList();
	}
}
