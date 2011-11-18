package com.juzhai.cms.controller;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.juzhai.act.mapper.ActMapper;
import com.juzhai.act.mapper.SynonymActMapper;
import com.juzhai.act.mapper.UserActMapper;
import com.juzhai.act.model.Act;
import com.juzhai.act.model.UserAct;
import com.juzhai.act.service.IActCategoryService;
import com.juzhai.act.service.IActService;
import com.juzhai.act.service.IHotActService;
import com.juzhai.act.service.ISynonymActService;
import com.juzhai.act.service.IUserActService;
import com.juzhai.msg.bean.MergerActMsg;

@Controller
@RequestMapping("/cms")
public class MigrateActController {
	private final Log log = LogFactory.getLog(getClass());
	@Autowired
	private IActService actService;
	@Autowired
	private IUserActService userActService;
	@Autowired
	private UserActMapper userActMapper;
	@Autowired
	private IHotActService hotActService;
	@Autowired
	private IActCategoryService actCategoryService;
	@Autowired
	private ISynonymActService synonymActService;
	@Autowired
	private ActMapper actMapper;
	@Autowired
	private SynonymActMapper synonymActMapper;
	@Autowired
	private RedisTemplate<String, Long> redisTemplate;
	@Autowired
	private RedisTemplate<String, MergerActMsg> redisMergerActMsgTemplate;

	@RequestMapping(value = "migrateAct")
	public String migrateAct() {
		Set<String> synonymKeys = redisTemplate.keys("*synonym");
		// migrateSynonym(synonymKeys);
		for (String key : synonymKeys) {
			List<Long> actIds = actService.listSynonymIds(getActId(key));
			for (long actId : actIds) {
				actService.addSynonym(getActId(key), actId);
			}
		}

		Map<Long, Long> actMaps = new HashMap<Long, Long>();
		for (String key : synonymKeys) {
			Long actId = getActId(key);
			if (actId == null) {
				continue;
			}
			List<Act> acts = actService.listSynonymActs(actId);
			acts.add(actService.getActById(actId));
			Collections.sort(acts, new Comparator<Act>() {
				@Override
				public int compare(Act o1, Act o2) {
					if (o1 == null || o2 == null) {
						return 0;
					}
					int p1 = o1.getPopularity();
					int p2 = o2.getPopularity();
					if (p1 > p2) {
						return 1;
					} else if (p1 < p2) {
						return -1;
					} else {
						long a1 = o1.getId();
						long a2 = o2.getId();
						if (a1 > a2) {
							return 1;
						} else {
							return -1;
						}

					}
				}
			});
			actMaps.putAll(getActMap(acts));
		}
		opTableAct(actMaps);
		opTableUserAct(actMaps);
		addSynonymAct(actMaps);
		opRedisActMsg();
		deleteScrapAct(actMaps);
		redisTemplate.delete(synonymKeys);
		log.debug("migrateAct is  success");
		return null;
	}

	private void deleteScrapAct(Map<Long, Long> actMaps) {
		// 删除废弃的act
		for (Entry<Long, Long> entry : actMaps.entrySet()) {
			actMapper.deleteByPrimaryKey(entry.getKey());
			actCategoryService.deleteActCategory(entry.getKey());

		}

	}

	private Long getActId(String key) {
		try {
			String str[] = key.split("\\.");
			return Long.parseLong(str[0]);
		} catch (Exception e) {
			return Long.valueOf("0");
		}
	}

	/**
	 * 将被遗弃的词迁移到指向词
	 * 
	 * @param actMaps
	 */
	private void addSynonymAct(Map<Long, Long> actMaps) {
		for (Entry<Long, Long> entry : actMaps.entrySet()) {
			Act act = actService.getActById(entry.getKey());
			if (act == null) {
				continue;
			}
			String name = act.getName();
			if (!synonymActService.isExist(name)) {
				synonymActService.synonymAct(name, entry.getValue());
			}

		}
	}

	/**
	 * 删除所有消息
	 */
	private void opRedisActMsg() {
		Set<String> keys = redisMergerActMsgTemplate.keys("*.MergerActMsg");
		if (keys != null && keys.size() > 0) {
			redisMergerActMsgTemplate.delete(keys);
		}
	}

	/**
	 * 
	 * @param acts
	 * @return Map<被遗弃的Act，指向的Act>）
	 */
	private Map<Long, Long> getActMap(List<Act> acts) {
		Map<Long, Long> actMap = new HashMap<Long, Long>();
		long hotActId = 0;
		for (Act act : acts) {
			// 如果在推荐列表里面
			if (hotActService.isExistHotAct(act.getId())) {
				hotActId = act.getId();
				break;
			}
		}
		if (hotActId == 0) {
			// 如果推荐列表里面没有择取流行度最高的
			Act hotAct = acts.get(acts.size() - 1);
			hotActId = hotAct.getId();
		}
		for (int i = 0; i < acts.size(); i++) {
			Act act = acts.get(i);
			if (act == null) {
				continue;
			}
			if (act.getId() != hotActId) {
				actMap.put(act.getId(), hotActId);
			}

		}
		return actMap;
	}

	/**
	 * 将被遗弃的act的流行度累加到指向的Act
	 * 
	 * @param actMaps
	 */
	private void opTableAct(Map<Long, Long> actMaps) {
		for (Entry<Long, Long> entry : actMaps.entrySet()) {
			long hotActId = entry.getValue();
			long actId = entry.getKey();
			Act act = actService.getActById(actId);
			int popularity = 0;
			if (act != null) {
				popularity = act.getPopularity();
			}
			Act hotAct = actService.getActById(hotActId);
			if (hotAct != null) {
				int oldPopularitry = hotAct.getPopularity();
				hotAct.setPopularity(oldPopularitry + popularity);
				actService.updateAct(hotAct, null);
			}
		}

	}

	private void opTableUserAct(Map<Long, Long> actMaps) {
		for (Entry<Long, Long> entry : actMaps.entrySet()) {
			long actId = entry.getKey();
			long hotActId = entry.getValue();
			// 找出找出所有添加过被遗弃的act的人
			int count = userActService.countUserActByActId(0, actId);
			List<UserAct> userActs = userActService.listUserActByActId(0,
					actId, 0, count);
			for (UserAct scrapUserAct : userActs) {
				UserAct newUserAct = userActService.getUserAct(
						scrapUserAct.getUid(), hotActId);
				if (newUserAct == null) {
					// 如果这个人没有添加过新的指向词则更新该条记录
					scrapUserAct.setActId(hotActId);
					userActMapper.updateByPrimaryKey(scrapUserAct);
				} else {
					// 添加过新的指向词则累加新指向词热门度。再删除该条记录
					newUserAct.setHotLev(newUserAct.getHotLev()
							+ scrapUserAct.getHotLev());
					userActMapper.updateByPrimaryKey(newUserAct);
					userActMapper.deleteByPrimaryKey(scrapUserAct.getId());
				}
			}

		}
	}

}
