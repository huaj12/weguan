package com.juzhai.cms.service.impl;

import org.quartz.CronTrigger;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.juzhai.cms.service.ISchedulerService;

@Service
public class SchedulerService implements ISchedulerService {
	@Autowired
	private Scheduler scheduler;

	@Override
	public void startJob(Trigger trigger, JobDetail jobDetail)
			throws SchedulerException {
		CronTrigger cronTrigger = (CronTrigger) scheduler.getTrigger(
				trigger.getName(), Scheduler.DEFAULT_GROUP);
		if (cronTrigger == null) {
			scheduler.addJob(jobDetail, true);
			scheduler.scheduleJob(trigger);
		}

	}

	@Override
	public void stopJob(Trigger trigger) throws SchedulerException {
		CronTrigger cronTrigger = (CronTrigger) scheduler.getTrigger(
				trigger.getName(), Scheduler.DEFAULT_GROUP);
		if (cronTrigger != null) {
			scheduler.unscheduleJob(trigger.getName(), Scheduler.DEFAULT_GROUP);
		}

	}

}
