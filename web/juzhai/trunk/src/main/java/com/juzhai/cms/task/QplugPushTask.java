package com.juzhai.cms.task;

import java.util.Locale;

import org.apache.commons.lang.StringUtils;
import org.springframework.context.MessageSource;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import com.juzhai.cms.controller.CmsQplugPushUserController;
import com.juzhai.core.cache.RedisKeyGenerator;
import com.juzhai.notice.bean.NoticeQplugUserTemplate;
import com.juzhai.notice.service.INoticeService;

public class QplugPushTask implements Runnable {
	private String type;
	private String text;
	private ThreadPoolTaskExecutor cmsTaskExecutor;
	private RedisTemplate<String, String> redisTemplate;
	private INoticeService noticeService;
	private MessageSource messageSource;
	private int count;

	public QplugPushTask(String type, String text, int count,
			ThreadPoolTaskExecutor cmsTaskExecutor,
			RedisTemplate<String, String> redisTemplate,
			INoticeService noticeService, MessageSource messageSource) {
		this.type = type;
		this.cmsTaskExecutor = cmsTaskExecutor;
		this.redisTemplate = redisTemplate;
		this.noticeService = noticeService;
		this.messageSource = messageSource;
		this.text = text;
		this.count = count;
	}

	@Override
	public void run() {
		String key = null;
		if ("new".equals(type)) {
			key = RedisKeyGenerator.genQplugPushNewUserKey();
		} else {
			key = RedisKeyGenerator.genQplugPushOldUserKey();
		}
		if (StringUtils.isEmpty(text)) {
			text = messageSource.getMessage(
					NoticeQplugUserTemplate.NOTICE_QPLUG_USER_TEXT_DEFAULT
							.getName(), null, Locale.SIMPLIFIED_CHINESE);
		}
		int i = 0;
		while (true) {
			String openid = redisTemplate.opsForSet().pop(key);
			if (StringUtils.isEmpty(openid)) {
				break;
			}
			cmsTaskExecutor.submit(new QplugSendTask(openid, text,
					noticeService));
			// TODO (review) 这里总觉得逻辑有点问题，一会想
			if (i != 0 && i % count == 0) {
				try {
					Thread.sleep(1000 * 60);
				} catch (InterruptedException e) {
				}
			}
			// 由外部来停止任务
			if (CmsQplugPushUserController.isUserPushStop) {
				break;
			}
			i++;
		}
		CmsQplugPushUserController.isUserPushRunning = false;
		CmsQplugPushUserController.isUserPushStop = true;

	}
}
