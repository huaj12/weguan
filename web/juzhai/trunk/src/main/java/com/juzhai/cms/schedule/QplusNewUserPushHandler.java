package com.juzhai.cms.schedule;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.scheduling.quartz.CronTriggerBean;
import org.springframework.stereotype.Component;

import com.juzhai.cms.controller.CmsQplusPushUserController;
import com.juzhai.cms.service.ISchedulerService;
import com.juzhai.cms.task.QplusSendTask;
import com.juzhai.core.cache.RedisKeyGenerator;
import com.juzhai.core.schedule.AbstractScheduleHandler;
import com.juzhai.notice.service.INoticeService;
import com.juzhai.post.model.Idea;
import com.juzhai.post.service.IIdeaService;

@Component
public class QplusNewUserPushHandler extends AbstractScheduleHandler {
	@Autowired
	private ThreadPoolTaskExecutor cmsTaskExecutor;
	@Value("${qplus.minute.new.user.push.count}")
	private int qplusMinuteNewUserPushCount;
	@Autowired
	private RedisTemplate<String, String> redisTemplate;
	@Autowired
	private INoticeService noticeService;
	@Autowired
	private CronTriggerBean qplusNewUserPushTrigger;
	@Autowired
	private ISchedulerService schedulerService;
	@Autowired
	private IIdeaService ideaService;
	private String link = "http://www.51juzhai.com/showideas";

	@Override
	protected void doHandle() {
		String key = RedisKeyGenerator.genQplusPushNewUserKey();
		if (redisTemplate.opsForSet().size(key) == null
				|| redisTemplate.opsForSet().size(key) == 0) {
			try {
				schedulerService.stopJob(qplusNewUserPushTrigger);
				CmsQplusPushUserController.qplusNewUserPushisRunning = false;
				CmsQplusPushUserController.qplusNewUserPushText = null;
			} catch (Exception e) {
				log.error("stop qplus push is error ", e);
			}
		} else {
			String text = CmsQplusPushUserController.qplusNewUserPushText;
			if (StringUtils.isEmpty(text)) {
				Idea idea = ideaService.getNewWindowIdea();
				text = idea.getContent();
				CmsQplusPushUserController.qplusNewUserPushText = text;
			}
			for (int i = 0; i < qplusMinuteNewUserPushCount; i++) {
				String openid = redisTemplate.opsForSet().pop(key);
				if (StringUtils.isNotEmpty(openid)) {
					cmsTaskExecutor.submit(new QplusSendTask(openid, text,
							link, noticeService));
				}
			}
		}
	}
}
