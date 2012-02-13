package com.juzhai.home.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.StringTokenizer;

import net.rubyeye.xmemcached.MemcachedClient;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.RandomUtils;
import org.apache.commons.lang.time.DateUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.juzhai.act.InitData;
import com.juzhai.act.caculator.IScoreGenerator;
import com.juzhai.act.model.Act;
import com.juzhai.act.model.Question;
import com.juzhai.act.service.IActService;
import com.juzhai.act.service.IUserActService;
import com.juzhai.core.cache.MemcachedKeyGenerator;
import com.juzhai.core.cache.RedisKeyGenerator;
import com.juzhai.home.bean.Feed;
import com.juzhai.home.bean.Feed.FeedType;
import com.juzhai.home.bean.ReadFeed;
import com.juzhai.home.bean.ReadFeedType;
import com.juzhai.home.service.IInboxService;
import com.juzhai.passport.bean.ProfileCache;
import com.juzhai.passport.bean.TpFriend;
import com.juzhai.passport.model.TpUser;
import com.juzhai.passport.service.IFriendService;
import com.juzhai.passport.service.IProfileService;
import com.juzhai.passport.service.ITpUserAuthService;
import com.juzhai.passport.service.ITpUserService;

@Service
public class InboxService implements IInboxService {

	private final Log log = LogFactory.getLog(getClass());
	private static final String VALUE_DELIMITER = "|";

	@Autowired
	private RedisTemplate<String, String> stringRedisTemplate;
	@Autowired
	private RedisTemplate<String, ReadFeed> readFeedRedisTemplate;
	@Autowired
	private RedisTemplate<String, Long> redisTemplate;
	@Autowired
	private IScoreGenerator inboxScoreGenerator;
	// @Autowired
	// private ThreadPoolTaskExecutor taskExecutor;
	@Autowired
	private IFriendService friendService;
	@Autowired
	private IProfileService profileService;
	@Autowired
	private IUserActService userActService;
	@Autowired
	private IActService actService;
	@Autowired
	private ITpUserAuthService tpUserAuthService;
	@Autowired
	private ITpUserService tpUserService;
	@Autowired
	private MessageSource messageSource;
	@Autowired
	private MemcachedClient memcachedClient;
	@Value("${random.feed.myAct.count}")
	private int randomFeedMyActCount = 5;
	@Value("${random.feed.act.count}")
	private int randomFeedActCount = 10;
	@Value("${want.feed.back.days.ago}")
	private int wantFeedBackDaysAgo = 7;
	@Value("${nill.feed.back.days.ago}")
	private int nillFeedBackDaysAgo = 7;

	// @Value("${last.push.expire.time}")
	// private int lastPushExpireTime = 86400
	// @Autowired
	// private int inboxCapacity = 100;

	@Override
	public void push(long receiverId, long senderId, long actId) {
		push(receiverId, senderId, actId, null);
	}

	@Override
	public void push(long receiverId, long senderId, long actId, Date time) {
		if (userActService.hasAct(receiverId, actId)
				|| isNilAct(receiverId, actId)) {
			return;
		} else {
			String key = RedisKeyGenerator.genInboxActsKey(receiverId);
			redisTemplate.opsForZSet().add(
					key,
					actId,
					inboxScoreGenerator.genScore(senderId, receiverId, actId,
							time));
		}
		// 更新最后推送时间
		// try {
		// memcachedClient.set(MemcachedKeyGenerator.genLastPushTimeKey(
		// senderId, receiverId), lastPushExpireTime, System
		// .currentTimeMillis());
		// } catch (Exception e) {
		// log.error(e.getMessage(), e);
		// }
		// TODO 删除超过100个的值
		// int overCount = redisTemplate.opsForZSet().size(key).intValue()
		// - inboxCapacity;
		// if (overCount > 0) {
		// redisTemplate.opsForZSet().removeRange(key, 0, overCount - 1);
		// }
	}

	private boolean isNilAct(long uid, long actId) {
		return redisTemplate.opsForSet().isMember(
				RedisKeyGenerator.genNillActsKey(uid), actId);
	}

	@Override
	public long inboxCount(long uid) {
		Long count = redisTemplate.opsForZSet().size(
				RedisKeyGenerator.genInboxActsKey(uid));
		return null == count ? 0 : count;
	}

	// @Override
	// public void syncInbox(long uid) {
	// Set<Long> friendIds = friendService.getAppFriends(uid);
	// Date startDate = DateUtils.addYears(new Date(), -1);
	// List<UserAct> userActList = userActService.listFriendsRecentAct(
	// friendIds, startDate, 0, 100);
	// for (UserAct userAct : userActList) {
	// push(uid, userAct.getUid(), userAct.getActId(),
	// userAct.getLastModifyTime());
	// }
	// }
	//
	// @Override
	// public void syncInboxByTask(final long uid) {
	// taskExecutor.execute(new Runnable() {
	// @Override
	// public void run() {
	// syncInbox(uid);
	// }
	// });
	// }

	@Override
	public void shiftRead(long uid, long senderId, long actId,
			ReadFeedType readFeedType) {
		if (ReadFeedType.WANT.equals(readFeedType)) {
			ReadFeed readFeed = new ReadFeed(senderId, uid, actId, readFeedType);
			readFeedRedisTemplate.opsForList().rightPush(
					RedisKeyGenerator.genReadFeedsKey(readFeedType), readFeed);
		} else {
			redisTemplate.opsForSet().add(
					RedisKeyGenerator.genNillActsKey(uid), actId);
			remove(uid, actId);
		}
	}

	@Override
	public boolean remove(long uid, long actId) {
		Boolean success = redisTemplate.opsForZSet().remove(
				RedisKeyGenerator.genInboxActsKey(uid), actId);
		return success == null ? false : success;
	}

	@Override
	public Feed showSpecific(long uid) {
		Set<Long> values = redisTemplate.opsForZSet().reverseRange(
				RedisKeyGenerator.genInboxActsKey(uid), 0, 0);
		if (CollectionUtils.isNotEmpty(values)) {
			for (long actId : values) {
				try {
					Act act = actService.getActById(actId);
					if (null == act) {
						remove(uid, actId);
					} else {
						return new Feed(null, FeedType.SPECIFIC, new Date(),
								act);
					}
				} catch (NumberFormatException e) {
					log.error(e.getMessage(), e);
					break;
				}
			}
		}
		return null;
	}

	@Override
	public Feed showYue(long uid) {
		ProfileCache profileCache = profileService.getProfileCacheByUid(uid);
		if (null == profileCache) {
			return null;
		}
		List<TpFriend> unInstallFriendList = friendService
				.getUnInstallFriends(uid);
		if (CollectionUtils.isEmpty(unInstallFriendList)) {
			// 没有未安装App的好友
			return null;
		}
		TpFriend tpFriend = unInstallFriendList.get(RandomUtils.nextInt(
				new Random(System.currentTimeMillis()),
				unInstallFriendList.size()));
		if (null == tpFriend) {
			return null;
		}

		List<Act> acts = userActService.getUserActFromCache(uid,
				randomFeedMyActCount);
		if (acts.size() < randomFeedActCount) {
			// 随机Act
			int genders = profileCache.getGender() + tpFriend.getGender();
			List<Act> randomActList = InitData.RANDOM_ACT_MAP.get(genders);
			for (int i = 0; i < randomFeedActCount - acts.size(); i++) {
				Act act = randomActList.get(RandomUtils.nextInt(new Random(
						System.currentTimeMillis()), randomActList.size()));
				if (!acts.contains(act)) {
					acts.add(act);
				}
			}
		}
		if (CollectionUtils.isEmpty(acts)) {
			return null;
		}
		return new Feed(tpFriend, FeedType.YUE, null, acts.toArray(new Act[0]));
	}

	@Override
	public Feed showQuestion(long uid) {
		Question question = randomQuestion();
		if (null == question) {
			return null;
		}

		List<TpFriend> unInstallFriendList = friendService
				.getUnInstallFriends(uid);
		if (CollectionUtils.isEmpty(unInstallFriendList)) {
			// 没有未安装App的好友
			return null;
		}
		Set<String> identitySet = stringRedisTemplate.opsForSet().members(
				RedisKeyGenerator.genQuestionUsersKey(uid));
		if (null != identitySet) {
			for (String identity : identitySet) {
				TpFriend removeTpFriend = null;
				for (TpFriend tpFriend : unInstallFriendList) {
					if (StringUtils.equals(tpFriend.getUserId(), identity)) {
						removeTpFriend = tpFriend;
						break;
					}
				}
				if (null != removeTpFriend) {
					unInstallFriendList.remove(removeTpFriend);
				}
			}
		}
		if (CollectionUtils.isEmpty(unInstallFriendList)) {
			return null;
		}
		TpFriend tpFriend = unInstallFriendList.get(RandomUtils.nextInt(
				new Random(System.currentTimeMillis()),
				unInstallFriendList.size()));
		if (null == tpFriend) {
			return null;
		}
		return new Feed(tpFriend, FeedType.QUESTION, question, new Act[0]);
	}

	private Question randomQuestion() {
		List<Question> questionList = new ArrayList<Question>(
				InitData.QUESTION_MAP.values());
		if (CollectionUtils.isEmpty(questionList)) {
			return null;
		}
		Question question = questionList.get(RandomUtils.nextInt(new Random(
				System.currentTimeMillis()), questionList.size()));
		return question;
	}

	@Override
	public Feed showRecommend(long uid) {
		TpUser tpUser = tpUserService.getTpUserByUid(uid);
		String tpName = tpUser == null ? StringUtils.EMPTY : tpUser.getTpName();
		Map<Long, Integer> rateMap = InitData.RECOMMEND_CATEGORY_RATE_MAP
				.get(tpName);
		Map<Long, Set<Long>> recommendActMap = InitData.TP_RECOMMEND_ACT_MAP
				.get(tpName);
		if (null == rateMap || null == recommendActMap) {
			return null;
		}

		// 需要排除的
		Set<Long> excludeActIds = redisTemplate.opsForSet().members(
				RedisKeyGenerator.genNillActsKey(uid));
		for (long actId : userActService.getUserActIdsFromCache(uid,
				Integer.MAX_VALUE)) {
			excludeActIds.add(actId);
		}
		// 准备需要随机的推荐Act
		List<Act> defaultActList = null;
		Map<Long, List<Act>> recommendMap = new HashMap<Long, List<Act>>();
		for (Long key : rateMap.keySet()) {
			Set<Long> recommendActIds = recommendActMap.get(key);
			List<Long> actIds = recommendActIds == null ? new ArrayList<Long>()
					: new ArrayList<Long>(recommendActIds);
			for (long excludeActId : excludeActIds) {
				actIds.remove(excludeActId);
			}
			List<Act> actList = actService.getActListByIds(actIds);
			if (CollectionUtils.isEmpty(defaultActList)
					&& CollectionUtils.isNotEmpty(actList)) {
				defaultActList = actList;
			}
			recommendMap.put(key, actList);
		}
		if (CollectionUtils.isNotEmpty(defaultActList)) {
			for (Long key : rateMap.keySet()) {
				if (CollectionUtils.isEmpty(recommendMap.get(key))) {
					recommendMap.put(key, defaultActList);
				}
			}
		}
		// 按照概率来随
		Act act = null;
		int randomValue = RandomUtils.nextInt(100);
		for (Map.Entry<Long, Integer> entry : rateMap.entrySet()) {
			List<Act> recommendList = recommendMap.get(entry.getKey());
			if (randomValue < entry.getValue()) {
				if (CollectionUtils.isNotEmpty(recommendList)) {
					act = recommendList.get(RandomUtils.nextInt(new Random(
							System.currentTimeMillis()), recommendList.size()));
				}
				break;
			}
		}
		if (act == null) {
			return null;
		} else {
			return new Feed(null, FeedType.RECOMMEND, new Date(), act);
		}
	}

	@Override
	public void answer(long uid, long tpId, long questionId, String identity,
			int answer, boolean isAdvise) {
		if (!isAdvise) {
			String key = RedisKeyGenerator.genQuestionUsersKey(uid);
			stringRedisTemplate.opsForSet().add(key, identity);
			stringRedisTemplate.opsForSet().add(
					RedisKeyGenerator.genQuestionUserKeysKey(), key);
			// accountService.profitPoint(uid, ProfitAction.ANSWER_QUESTION);
		}
	}

	// private boolean sendQuestionMssage(long uid, long tpId, long questionId,
	// String identity, int answer) {
	// Question question = InitData.QUESTION_MAP.get(questionId);
	// if (null == question) {
	// return false;
	// }
	// String[] answers = StringUtils.split(question.getAnswer(), "|");
	// if (answer <= 0 || answer > answers.length) {
	// return false;
	// }
	// if (question.getType() == 1 && answer == 2) {
	// // 是非题选择了no
	// return false;
	// }
	// AuthInfo authInfo = tpUserAuthService.getAuthInfo(uid, tpId);
	// if (null != authInfo) {
	// if (StringUtils.isNotEmpty(question.getInviteText())) {
	// // ProfileCache profileCache = profileService
	// // .getProfileCacheByUid(uid);
	// // String text = question.getInviteText().replace("{0}",
	// // profileCache.getNickname());
	// String text = question.getInviteText();
	// String word = question.getInviteWord().replace("{0}",
	// answers[answer - 1]);
	// if (StringUtils.isNotEmpty(text)) {
	// List<String> fuids = new ArrayList<String>();
	// fuids.add(identity);
	// String linktext = getContent(
	// TpMessageKey.QUESTION_LINKTEXT, null);
	// Thirdparty tp = com.juzhai.passport.InitData.TP_MAP
	// .get(tpId);
	// if (null == tp) {
	// return false;
	// }
	// messageService.sendSysMessage(fuids, linktext,
	// tp.getAppUrl(), word, text, StringUtils.EMPTY,
	// authInfo);
	// }
	// }
	// }
	// return true;
	// }

	private String getContent(String code, Object[] args) {
		return messageSource.getMessage(code, args, StringUtils.EMPTY,
				Locale.SIMPLIFIED_CHINESE);
	}

	@Deprecated
	private String assembleValue(long senderId, long actId) {
		return senderId + VALUE_DELIMITER + actId;
	}

	/**
	 * 解析redis中存的value
	 * 
	 * @param value
	 * @return 长度为2的数组，第一个是friendId，第二个是ActId.如果解析失败，返回null
	 */
	@Deprecated
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

	@Override
	@Deprecated
	public void clearPunishTimes(long senderId, long receiverId) {
		try {
			memcachedClient.deleteWithNoReply(MemcachedKeyGenerator
					.genPushPunishTimesKey(senderId, receiverId));
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}

	@Override
	@Deprecated
	public long increasePunishTimes(long senderId, long receiverId) {
		try {
			return memcachedClient.incr(MemcachedKeyGenerator
					.genPushPunishTimesKey(senderId, receiverId), 1, 1);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return 0L;
		}
	}

	@Override
	@Deprecated
	public long getLastPushTime(long senderId, long receiverId) {
		try {
			Long time = memcachedClient.get(MemcachedKeyGenerator
					.genLastPushTimeKey(senderId, receiverId));
			return time == null ? 0L : time;
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return 0L;
		}
	}

	@Deprecated
	@Override
	public List<ReadFeed> listGetBackFeed() {
		List<ReadFeed> getBackFeedList = getBackFeedList(ReadFeedType.WANT,
				wantFeedBackDaysAgo);
		getBackFeedList.addAll(getBackFeedList(ReadFeedType.NILL,
				nillFeedBackDaysAgo));
		return getBackFeedList;
	}

	@Deprecated
	private List<ReadFeed> getBackFeedList(ReadFeedType type, int daysAgo) {
		List<ReadFeed> getBackFeedList = new ArrayList<ReadFeed>();
		Date cDate = new Date();
		while (true) {
			String key = RedisKeyGenerator.genReadFeedsKey(type);
			ReadFeed readFeed = readFeedRedisTemplate.opsForList().leftPop(key);
			if (null == readFeed) {
				break;
			} else if (readFeed.getTime().after(
					DateUtils.addDays(cDate, -daysAgo))) {
				readFeedRedisTemplate.opsForList().leftPush(key, readFeed);
				break;
			} else {
				getBackFeedList.add(readFeed);
			}
		}
		return getBackFeedList;
	}
}
