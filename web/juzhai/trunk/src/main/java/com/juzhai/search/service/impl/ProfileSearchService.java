package com.juzhai.search.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
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
import org.apache.lucene.search.Sort;
import org.apache.lucene.search.SortField;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.search.TopFieldCollector;
import org.apache.lucene.util.Version;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.juzhai.core.lucene.searcher.IndexSearcherTemplate;
import com.juzhai.core.lucene.searcher.IndexSearcherTemplate.SearcherCallback;
import com.juzhai.passport.service.impl.ProfileService;
import com.juzhai.search.bean.LuceneResult;
import com.juzhai.search.controller.form.SearchProfileForm;
import com.juzhai.search.controller.view.SearchUserView;
import com.juzhai.search.rabbit.message.ActionType;
import com.juzhai.search.rabbit.message.ProfileIndexMessage;
import com.juzhai.search.service.IProfileSearchService;

@Service
public class ProfileSearchService implements IProfileSearchService {
	private final Log log = LogFactory.getLog(getClass());
	@Autowired
	private RabbitTemplate profileIndexCreateRabbitTemplate;
	@Autowired
	private IndexSearcherTemplate profileIndexSearcherTemplate;
	@Autowired
	private Analyzer profileIKAnalyzer;
	@Autowired
	private ProfileService profileService;

	@Override
	public void createIndex(long uid) {
		ProfileIndexMessage msgMessage = new ProfileIndexMessage();
		msgMessage.buildBody(uid).buildActionType(ActionType.CREATE);
		profileIndexCreateRabbitTemplate.convertAndSend(msgMessage);
	}

	@Override
	public void updateIndex(long uid) {
		ProfileIndexMessage msgMessage = new ProfileIndexMessage();
		msgMessage.buildBody(uid).buildActionType(ActionType.UPDATE);
		profileIndexCreateRabbitTemplate.convertAndSend(msgMessage);

	}

	@Override
	public void deleteIndex(long uid) {
		ProfileIndexMessage msgMessage = new ProfileIndexMessage();
		msgMessage.buildBody(uid).buildActionType(ActionType.DELETE);
		profileIndexCreateRabbitTemplate.convertAndSend(msgMessage);

	}

	@Override
	public LuceneResult<SearchUserView> queryProfile(final long uid,
			final SearchProfileForm form, final int firstResult,
			final int maxResults) {
		return profileIndexSearcherTemplate.excute(new SearcherCallback() {
			@SuppressWarnings("unchecked")
			@Override
			public <T> T doCallback(IndexSearcher indexSearcher)
					throws IOException {
				Query query = getQuery(uid, form.getCity(), form.getTown(),
						form.getGender(), form.getMinYear(), form.getMaxYear(),
						form.getEducations(), form.getMinMonthlyIncome(),
						form.getHome(), form.getConstellationId(),
						form.getHouse(), form.getCar(), form.getMinHeight(),
						form.getMaxHeight());

				Sort sort = new Sort(new SortField("lastWebLoginTime",
						SortField.LONG, true));// 排序，true倒序 false 升序
				TopFieldCollector collector = TopFieldCollector.create(sort,
						(firstResult + maxResults), false, false, false, false);
				indexSearcher.search(query, collector);
				TopDocs topDocs = collector.topDocs(firstResult, maxResults);
				List<SearchUserView> list = new ArrayList<SearchUserView>(
						maxResults);
				for (ScoreDoc scoreDoc : topDocs.scoreDocs) {
					try {
						Document doc = indexSearcher.doc(scoreDoc.doc);
						SearchUserView userView = new SearchUserView();
						userView.setProfile(profileService
								.getProfileCacheByUid(Long.valueOf(doc
										.get("uid"))));
						userView.setLastWebLoginTime(new Date(Long.valueOf(doc
								.get("lastWebLoginTime"))));
						list.add(userView);
					} catch (Exception e) {
						log.error("queryProfile add LuceneUserView is error ",
								e);
					}
				}
				LuceneResult<SearchUserView> result = new LuceneResult<SearchUserView>(
						topDocs.totalHits, list);
				return (T) result;
			}
		});
	}

	private Query getQuery(long uid, long city, long town, Integer gender,
			int minYear, int maxYear, List<String> educations,
			int minMonthlyIncome, String home, List<Long> constellationIds,
			String house, String car, int minHeight, int maxHeight) {
		BooleanQuery query = new BooleanQuery();
		// 身高
		if (minHeight > 0 || maxHeight > 0) {
			Query heightQuery = NumericRangeQuery.newIntRange("height",
					minHeight == 0 ? null : minHeight, maxHeight == 0 ? null
							: maxHeight, true, true);
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
		if (minMonthlyIncome > 0) {
			// 选取xx以上
			Query incomeQuery = NumericRangeQuery.newIntRange("minIncomeNum",
					minMonthlyIncome, null, true, true);
			query.add(incomeQuery, Occur.MUST);
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
		if (minYear > 0 || maxYear > 0) {
			Query ageQuery = NumericRangeQuery.newIntRange("age",
					minYear == 0 ? null : minYear, maxYear == 0 ? null
							: maxYear, true, true);
			query.add(ageQuery, Occur.MUST);
		} else {
			Calendar cal = Calendar.getInstance();
			int year = cal.get(Calendar.YEAR);
			Query ageQuery = NumericRangeQuery.newIntRange("age", year - 200,
					year, true, true);
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
		if (uid > 0) {
			query.add(new TermQuery(new Term("uid", String.valueOf(uid))),
					Occur.MUST_NOT);
		}
		return query;
	}
}
