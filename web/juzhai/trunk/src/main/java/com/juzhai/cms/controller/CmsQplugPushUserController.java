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
	public static boolean isOldUserPushStop = true;
	public static boolean isOldUserpushruning = false;
	public static boolean isNewUserPushStop = true;
	public static boolean isNewUserpushruning = false;
	public static boolean isInitRuning = false;
	public static boolean isInitStop = false;

	@RequestMapping(value = "/qplug/initPushUser", method = RequestMethod.POST)
	@ResponseBody
	public synchronized AjaxResult initPushUser() {
		AjaxResult ajaxResult = new AjaxResult();
		if (isInitRuning) {
			ajaxResult.setErrorInfo("init runing");
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
		//TODO (review) 判断是否在执行任务，为什么放在load文件之后？
		// 正在给老用户push
		if (!isOldUserPushStop && isOldUserpushruning) {
			ajaxResult.setErrorInfo("old user pushing");
			ajaxResult.setSuccess(false);
			return ajaxResult;
		}
		if (!isNewUserPushStop && isNewUserpushruning) {
			ajaxResult.setErrorInfo("new user pushing");
			ajaxResult.setSuccess(false);
			return ajaxResult;
		}
		isInitRuning = true;
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
		isInitRuning = false;
		//TODO (review) isInitStop需要更新吗？
		return ajaxResult;
	}

	@RequestMapping(value = "/qplug/push", method = RequestMethod.POST)
	@ResponseBody
	public synchronized AjaxResult push(String type, String text) {
		AjaxResult ajaxResult = new AjaxResult();
		if (isInitRuning) {
			ajaxResult.setErrorInfo("init runing");
			ajaxResult.setSuccess(false);
			return ajaxResult;
		}
		if ("new".equals(type)) {
			if (!isNewUserPushStop && isNewUserpushruning) {
				ajaxResult.setSuccess(false);
				ajaxResult.setErrorInfo("new User push runing");
				return ajaxResult;
			}
		} else {
			if (!isOldUserPushStop && isOldUserpushruning) {
				ajaxResult.setSuccess(false);
				ajaxResult.setErrorInfo("old User push runing");
				return ajaxResult;
			}
		}
		//TODO (review) 新的用户和老的用户，两个任务能同时进行？
		QplugPushTask task = new QplugPushTask(type, text,
				qplugMinutePushCount, cmsTaskExecutor, redisTemplate,
				noticeService, messageSource);
		Thread thread = new Thread(task);
		thread.start();
		//TODO (review) 任务执行中和任务结束的变量不更新为执行中？
		return ajaxResult;
	}

	@RequestMapping(value = "/qplug/stop", method = RequestMethod.POST)
	@ResponseBody
	public synchronized AjaxResult stop(String type) {
		if ("new".equals(type)) {
			isNewUserPushStop = true;
			isNewUserpushruning = false;
		} else {
			isOldUserPushStop = true;
			isOldUserpushruning = false;
		}
		return new AjaxResult();
	}

	@RequestMapping(value = "/qplug/stopInit", method = RequestMethod.GET)
	@ResponseBody
	public synchronized String stopInit() {
		isInitRuning = false;
		isInitStop = true;
		//TODO (review) 这个方法还有用吗？
		return "sucess";
	}

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
						isInitRuning, isNewUserpushruning, isOldUserpushruning,
						oldUserSize, newUserSize }, Locale.SIMPLIFIED_CHINESE);
		return content;
	}
}
