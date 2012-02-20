package com.juzhai.search.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryParser.MultiFieldQueryParser;
import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.search.TopScoreDocCollector;
import org.apache.lucene.util.Version;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.juzhai.act.model.Act;
import com.juzhai.act.service.IActService;
import com.juzhai.core.lucene.searcher.IndexSearcherTemplate;
import com.juzhai.core.lucene.searcher.IndexSearcherTemplate.SearcherCallback;
import com.juzhai.search.rabbit.message.ActIndexMessage;
import com.juzhai.search.rabbit.message.ActionType;
import com.juzhai.search.service.IActSearchService;

@Service
public class ActSearchService implements IActSearchService {

	private final Log log = LogFactory.getLog(getClass());

	@Autowired
	private IndexSearcherTemplate actIndexSearcherTemplate;
	@Autowired
	private RabbitTemplate actIndexCreateRabbitTemplate;
	@Autowired
	private Analyzer actIKAnalyzer;
	@Autowired
	private IActService actService;

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
	public Map<Integer, List<Act>> searchActs(final String queryString,
			final int firstResult, final int maxResults) {
		return actIndexSearcherTemplate.excute(new SearcherCallback() {
			@SuppressWarnings("unchecked")
			@Override
			public <T> T doCallback(IndexSearcher indexSearcher)
					throws IOException {
				MultiFieldQueryParser parser = new MultiFieldQueryParser(
						Version.LUCENE_33, new String[] { "name", "keywords" },
						actIKAnalyzer);
				parser.setPhraseSlop(20);
				Query query;
				try {
					query = parser.parse(queryString);
				} catch (ParseException e) {
					log.error(e.getMessage(), e);
					return (T) Collections.emptyList();
				}
				TopScoreDocCollector collector = TopScoreDocCollector.create(
						firstResult + maxResults, false);
				indexSearcher.search(query, collector);
				TopDocs topDocs = collector.topDocs(firstResult, maxResults);
				List<Long> actIdList = new ArrayList<Long>(maxResults);
				for (ScoreDoc scoreDoc : topDocs.scoreDocs) {
					Document doc = indexSearcher.doc(scoreDoc.doc);
					actIdList.add(Long.valueOf(doc.get("id")));
				}
				Map<Integer, List<Act>> result = new HashMap<Integer, List<Act>>();
				result.put(topDocs.totalHits,
						actService.getActListByIds(actIdList));
				return (T) result;
			}
		});
	}

	@Override
	public void createIndex(Act act) {
		ActIndexMessage msgMessage = new ActIndexMessage();
		msgMessage.buildBody(act).buildActionType(ActionType.CREATE);
		actIndexCreateRabbitTemplate.convertAndSend(msgMessage);
		if (log.isDebugEnabled()) {
			log.debug("send act index create message");
		}
	}

	@Override
	public void updateIndex(Act act) {
		ActIndexMessage msgMessage = new ActIndexMessage();
		msgMessage.buildBody(act).buildActionType(ActionType.UPDATE);
		actIndexCreateRabbitTemplate.convertAndSend(msgMessage);
		if (log.isDebugEnabled()) {
			log.debug("send act index update message");
		}
	}

}
