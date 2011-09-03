package com.juzhai.core.schedule;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public abstract class AbstractScheduleHandler {

	protected final Log log = LogFactory.getLog(getClass());

	public void handle() {
		if (log.isDebugEnabled()) {
			log.debug("start schedule[" + getClass() + "].");
		}
		doHandle();
		if (log.isDebugEnabled()) {
			log.debug("end schedule[" + getClass() + "].");
		}
	}

	protected abstract void doHandle();
}
