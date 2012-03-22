package com.juzhai.home.service.impl;

import java.util.Calendar;
import java.util.List;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.juzhai.core.cache.RedisKeyGenerator;
import com.juzhai.core.dao.Limit;
import com.juzhai.home.service.IGuessYouService;
import com.juzhai.passport.bean.ProfileCache;
import com.juzhai.passport.mapper.ProfileMapper;
import com.juzhai.passport.model.Profile;
import com.juzhai.passport.model.ProfileExample;
import com.juzhai.passport.service.IProfileService;
import com.juzhai.post.controller.view.PostView;
import com.juzhai.post.service.IPostService;
import com.juzhai.preference.bean.SiftTypePreference;
import com.juzhai.preference.service.IUserPreferenceService;

@Service
public class GuessYouService implements IGuessYouService {

	private final Log log = LogFactory.getLog(getClass());

	@Autowired
	private IUserPreferenceService userPreferenceService;
	@Autowired
	private ProfileMapper profileMapper;
	@Autowired
	private RedisTemplate<String, Long> redisTemplate;
	@Autowired
	private IProfileService profileService;
	@Autowired
	private IPostService postService;
	@Value("${rescue.users.total.count}")
	private int rescueUsersTotalCount;

	@Override
	// TODO 要使用rabbitmq
	public void updateLikeUsers(long uid) {
		ProfileCache profile = profileService.getProfileCacheByUid(uid);
		if (null == profile || null == profile.getCity()
				|| profile.getCity() <= 0) {
			return;
		}
		ProfileExample example = new ProfileExample();
		example.setOrderByClause("create_time asc");
		ProfileExample.Criteria c = example.createCriteria()
				.andCityEqualTo(profile.getCity()).andLastUpdateTimeIsNotNull()
				.andLogoPicIsNotNull().andLogoPicNotEqualTo(StringUtils.EMPTY)
				.andUidNotEqualTo(uid);
		List<String> genders = userPreferenceService.getUserAnswer(uid,
				SiftTypePreference.GENDER.getPreferenceId());
		if (CollectionUtils.isNotEmpty(genders) && genders.size() == 1) {
			try {
				c.andGenderEqualTo(Integer.valueOf(genders.get(0)));
			} catch (NumberFormatException e) {
				log.error("User[" + uid + "] gender sift error", e);
			}
		}
		List<String> ages = userPreferenceService.getUserAnswer(uid,
				SiftTypePreference.AGE.getPreferenceId());
		if (CollectionUtils.isNotEmpty(ages)) {
			try {
				int minAge = StringUtils.isEmpty(ages.get(0)) ? 0 : Integer
						.valueOf(ages.get(0));
				int maxAge = StringUtils.isEmpty(ages.get(1)) ? 100 : Integer
						.valueOf(ages.get(1));
				Calendar cal = Calendar.getInstance();
				int year = cal.get(Calendar.YEAR);
				c.andBirthYearBetween(year - maxAge, year - minAge);
			} catch (NumberFormatException e) {
				log.error("User[" + uid + "] age sift error", e);
			}
		}
		int firstResult = 0;
		int maxResults = 200;
		List<Profile> likeUserList = null;
		redisTemplate.delete(RedisKeyGenerator.genGuessYouLikeUsersKey(uid));
		while (true) {
			example.setLimit(new Limit(firstResult, maxResults));
			likeUserList = profileMapper.selectByExample(example);
			if (CollectionUtils.isEmpty(likeUserList)) {
				break;
			}
			for (Profile lickUser : likeUserList) {
				redisTemplate.opsForZSet().add(
						RedisKeyGenerator.genGuessYouLikeUsersKey(uid),
						lickUser.getUid(),
						lickUser.getLastUpdateTime() != null ? lickUser
								.getLastUpdateTime().getTime() : lickUser
								.getCreateTime().getTime());
			}
			firstResult += maxResults;
		}
		if (!existRescueUsers(uid)) {
			// 复制数据进解救
			Set<Long> uids = redisTemplate.opsForZSet().reverseRange(
					RedisKeyGenerator.genGuessYouLikeUsersKey(uid), 0,
					0 + rescueUsersTotalCount);
			for (Long userId : uids) {
				redisTemplate.opsForSet().add(
						RedisKeyGenerator.genRescueUsersKey(uid), userId);
			}
		}
	}

	@Override
	public boolean existLikeUsers(long uid) {
		return redisTemplate.hasKey(RedisKeyGenerator
				.genGuessYouLikeUsersKey(uid));
	}

	@Override
	public boolean existRescueUsers(long uid) {
		return redisTemplate.hasKey(RedisKeyGenerator.genRescueUsersKey(uid));
	}

	@Override
	public void removeFromRescueUsers(long uid, long removeUid) {
		redisTemplate.opsForSet().remove(
				RedisKeyGenerator.genRescueUsersKey(uid), removeUid);
	}

	@Override
	public PostView randomRescue(long uid) {
		String key = RedisKeyGenerator.genRescueUsersKey(uid);
		if (redisTemplate.opsForSet().size(key) <= 0) {
			return null;
		}
		// Long rescueUid = redisTemplate.opsForSet().randomMember(key);
		Long rescueUid = redisTemplate.opsForSet().pop(key);
		if (null == rescueUid || rescueUid <= 0) {
			return null;
		} else {
			ProfileCache profile = profileService
					.getProfileCacheByUid(rescueUid);
			Long postId = postService.getUserLatestPost(rescueUid);
			if (postId == null || postId <= 0 || profile == null) {
				// removeFromRescueUsers(uid, rescueUid);
				return randomRescue(uid);
			} else {
				PostView postView = new PostView();
				postView.setProfileCache(profile);
				postView.setPost(postService.getPostById(postId));

				redisTemplate.opsForSet().add(key, rescueUid);
				return postView;
			}
		}
	}

	@Override
	public void clearRescueUsers(long uid) {
		redisTemplate.delete(RedisKeyGenerator.genRescueUsersKey(uid));
	}
}
