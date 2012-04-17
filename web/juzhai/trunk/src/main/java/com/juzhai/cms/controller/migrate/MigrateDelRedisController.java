package com.juzhai.cms.controller.migrate;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.juzhai.core.cache.RedisKeyGenerator;
import com.juzhai.core.dao.Limit;
import com.juzhai.passport.mapper.ProfileMapper;
import com.juzhai.passport.model.Profile;
import com.juzhai.passport.model.ProfileExample;

@Controller
@RequestMapping("/cms")
public class MigrateDelRedisController {

	private final Log log = LogFactory.getLog(getClass());

	@Autowired
	private RedisTemplate<String, Object> redisTemplate;
	@Autowired
	private ProfileMapper profileMapper;

	@RequestMapping(value = "/redis/deleteLikeUser", method = RequestMethod.GET)
	@ResponseBody
	public String redisDelete(HttpServletRequest request, String keys) {
		log.error("delete like user redis start");
		int firstResult = 0;
		int maxResults = 200;
		ProfileExample example = new ProfileExample();
		example.setOrderByClause("uid asc");
		while (true) {
			example.setLimit(new Limit(firstResult, maxResults));
			List<Profile> list = profileMapper.selectByExample(example);
			if (CollectionUtils.isEmpty(list)) {
				break;
			}
			for (Profile profile : list) {
				String key = RedisKeyGenerator.genGuessYouLikeUsersKey(profile
						.getUid());
				redisTemplate.delete(key);
				if (profile.getUid() % 100 == 0) {
					log.error("delete to user[" + profile.getUid() + "]");
				}
			}
			firstResult += maxResults;
		}
		log.error("delete like user redis end");
		return "success";
	}
}
