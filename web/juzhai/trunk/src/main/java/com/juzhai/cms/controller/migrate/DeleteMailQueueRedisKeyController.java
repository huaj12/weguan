package com.juzhai.cms.controller.migrate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/cms")
public class DeleteMailQueueRedisKeyController {
	@Autowired
	private RedisTemplate<String, Object> redisTemplate;
}
