package com.juzhai.search.service.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.juzhai.post.model.Post;
import com.juzhai.post.service.IPostService;
import com.juzhai.search.rabbit.message.ActionType;
import com.juzhai.search.rabbit.message.PostIndexMessage;
import com.juzhai.search.service.IPostSearchService;

@Service
public class PostSearchService implements IPostSearchService {
	private final Log log = LogFactory.getLog(getClass());
	// @Autowired
	// private IndexSearcherTemplate postIndexSearcherTemplate;
	// @Autowired
	// private Analyzer postIKAnalyzer;
	@Autowired
	private IPostService postService;
	@Autowired
	private RabbitTemplate postIndexCreateRabbitTemplate;

	// @Override
	// public Map<Integer, List<Post>> searchPosts(final String queryString,
	// final int firstResult, final int maxResults) {
	// return postIndexSearcherTemplate.excute(new SearcherCallback() {
	// @SuppressWarnings("unchecked")
	// @Override
	// public <T> T doCallback(IndexSearcher indexSearcher)
	// throws IOException {
	// MultiFieldQueryParser parser = new MultiFieldQueryParser(
	// Version.LUCENE_33, new String[] { "content", "place" },
	// postIKAnalyzer);
	// parser.setPhraseSlop(20);
	// Query query;
	// try {
	// query = parser.parse(queryString);
	// } catch (ParseException e) {
	// log.error(e.getMessage(), e);
	// return (T) Collections.emptyList();
	// }
	// TopScoreDocCollector collector = TopScoreDocCollector.create(
	// firstResult + maxResults, false);
	// indexSearcher.search(query, collector);
	// TopDocs topDocs = collector.topDocs(firstResult, maxResults);
	// List<Long> postIdList = new ArrayList<Long>(maxResults);
	// for (ScoreDoc scoreDoc : topDocs.scoreDocs) {
	// Document doc = indexSearcher.doc(scoreDoc.doc);
	// postIdList.add(Long.valueOf(doc.get("id")));
	// }
	// Map<Integer, List<Post>> result = new HashMap<Integer, List<Post>>();
	// result.put(topDocs.totalHits,
	// postService.getPostListByIds(postIdList));
	//
	// return (T) result;
	// }
	// });
	// }

	@Override
	public void createIndex(long postId) {
		Post post = postService.getPostById(postId);
		PostIndexMessage msgMessage = new PostIndexMessage();
		msgMessage.buildBody(post).buildActionType(ActionType.CREATE);
		postIndexCreateRabbitTemplate.convertAndSend(msgMessage);
		if (log.isDebugEnabled()) {
			log.debug("send post index create message");
		}
	}

	@Override
	public void updateIndex(long postId) {
		Post post = postService.getPostById(postId);
		PostIndexMessage msgMessage = new PostIndexMessage();
		msgMessage.buildBody(post).buildActionType(ActionType.UPDATE);
		postIndexCreateRabbitTemplate.convertAndSend(msgMessage);
		if (log.isDebugEnabled()) {
			log.debug("send post index update message");
		}

	}

	@Override
	public void deleteIndex(long postId) {
		Post post = postService.getPostById(postId);
		PostIndexMessage msgMessage = new PostIndexMessage();
		msgMessage.buildBody(post).buildActionType(ActionType.DELETE);
		postIndexCreateRabbitTemplate.convertAndSend(msgMessage);
		if (log.isDebugEnabled()) {
			log.debug("send post index delete message");
		}
	}

	// @Override
	// public List<Post> queryPosts(final String content, final String place,
	// final Date startDate, final Date endDate, final long categoryId,
	// final int firstResult, final int maxResults) {
	// return postIndexSearcherTemplate.excute(new SearcherCallback() {
	// @SuppressWarnings("unchecked")
	// @Override
	// public <T> T doCallback(IndexSearcher indexSearcher)
	// throws IOException {
	// BooleanQuery query = new BooleanQuery();
	//
	// String start = null;
	// String end = null;
	// try {
	// start = DateFormat.SDF.format(startDate);
	// end = DateFormat.SDF.format(endDate);
	// } catch (Exception e) {
	// start = "";
	// end = "";
	// }
	// if (StringUtils.isNotEmpty(start)
	// && StringUtils.isNotEmpty(end)) {
	// Query timeQuery = new TermRangeQuery("dateTime", start,
	// end, true, true);
	// query.add(timeQuery, Occur.MUST);
	// }
	// if (StringUtils.isNotEmpty(content)) {
	// Query contentQuery = new TermQuery(new Term("content",
	// content));
	// query.add(contentQuery, Occur.MUST);
	// }
	// if (StringUtils.isNotEmpty(place)) {
	// Query placeQuery = new TermQuery(new Term("place", place));
	// query.add(placeQuery, Occur.MUST);
	// }
	// if (categoryId != 0) {
	// Query categoryIdQuery = new TermQuery(new Term(
	// "categoryId", String.valueOf(categoryId)));
	// query.add(categoryIdQuery, Occur.MUST);
	// }
	//
	// TopScoreDocCollector collector = TopScoreDocCollector.create(
	// firstResult + maxResults, false);
	// indexSearcher.search(query, collector);
	// TopDocs topDocs = collector.topDocs(firstResult, maxResults);
	// List<Long> postIdList = new ArrayList<Long>(maxResults);
	// for (ScoreDoc scoreDoc : topDocs.scoreDocs) {
	// Document doc = indexSearcher.doc(scoreDoc.doc);
	// postIdList.add(Long.valueOf(doc.get("id")));
	// }
	// return (T) postService.getPostListByIds(postIdList);
	// }
	// });
	// }

}
