/*
 *The code is written by 51juzhai, All rights is reserved
 */
package com.juzhai.act.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

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

import com.juzhai.act.InitData;
import com.juzhai.act.dao.IActDao;
import com.juzhai.act.exception.ActInputException;
import com.juzhai.act.mapper.ActMapper;
import com.juzhai.act.model.Act;
import com.juzhai.act.model.ActExample;
import com.juzhai.act.rabbit.message.ActIndexMessage;
import com.juzhai.act.rabbit.message.ActIndexMessage.ActionType;
import com.juzhai.act.service.IActService;
import com.juzhai.core.cache.RedisKeyGenerator;
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
	private InitData actInitData;
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
	@Value("${act.name.length.min}")
	private int actNameLengthMin = 2;
	@Value("${act.name.length.max}")
	private int actNameLengthMax = 20;
	@Value("${act.name.wordfilter.application}")
	private int actNameWordfilterApplication = 0;

	@Override
	public void verifyAct(long rawActId, String actCategoryIds) {
		Act act = actMapper.selectByPrimaryKey(rawActId);
		if (null != act) {
			act.setId(rawActId);
			act.setCategoryIds(actCategoryIds);
			act.setLastModifyTime(new Date());
			act.setActive(true);
			actMapper.updateByPrimaryKeySelective(act);

			// 加载Act
			actInitData.loadAct(act);
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
		if (null != act) {
			// 加载Act
			actInitData.loadAct(act);
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
		List<Act> synonymActList = new ArrayList<Act>(synonymIdList.size());
		for (long id : synonymIdList) {
			Act act = InitData.ACT_MAP.get(id);
			if (null != act) {
				synonymActList.add(act);
			}
		}
		return synonymActList;
	}

	@Override
	public void addSynonym(long actId1, long actId2) {
		removeSynonym(actId1, actId2);
		redisTemplate.opsForList().leftPush(
				RedisKeyGenerator.genActSynonymKey(actId1), actId2);
		redisTemplate.opsForList().leftPush(
				RedisKeyGenerator.genActSynonymKey(actId2), actId1);
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
	public void removeSynonym(long actId1, long actId2) {
		redisTemplate.opsForList().remove(
				RedisKeyGenerator.genActSynonymKey(actId1), 1, actId2);
		redisTemplate.opsForList().remove(
				RedisKeyGenerator.genActSynonymKey(actId2), 1, actId1);
	}

	@Override
	public List<Act> searchNewActs(Date startDate, Date endDate, int order) {
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
		return actMapper.selectByExample(example);
	}
}
