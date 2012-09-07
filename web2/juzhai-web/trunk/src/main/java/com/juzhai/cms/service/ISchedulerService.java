package com.juzhai.cms.service;

import org.quartz.JobDetail;
import org.quartz.SchedulerException;
import org.quartz.Trigger;

public interface ISchedulerService {
	/**
	 * 开始任务
	 * 
	 * @param trigger
	 */
	void startJob(Trigger trigger, JobDetail jobDetail)
			throws SchedulerException;

	/**
	 * 结束任务
	 * 
	 * @param trigger
	 */
	void stopJob(Trigger trigger) throws SchedulerException;
}
