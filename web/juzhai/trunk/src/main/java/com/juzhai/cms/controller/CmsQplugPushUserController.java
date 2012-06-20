package com.juzhai.cms.controller;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.quartz.CronTrigger;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.quartz.CronTriggerBean;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.juzhai.core.Constants;
import com.juzhai.core.cache.RedisKeyGenerator;
import com.juzhai.core.web.AjaxResult;

@Controller
@RequestMapping("/cms")
public class CmsQplugPushUserController {
	private final Log log = LogFactory.getLog(getClass());
	@Autowired
	private RedisTemplate<String, String> redisTemplate;
	@Autowired
	private CronTriggerBean qplugNewUserPushTrigger;
	@Autowired
	private Scheduler scheduler;
	@Autowired
	private MessageSource messageSource;
	public static String qplugNewUserPushText = "";
	public static String qplugOldUserPushText = "";
	public static boolean qplugNewUserPushisRunning = false;
	public static boolean qplugOldUserPushisRunning = false;

	@RequestMapping(value = "/qplug/import", method = RequestMethod.POST)
	public ModelAndView importConfig(HttpServletRequest request, Model model,
			@RequestParam("config") MultipartFile config, String type) {
		ModelMap mmap = new ModelMap();
		try {
			if (config == null || config.getInputStream() == null
					|| config.getSize() <= 0) {
				mmap.addAttribute("msg", "file is not null");
			} else {
				// 新创建
				if ("new".equals(type)) {
					redisTemplate.delete(RedisKeyGenerator
							.genQplugPushNewUserKey());
					redisTemplate.delete(RedisKeyGenerator
							.genQplugPushOldUserKey());
				}
				BufferedReader br = new BufferedReader(new InputStreamReader(
						config.getInputStream(), Constants.UTF8));
				String line;
				while ((line = br.readLine()) != null) {
					// 和判断是否注册。现在默认都是新用户
					boolean isNew = true;
					String key = null;
					if (isNew) {
						key = RedisKeyGenerator.genQplugPushNewUserKey();
					} else {
						key = RedisKeyGenerator.genQplugPushOldUserKey();
					}
					redisTemplate.opsForSet().add(key, line);
				}
			}
		} catch (Exception e) {
			mmap.addAttribute("msg", "upload file is error");
		}
		mmap.addAttribute("msg", "upload file is success");
		return new ModelAndView("redirect:/cms/qplug/show", mmap);
	}

	@RequestMapping(value = "/qplug/show", method = RequestMethod.GET)
	public String show(String msg, Model model) {
		model.addAttribute("msg", msg);
		return "/cms/qplug/show";
	}

	@RequestMapping(value = "/qplug/update/content", method = RequestMethod.POST)
	@ResponseBody
	public AjaxResult updateContent(String type, String text) {
		if ("new".equals(type)) {
			qplugNewUserPushText = text;
		} else {
			qplugOldUserPushText = text;
		}
		return new AjaxResult();
	}

	@RequestMapping(value = "/qplug/push/start", method = RequestMethod.POST)
	@ResponseBody
	public AjaxResult start(String type) {
		AjaxResult ajaxResult = new AjaxResult();
		try {
			if ("new".equals(type)) {
				scheduler.addJob(qplugNewUserPushTrigger.getJobDetail(), true);
				scheduler.scheduleJob(qplugNewUserPushTrigger);
				qplugNewUserPushisRunning = true;
			}
		} catch (SchedulerException e) {
			log.error("start qplug push is error type=" + type, e);
			ajaxResult.setSuccess(false);
		}
		return ajaxResult;
	}

	@RequestMapping(value = "/qplug/push/stop", method = RequestMethod.POST)
	@ResponseBody
	public AjaxResult stop(String type) {
		AjaxResult ajaxResult = new AjaxResult();
		try {
			if ("new".equals(type)) {
				CronTrigger cronTrigger = (CronTrigger) scheduler.getTrigger(
						qplugNewUserPushTrigger.getName(),
						Scheduler.DEFAULT_GROUP);
				if (cronTrigger != null) {
					scheduler.unscheduleJob(qplugNewUserPushTrigger.getName(),
							Scheduler.DEFAULT_GROUP);
					qplugNewUserPushisRunning = false;
				}
			}
		} catch (SchedulerException e) {
			log.error("stop qplug push is error type=" + type, e);
			ajaxResult.setSuccess(false);
		}
		return ajaxResult;
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
						oldUserSize, newUserSize, qplugNewUserPushText,
						qplugOldUserPushText, qplugNewUserPushisRunning,
						qplugOldUserPushisRunning }, Locale.SIMPLIFIED_CHINESE);
		return content;
	}
}
