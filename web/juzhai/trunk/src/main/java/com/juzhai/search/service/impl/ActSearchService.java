package com.juzhai.search.service.impl;

import java.io.IOException;
import java.util.ArrayList;
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
import org.springframework.stereotype.Service;

import com.juzhai.act.model.Act;
import com.juzhai.core.lucene.searcher.IndexSearcherTemplate;
import com.juzhai.core.lucene.searcher.IndexSearcherTemplate.SearcherCallback;
import com.juzhai.search.rabbit.message.ActIndexMessage;
import com.juzhai.search.rabbit.message.ActIndexMessage.ActionType;
import com.juzhai.search.service.IActSearchService;

@Service
public class ActSearchService implements IActSearchService {

	private final Log log = LogFactory.getLog(getClass());

	@Autowired
	private IndexSearcherTemplate actIndexSearcherTemplate;
	@Autowired
	private RabbitTemplate actIndexCreateRabbitTemplate;

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
	public List<Long> searchAct(String queryString, int firstResult,
			int maxResults) {
		return actIndexSearcherTemplate.excute(new SearcherCallback() {
			@SuppressWarnings("unchecked")
			@Override
			public <T> T doCallback(IndexSearcher indexSearcher)
					throws IOException {

				// TopDocs topDocs = indexSearcher.search(termQuery, count);
				// List<String> actNameList = new ArrayList<String>(
				// topDocs.totalHits);
				// for (ScoreDoc scoreDoc : topDocs.scoreDocs) {
				// Document doc = indexSearcher.doc(scoreDoc.doc);
				// actNameList.add(doc.get("name"));
				// }
				// return (T) actNameList;
				return null;
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
