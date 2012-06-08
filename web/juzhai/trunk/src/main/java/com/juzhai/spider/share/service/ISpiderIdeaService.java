package com.juzhai.spider.share.service;

import com.juzhai.spider.share.exception.SpiderIdeaException;

public interface ISpiderIdeaService {
	/**
	 * 根据url 抓取rawIdea
	 * 
	 * @param url
	 * @return
	 */
	String crawl(String url) throws SpiderIdeaException;

	/**
	 * 是否还能抓取网页
	 * 
	 * @param uid
	 * @return 当前用户抓取的次数
	 * @throws SpiderIdeaException
	 */
	int isCrawl(long uid) throws SpiderIdeaException;

	/**
	 * 更新当前用户抓取次数
	 * 
	 * @param uid
	 * @param count
	 */
	void setCrawlCount(long uid, int count);
}
