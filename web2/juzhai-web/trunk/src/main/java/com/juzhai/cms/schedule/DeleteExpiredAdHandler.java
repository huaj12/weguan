package com.juzhai.cms.schedule;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.juzhai.core.schedule.AbstractScheduleHandler;
import com.juzhai.post.service.IAdService;

@Component
public class DeleteExpiredAdHandler extends AbstractScheduleHandler {
	@Autowired
	private IAdService adService;
	private final Log log = LogFactory.getLog(getClass());

	@Override
	protected void doHandle() {
		try {
			adService.removeAllExpiredAd();
		} catch (Exception e) {
			log.error("delete Expired Ad Handler is error");
		}
	}

}
