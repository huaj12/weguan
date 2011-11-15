package com.juzhai.cms.controller;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.lucene.index.CorruptIndexException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations.TypedTuple;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.juzhai.act.controller.view.UserActView;
import com.juzhai.act.mapper.ActCategoryMapper;
import com.juzhai.act.mapper.ActMapper;
import com.juzhai.act.model.Act;
import com.juzhai.act.model.ActExample;
import com.juzhai.act.service.IActService;
import com.juzhai.act.service.IHotActService;
import com.juzhai.act.service.IUserActService;
import com.juzhai.core.cache.RedisKeyGenerator;
import com.juzhai.core.dao.Limit;
import com.juzhai.core.lucene.index.Indexer;
import com.juzhai.core.lucene.searcher.IndexSearcherManager;
import com.juzhai.home.bean.ReadFeed;
import com.juzhai.msg.bean.ActMsg;
import com.juzhai.msg.bean.ActMsg.MsgType;
import com.juzhai.msg.bean.MergerActMsg;
import com.juzhai.msg.service.IMsgService;
import com.juzhai.passport.mapper.ProfileMapper;
import com.juzhai.passport.model.Profile;
import com.juzhai.passport.model.ProfileExample;

@Deprecated
@Controller
@RequestMapping("/cms")
public class MigrateController {

	private final Log log = LogFactory.getLog(getClass());

	@Autowired
	private RedisTemplate<String, ActMsg> redisTemplate;
	@Autowired
	private RedisTemplate<String, MergerActMsg> redisMergerActMsgTemplate;
	@Autowired
	private RedisTemplate<String, ReadFeed> readFeedRedisTemplate;
	@Autowired
	private RedisTemplate<String, Long> longRedisTemplate;
	@Autowired
	private RedisTemplate<String, String> stringRedisTemplate;
	@Autowired
	private IMsgService<MergerActMsg> msgService;
	@Autowired
	private ActMapper actMapper;
	@Autowired
	private ActCategoryMapper actCategoryMapper;
	@Autowired
	private IHotActService hotActService;
	@Autowired
	private ProfileMapper profileMapper;
	@Autowired
	private IUserActService userActService;
	@Autowired
	private IActService actService;
	@Autowired
	private Indexer<Act> actIndexer;
	@Autowired
	private IndexSearcherManager actIndexSearcherManager;

	@RequestMapping(value = "migrateFeed")
	public String migrateFeed(HttpServletRequest request) {
		ProfileExample example = new ProfileExample();
		example.setOrderByClause("uid asc");
		int firstResult = 0;
		int maxResults = 200;
		while (true) {
			example.setLimit(new Limit(firstResult, maxResults));
			List<Profile> profileList = profileMapper.selectByExample(example);
			if (CollectionUtils.isEmpty(profileList)) {
				break;
			}
			for (Profile profile : profileList) {
				migrateFeedByUid(profile.getUid());
				// migrateMyActsByUid(profile.getUid());
			}
			firstResult += maxResults;
		}
		// List<String> keys = new ArrayList<String>();
		// for (ReadFeedType readFeedType : ReadFeedType.values()) {
		// keys.add(RedisKeyGenerator.genReadFeedsKey(readFeedType));
		// }
		// readFeedRedisTemplate.delete(keys);
		return null;
	}

	private void migrateFeedByUid(long uid) {
		String key = RedisKeyGenerator.genInboxActsKey(uid);
		Set<TypedTuple<String>> values = stringRedisTemplate.opsForZSet()
				.reverseRangeWithScores(key, 0, -1);
		stringRedisTemplate.delete(key);
		Map<Long, Double> map = new HashMap<Long, Double>();
		for (TypedTuple<String> value : values) {
			try {
				long[] ids = parseValue(value.getValue());
				if (ids.length == 2) {
					if (!map.containsKey(ids[1])) {
						map.put(ids[1], value.getScore());
					}
				}
			} catch (Exception e) {
				continue;
			}
		}
		for (Map.Entry<Long, Double> entry : map.entrySet()) {
			if (actService.actExist(entry.getKey())) {
				longRedisTemplate.opsForZSet().add(key, entry.getKey(),
						entry.getValue());
			}
		}
	}

	private void migrateMyActsByUid(long uid) {
		String key = RedisKeyGenerator.genMyActsKey(uid);
		longRedisTemplate.delete(key);
		List<UserActView> userActViewList = userActService.pageUserActView(uid,
				0, Integer.MAX_VALUE);
		for (UserActView userActView : userActViewList) {
			longRedisTemplate.opsForZSet().add(key,
					userActView.getUserAct().getActId(),
					userActView.getUserAct().getCreateTime().getTime());
		}
	}

	private long[] parseValue(String value) {
		try {
			StringTokenizer st = new StringTokenizer(value, "|");
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

	@RequestMapping(value = "migrateActIndex")
	public String migrateActIndex(HttpServletRequest request) {
		ActExample example = new ActExample();
		example.setOrderByClause("id asc");
		int firstResult = 0;
		int maxResults = 200;
		while (true) {
			example.setLimit(new Limit(firstResult, maxResults));
			List<Act> actList = actMapper.selectByExample(example);
			if (CollectionUtils.isEmpty(actList)) {
				break;
			}
			for (Act act : actList) {
				try {
					actIndexer.addIndex(act, false);
				} catch (CorruptIndexException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			firstResult += maxResults;
		}
		try {
			actIndexSearcherManager.maybeReopen();
		} catch (Exception e) {
			log.error("reopen indexReader failed when migrate index");
		}
		return null;
	}

	@RequestMapping(value = "initHotAct")
	public String initHotAct(HttpServletRequest request, Model model) {
		ActExample example = new ActExample();
		example.createCriteria().andLogoIsNotNull().andLogoNotEqualTo("");
		List<Act> actList = actMapper.selectByExample(example);
		for (Act act : actList) {
			hotActService.activeHotAct(act.getId());
		}
		return null;
	}

	public String migrateMsg(HttpServletRequest request, Model model) {
		// 迁移未读消息
		try {
			Set<String> unReadkeys = redisTemplate.keys("*.unreadActMsg");
			migrate(unReadkeys, "unread");
			Set<String> readkeys = redisTemplate.keys("*.readActMsg");
			migrate(readkeys, "read");
		} catch (Exception e) {
			log.error("migrate is error", e);
		}
		return null;
	}

	private void migrate(Set<String> keys, String type) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		for (String key : keys) {
			long count = redisTemplate.opsForList().size(key);
			MergerActMsg merge = new MergerActMsg();
			Map<String, List<ActMsg>> map = new HashMap<String, List<ActMsg>>();
			for (int i = 0; i < count; i++) {
				ActMsg actMsg = redisTemplate.opsForList().leftPop(key);
				if (actMsg == null) {
					log.info("actMsg is null key=" + key);
					continue;
				}
				List<ActMsg> actMsgs = null;

				String nowDate = sdf.format(actMsg.getDate());
				log.debug(actMsg.getUid() + ":" + actMsg.getType() + ":"
						+ nowDate);
				String mapKey = actMsg.getUid() + ":" + actMsg.getType() + ":"
						+ nowDate;
				if (map.get(mapKey) == null) {
					actMsgs = new ArrayList<ActMsg>();
				} else {
					actMsgs = map.get(mapKey);
				}
				actMsgs.add(actMsg);
				map.put(mapKey, actMsgs);
			}
			for (Map.Entry<String, List<ActMsg>> entry : map.entrySet()) {
				String str[] = entry.getKey().split(":");
				long uid = Long.valueOf(str[0]);
				MsgType msgType = MsgType.valueOf(str[1]);
				String nowDate = str[2];
				merge.setUid(uid);
				merge.setType(msgType);
				merge.setDate(sdf.parse(nowDate));
				merge.setMsgs(entry.getValue());
				if ("read".equals(type)) {
					String readKey = RedisKeyGenerator.genReadMsgsKey(
							getreceiverId(key),
							MergerActMsg.class.getSimpleName());
					redisMergerActMsgTemplate.opsForList().leftPush(readKey,
							merge);
				} else {
					msgService.sendMsg(getreceiverId(key), merge);
				}

			}
		}
		redisTemplate.delete(keys);
	}

	private int getreceiverId(String key) {
		try {
			String str[] = key.split("\\.");
			return Integer.parseInt(str[0]);
		} catch (Exception e) {
			return 0;
		}

	}

}
