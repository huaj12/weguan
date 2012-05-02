package com.juzhai.search.service.impl;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryParser.MultiFieldQueryParser;
import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.search.BooleanClause.Occur;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.search.TopScoreDocCollector;
import org.apache.lucene.search.highlight.Highlighter;
import org.apache.lucene.search.highlight.QueryScorer;
import org.apache.lucene.search.highlight.SimpleHTMLFormatter;
import org.apache.lucene.util.Version;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.juzhai.core.lucene.searcher.IndexSearcherTemplate;
import com.juzhai.core.lucene.searcher.IndexSearcherTemplate.SearcherCallback;
import com.juzhai.post.mapper.PostMapper;
import com.juzhai.post.model.Post;
import com.juzhai.post.service.IPostService;
import com.juzhai.search.bean.LuneceResult;
import com.juzhai.search.rabbit.message.ActionType;
import com.juzhai.search.rabbit.message.PostIndexMessage;
import com.juzhai.search.service.IPostSearchService;

@Service
public class PostSearchService implements IPostSearchService {
	private final Log log = LogFactory.getLog(getClass());
	@Autowired
	private IndexSearcherTemplate postIndexSearcherTemplate;
	@Autowired
	private Analyzer postIKAnalyzer;
	@Autowired
	private IPostService postService;
	@Autowired
	private RabbitTemplate postIndexCreateRabbitTemplate;
	@Autowired
	private PostMapper postMapper;

	// TODO (done) 如果能够不查询数据库，想想有办法用上吗？
	private String highLightText(String fieldName, String text,
			Highlighter highlighter, Analyzer analyzer) {
		TokenStream tokenStream = postIKAnalyzer.tokenStream(fieldName,
				new StringReader(text));
		String highLightText = null;
		try {
			highLightText = highlighter.getBestFragment(tokenStream, text);
		} catch (Exception e) {
		}
		return highLightText;
	}

	@Override
	public void createIndex(long postId) {
		PostIndexMessage msgMessage = new PostIndexMessage();
		msgMessage.buildBody(postId).buildActionType(ActionType.CREATE);
		postIndexCreateRabbitTemplate.convertAndSend(msgMessage);
		if (log.isDebugEnabled()) {
			log.debug("send post index create message");
		}
	}

	@Override
	public void updateIndex(long postId) {
		PostIndexMessage msgMessage = new PostIndexMessage();
		msgMessage.buildBody(postId).buildActionType(ActionType.UPDATE);
		postIndexCreateRabbitTemplate.convertAndSend(msgMessage);
		if (log.isDebugEnabled()) {
			log.debug("send post index update message");
		}

	}

	@Override
	public void deleteIndex(long postId) {
		PostIndexMessage msgMessage = new PostIndexMessage();
		msgMessage.buildBody(postId).buildActionType(ActionType.DELETE);
		postIndexCreateRabbitTemplate.convertAndSend(msgMessage);
		if (log.isDebugEnabled()) {
			log.debug("send post index delete message");
		}
	}

	@Override
	public LuneceResult<Post> searchPosts(final String queryString,
			final Integer gender, final int firstResult, final int maxResults) {
		return postIndexSearcherTemplate.excute(new SearcherCallback() {
			@SuppressWarnings("unchecked")
			@Override
			public <T> T doCallback(IndexSearcher indexSearcher)
					throws IOException {
				if (StringUtils.isEmpty(queryString)) {
					return (T) Collections.emptyList();
				}
				Query query = getQuery(queryString, gender);
				if (query == null) {
					return (T) Collections.emptyList();
				}
				TopScoreDocCollector collector = TopScoreDocCollector.create(
						firstResult + maxResults, false);
				indexSearcher.search(query, collector);
				TopDocs topDocs = collector.topDocs(firstResult, maxResults);
				List<Post> postIdList = new ArrayList<Post>(maxResults);
				SimpleHTMLFormatter simpleHTMLFormatter = new SimpleHTMLFormatter(
						"<font color='red'>", "</font>");
				Highlighter highlighter = new Highlighter(simpleHTMLFormatter,
						new QueryScorer(query));
				for (ScoreDoc scoreDoc : topDocs.scoreDocs) {
					Document doc = indexSearcher.doc(scoreDoc.doc);
					// TODO (done) 不知道有没有办法不要去数据库查询呢？
					Post post = new Post();
					post.setId(Long.valueOf(doc.get("id")));
					post.setContent(highLightText(queryString,
							doc.get("content"), highlighter, postIKAnalyzer));
					post.setPlace(doc.get("place"));
					post.setPic(doc.get("pic"));
					post.setLink(doc.get("link"));
					post.setCategoryId(Long.valueOf(doc.get("categoryId")));
					post.setPurposeType(Integer.parseInt(doc.get("purposeType")));
					if (StringUtils.isNotEmpty(doc.get("dateTime"))) {
						post.setDateTime(new Date(Long.valueOf(doc
								.get("dateTime"))));
					}
					post.setIdeaId(Long.valueOf(doc.get("ideaId")));
					post.setResponseCnt(Integer.parseInt(doc.get("responseCnt")));
					post.setCommentCnt(Integer.parseInt(doc.get("commentCnt")));
					post.setCity(Long.valueOf(doc.get("city")));
					if (StringUtils.isNotEmpty(doc.get("userCity"))) {
						post.setUserCity(Long.valueOf(doc.get("userCity")));
					}
					if (StringUtils.isNotEmpty(doc.get("userGender"))) {
						post.setUserGender(Integer.parseInt(doc
								.get("userGender")));
					}
					post.setCreateUid(Long.valueOf(doc.get("createUid")));
					post.setCreateTime(new Date(Long.valueOf(doc
							.get("createTime"))));
					post.setLastModifyTime(new Date(Long.valueOf(doc
							.get("lastModifyTime"))));
					postIdList.add(post);
				}
				LuneceResult<Post> result = new LuneceResult<Post>(
						topDocs.totalHits, postIdList);
				return (T) result;
			}
		});
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

	private Query getQuery(String queryString, Integer gender) {
		BooleanQuery query = new BooleanQuery();
		// 性别
		if (null != gender) {
			query.add(
					new TermQuery(new Term("gender", String.valueOf(gender))),
					Occur.MUST);
		}
		MultiFieldQueryParser parser = new MultiFieldQueryParser(
				Version.LUCENE_33, new String[] { "content" }, postIKAnalyzer);
		parser.setPhraseSlop(10);
		try {
			query.add(parser.parse(queryString), Occur.MUST);
		} catch (ParseException e) {
			log.error(e.getMessage(), e);
			return null;
		}
		return query;
	}

	// public List<Post> searchPosts(final String queryString,
	// final int firstResult, final int maxResults) {
	// return postIndexSearcherTemplate.excute(new SearcherCallback() {
	// @SuppressWarnings("unchecked")
	// @Override
	// public <T> T doCallback(IndexSearcher indexSearcher)
	// throws IOException {
	// MultiFieldQueryParser parser = new MultiFieldQueryParser(
	// Version.LUCENE_33, new String[] { "content", "place" },
	// postIKAnalyzer);
	// parser.setPhraseSlop(10);
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
	// // 高亮显示设置
	// SimpleHTMLFormatter simpleHTMLFormatter = new SimpleHTMLFormatter(
	// "<font color='red'>", "</font>");
	// Highlighter highlighter = new Highlighter(simpleHTMLFormatter,
	// new QueryScorer(query));
	// // 指定关键字字符串的context的长度
	// // highlighter.setTextFragmenter(new SimpleFragmenter(100));
	// List<Post> postIdList = new ArrayList<Post>(maxResults);
	// for (ScoreDoc scoreDoc : topDocs.scoreDocs) {
	// Post post = new Post();
	// Document doc = indexSearcher.doc(scoreDoc.doc);
	// long id = Long.valueOf(doc.get("id"));
	// String content = highLightText("content",
	// doc.get("content"), highlighter, postIKAnalyzer);
	// String place = highLightText("place", doc.get("place"),
	// highlighter, postIKAnalyzer);
	// post.setContent(content);
	// post.setPlace(place);
	// post.setId(id);
	// postIdList.add(post);
	// }
	//
	// return (T) postIdList;
	// }
	// });
	// }

}
