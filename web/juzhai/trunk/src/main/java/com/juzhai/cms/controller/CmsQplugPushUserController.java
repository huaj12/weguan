package com.juzhai.cms.controller;

import java.io.IOException;
import java.util.Locale;
import java.util.Map.Entry;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.juzhai.cms.task.QplugPushTask;
import com.juzhai.core.cache.RedisKeyGenerator;
import com.juzhai.core.web.AjaxResult;
import com.juzhai.notice.service.INoticeService;
import com.juzhai.passport.LockLevelConfig;

@Controller
@RequestMapping("/cms")
public class CmsQplugPushUserController {
	private final Log log = LogFactory.getLog(CmsQplugPushUserController.class);
	private final String QPLUGPUSHUSER_CONFIG_PATH = "properties/qplugpush.properties";
	@Autowired
	private RedisTemplate<String, String> redisTemplate;
	@Autowired
	private INoticeService noticeService;
	@Autowired
	private ThreadPoolTaskExecutor cmsTaskExecutor;
	@Autowired
	private MessageSource messageSource;
	@Value("${qplug.minute.push.count}")
	private int qplugMinutePushCount;
	public static boolean isUserPushStop = true;
	public static boolean isUserPushRunning = false;
	public static boolean isInitRunning = false;
	public static boolean isInitStop = false;

	@RequestMapping(value = "/qplug/initPushUser", method = RequestMethod.POST)
	@ResponseBody
	public synchronized AjaxResult initPushUser() {
		AjaxResult ajaxResult = new AjaxResult();
		if (isInitRunning) {
			ajaxResult.setErrorInfo("init runing");
			ajaxResult.setSuccess(false);
			return ajaxResult;
		}
		if (isUserPushRunning) {
			ajaxResult.setErrorInfo("user pushing");
			ajaxResult.setSuccess(false);
			return ajaxResult;
		}

		Properties config = new Properties();
		try {
			config.load(LockLevelConfig.class.getClassLoader()
					.getResourceAsStream(QPLUGPUSHUSER_CONFIG_PATH));
		} catch (IOException e) {
			ajaxResult.setErrorInfo("load qplugpush.properties is error");
			ajaxResult.setSuccess(false);
			return ajaxResult;
		}
		isInitRunning = true;
		isInitStop = false;
		redisTemplate.delete(RedisKeyGenerator.genQplugPushNewUserKey());
		redisTemplate.delete(RedisKeyGenerator.genQplugPushOldUserKey());

		for (Entry<Object, Object> entry : config.entrySet()) {
			if (isInitStop) {
				break;
			}
			String value = (String) entry.getKey();
			// 和判断是否注册。现在默认都是新用户
			boolean isNew = true;
			String key = null;
			if (isNew) {
				key = RedisKeyGenerator.genQplugPushNewUserKey();
			} else {
				key = RedisKeyGenerator.genQplugPushOldUserKey();
			}
			redisTemplate.opsForSet().add(key, value);
		}
		isInitRunning = false;
		// TODO (done) isInitStop需要更新吗？

		return ajaxResult;
	}

	@RequestMapping(value = "/qplug/push", method = RequestMethod.POST)
	@ResponseBody
	public synchronized AjaxResult push(String type, String text) {
		AjaxResult ajaxResult = new AjaxResult();
		if (isInitRunning) {
			ajaxResult.setErrorInfo("init runing");
			ajaxResult.setSuccess(false);
			return ajaxResult;
		}
		// TODO (done) 前面改过，没理解？告诉我为什么stop不能表示已经结束？
		if (isUserPushRunning) {
			ajaxResult.setSuccess(false);
			ajaxResult.setErrorInfo("User push runing");
			return ajaxResult;
		}
		// TODO (done) 要放在新线程执行前，告诉我为什么要放在前面
		isUserPushRunning = true;
		isUserPushStop = false;
		QplugPushTask task = new QplugPushTask(type, text,
				qplugMinutePushCount, cmsTaskExecutor, redisTemplate,
				noticeService, messageSource);
		Thread thread = new Thread(task);
		thread.start();
		return ajaxResult;
	}

	@RequestMapping(value = "/qplug/stop", method = RequestMethod.POST)
	@ResponseBody
	public synchronized AjaxResult stop(String type) {
		isUserPushStop = true;
		return new AjaxResult();
	}

	// @RequestMapping(value = "/qplug/stopInit", method = RequestMethod.GET)
	// @ResponseBody
	// public synchronized String stopInit() {
	// isInitRuning = false;
	// isInitStop = true;
	// return "sucess";
	// }

	@RequestMapping(value = "/qplug/show", method = RequestMethod.GET)
	public String show() {
		return "/cms/qplug/show";
	}

	@RequestMapping(value = "/qplug/state", method = RequestMethod.GET)
	@ResponseBody
	public String getState() {
		Long newUserSize = redisTemplate.opsForSet().size(
				RedisKeyGenerator.genQplugPushNewUserKey());
		Long oldUserSize = redisTemplate.opsForSet().size(
				RedisKeyGenerator.genQplugPushOldUserKey());
		String content = messageSource.getMessage(
				"notice.qplug.user.send.state.text", new Object[] {
						isInitRunning, isUserPushRunning, oldUserSize,
						newUserSize }, Locale.SIMPLIFIED_CHINESE);
		return content;
	}
}
