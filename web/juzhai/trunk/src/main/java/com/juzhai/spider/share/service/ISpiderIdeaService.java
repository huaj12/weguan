package com.juzhai.spider.share.service;

import com.juzhai.post.controller.form.RawIdeaForm;
import com.juzhai.spider.share.exception.SpiderIdeaException;

public interface ISpiderIdeaService {
	/**
	 * 根据url 抓取rawIdea
	 * 
	 * @param url
	 * @return
	 */
	RawIdeaForm crawl(String url, long uid) throws SpiderIdeaException;
}
