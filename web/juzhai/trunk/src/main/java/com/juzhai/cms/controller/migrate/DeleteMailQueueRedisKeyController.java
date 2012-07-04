package com.juzhai.cms.controller.migrate;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.juzhai.core.mail.bean.Mail;

@Controller
@RequestMapping("/cms")
public class DeleteMailQueueRedisKeyController {
	private final Log log = LogFactory.getLog(getClass());
	@Autowired
	private RedisTemplate<String, Mail> redisTemplate;

	@ResponseBody
	@RequestMapping(value = "delMailQueueRedisKey")
	public String migDoubanUser(HttpServletRequest request, String type) {
		log.error("start...");
		String key = null;
		if (StringUtils.isEmpty(type)) {
			key = "mailQueue";
		} else {
			key = type + ".mailQueue";
		}
		redisTemplate.delete(key);
		log.error("end...");
		return "success";
	}
}
