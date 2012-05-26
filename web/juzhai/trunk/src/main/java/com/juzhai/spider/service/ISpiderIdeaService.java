package com.juzhai.spider.service;

import com.juzhai.post.controller.form.RawIdeaForm;
import com.juzhai.spider.exception.SpiderIdeaException;

public interface ISpiderIdeaService {
	/**
	 * 根据url 抓取rawIdea
	 * 
	 * @param url
	 * @return
	 */
	RawIdeaForm crawl(String url) throws SpiderIdeaException;
}
