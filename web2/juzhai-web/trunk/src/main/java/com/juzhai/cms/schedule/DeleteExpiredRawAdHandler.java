package com.juzhai.cms.schedule;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.juzhai.cms.service.IRawAdService;
import com.juzhai.core.schedule.AbstractScheduleHandler;

@Component
public class DeleteExpiredRawAdHandler extends AbstractScheduleHandler {
	@Autowired
	private IRawAdService rawAdService;

	@Override
	protected void doHandle() {
		try {
			rawAdService.removeAllExpiredRawAd();
		} catch (Exception e) {
			log.error("delete expired raw ad handler is error");
		}
	}

}
