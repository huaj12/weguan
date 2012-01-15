package com.juzhai.act.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.juzhai.act.InitData;
import com.juzhai.act.exception.ActInputException;
import com.juzhai.act.mapper.SynonymActMapper;
import com.juzhai.act.model.Act;
import com.juzhai.act.model.SynonymAct;
import com.juzhai.act.model.SynonymActExample;
import com.juzhai.act.service.ISynonymActService;
import com.juzhai.core.cache.RedisKeyGenerator;
import com.juzhai.core.dao.Limit;

@Service
public class SynonymActService implements ISynonymActService {
	private final Log log = LogFactory.getLog(getClass());
	@Autowired
	private SynonymActMapper synonymActMapper;
	@Autowired
	private ActService actService;
	@Autowired
	private RedisTemplate<String, Long> redisTemplate;

	@Override
	public boolean synonymAct(String name, String actName) {
		if (StringUtils.isEmpty(name)) {
			log.error("synonymAct name is null");
			return false;
		}
		Act act = actService.getActByName(actName);
		if (act == null) {
			log.error("synonymAct actName is not exist");
			return false;
		}
		return synonymAct(actName, act.getId());
	}

	@Override
	public boolean synonymAct(String name, long actId) {
		if (StringUtils.isEmpty(name)) {
			log.error("synonymAct name is null");
			return false;
		}
		if (actId == 0) {
			log.error("synonymAct actId is null");
			return false;
		}
		try {
			SynonymAct syn = new SynonymAct();
			syn.setActId(actId);
			syn.setCreateTime(new Date());
			syn.setLastModifyTime(new Date());
			syn.setName(name);
			synonymActMapper.insert(syn);
			// 将指向词加入内存
			InitData.SYNONYM_ACT.put(name, actId);
		} catch (Exception e) {
			log.error("synonymAct is error." + e.getMessage());
			return false;
		}
		return true;
	}

	@Override
	public List<SynonymAct> getSysonymActs(int firstResult, int maxResults) {
		SynonymActExample example = new SynonymActExample();
		example.setLimit(new Limit(firstResult, maxResults));
		example.setOrderByClause("last_modify_time desc");
		return synonymActMapper.selectByExample(example);
	}

	@Override
	public int countSysonymActs() {
		SynonymActExample example = new SynonymActExample();
		return synonymActMapper.countByExample(example);
	}

	@Override
	public boolean updateSynonymAct(Long id, long actId) {
		try {
			if (id == null) {
				return false;
			}
			SynonymAct synAct = synonymActMapper.selectByPrimaryKey(id);
			if (synAct == null) {
				return false;
			}
			if (actId == 0) {
				return false;
			}
			synAct.setActId(actId);
			synAct.setLastModifyTime(new Date());
			synonymActMapper.updateByPrimaryKey(synAct);
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	@Override
	public boolean isExist(String name) {
		SynonymActExample example = new SynonymActExample();
		example.createCriteria().andNameEqualTo(name);
		if (synonymActMapper.countByExample(example) > 0) {
			return true;
		}
		return false;
	}

	@Override
	public List<Long> listSynonymIds(long actId) {
		List<Long> synonymList = redisTemplate.opsForList().range(
				RedisKeyGenerator.genActSynonymKey(actId), 0, -1);
		if (synonymList == null) {
			synonymList = Collections.emptyList();
		}
		return synonymList;
	}

	@Override
	public List<Act> listSynonymActs(long actId) {
		List<Long> synonymIdList = listSynonymIds(actId);
		return actService.getActListByIds(synonymIdList);
	}

	@Override
	public void addSynonym(long actId1, long actId2) {
		List<Long> synonymIds1 = listSynonymIds(actId1);
		synonymIds1.add(actId1);
		List<Long> synonymIds2 = listSynonymIds(actId2);
		synonymIds2.add(actId2);
		for (long id1 : synonymIds1) {
			for (long id2 : synonymIds2) {
				if (id1 != id2) {
					removeSynonymEachother(id1, id2);
					redisTemplate.opsForList().leftPush(
							RedisKeyGenerator.genActSynonymKey(id1), id2);
					redisTemplate.opsForList().leftPush(
							RedisKeyGenerator.genActSynonymKey(id2), id1);
				}
			}
		}
	}

	@Override
	public void addSynonym(long actId1, String actName2)
			throws ActInputException {
		Act act = actService.getActByName(actName2);
		if (null == act) {
			act = actService.createAct(1L, actName2, null);
			if (null == act) {
				log.error("add act by name failed.[createUid:" + 1L
						+ ", actName:" + actName2 + "]");
				throw new ActInputException(ActInputException.ACT_NAME_INVALID);
			}
		}
		addSynonym(actId1, act.getId());
	}

	@Override
	public void removeSynonym(long actId, long removeId) {
		List<Long> synonymIds = listSynonymIds(actId);
		synonymIds.add(actId);
		for (long id : synonymIds) {
			if (id != removeId) {
				removeSynonymEachother(id, removeId);
			}
		}
	}

	private void removeSynonymEachother(long actId1, long actId2) {
		redisTemplate.opsForList().remove(
				RedisKeyGenerator.genActSynonymKey(actId1), 1, actId2);
		redisTemplate.opsForList().remove(
				RedisKeyGenerator.genActSynonymKey(actId2), 1, actId1);
	}

	@Override
	public void addActShield(long actId) {
		redisTemplate.opsForZSet().add(RedisKeyGenerator.genActShieldKey(),
				actId, System.currentTimeMillis());
	}

	@Override
	public void removeActShield(long actId) {
		redisTemplate.opsForZSet().remove(RedisKeyGenerator.genActShieldKey(),
				actId);
	}

	@Override
	public List<Long> listShieldActIds() {
		Set<Long> shieldActIds = redisTemplate.opsForZSet().reverseRange(
				RedisKeyGenerator.genActShieldKey(), 0, -1);
		if (null == shieldActIds) {
			return Collections.emptyList();
		}
		return new ArrayList<Long>(shieldActIds);
	}

	@Override
	public List<Act> listShieldActs() {
		List<Long> shieldActIds = listShieldActIds();
		return actService.getActListByIds(shieldActIds);
	}

	@Override
	public boolean isShieldAct(long actId) {
		Double score = redisTemplate.opsForZSet().score(
				RedisKeyGenerator.genActShieldKey(), actId);
		return null != score && score > 0;
	}
}
