package com.juzhai.index.schedule;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.juzhai.act.service.ICategoryService;
import com.juzhai.core.schedule.AbstractScheduleHandler;

//@Component
public class UpdateShowCategoriesHandler extends AbstractScheduleHandler {

	private final Log log = LogFactory.getLog(getClass());

	@Autowired
	private ICategoryService categoryService;

	@Override
	protected void doHandle() {
		if (log.isDebugEnabled()) {
			log.debug("start update show categories");
		}
		categoryService.updateShowCategories();
	}

}
