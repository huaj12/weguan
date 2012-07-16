package com.juzhai.home.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
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

	// TODO (done) 普通的来访者不需要加数字？
	// TODO (done) 什么理由在controller里加？
	// TODO (done) 不觉得有问题吗？
	// TODO (done) 你自己写代码的时候，没觉得有问题吗？
	@Override
	public void addVisitUser(long uid, long visitUid) {
		String key = RedisKeyGenerator.genVisitUsersKey(uid);
		redisTemplate.opsForZSet().add(key, visitUid,
				System.currentTimeMillis());
		noticeService.incrNotice(uid, NoticeType.VISITOR);
		// redisTemplate.opsForZSet().removeRange(key, -100, 0);
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
		// TODO (done) 随机数错误
		Random random = new Random();
		int maxResults = random.nextInt(5) + 1;
		List<Profile> list = profileService.queryProfile(cache.getUid(),
				gender, cache.getCity(), null, 0, 0, 0, maxResults);
		for (Profile profile : list) {
			addVisitUser(uid, profile.getUid());
		}
	}
}
