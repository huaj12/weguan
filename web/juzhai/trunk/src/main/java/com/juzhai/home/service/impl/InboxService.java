package com.juzhai.home.service.impl;

import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.SortedMap;
import java.util.StringTokenizer;
import java.util.TreeMap;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.math.RandomUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

import com.juzhai.act.InitData;
import com.juzhai.act.bean.ActDealType;
import com.juzhai.act.caculator.IScoreGenerator;
import com.juzhai.act.model.Act;
import com.juzhai.core.cache.RedisKeyGenerator;
import com.juzhai.home.service.IInboxService;
import com.juzhai.passport.bean.ProfileCache;
import com.juzhai.passport.bean.TpFriend;
import com.juzhai.passport.service.IFriendService;
import com.juzhai.passport.service.IProfileService;

@Service
public class InboxService implements IInboxService {

	private final Log log = LogFactory.getLog(getClass());
	private static final String VALUE_DELIMITER = "|";

	@Autowired
	private RedisTemplate<String, String> redisTemplate;
	@Autowired
	private IScoreGenerator inboxScoreGenerator;
	@Autowired
	private ThreadPoolTaskExecutor taskExecutor;
	@Autowired
	private IFriendService friendService;
	@Autowired
	private IProfileService profileService;

	// @Autowired
	// private int inboxCapacity = 100;

	@Override
	public void push(long receiverId, long senderId, long actId) {
		String key = RedisKeyGenerator.genInboxActsKey(receiverId);
		redisTemplate.opsForZSet().add(key, assembleValue(senderId, actId),
				inboxScoreGenerator.genScore(senderId, receiverId, actId));
		// TODO 删除超过100个的值
		// int overCount = redisTemplate.opsForZSet().size(key).intValue()
		// - inboxCapacity;
		// if (overCount > 0) {
		// redisTemplate.opsForZSet().removeRange(key, 0, overCount - 1);
		// }
	}

	@Override
	public void syncInbox(long uid) {
		// TODO 根据算法决定怎么存储
	}

	@Override
	public void syncInboxByTask(final long uid) {
		taskExecutor.execute(new Runnable() {
			@Override
			public void run() {
				syncInbox(uid);
			}
		});
	}

	@Override
	public void shiftRead(long uid, long senderId, long actId,
			ActDealType actDealType) {
		redisTemplate.opsForList().rightPush(
				RedisKeyGenerator.genDealedActsKey(uid, actDealType),
				assembleValue(senderId, actId));
	}

	@Override
	public boolean remove(long uid, long senderId, long actId) {
		Boolean success = redisTemplate.opsForZSet().remove(
				RedisKeyGenerator.genInboxActsKey(uid),
				assembleValue(senderId, actId));
		return success == null ? false : success;
	}

	@Override
	public SortedMap<ProfileCache, Act> showFirst(long uid) {
		Set<String> values = redisTemplate.opsForZSet().reverseRange(
				RedisKeyGenerator.genInboxActsKey(uid), 0, 0);
		if (CollectionUtils.isNotEmpty(values)) {
			for (String value : values) {
				long[] ids = parseValue(value);
				if (null != ids) {
					ProfileCache profileCache = profileService
							.getProfileCacheByUid(ids[0]);
					Act act = InitData.ACT_MAP.get(ids[1]);
					if (null == profileCache || null == act) {
						remove(uid, ids[0], ids[1]);
					} else {
						SortedMap<ProfileCache, Act> result = new TreeMap<ProfileCache, Act>();
						result.put(profileCache, act);
						return result;
					}
				}
			}
		}
		return null;
	}

	@Override
	public SortedMap<TpFriend, Act> showRandam(long uid) {
		ProfileCache profileCache = profileService.getProfileCacheByUid(uid);
		if (null == profileCache) {
			return null;
		}
		List<TpFriend> unInstallFriendList = friendService
				.getUnInstallFriends(uid);
		TpFriend tpFriend = unInstallFriendList.get(RandomUtils.nextInt(
				new Random(System.currentTimeMillis()),
				unInstallFriendList.size()));
		if (null == tpFriend) {
			return null;
		}
		int genders = profileCache.getGender() + tpFriend.getGender();
		List<Act> randomActList = InitData.RANDOM_ACT_MAP.get(genders);
		Act act = randomActList.get(RandomUtils.nextInt(
				new Random(System.currentTimeMillis()), randomActList.size()));
		if (null == act) {
			return null;
		}
		SortedMap<TpFriend, Act> result = new TreeMap<TpFriend, Act>();
		result.put(tpFriend, act);
		return result;
	}

	private String assembleValue(long senderId, long actId) {
		return senderId + VALUE_DELIMITER + actId;
	}

	/**
	 * 解析redis中存的value
	 * 
	 * @param value
	 * @return 长度为2的数组，第一个是friendId，第二个是ActId.如果解析失败，返回null
	 */
	private long[] parseValue(String value) {
		try {
			StringTokenizer st = new StringTokenizer(value, VALUE_DELIMITER);
			if (st.countTokens() == 2) {
				long friendId = Long.valueOf(st.nextToken());
				long actId = Long.valueOf(st.nextToken());
				return new long[] { friendId, actId };
			}
		} catch (Exception e) {
			if (log.isDebugEnabled()) {
				log.debug("parse inbox element error.", e);
			}
		}
		return null;
	}
}
