package com.juzhai.cms.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.juzhai.act.mapper.ActMapper;
import com.juzhai.act.mapper.SynonymActMapper;
import com.juzhai.act.mapper.UserActMapper;
import com.juzhai.act.model.Act;
import com.juzhai.act.model.SynonymActExample;
import com.juzhai.act.model.UserAct;
import com.juzhai.act.service.IActService;
import com.juzhai.act.service.IHotActService;
import com.juzhai.act.service.ISynonymActService;
import com.juzhai.act.service.IUserActService;
import com.juzhai.core.cache.RedisKeyGenerator;
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
		migrateSynonym(synonymKeys);
		for (String key : synonymKeys) {
			Long actId = getActId(key);
			if (actId == null) {
				continue;
			}
			List<Act> acts = actService.listSynonymActs(actId);
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
			addSynonymAct(actMaps);
			opRedisActSynonym(actMaps);
			opRedisActMsg();
			deleteScrapAct(actMaps);
		}
		redisTemplate.delete(synonymKeys);
		return null;
	}
	//合并同义词
	private void migrateSynonym(Set<String> synonymKeys){
		for(String key:synonymKeys){
			long count=redisTemplate.opsForList().size(key);
			Set<Long> set=new HashSet<Long>();
			for (int i = 0; i < count; i++) {
				Long actId=redisTemplate.opsForList().index(key, i);
				Set<String> sameSynKeys=findSameSynonym(synonymKeys, key, actId);
				for(String sameSynKey:sameSynKeys){
					long sameCount=redisTemplate.opsForList().size(sameSynKey);
					for(int j=0;j<sameCount;j++){
						set.add(redisTemplate.opsForList().leftPop(sameSynKey));
					}
				}
			}
			setSameSynoym(set, key, count);
			
		}
	}
	
	private void setSameSynoym(Set<Long> set,String key,long count){
		for (int i = 0; i < count; i++) {
			Long actId=redisTemplate.opsForList().leftPop(key);
			set.add(actId);
		}
		for(Long aId:set){
			redisTemplate.opsForList().leftPush(key, aId);
		}
		
	}
	
	private Set<String>  findSameSynonym(Set<String> synonymKeys,String k,Long actId){
		Set<String> keys=new HashSet<String>();
		for(String key:synonymKeys){
			if(!k.equals(key)){
				long count=redisTemplate.opsForList().size(key);
				for (int i = 0; i < count; i++) {
					if(redisTemplate.opsForList().index(key, i)==actId){
						keys.add(key);
						break;
					}
				}
			}
		}
		return keys;
	}

	private void deleteScrapAct(Map<Long, Long> actMaps) {
		// 删除废弃的act
		for (Entry<Long, Long> entry : actMaps.entrySet()) {
				actMapper.deleteByPrimaryKey(entry.getKey());
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
		for (int i = 0; i < acts.size(); i++) {
			if (hotActId == 0) {
				// 如果推荐列表里面没有择取流行度最高的
				Act hotAct = acts.get(acts.size() - 1);
				if (hotAct == null) {
					continue;
				}
				long aId = hotAct.getId();
				if (i != acts.size() - 1) {
					Act act = acts.get(i);
					if (act == null) {
						continue;
					}
					actMap.put(act.getId(), aId);
//					System.out.println("actid:" + act.getId() + "|hotActId:"
//							+ aId);
				}
			} else {
				Act act = acts.get(i);
				if (act == null) {
					continue;
				}
				if (act.getId() != hotActId) {
					actMap.put(act.getId(), hotActId);
//					System.out.println("actid:" + act.getId() + "|hotActId:"
//							+ hotActId);
				}
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
			// 删除被遗弃的act

		}
		Act hotAct = actService.getActById(hotActId);
		if (hotAct != null) {
			int oldPopularitry = hotAct.getPopularity();
			hotAct.setPopularity(oldPopularitry + popularity);
			actService.updateAct(hotAct, null);
		}
	}

	private void opTableUserAct(Map<Long, Long> actMaps) {
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
