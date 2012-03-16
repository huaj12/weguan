package com.juzhai.cms.controller.migrate;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.juzhai.core.cache.RedisKeyGenerator;
import com.juzhai.core.dao.Limit;
import com.juzhai.passport.mapper.ProfileMapper;
import com.juzhai.passport.model.Profile;
import com.juzhai.passport.model.ProfileExample;
import com.juzhai.passport.service.IProfileService;
import com.juzhai.post.bean.VerifyType;
import com.juzhai.post.mapper.PostMapper;
import com.juzhai.post.model.Post;
import com.juzhai.post.model.PostExample;

//@Controller
@RequestMapping("/cms")
public class MigratePostController {

	@Autowired
	private ProfileMapper profileMapper;
	@Autowired
	private PostMapper postMapper;
	@Autowired
	private RedisTemplate<String, Long> redisTemplate;
	@Autowired
	private IProfileService profileService;

	@RequestMapping(value = "/migrateUserLatestPost", method = RequestMethod.GET)
	@ResponseBody
	public String migrateUserLatestPost(HttpServletRequest request) {
		int firstResult = 0;
		int maxResults = 200;
		ProfileExample example = new ProfileExample();
		example.setOrderByClause("uid asc");
		while (true) {
			example.setLimit(new Limit(firstResult, maxResults));
			List<Profile> profileList = profileMapper.selectByExample(example);
			if (CollectionUtils.isEmpty(profileList)) {
				break;
			}
			for (Profile profile : profileList) {
				redisTemplate.delete(RedisKeyGenerator
						.genUserLatestPostKey(profile.getUid()));

				PostExample postExample = new PostExample();
				postExample.createCriteria()
						.andCreateUidEqualTo(profile.getUid())
						.andVerifyTypeEqualTo(VerifyType.QUALIFIED.getType())
						.andDefunctEqualTo(false);
				postExample.setLimit(new Limit(0, 1));
				postExample.setOrderByClause("create_time desc");
				List<Post> list = postMapper.selectByExample(postExample);
				if (CollectionUtils.isNotEmpty(list)) {
					Post post = list.get(0);
					// 更新
					profileService.updateLastUpdateTime(profile.getUid(),
							post.getCreateTime());
					redisTemplate.opsForValue().set(
							RedisKeyGenerator.genUserLatestPostKey(profile
									.getUid()), post.getId());
				}
			}
			firstResult += maxResults;
		}
		return "success";
	}
}
