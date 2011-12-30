package com.spider.core.service;

import com.spider.core.bean.Target;

public interface ISpiderService {
	/**
	 * 根据商品列表爬取明细
	 * 
	 * @param url
	 */
	void spiderProduct(String url, Target tager);

	String getStartDate(String date, Target tager);

	String getEndDate(String date, Target tager);

	String getTargetUrl(String url, Target tager);

}
