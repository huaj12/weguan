package com.juzhai.cms.controller.migrate;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.juzhai.core.mail.bean.Mail;
import com.juzhai.core.mail.manager.FrequencyMailManager;
import com.juzhai.core.mail.manager.SimpleMailManager;

@Controller
@RequestMapping("/cms")
public class DeleteMailQueueRedisKeyController {
	private final Log log = LogFactory.getLog(getClass());
	@Autowired
	private RedisTemplate<String, Mail> redisTemplate;

	@ResponseBody
	@RequestMapping(value = "delMailQueueRedisKey")
	public String migDoubanUser(HttpServletRequest request, Integer type) {
		log.error("start...");
		String key = "mailQueue";
		if (type != null && type == 1) {
			key = SimpleMailManager.class.getSimpleName() + "." + key;
		} else if (type != null && type == 2) {
			key = FrequencyMailManager.class.getSimpleName() + "." + key;
		}
		redisTemplate.delete(key);
		log.error("end...");
		return "success";
	}
}
