package com.juzhai.spider.share.service;

import com.juzhai.spider.share.exception.SpiderIdeaException;

public interface ISpiderIdeaService {
	/**
	 * 根据url 抓取rawIdea
	 * 
	 * @param url
	 * @return
	 */
	// TODO (done) 这里需要重构。不应该带入逻辑。1、RawIdeaForm换成JSON；2、次数限制判断应该由调用方控制
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
