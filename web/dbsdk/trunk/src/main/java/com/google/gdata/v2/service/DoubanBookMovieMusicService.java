package com.google.gdata.v2.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import com.google.gdata.v2.constants.RequestUrls;
import com.google.gdata.v2.model.app.DoubanException;
import com.google.gdata.v2.model.subject.DoubanSubjectFeedObj;
import com.google.gdata.v2.model.subject.DoubanSubjectObj;
import com.google.gdata.v2.utils.ErrorHandler;

/**
 * 
 * @author Zhibo Wei <uglytroll@dongxuexidu.com>
 */
public class DoubanBookMovieMusicService extends DoubanService {

	final static Logger logger = Logger
			.getLogger(DoubanBookMovieMusicService.class.getName());

	public DoubanBookMovieMusicService(String accessToken) {
		super(accessToken);
	}

	public DoubanBookMovieMusicService() {
		super();
	}

	public DoubanSubjectObj getBookInfoById(long bookId)
			throws DoubanException, IOException {
		String url = RequestUrls.DOUBAN_BOOK_SUBJECT_PREFIX + "/" + bookId;
		DoubanSubjectObj book = this.client.getResponse(url, null,
				DoubanSubjectObj.class, false);
		return book;
	}

	public DoubanSubjectObj getBookInfoByISBN(String isbn)
			throws DoubanException, IOException {
		String url = RequestUrls.DOUBAN_BOOK_SUBJECT_PREFIX + "/isbn/" + isbn;
		DoubanSubjectObj book = this.client.getResponse(url, null,
				DoubanSubjectObj.class, false);
		return book;
	}

	public DoubanSubjectObj getMovieInfoById(long movieId)
			throws DoubanException, IOException {
		String url = RequestUrls.DOUBAN_MOVIE_SUBJECT_PREFIX + "/" + movieId;
		DoubanSubjectObj movie = this.client.getResponse(url, null,
				DoubanSubjectObj.class, false);
		return movie;
	}

	public DoubanSubjectObj getMovieInfoByIMDBId(String imdbId)
			throws DoubanException, IOException {
		String url = RequestUrls.DOUBAN_MOVIE_SUBJECT_PREFIX + "/imdb/"
				+ imdbId;
		DoubanSubjectObj movie = this.client.getResponse(url, null,
				DoubanSubjectObj.class, false);
		return movie;
	}

	public DoubanSubjectObj getMusicInfoById(long musicId)
			throws DoubanException, IOException {
		String url = RequestUrls.DOUBAN_MUSIC_SUBJECT_PREFIX + "/" + musicId;
		DoubanSubjectObj music = this.client.getResponse(url, null,
				DoubanSubjectObj.class, false);
		return music;
	}

	public DoubanSubjectFeedObj searchBook(String q, String tag)
			throws DoubanException, IOException {
		String url = RequestUrls.DOUBAN_BOOK_SUBJECT_PREFIX + "s";
		return searchSubject(url, q, tag, null, null);
	}

	public DoubanSubjectFeedObj searchBook(String q, String tag,
			int startIndex, int maxResult) throws DoubanException, IOException {
		String url = RequestUrls.DOUBAN_BOOK_SUBJECT_PREFIX + "s";
		return searchSubject(url, q, tag, startIndex, maxResult);
	}

	public DoubanSubjectFeedObj searchMovie(String q, String tag)
			throws DoubanException, IOException {
		String url = RequestUrls.DOUBAN_MOVIE_SUBJECT_PREFIX + "s";
		return searchSubject(url, q, tag, null, null);
	}

	public DoubanSubjectFeedObj searchMovie(String q, String tag,
			int startIndex, int maxResult) throws DoubanException, IOException {
		String url = RequestUrls.DOUBAN_MOVIE_SUBJECT_PREFIX + "s";
		return searchSubject(url, q, tag, startIndex, maxResult);
	}

	public DoubanSubjectFeedObj searchMusic(String q, String tag)
			throws DoubanException, IOException {
		String url = RequestUrls.DOUBAN_MUSIC_SUBJECT_PREFIX + "s";
		return searchSubject(url, q, tag, null, null);
	}

	public DoubanSubjectFeedObj searchMusic(String q, String tag,
			int startIndex, int maxResult) throws DoubanException, IOException {
		String url = RequestUrls.DOUBAN_MUSIC_SUBJECT_PREFIX + "s";
		return searchSubject(url, q, tag, startIndex, maxResult);
	}

	private DoubanSubjectFeedObj searchSubject(String url, String q,
			String tag, Integer startIndex, Integer maxResult)
			throws DoubanException, IOException {
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		if (q != null || q.length() > 0) {
			params.add(new BasicNameValuePair("q", q));
		}
		if (tag != null && tag.length() > 0) {
			params.add(new BasicNameValuePair("tag", tag));
		}
		if (params.isEmpty()) {
			throw ErrorHandler.missingRequiredParam();
		}
		if (startIndex != null) {
			params.add(new BasicNameValuePair("start-index", startIndex
					.toString()));
		}
		if (maxResult != null) {
			params.add(new BasicNameValuePair("max-results", maxResult
					.toString()));
		}
		DoubanSubjectFeedObj result = this.client.getResponse(url, params,
				DoubanSubjectFeedObj.class, false);
		return result;
	}

}