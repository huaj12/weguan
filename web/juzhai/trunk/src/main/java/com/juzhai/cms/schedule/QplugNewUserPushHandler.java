package com.juzhai.cms.schedule;

import java.util.Locale;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

import com.juzhai.cms.controller.CmsQplugPushUserController;
import com.juzhai.cms.task.QplugSendTask;
import com.juzhai.core.cache.RedisKeyGenerator;
import com.juzhai.core.schedule.AbstractScheduleHandler;
import com.juzhai.notice.bean.NoticeQplugUserTemplate;
import com.juzhai.notice.service.INoticeService;

@Component
public class QplugNewUserPushHandler extends AbstractScheduleHandler {
	@Autowired
	private ThreadPoolTaskExecutor cmsTaskExecutor;
	@Autowired
	private MessageSource messageSource;
	@Value("${qplug.minute.new.user.push.count}")
	private int qplugMinuteNewUserPushCount;
	@Autowired
	private RedisTemplate<String, String> redisTemplate;
	@Autowired
	private INoticeService noticeService;

	@Override
	protected void doHandle() {
		String key = RedisKeyGenerator.genQplugPushNewUserKey();
		String text = CmsQplugPushUserController.qplugNewUserPushText;
		if (StringUtils.isEmpty(text)) {
			text = messageSource.getMessage(
					NoticeQplugUserTemplate.NOTICE_QPLUG_USER_TEXT_DEFAULT
							.getName(), null, Locale.SIMPLIFIED_CHINESE);
		}
		for (int i = 0; i < qplugMinuteNewUserPushCount; i++) {
			String openid = redisTemplate.opsForSet().pop(key);
			if (StringUtils.isNotEmpty(openid)) {
				cmsTaskExecutor.submit(new QplugSendTask(openid, text,
						noticeService));
			}
		}
	}

}
