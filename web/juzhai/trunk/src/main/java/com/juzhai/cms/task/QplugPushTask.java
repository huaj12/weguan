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
			CmsQplugPushUserController.isNewUserpushruning = true;
			CmsQplugPushUserController.isNewUserPushStop = false;
			key = RedisKeyGenerator.genQplugPushNewUserKey();
		} else {
			CmsQplugPushUserController.isOldUserpushruning = true;
			CmsQplugPushUserController.isOldUserPushStop = false;
			key = RedisKeyGenerator.genQplugPushOldUserKey();
		}
		Long size = redisTemplate.opsForSet().size(key);
		if (size == null) {
			size = 0l;
		}
		//TODO (review) 不需要用size，while＋判断拿出来的openId是否为null来结束循环
		for (int i = 0; i < size; i++) {
			String openid = redisTemplate.opsForSet().pop(key);
			//TODO (review) text完全能提出循环
			if (StringUtils.isEmpty(text)) {
				text = messageSource.getMessage(
						NoticeQplugUserTemplate.NOTICE_QPLUG_USER_TEXT_DEFAULT
								.getName(), null, Locale.SIMPLIFIED_CHINESE);
			}
			cmsTaskExecutor.submit(new QplugSendTask(openid, text,
					noticeService));
			if (i != 0 && i % count == 0) {
				try {
					Thread.sleep(1000 * 60);
				} catch (InterruptedException e) {
				}
			}
			if ("new".equals(type)) {
				if (CmsQplugPushUserController.isNewUserPushStop) {
					CmsQplugPushUserController.isNewUserpushruning = false;
					break;
				}
			} else {
				if (CmsQplugPushUserController.isOldUserPushStop) {
					CmsQplugPushUserController.isOldUserpushruning = false;
					break;
				}
			}
		}
		if ("new".equals(type)) {
			CmsQplugPushUserController.isNewUserpushruning = false;
			CmsQplugPushUserController.isNewUserPushStop = true;
		} else {
			CmsQplugPushUserController.isOldUserpushruning = false;
			CmsQplugPushUserController.isOldUserPushStop = true;
		}

	}
}
