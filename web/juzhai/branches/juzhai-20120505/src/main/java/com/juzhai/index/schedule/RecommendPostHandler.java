package com.juzhai.index.schedule;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.juzhai.core.schedule.AbstractScheduleHandler;
import com.juzhai.post.service.IRecommendPostService;

@Component
public class RecommendPostHandler extends AbstractScheduleHandler {
	private final Log log = LogFactory.getLog(getClass());
	@Autowired
	private IRecommendPostService recommendPostService;

	@Override
	protected void doHandle() {
		try {
			recommendPostService.updateRecommendPost();
		} catch (Exception e) {
			log.error("RecommendPostHandler is error." + e);
		}
	}

}
