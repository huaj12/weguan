package com.juzhai.search.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.BooleanClause.Occur;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.NumericRangeQuery;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.search.TopScoreDocCollector;
import org.apache.lucene.util.Version;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.juzhai.core.lucene.searcher.IndexSearcherTemplate;
import com.juzhai.core.lucene.searcher.IndexSearcherTemplate.SearcherCallback;
import com.juzhai.passport.mapper.ProfileMapper;
import com.juzhai.passport.model.Profile;
import com.juzhai.passport.model.ProfileExample;
import com.juzhai.passport.service.IProfileService;
import com.juzhai.search.rabbit.message.ActionType;
import com.juzhai.search.rabbit.message.ProfileIndexMessage;
import com.juzhai.search.service.IProfileSearchService;

@Service
public class ProfileSearchService implements IProfileSearchService {
	private final Log log = LogFactory.getLog(getClass());
	@Autowired
	private IProfileService profileService;
	@Autowired
	private RabbitTemplate profileIndexCreateRabbitTemplate;
	@Autowired
	private IndexSearcherTemplate profileIndexSearcherTemplate;
	@Autowired
	private Analyzer profileIKAnalyzer;
	@Autowired
	private ProfileMapper profileMapper;

	@Override
	public void createIndex(long uid) {
		// TODO (review)
		// 考虑一下，如果放入rabbitmq的，只有Id，然后在listener里去数据库搜，是不是对于rabbitmq来说性能能提升？
		Profile profile = profileService.getProfile(uid);
		ProfileIndexMessage msgMessage = new ProfileIndexMessage();
		msgMessage.buildBody(profile).buildActionType(ActionType.CREATE);
		profileIndexCreateRabbitTemplate.convertAndSend(msgMessage);
	}

	@Override
	public void updateIndex(long uid) {
		// TODO (review)
		// 考虑一下，如果放入rabbitmq的，只有Id，然后在listener里去数据库搜，是不是对于rabbitmq来说性能能提升？
		Profile profile = profileService.getProfile(uid);
		ProfileIndexMessage msgMessage = new ProfileIndexMessage();
		msgMessage.buildBody(profile).buildActionType(ActionType.UPDATE);
		profileIndexCreateRabbitTemplate.convertAndSend(msgMessage);

	}

	@Override
	public void deleteIndex(long uid) {
		// TODO (review)
		// 考虑一下，如果放入rabbitmq的，只有Id，然后在listener里去数据库搜，是不是对于rabbitmq来说性能能提升？
		Profile profile = profileService.getProfile(uid);
		ProfileIndexMessage msgMessage = new ProfileIndexMessage();
		msgMessage.buildBody(profile).buildActionType(ActionType.DELETE);
		profileIndexCreateRabbitTemplate.convertAndSend(msgMessage);

	}

	@Override
	public List<Profile> queryProfile(final long city, final long town,
			final Integer gender, final int minYear, final int maxYear,
			final List<String> educations, final int minMonthlyIncome,
			final int maxMonthlyIncome, final boolean isMoreIncome,
			final String home, final List<Long> constellationIds,
			final String house, final String car, final int minHeight,
			final int maxHeight, final int firstResult, final int maxResults) {
		return profileIndexSearcherTemplate.excute(new SearcherCallback() {
			@SuppressWarnings("unchecked")
			@Override
			public <T> T doCallback(IndexSearcher indexSearcher)
					throws IOException {
				Query query = getQuery(city, town, gender, minYear, maxYear,
						educations, minMonthlyIncome, maxMonthlyIncome,
						isMoreIncome, home, constellationIds, house, car,
						minHeight, maxHeight);
				TopScoreDocCollector collector = TopScoreDocCollector.create(
						firstResult + maxResults, false);
				indexSearcher.search(query, collector);
				TopDocs topDocs = collector.topDocs(firstResult, maxResults);
				List<Long> uids = new ArrayList<Long>(maxResults);
				for (ScoreDoc scoreDoc : topDocs.scoreDocs) {
					Document doc = indexSearcher.doc(scoreDoc.doc);
					uids.add(Long.valueOf(doc.get("uid")));
				}
				List<Profile> list = Collections.emptyList();
				if (CollectionUtils.isNotEmpty(uids)) {
					// TODO (review) 这里list初始化干嘛呢？
					list = new ArrayList<Profile>(uids.size());
					ProfileExample example = new ProfileExample();
					example.createCriteria().andUidIn(uids);
					// TODO (review) 确定搜出来的顺序，是你要的顺序？
					list = profileMapper.selectByExample(example);
				}
				return (T) list;
			}
		});
	}

	@Override
	public int countQqueryProfile(final long city, final long town,
			final Integer gender, final int minYear, final int maxYear,
			final List<String> educations, final int minMonthlyIncome,
			final int maxMonthlyIncome, final boolean isMoreIncome,
			final String home, final List<Long> constellationIds,
			final String house, final String car, final int minHeight,
			final int maxHeight) {
		return profileIndexSearcherTemplate.excute(new SearcherCallback() {
			@SuppressWarnings("unchecked")
			@Override
			public <T> T doCallback(IndexSearcher indexSearcher)
					throws IOException {
				Query query = getQuery(city, town, gender, minYear, maxYear,
						educations, minMonthlyIncome, maxMonthlyIncome,
						isMoreIncome, home, constellationIds, house, car,
						minHeight, maxHeight);
				// TODO (review) 下面的参数1，表示什么意思？没理解
				TopDocs topDocs = indexSearcher.search(query, 1);
				return (T) new Integer(topDocs.totalHits);
			}
		});
	}

	private Query getQuery(long city, long town, Integer gender, int minYear,
			int maxYear, List<String> educations, int minMonthlyIncome,
			int maxMonthlyIncome, boolean isMoreIncome, String home,
			List<Long> constellationIds, String house, String car,
			int minHeight, int maxHeight) {
		BooleanQuery query = new BooleanQuery();
		// 身高
		// TODO (done) 会不会存在，只有下限，或者只有上限？会存在但是代码没问题
		if (minHeight > 0 || maxHeight > 0) {
			Query heightQuery = NumericRangeQuery.newIntRange("height",
					minHeight, maxHeight, true, true);
			query.add(heightQuery, Occur.MUST);
		}
		// 购车情况
		if (StringUtils.isNotEmpty(car)) {
			Query carQuery = new TermQuery(new Term("car", car));
			query.add(carQuery, Occur.MUST);
		}
		// 居所条件
		if (StringUtils.isNotEmpty(house)) {
			Query placeQuery = new TermQuery(new Term("house", house));
			query.add(placeQuery, Occur.MUST);
		}
		if (CollectionUtils.isNotEmpty(constellationIds)) {
			BooleanQuery queryConstellation = new BooleanQuery();
			for (Long constellationId : constellationIds) {
				Query categoryIdQuery = new TermQuery(new Term(
						"constellationId", String.valueOf(constellationId)));
				queryConstellation.add(categoryIdQuery, Occur.SHOULD);
			}
			query.add(queryConstellation, Occur.MUST);
		}
		if (StringUtils.isNotEmpty(home)) {
			QueryParser homeParser = new QueryParser(Version.LUCENE_33, "home",
					profileIKAnalyzer);
			try {
				query.add(homeParser.parse(home), Occur.MUST);
			} catch (ParseException e) {
				log.error("homeParser is error" + e.getMessage());
			}

		}
		// TODO (review) 没看懂，解释一下
		if (minMonthlyIncome > 0 || maxMonthlyIncome > 0) {
			// 选取xx以上
			if (isMoreIncome) {
				Query incomeQuery = NumericRangeQuery.newIntRange(
						"minIncomeNum", minMonthlyIncome, 0, true, true);
				query.add(incomeQuery, Occur.MUST);
			} else {
				Query minMonthlyIncomeQuery = new TermQuery(new Term(
						"minMonthlyIncome", String.valueOf(minMonthlyIncome)));
				Query maxMonthlyIncomeQuery = new TermQuery(new Term(
						"maxMonthlyIncome", String.valueOf(maxMonthlyIncome)));
				query.add(minMonthlyIncomeQuery, Occur.MUST);
				query.add(maxMonthlyIncomeQuery, Occur.MUST);
			}
		}

		// 教育经历
		if (CollectionUtils.isNotEmpty(educations)) {
			BooleanQuery queryEducation = new BooleanQuery();
			for (String education : educations) {
				queryEducation.add(new TermQuery(new Term("education",
						education)), Occur.SHOULD);
			}
			query.add(queryEducation, Occur.MUST);
		}

		// 年龄
		// TODO (done) 会不会存在，只有下限，或者只有上限？类似于身高 会存在但是代码没问题
		if (minYear > 0 || maxYear > 0) {
			Query ageQuery = NumericRangeQuery.newIntRange("age", minYear,
					maxYear, true, true);
			query.add(ageQuery, Occur.MUST);
		}
		// 性别
		if (null != gender) {
			query.add(
					new TermQuery(new Term("gender", String.valueOf(gender))),
					Occur.MUST);
		}
		// 城市
		if (city > 0) {
			query.add(new TermQuery(new Term("city", String.valueOf(city))),
					Occur.MUST);
		}
		// 区
		if (town > 0) {
			query.add(new TermQuery(new Term("town", String.valueOf(town))),
					Occur.MUST);
		}
		return query;
	}
}
