package com.juzhai.index.schedule;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.juzhai.core.schedule.AbstractScheduleHandler;
import com.juzhai.post.service.IRecommendIdeaService;
import com.juzhai.post.service.IRecommendPostService;

@Component
public class RecommendDataHandler extends AbstractScheduleHandler {
	private final Log log = LogFactory.getLog(getClass());
	@Autowired
	private IRecommendPostService recommendPostService;
	@Autowired
	private IRecommendIdeaService recommendIdeaService;

	@Override
	protected void doHandle() {
		try {
			recommendPostService.updateRecommendPost();
		} catch (Exception e) {
			log.error("Recommend Post Handler is error." + e);
		}
		try {
			recommendIdeaService.updateRecommendIdea();
		} catch (Exception e) {
			log.error("Recommend idea Handler is error." + e);
		}
	}

}
