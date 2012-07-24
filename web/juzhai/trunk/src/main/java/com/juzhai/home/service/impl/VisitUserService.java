package com.juzhai.home.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.Set;

import org.apache.commons.lang.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations.TypedTuple;
import org.springframework.stereotype.Service;

import com.juzhai.core.cache.RedisKeyGenerator;
import com.juzhai.home.controller.view.VisitorView;
import com.juzhai.home.service.IVisitUserService;
import com.juzhai.notice.bean.NoticeType;
import com.juzhai.notice.service.INoticeService;
import com.juzhai.passport.bean.ProfileCache;
import com.juzhai.passport.model.Profile;
import com.juzhai.passport.service.IProfileService;

@Service
public class VisitUserService implements IVisitUserService {

	@Autowired
	private RedisTemplate<String, Long> redisTemplate;
	@Autowired
	private IProfileService profileService;
	@Autowired
	private INoticeService noticeService;
	@Value("${user.visit.random.count}")
	private int userVisitRandomCount;

	@Override
	public void addVisitUser(long uid, long visitUid) {
		addVisitUser(uid, visitUid, System.currentTimeMillis());
	}

	@Override
	public List<VisitorView> listVisitUsers(long uid, int firstResult,
			int maxResults) {
		List<VisitorView> visitorViewList = new ArrayList<VisitorView>();
		Set<TypedTuple<Long>> users = redisTemplate.opsForZSet()
				.reverseRangeWithScores(
						RedisKeyGenerator.genVisitUsersKey(uid), firstResult,
						firstResult + maxResults - 1);
		for (TypedTuple<Long> user : users) {
			VisitorView view = new VisitorView();
			view.setProfileCache(profileService.getProfileCacheByUid(user
					.getValue()));
			view.setVisitTime(new Date(user.getScore().longValue()));
			visitorViewList.add(view);
		}
		return visitorViewList;
	}

	@Override
	public int countVisitUsers(long uid) {
		return redisTemplate.opsForZSet()
				.size(RedisKeyGenerator.genVisitUsersKey(uid)).intValue();
	}

	@Override
	public void autoExchangeVisits(long uid) {
		ProfileCache cache = profileService.getProfileCacheByUid(uid);
		Integer gender = null;
		if (cache.getGender() != null) {
			gender = cache.getGender() == 1 ? 0 : 1;
		}
		Random random = new Random();
		int maxResults = random.nextInt(userVisitRandomCount) + 1;
		List<Profile> list = profileService.queryProfile(cache.getUid(),
				gender, cache.getCity(), null, 0, 0, 0, maxResults);
		Date date = new Date();
		for (Profile profile : list) {
			addVisitUser(uid, profile.getUid(),
					DateUtils.addMinutes(date, -(random.nextInt(60) + 1))
							.getTime());
		}
	}

	private void addVisitUser(long uid, long visitUid, long time) {
		String key = RedisKeyGenerator.genVisitUsersKey(uid);
		redisTemplate.opsForZSet().add(key, visitUid, time);
		noticeService.incrNotice(uid, NoticeType.VISITOR);
	}

}
