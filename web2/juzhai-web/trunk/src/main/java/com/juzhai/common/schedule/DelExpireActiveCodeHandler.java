package com.juzhai.common.schedule;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.juzhai.common.service.IActiveCodeService;
import com.juzhai.core.schedule.AbstractScheduleHandler;

@Component
public class DelExpireActiveCodeHandler extends AbstractScheduleHandler {

	@Autowired
	private IActiveCodeService activeCodeService;

	@Override
	protected void doHandle() {
		activeCodeService.delExpired();
	}
}
