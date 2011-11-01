/*
 *The code is written by 51juzhai, All rights is reserved
 */
package com.juzhai.act.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.rubyeye.xmemcached.MemcachedClient;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.TopDocs;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.juzhai.act.dao.IActDao;
import com.juzhai.act.exception.ActInputException;
import com.juzhai.act.mapper.ActMapper;
import com.juzhai.act.model.Act;
import com.juzhai.act.model.ActExample;
import com.juzhai.act.model.ActExample.Criteria;
import com.juzhai.act.rabbit.message.ActIndexMessage;
import com.juzhai.act.rabbit.message.ActIndexMessage.ActionType;
import com.juzhai.act.service.IActCategoryService;
import com.juzhai.act.service.IActService;
import com.juzhai.core.cache.MemcachedKeyGenerator;
import com.juzhai.core.cache.RedisKeyGenerator;
import com.juzhai.core.dao.Limit;
import com.juzhai.core.lucene.searcher.IndexSearcherTemplate;
import com.juzhai.core.lucene.searcher.IndexSearcherTemplate.SearcherCallback;
import com.juzhai.core.util.StringUtil;
import com.juzhai.wordfilter.service.IWordFilterService;

@Service
public class ActService implements IActService {

	private final Log log = LogFactory.getLog(getClass());

	@Autowired
	private ActMapper actMapper;
	@Autowired
	private IActDao actDao;
	@Autowired
	private RabbitTemplate actIndexCreateRabbitTemplate;
	@Autowired
	private IndexSearcherTemplate actIndexSearcherTemplate;
	@Autowired
	private RedisTemplate<String, Long> redisTemplate;
	@Autowired
	private IWordFilterService wordFilterService;
	@Autowired
	private IActCategoryService actCategoryService;
	@Autowired
	private MemcachedClient memcachedClient;
	@Value("${act.name.length.min}")
	private int actNameLengthMin = 2;
	@Value("${act.name.length.max}")
	private int actNameLengthMax = 20;
	@Value("${act.name.wordfilter.application}")
	private int actNameWordfilterApplication = 0;
	@Value("${act.cache.expire.time}")
	private int actCacheExpireTime = 0;

	@Override
	public boolean actExist(long actId) {
		try {
			Act act = memcachedClient.get(MemcachedKeyGenerator
					.genActCacheKey(actId));
			if (null != act) {
				return true;
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		ActExample example = new ActExample();
		example.createCriteria().andIdEqualTo(actId);
		return actMapper.countByExample(example) > 0;
	}

	@Override
	public Act getActById(long actId) {
		String key = MemcachedKeyGenerator.genActCacheKey(actId);
		Act act = null;
		try {
			act = memcachedClient.get(key);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		if (null == act) {
			act = actMapper.selectByPrimaryKey(actId);
			if (null != act) {
				try {
					memcachedClient.set(key, actCacheExpireTime, act);
				} catch (Exception e) {
					log.error(e.getMessage(), e);
				}
			}
		}
		return act;
	}

	@Override
	public Map<Long, Act> getMultiActByIds(List<Long> actIds) {
		// 判断是否为空
		if (CollectionUtils.isEmpty(actIds)) {
			return Collections.emptyMap();
		}
		// 组装Key
		Map<String, Long> actKeyMap = new HashMap<String, Long>(actIds.size());
		for (long actId : actIds) {
			actKeyMap.put(MemcachedKeyGenerator.genActCacheKey(actId), actId);
		}
		// 缓存搜索
		Map<Long, Act> actMap = new HashMap<Long, Act>();
		try {
			Map<String, Act> actCacheMap = memcachedClient.get(actKeyMap
					.keySet());
			for (Map.Entry<String, Act> entry : actCacheMap.entrySet()) {
				actMap.put(actKeyMap.get(entry.getKey()), entry.getValue());
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}

		// 数据库搜索未被缓存的
		if (actMap.size() != actIds.size()) {
			// 组装需要数据库搜索的ActId
			List<Long> searchActIds = new ArrayList<Long>();
			for (long actId : actIds) {
				if (!actMap.containsKey(actId)) {
					searchActIds.add(actId);
				}
			}
			if (CollectionUtils.isNotEmpty(searchActIds)) {
				// 数据库搜索
				ActExample example = new ActExample();
				example.createCriteria().andIdIn(searchActIds);
				List<Act> actList = actMapper.selectByExample(example);
				for (Act act : actList) {
					// 更新缓存
					try {
						memcachedClient.set(MemcachedKeyGenerator
								.genActCacheKey(act.getId()),
								actCacheExpireTime, act);
					} catch (Exception e) {
						log.error(e.getMessage(), e);
					}
					// 放入返回Map中
					actMap.put(act.getId(), act);
				}
			}
		}

		return actMap;
	}

	@Override
	public List<Act> getActListByIds(List<Long> actIds) {
		// 判断是否为空
		if (CollectionUtils.isEmpty(actIds)) {
			return Collections.emptyList();
		}
		List<Act> actList = new ArrayList<Act>(actIds.size());
		Map<Long, Act> actMap = getMultiActByIds(actIds);
		for (long actId : actIds) {
			Act act = actMap.get(actId);
			if (null != act) {
				actList.add(act);
			}
		}
		return actList;
	}

	@Override
	public void verifyAct(long rawActId, String actCategoryIds) {
		Act act = actMapper.selectByPrimaryKey(rawActId);
		if (null != act) {
			act.setId(rawActId);
			act.setCategoryIds(actCategoryIds);
			act.setLastModifyTime(new Date());
			act.setActive(true);
			actMapper.updateByPrimaryKeySelective(act);

			// // 加载Act
			// actInitData.loadAct(act);
			clearActCache(act.getId());
		}
	}

	@Override
	public Act createAct(long uid, String actName, List<Long> categoryIds)
			throws ActInputException {
		try {
			if (wordFilterService.wordFilter(actNameWordfilterApplication, uid,
					null, actName.getBytes("GBK")) < 0) {
				throw new ActInputException(ActInputException.ACT_NAME_FORBID);
			}
		} catch (IOException e) {
			log.error("Wordfilter service down.", e);
		}
		long length = StringUtil.chineseLength(actName);
		if (length < actNameLengthMin || length > actNameLengthMax) {
			throw new ActInputException(ActInputException.ACT_NAME_INVALID);
		}
		Act act = actDao.insertAct(uid, actName, null);
		updateActCategory(act.getId(),categoryIds);
		if (null != act) {
			// // 加载Act
			// actInitData.loadAct(act);
			if (log.isDebugEnabled()) {
				log.debug("load new act to InitData");
			}
			// add 索引
			ActIndexMessage msgMessage = new ActIndexMessage();
			msgMessage.buildBody(act).buildActionType(ActionType.CREATE);
			actIndexCreateRabbitTemplate.convertAndSend(msgMessage);
			if (log.isDebugEnabled()) {
				log.debug("send act index create message");
			}
		}
		return act;
	}

	private void updateActCategory(long id, List<Long> categoryIds) {
		if (categoryIds != null) {
			actCategoryService.updateActCategory(id, categoryIds);
		}
	}

	@Override
	public Act createAct(Act act, List<Long> categoryIds)
			throws ActInputException {
		if (act == null || act.getName() == null || act.getCreateUid() == 0) {
			throw new ActInputException(ActInputException.ACT_NAME_FORBID);
		}
		try {
			if (wordFilterService.wordFilter(actNameWordfilterApplication,
					act.getCreateUid(), null, act.getName().getBytes("GBK")) < 0) {
				throw new ActInputException(ActInputException.ACT_NAME_FORBID);
			}
		} catch (IOException e) {
			log.error("Wordfilter service down.", e);
		}
		long length = StringUtil.chineseLength(act.getName());
		if (length < actNameLengthMin || length > actNameLengthMax) {
			throw new ActInputException(ActInputException.ACT_NAME_INVALID);
		}
		actDao.inserAct(act, categoryIds);
		updateActCategory(act.getId(),categoryIds);
		if (null != act) {
			// // 加载Act
			// actInitData.loadAct(act);
			if (log.isDebugEnabled()) {
				log.debug("load new act to InitData");
			}
			// add 索引
			ActIndexMessage msgMessage = new ActIndexMessage();
			msgMessage.buildBody(act).buildActionType(ActionType.CREATE);
			actIndexCreateRabbitTemplate.convertAndSend(msgMessage);
			if (log.isDebugEnabled()) {
				log.debug("send act index create message");
			}
		}
		return act;
	}

	@Override
	public Act getActByName(String name) {
		return actDao.selectActByName(StringUtils.trim(name));
	}

	@Override
	public void inOrDePopularity(long actId, int p) {
		if (p != 0) {
			actDao.incrOrDecrPopularity(actId, p);
		}
	}

	@Override
	public List<String> indexSearchName(final String queryString,
			final int count) {
		return actIndexSearcherTemplate.excute(new SearcherCallback() {
			@SuppressWarnings("unchecked")
			@Override
			public <T> T doCallback(IndexSearcher indexSearcher)
					throws IOException {
				TermQuery termQuery = new TermQuery(new Term("name",
						StringUtils.trim(queryString)));
				TopDocs topDocs = indexSearcher.search(termQuery, count);
				List<String> actNameList = new ArrayList<String>(
						topDocs.totalHits);
				for (ScoreDoc scoreDoc : topDocs.scoreDocs) {
					Document doc = indexSearcher.doc(scoreDoc.doc);
					actNameList.add(doc.get("name"));
				}
				return (T) actNameList;
			}
		});
	}

	@Override
	public List<Long> listSynonymIds(long actId) {
		// TODO 试验，set和sortedset能否共同使用inter方法
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
		return getActListByIds(synonymIdList);
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
		Act act = getActByName(actName2);
		if (null == act) {
			act = createAct(1L, actName2, null);
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
	public List<Act> searchNewActs(Date startDate, Date endDate, int order,
			int firstResult, int maxResults) {
		ActExample example = new ActExample();
		example.createCriteria().andCreateTimeBetween(startDate, endDate);
		switch (order) {
		case 1:
			example.setOrderByClause("create_time desc, id desc");
			break;
		default:
			example.setOrderByClause("popularity desc, id desc");
			break;
		}
		example.setLimit(new Limit(firstResult, maxResults));
		return actMapper.selectByExample(example);
	}

	@Override
	public int countNewActs(Date startDate, Date endDate) {
		ActExample example = new ActExample();
		example.createCriteria().andCreateTimeBetween(startDate, endDate);
		return actMapper.countByExample(example);
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
		// TODO 特殊处理
		return getActListByIds(shieldActIds);
	}

	@Override
	public boolean isShieldAct(long actId) {
		Double score = redisTemplate.opsForZSet().score(
				RedisKeyGenerator.genActShieldKey(), actId);
		return null != score && score > 0;
	}

	private void clearActCache(long actId) {
		try {
			memcachedClient.deleteWithNoReply(MemcachedKeyGenerator
					.genActCacheKey(actId));
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}

	@Override
	public List<Act> searchActs(Date beginDate, Date endDate, String name,
			String catId, int firstResult, int maxResults) {
		ActExample example = new ActExample();
		Criteria criteria = example.createCriteria();
		if (beginDate != null && endDate != null) {
			criteria.andCreateTimeBetween(beginDate, endDate);
		}
		if (!StringUtils.isEmpty(name)) {
			criteria.andNameEqualTo(name);
		}
		if (!StringUtils.isEmpty(catId)) {
			criteria.andCategoryIdsLike(catId);
		}
		example.setLimit(new Limit(firstResult, maxResults));
		return actMapper.selectByExample(example);
	}

	@Override
	public int searchActsCount(Date beginDate, Date endDate, String name,
			String catId) {
		ActExample example = new ActExample();
		Criteria criteria = example.createCriteria();
		if (beginDate != null && endDate != null) {
			criteria.andCreateTimeBetween(beginDate, endDate);
		}
		if (!StringUtils.isEmpty(name)) {
			criteria.andNameEqualTo(name);
		}
		if (!StringUtils.isEmpty(catId)) {
			criteria.andCategoryIdsLike(catId);
		}
		return actMapper.countByExample(example);
	}

	@Override
	public void updateAct(Act act, List<Long> categoryIds) {
		actMapper.updateByPrimaryKey(act);
		updateActCategory(act.getId(), categoryIds);
		clearActCache(act.getId());
	}

}
