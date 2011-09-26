package com.juzhai.home.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.Set;
import java.util.StringTokenizer;

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
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

import com.juzhai.account.bean.ProfitAction;
import com.juzhai.account.service.IAccountService;
import com.juzhai.act.InitData;
import com.juzhai.act.caculator.IScoreGenerator;
import com.juzhai.act.model.Act;
import com.juzhai.act.model.UserAct;
import com.juzhai.act.service.IUserActService;
import com.juzhai.app.bean.TpMessageKey;
import com.juzhai.core.cache.RedisKeyGenerator;
import com.juzhai.home.bean.Feed;
import com.juzhai.home.bean.Feed.FeedType;
import com.juzhai.home.bean.ReadFeed;
import com.juzhai.home.bean.ReadFeedType;
import com.juzhai.home.service.IInboxService;
import com.juzhai.passport.bean.AuthInfo;
import com.juzhai.passport.bean.ProfileCache;
import com.juzhai.passport.bean.TpFriend;
import com.juzhai.passport.service.IAuthorizeService;
import com.juzhai.passport.service.IFriendService;
import com.juzhai.passport.service.IProfileService;
import com.juzhai.passport.service.ITpUserAuthService;

@Service
public class InboxService implements IInboxService {

	private final Log log = LogFactory.getLog(getClass());
	private static final String VALUE_DELIMITER = "|";

	@Autowired
	private RedisTemplate<String, String> redisTemplate;
	@Autowired
	private RedisTemplate<String, ReadFeed> readFeedRedisTemplate;
	@Autowired
	private IScoreGenerator inboxScoreGenerator;
	@Autowired
	private ThreadPoolTaskExecutor taskExecutor;
	@Autowired
	private IFriendService friendService;
	@Autowired
	private IProfileService profileService;
	@Autowired
	private IUserActService userActService;
	@Autowired
	private ITpUserAuthService tpUserAuthService;
	@Autowired
	private IAuthorizeService authorizeService;
	@Autowired
	private MessageSource messageSource;
	@Autowired
	private IAccountService accountService;
	@Value("${random.feed.myAct.count}")
	private int randomFeedMyActCount = 5;
	@Value("${random.feed.act.count}")
	private int randomFeedActCount = 10;
	@Value("${want.feed.back.days.ago}")
	private int wantFeedBackDaysAgo = 7;
	@Value("${nill.feed.back.days.ago}")
	private int nillFeedBackDaysAgo = 7;

	// @Autowired
	// private int inboxCapacity = 100;

	@Override
	public void push(long receiverId, long senderId, long actId) {
		push(receiverId, senderId, actId, null);
	}

	@Override
	public void push(long receiverId, long senderId, long actId, Date time) {
		String key = RedisKeyGenerator.genInboxActsKey(receiverId);
		redisTemplate.opsForZSet()
				.add(key,
						assembleValue(senderId, actId),
						inboxScoreGenerator.genScore(senderId, receiverId,
								actId, time));
		// TODO 删除超过100个的值
		// int overCount = redisTemplate.opsForZSet().size(key).intValue()
		// - inboxCapacity;
		// if (overCount > 0) {
		// redisTemplate.opsForZSet().removeRange(key, 0, overCount - 1);
		// }
	}

	@Override
	public long inboxCount(long uid) {
		Long count = redisTemplate.opsForZSet().size(
				RedisKeyGenerator.genInboxActsKey(uid));
		return null == count ? 0 : count;
	}

	@Override
	public void syncInbox(long uid) {
		Set<Long> friendIds = friendService.getAppFriends(uid);
		Date startDate = DateUtils.addYears(new Date(), -1);
		List<UserAct> userActList = userActService.listFriendsRecentAct(
				friendIds, startDate, 0, 100);
		for (UserAct userAct : userActList) {
			push(uid, userAct.getUid(), userAct.getActId(),
					userAct.getLastModifyTime());
		}
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
			ReadFeedType readFeedType) {
		ReadFeed readFeed = new ReadFeed(senderId, uid, actId, readFeedType);
		readFeedRedisTemplate.opsForList().rightPush(
				RedisKeyGenerator.genReadFeedsKey(readFeedType), readFeed);
	}

	@Override
	public List<ReadFeed> listGetBackFeed() {
		List<ReadFeed> getBackFeedList = getBackFeedList(ReadFeedType.WANT,
				wantFeedBackDaysAgo);
		getBackFeedList.addAll(getBackFeedList(ReadFeedType.NILL,
				nillFeedBackDaysAgo));
		return getBackFeedList;
	}

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

	@Override
	public boolean remove(long uid, long senderId, long actId) {
		Boolean success = redisTemplate.opsForZSet().remove(
				RedisKeyGenerator.genInboxActsKey(uid),
				assembleValue(senderId, actId));
		return success == null ? false : success;
	}

	@Override
	public Feed showFirst(long uid) {
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
						UserAct userAct = userActService.getUserAct(
								profileCache.getUid(), act.getId());
						if (null == userAct) {
							remove(uid, ids[0], ids[1]);
						} else {
							return new Feed(profileCache, FeedType.SPECIFIC,
									userAct.getLastModifyTime(), act);
						}
					}
				}
			}
		}
		return null;
	}

	@Override
	public Feed showRandam(long uid) {
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
		return new Feed(tpFriend, FeedType.RANDOM, acts.toArray(new Act[0]));
	}

	@Override
	public Feed showGrade(long uid) {
		List<TpFriend> unInstallFriendList = friendService
				.getUnInstallFriends(uid);
		if (CollectionUtils.isEmpty(unInstallFriendList)) {
			// 没有未安装App的好友
			return null;
		}
		Set<String> identitySet = redisTemplate.opsForSet().members(
				RedisKeyGenerator.genGradedUsersKey(uid));
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
					unInstallFriendList.remove(unInstallFriendList);
				}
			}
		}

		TpFriend tpFriend = unInstallFriendList.get(RandomUtils.nextInt(
				new Random(System.currentTimeMillis()),
				unInstallFriendList.size()));
		if (null == tpFriend) {
			return null;
		}
		return new Feed(tpFriend, FeedType.GRADE, new Act[0]);
	}

	@Override
	public void grade(long uid, long tpId, String identity, int star) {
		redisTemplate.opsForSet().add(RedisKeyGenerator.genGradedUsersKey(uid),
				identity);
		if (star > 0 && star <= 5) {
			AuthInfo authInfo = tpUserAuthService.getAuthInfo(uid, tpId);
			if (null != authInfo) {
				List<String> fuids = new ArrayList<String>();
				fuids.add(identity);
				// TODO 确定内容
				String linktext = getContent(TpMessageKey.GRADE_LINKTEXT, null);
				String link = getContent(TpMessageKey.GRADE_LINK, null);
				String word = getContent(TpMessageKey.GRADE_WORD, null);
				String text = getContent(TpMessageKey.GRADE_TEXT, null);
				String picurl = getContent(TpMessageKey.GRADE_PICURL, null);
				authorizeService.sendSystemMessage(authInfo, fuids, linktext,
						link, word, text, picurl);
			}
			accountService.profitPoint(uid, ProfitAction.GRADE_STAR);
		}
	}

	private String getContent(String code, Object[] args) {
		return messageSource.getMessage(code, args, StringUtils.EMPTY,
				Locale.SIMPLIFIED_CHINESE);
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
