package com.juzhai.cms.schedule;

import java.util.Locale;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.scheduling.quartz.CronTriggerBean;
import org.springframework.stereotype.Component;

import com.juzhai.cms.controller.CmsQplusPushUserController;
import com.juzhai.cms.service.ISchedulerService;
import com.juzhai.cms.task.QplusSendTask;
import com.juzhai.core.cache.RedisKeyGenerator;
import com.juzhai.core.schedule.AbstractScheduleHandler;
import com.juzhai.notice.bean.NoticeQplusUserTemplate;
import com.juzhai.notice.service.INoticeService;

@Component
public class QplusNewUserPushHandler extends AbstractScheduleHandler {
	@Autowired
	private ThreadPoolTaskExecutor cmsTaskExecutor;
	@Autowired
	private MessageSource messageSource;
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

	@Override
	protected void doHandle() {
		String key = RedisKeyGenerator.genQplusPushNewUserKey();
		if (redisTemplate.opsForSet().size(key) == null
				|| redisTemplate.opsForSet().size(key) == 0) {
			try {
				schedulerService.stopJob(qplusNewUserPushTrigger);
				CmsQplusPushUserController.qplusNewUserPushisRunning = false;
			} catch (Exception e) {
				log.error("stop qplus push is error ", e);
			}
		}
		String text = CmsQplusPushUserController.qplusNewUserPushText;
		if (StringUtils.isEmpty(text)) {
			text = messageSource.getMessage(
					NoticeQplusUserTemplate.NOTICE_QPLUS_USER_TEXT_DEFAULT
							.getName(), null, Locale.SIMPLIFIED_CHINESE);
		}
		String link = CmsQplusPushUserController.qplusNewUserPushLink;
		if (StringUtils.isEmpty(link)) {
			link = messageSource.getMessage(
					NoticeQplusUserTemplate.NOTICE_QPLUS_USER_LINK_DEFAULT
							.getName(), null, Locale.SIMPLIFIED_CHINESE);
		}
		for (int i = 0; i < qplusMinuteNewUserPushCount; i++) {
			String openid = redisTemplate.opsForSet().pop(key);
			if (StringUtils.isNotEmpty(openid)) {
				cmsTaskExecutor.submit(new QplusSendTask(openid, text, link,
						noticeService));
			}
		}
	}

}
