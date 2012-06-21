package com.juzhai.cms.controller;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
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

import com.juzhai.cms.service.ISchedulerService;
import com.juzhai.core.Constants;
import com.juzhai.core.cache.RedisKeyGenerator;
import com.juzhai.core.web.AjaxResult;

@Controller
@RequestMapping("/cms")
public class CmsQplusPushUserController {
	private final Log log = LogFactory.getLog(getClass());
	@Autowired
	private RedisTemplate<String, String> redisTemplate;
	@Autowired
	private CronTriggerBean qplusNewUserPushTrigger;
	@Autowired
	private ISchedulerService schedulerService;
	@Autowired
	private MessageSource messageSource;
	public static String qplusNewUserPushText = "";
	public static String qplusOldUserPushText = "";
	public static boolean qplusNewUserPushisRunning = false;
	public static boolean qplusOldUserPushisRunning = false;

	@RequestMapping(value = "/qplus/import", method = RequestMethod.POST)
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
							.genQplusPushNewUserKey());
					redisTemplate.delete(RedisKeyGenerator
							.genQplusPushOldUserKey());
				}
				BufferedReader br = new BufferedReader(new InputStreamReader(
						config.getInputStream(), Constants.UTF8));
				String line;
				while ((line = br.readLine()) != null) {
					// 和判断是否注册。现在默认都是新用户
					boolean isNew = true;
					String key = null;
					if (isNew) {
						key = RedisKeyGenerator.genQplusPushNewUserKey();
					} else {
						key = RedisKeyGenerator.genQplusPushOldUserKey();
					}
					redisTemplate.opsForSet().add(key, line);
				}
				mmap.addAttribute("msg", "upload file is success");
			}
		} catch (Exception e) {
			mmap.addAttribute("msg", "upload file is error");
		}
		return new ModelAndView("redirect:/cms/qplus/show", mmap);
	}

	@RequestMapping(value = "/qplus/show", method = RequestMethod.GET)
	public String show(String msg, Model model) {
		model.addAttribute("msg", msg);
		return "/cms/qplus/show";
	}

	@RequestMapping(value = "/qplus/update/content", method = RequestMethod.POST)
	@ResponseBody
	public AjaxResult updateContent(String type, String text) {
		if ("new".equals(type)) {
			qplusNewUserPushText = text;
		} else {
			qplusOldUserPushText = text;
		}
		return new AjaxResult();
	}

	@RequestMapping(value = "/qplus/push/start", method = RequestMethod.POST)
	@ResponseBody
	public synchronized AjaxResult start(String type) {
		AjaxResult ajaxResult = new AjaxResult();
		try {
			if ("new".equals(type)) {
				schedulerService.startJob(qplusNewUserPushTrigger,
						qplusNewUserPushTrigger.getJobDetail());
				qplusNewUserPushisRunning = true;
			}
		} catch (SchedulerException e) {
			log.error("start qplus push is error type=" + type, e);
			ajaxResult.setSuccess(false);
		}
		return ajaxResult;
	}

	@RequestMapping(value = "/qplus/push/stop", method = RequestMethod.POST)
	@ResponseBody
	public synchronized AjaxResult stop(String type) {
		AjaxResult ajaxResult = new AjaxResult();
		try {
			if ("new".equals(type)) {
				schedulerService.stopJob(qplusNewUserPushTrigger);
				qplusNewUserPushisRunning = false;
			}
		} catch (SchedulerException e) {
			log.error("stop qplus push is error type=" + type, e);
			ajaxResult.setSuccess(false);
		}
		return ajaxResult;
	}

	@RequestMapping(value = "/qplus/state", method = RequestMethod.GET)
	@ResponseBody
	public String getState() {
		Long newUserSize = redisTemplate.opsForSet().size(
				RedisKeyGenerator.genQplusPushNewUserKey());
		Long oldUserSize = redisTemplate.opsForSet().size(
				RedisKeyGenerator.genQplusPushOldUserKey());
		String content = messageSource.getMessage(
				"notice.qplus.user.send.state.text", new Object[] {
						oldUserSize, newUserSize, qplusNewUserPushText,
						qplusOldUserPushText, qplusNewUserPushisRunning,
						qplusOldUserPushisRunning }, Locale.SIMPLIFIED_CHINESE);
		return content;
	}
}
