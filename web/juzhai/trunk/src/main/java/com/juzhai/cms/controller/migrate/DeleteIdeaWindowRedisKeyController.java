package com.juzhai.cms.controller.migrate;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.juzhai.core.cache.RedisKeyGenerator;

@Controller
@RequestMapping("/cms")
public class DeleteIdeaWindowRedisKeyController {
	private final Log log = LogFactory.getLog(getClass());

	@Autowired
	private RedisTemplate<String, Object> redisTemplate;

	@ResponseBody
	@RequestMapping(value = "delIdeaWindowRedisKey")
	public String migDoubanUser(HttpServletRequest request) {
		log.error("start...");
		String key = RedisKeyGenerator.genIdeaWindowSortKey();
		redisTemplate.delete(key);
		log.error("end...");
		return "success";
	}
}
