/*
 *The code is written by 51juzhai, All rights is reserved
 */
package com.juzhai.passport.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.rubyeye.xmemcached.MemcachedClient;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations.TypedTuple;
import org.springframework.stereotype.Service;

import com.juzhai.core.cache.MemcachedKeyGenerator;
import com.juzhai.core.cache.RedisKeyGenerator;
import com.juzhai.passport.InitData;
import com.juzhai.passport.bean.AuthInfo;
import com.juzhai.passport.bean.FriendsBean;
import com.juzhai.passport.bean.TpFriend;
import com.juzhai.passport.mapper.TpUserMapper;
import com.juzhai.passport.model.Thirdparty;
import com.juzhai.passport.model.TpUser;
import com.juzhai.passport.model.TpUserExample;
import com.juzhai.passport.service.IFriendService;
import com.juzhai.passport.service.ITpUserAuthService;
import com.juzhai.platform.service.IRelationshipService;

@Service
public class FriendService implements IFriendService {

	private final Log log = LogFactory.getLog(getClass());

	@Autowired
	private ITpUserAuthService tpUserAuthService;
	@Autowired
	private IRelationshipService relationshipService;
	@Autowired
	private MemcachedClient memcachedClient;
	@Autowired
	private RedisTemplate<String, List<TpFriend>> listRedisTemplate;
	@Autowired
	private RedisTemplate<String, Long> longRedisTemplate;
	@Autowired
	private RedisTemplate<String, List<Long>> listLongRedisTemplate;
	@Autowired
	private TpUserMapper tpUserMapper;
	// @Autowired
	// private IProfileService profileService;
	// @Autowired
	// private IUserActService userActService;
	@Value("${friends.cache.expire.time}")
	private int friendsCacheExpireTime;

	@Override
	public FriendsBean getAllFriends(long uid) {
		return new FriendsBean(getAppFriendsWithIntimacy(uid),
				getUnInstallFriends(uid));
	}

	@Override
	public Map<Long, Integer> getAppFriendsWithIntimacy(long uid) {
		Set<TypedTuple<Long>> tupleSet = longRedisTemplate.opsForZSet()
				.rangeWithScores(RedisKeyGenerator.genFriendsKey(uid), 0, -1);
		Map<Long, Integer> friendsIntimacy = new HashMap<Long, Integer>();
		for (TypedTuple<Long> tt : tupleSet) {
			friendsIntimacy.put(tt.getValue(), tt.getScore().intValue());
		}
		return friendsIntimacy;
	}

	@Override
	public List<Long> getAppFriends(long uid) {
		Set<Long> friendIds = longRedisTemplate.opsForZSet().range(
				RedisKeyGenerator.genFriendsKey(uid), 0, -1);
		if (CollectionUtils.isEmpty(friendIds)) {
			return Collections.emptyList();
		} else {
			return new ArrayList<Long>(friendIds);
		}
	}

	@Override
	public int countAppFriends(long uid) {
		Long count = longRedisTemplate.opsForZSet().size(
				RedisKeyGenerator.genFriendsKey(uid));
		return count == null ? 0 : count.intValue();
	}

	@Override
	public List<TpFriend> getUnInstallFriends(long uid) {
		return listRedisTemplate.opsForValue().get(
				RedisKeyGenerator.genUnInstallFriendsKey(uid));
	}

	@Override
	public void updateExpiredFriends(long uid, long tpId) {
		final AuthInfo authInfo = tpUserAuthService.getAuthInfo(uid, tpId);
		if (null != authInfo) {
			updateExpiredFriends(uid, tpId, authInfo);
		}
	}

	@Override
	public void updateExpiredFriends(long uid, long tpId, AuthInfo authInfo) {
		if (isExpired(uid)) {
			if (null != authInfo) {
				// List<String> installFollows = relationshipService
				// .getInstallFollows(authInfo);
				// if (null != installFollows) {
				// listLongRedisTemplate.opsForValue().set(
				// RedisKeyGenerator.genInstallFollowsKey(uid),
				// getAppFriendUids(installFollows, tpId));
				// }

				// 第三方安装应用好友
				List<String> appFriendIds = relationshipService
						.getAppFriends(authInfo);

				// 第三方未安装应用的好友
				listRedisTemplate.opsForValue().set(
						RedisKeyGenerator.genUnInstallFriendsKey(uid),
						getNonAppFriends(
								relationshipService.getAllFriends(authInfo),
								appFriendIds));

				// 更新安装了App的好友
				List<Long> appFriends = getAppFriendUids(appFriendIds, tpId);

				String key = RedisKeyGenerator.genFriendsKey(uid);
				Set<Long> cachedFriends = null;
				if (longRedisTemplate.hasKey(key)) {
					cachedFriends = longRedisTemplate.opsForZSet().range(key,
							0, -1);
					for (Long id : cachedFriends) {
						if (!appFriends.contains(id)) {
							longRedisTemplate.opsForZSet().remove(key, id);
							longRedisTemplate.opsForZSet().remove(
									RedisKeyGenerator.genFriendsKey(id), uid);
						}
					}
				}
				for (Long id : appFriends) {
					if (null == cachedFriends || !cachedFriends.contains(id)) {
						longRedisTemplate.opsForZSet().add(key, id, 0);
						longRedisTemplate.opsForZSet().add(
								RedisKeyGenerator.genFriendsKey(id), uid, 0);
					}
				}
				touchCache(uid);
			}
		}
	}

	@Override
	public boolean isExpired(long uid) {
		Boolean cached = null;
		try {
			cached = memcachedClient.get(MemcachedKeyGenerator
					.genCachedFriendsKey(uid));
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return cached == null || !cached;
	}

	@Override
	public void incrOrDecrIntimacy(long uid, long friendId, int intimacy) {
		String key = RedisKeyGenerator.genFriendsKey(uid);
		if (null != longRedisTemplate.opsForZSet().score(key, friendId)) {
			longRedisTemplate.opsForZSet().incrementScore(key, friendId,
					intimacy);
		}
	}

	@Override
	public int getFriendIntimacy(long uid, long friendId) {
		Double score = longRedisTemplate.opsForZSet().score(
				RedisKeyGenerator.genFriendsKey(uid), friendId);
		return score == null ? 0 : score.intValue();
	}

	private void touchCache(long uid) {
		try {
			memcachedClient.set(MemcachedKeyGenerator.genCachedFriendsKey(uid),
					friendsCacheExpireTime, true);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}

	private List<TpFriend> getNonAppFriends(List<TpFriend> allFriendIds,
			List<String> appFriendIds) {
		List<TpFriend> unInstallFriends = new ArrayList<TpFriend>();
		if (CollectionUtils.isEmpty(appFriendIds)) {
			unInstallFriends.addAll(allFriendIds);
		} else {
			for (TpFriend tpFriend : allFriendIds) {
				if (!appFriendIds.contains(tpFriend.getUserId())) {
					unInstallFriends.add(tpFriend);
				}
			}
		}
		return unInstallFriends;
	}

	private List<Long> getAppFriendUids(List<String> appFriendIds, long tpId) {
		if (CollectionUtils.isEmpty(appFriendIds)) {
			return Collections.emptyList();
		}
		Thirdparty tp = InitData.TP_MAP.get(tpId);
		if (null == tp) {
			return Collections.emptyList();
		}
		List<Long> appFriendUids = new ArrayList<Long>(appFriendIds.size());

		TpUserExample example = new TpUserExample();
		example.createCriteria().andTpNameEqualTo(tp.getName())
				.andTpIdentityIn(appFriendIds);
		List<TpUser> tpUserList = tpUserMapper.selectByExample(example);
		for (TpUser tpUser : tpUserList) {
			appFriendUids.add(tpUser.getUid());
		}
		return appFriendUids;
	}

	@Override
	public boolean isAppFriend(long uid, long friendId) {
		Double score = longRedisTemplate.opsForZSet().score(
				RedisKeyGenerator.genFriendsKey(uid), friendId);
		return score != null;
	}

	// @Override
	// public List<ProfileCache> findSameActFriends(long uid, long actId, int
	// num) {
	// List<ProfileCache> list = new ArrayList<ProfileCache>();
	// List<Long> friendIds = getAppFriends(uid);
	// for (long fid : friendIds) {
	// if (userActService.hasAct(fid, actId)) {
	// list.add(profileService.getProfileCacheByUid(fid));
	// if (list.size() >= num) {
	// break;
	// }
	// }
	// }
	// return list;
	// }

	@Override
	public List<Long> listInstallFollowUids(long uid) {
		return listLongRedisTemplate.opsForValue().get(
				RedisKeyGenerator.genInstallFollowsKey(uid));
	}
}
