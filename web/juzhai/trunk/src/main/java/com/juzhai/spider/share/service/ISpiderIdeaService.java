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
	//TODO (review) 这里需要重构。不应该带入逻辑。1、RawIdeaForm换成JSON；2、次数限制判断应该由调用方控制
	RawIdeaForm crawl(String url, long uid) throws SpiderIdeaException;
}
