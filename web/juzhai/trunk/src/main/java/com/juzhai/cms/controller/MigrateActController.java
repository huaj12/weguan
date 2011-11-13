package com.juzhai.cms.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.juzhai.act.mapper.UserActMapper;
import com.juzhai.act.model.Act;
import com.juzhai.act.model.UserAct;
import com.juzhai.act.service.IActService;
import com.juzhai.act.service.IHotActService;
import com.juzhai.act.service.IUserActService;
import com.juzhai.core.cache.RedisKeyGenerator;
import com.juzhai.core.pager.PagerManager;
import com.juzhai.msg.bean.MergerActMsg;

@Controller
@RequestMapping("/cms")
public class MigrateActController {

	@Autowired
	private IActService actService;
	@Autowired
	private IUserActService userActService;
	@Autowired
	private UserActMapper userActMapper;
	@Autowired
	private IHotActService hotActService;
	@Autowired
	private RedisTemplate<String, Long> redisTemplate;
	@Autowired
	private RedisTemplate<String, MergerActMsg> redisMergerActMsgTemplate;
	@RequestMapping(value = "migrateAct")
	public String migrateAct() {
		try {
			Date endDate = new Date();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-mm-dd");
			Date beginDate = sdf.parse("2010-12-12");
			PagerManager pager = new PagerManager(1, 10,
					actService.searchActsCount(beginDate, endDate, null));
			for (int i = 1; i <= pager.getTotalPage(); i++) {
				List<Act> list = actService.searchActs(beginDate, endDate,
						null, (i - 1) * 50, 50);
				if (list == null) {
					break;
				}
				for (Act act : list) {

					List<Act> acts = actService.listSynonymActs(act.getId());
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
								return 0;
							}
						}
					});
					Map<Long, Long> actMaps = getActMap(acts);
					opTableAct(actMaps);
					opTableUserAct(actMaps);
					opRedisActSynonym(actMaps);
					opRedisActMsg();
				}
			}
		} catch (Exception e) {

		}
		return null;
	}

	/**
	 * 删除遗弃的actId 在redis里面的近义词
	 * 
	 * @param actMaps
	 */
	private void opRedisActSynonym(Map<Long, Long> actMaps) {
		Set<Long> acts = actMaps.keySet();
		Set<String> keys = new HashSet<String>();
		for (Long actId : acts) {
			keys.add(RedisKeyGenerator.genActSynonymKey(actId));
		}
		if (keys != null && keys.size() > 0) {
			redisTemplate.delete(keys);
		}
	}
	/**
	 * 删除所有消息
	 */
	public void opRedisActMsg() {
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
	public Map<Long, Long> getActMap(List<Act> acts) {
		Map<Long, Long> actMap = new HashMap<Long, Long>();
		long hotActId = 0;
		for (Act act : acts) {
			// 如果在推荐列表里面
			if (hotActService.isExistHotAct(act.getId())) {
				hotActId = act.getId();
				break;
			}
		}
		for (int i = 0; i < acts.size(); i++) {
			if (hotActId == 0) {
				// 如果推荐列表里面没有择取流行度最高的
				Act hotAct = acts.get(acts.size() - 1);
				if (hotAct == null) {
					continue;
				}
				hotActId = hotAct.getId();
			}
			if (i != acts.size() - 1) {

				Act act = acts.get(i);
				if (act == null) {
					continue;
				}
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
	public void opTableAct(Map<Long, Long> actMaps) {
		int popularity = 0;
		long hotActId = 0;
		for (Entry<Long, Long> entry : actMaps.entrySet()) {
			if (hotActId == 0) {
				hotActId = entry.getValue();
			}
			long actId = entry.getKey();
			Act act = actService.getActById(actId);
			// 累加流行度
			if (act != null) {
				popularity = popularity + act.getPopularity();
			}
		}
		Act hotAct = actService.getActById(hotActId);
		if (hotAct != null) {
			int oldPopularitry = hotAct.getPopularity();
			hotAct.setPopularity(oldPopularitry + popularity);
			actService.updateAct(hotAct, null);
		}
	}

	public void opTableUserAct(Map<Long, Long> actMaps) {
		for (Entry<Long, Long> entry : actMaps.entrySet()) {
			long actId = entry.getKey();
			long hotActId = entry.getValue();
			int count = userActService.countUserActByActId(actId);
			// 找出找出所有添加过被遗弃的act的人
			List<UserAct> userActs = userActService.listUserActByActId(actId,
					0, count);
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
