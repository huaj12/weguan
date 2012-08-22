package com.juzhai.cms.controller.migrate;

import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.juzhai.core.cache.RedisKeyGenerator;
import com.juzhai.post.exception.InputRecommendException;
import com.juzhai.post.model.Idea;
import com.juzhai.post.service.IRecommendIdeaService;

@Controller
@RequestMapping("/cms")
public class MigrateIndexIdeaController {
	private final Log log = LogFactory.getLog(getClass());

	@Autowired
	private RedisTemplate<String, Idea> redisTemplate;
	@Autowired
	private IRecommendIdeaService recommendIdeaService;

	@ResponseBody
	@RequestMapping(value = "migIndexIdea")
	public String migDoubanUser(HttpServletRequest request) {
		log.error("start...");
		String key = RedisKeyGenerator.genIndexIdeaKey();
		Set<Idea> ideas = redisTemplate.opsForSet().members(key);
		redisTemplate.delete(key);
		if (CollectionUtils.isNotEmpty(ideas)) {
			for (Idea idea : ideas) {
				try {
					recommendIdeaService.addIndexIdea(idea.getId());
				} catch (InputRecommendException e) {
					log.error("migIndexIdea add index idea is error."
							+ e.getErrorCode());
				}
			}
		}
		log.error("end...");
		return "success";
	}
}
