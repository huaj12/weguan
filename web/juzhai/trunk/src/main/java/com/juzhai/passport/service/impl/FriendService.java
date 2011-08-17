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
import com.juzhai.passport.service.IAuthorizeService;
import com.juzhai.passport.service.IFriendService;
import com.juzhai.passport.service.ITpUserAuthService;

@Service
public class FriendService implements IFriendService {

	private final Log log = LogFactory.getLog(getClass());

	@Autowired
	private ITpUserAuthService tpUserAuthService;
	@Autowired
	private IAuthorizeService authorizeService;
	@Autowired
	private MemcachedClient memcachedClient;
	@Autowired
	private RedisTemplate<String, List<TpFriend>> listRedisTemplate;
	@Autowired
	private RedisTemplate<String, Long> longRedisTemplate;
	@Autowired
	private TpUserMapper tpUserMapper;

	@Override
	public FriendsBean getAllFriends(long uid) {
		return new FriendsBean(getAppFriendsWithIntimacy(uid),
				getTpFriends(uid));
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
	public Set<Long> getAppFriends(long uid) {
		Set<Long> friendIds = longRedisTemplate.opsForZSet().range(
				RedisKeyGenerator.genFriendsKey(uid), 0, -1);
		if (CollectionUtils.isEmpty(friendIds)) {
			return Collections.emptySet();
		} else {
			return friendIds;
		}
	}

	@Override
	public List<TpFriend> getTpFriends(long uid) {
		return listRedisTemplate.opsForValue().get(
				RedisKeyGenerator.genTpFriendsKey(uid));
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
				// 第三方用户ID列表
				List<TpFriend> tpFriends = authorizeService
						.getAllFriends(authInfo);
				listRedisTemplate.opsForValue().set(
						RedisKeyGenerator.genTpFriendsKey(uid), tpFriends);

				List<Long> appFriends = getAppFriendUids(
						authorizeService.getAppFriends(authInfo), tpId);
				String key = RedisKeyGenerator.genFriendsKey(uid);
				Set<Long> cachedFriends = null;
				if (longRedisTemplate.hasKey(key)) {
					cachedFriends = longRedisTemplate.opsForZSet().range(key,
							0, -1);
					for (Long id : cachedFriends) {
						if (!appFriends.contains(id)) {
							longRedisTemplate.opsForZSet().remove(key, id);
						}
					}
				}
				for (Long id : appFriends) {
					if (null == cachedFriends || !cachedFriends.contains(id)) {
						longRedisTemplate.opsForZSet().add(key, id, 0);
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

	private void touchCache(long uid) {
		try {
			memcachedClient.set(MemcachedKeyGenerator.genCachedFriendsKey(uid),
					3600 * 24, true);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}

	@Deprecated
	private List<String> getNonAppFriends(List<String> allFriendIds,
			List<String> appFriendIds) {
		List<String> nonAppFriends = new ArrayList<String>();
		if (CollectionUtils.isEmpty(appFriendIds)) {
			nonAppFriends.addAll(allFriendIds);
		} else {
			for (String tpUid : allFriendIds) {
				if (!appFriendIds.contains(tpUid)) {
					nonAppFriends.add(tpUid);
				}
			}
		}
		return nonAppFriends;
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
}
