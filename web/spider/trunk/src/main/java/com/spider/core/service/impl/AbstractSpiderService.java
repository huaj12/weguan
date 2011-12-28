package com.spider.core.service.impl;

import java.util.List;

import com.spider.core.bean.Target;
import com.spider.core.service.ISpiderService;

public abstract class AbstractSpiderService implements ISpiderService {

	public void spiderProduct(String link, Target tager) {
		List<String> urls = getPageUrl(link);
		for (String url : urls) {
			// 用多线程处理getProductUrl将明细页面url放入队列里
			// analysis从队列里取数据且进行分析
			getProductUrl(url);
			analysis();
		}
	}

	/**
	 * 根据商品列表页面获取分页url
	 * 
	 * @param url
	 * @return
	 */
	protected abstract List<String> getPageUrl(String link);

	/**
	 * 获取商品明细页面url
	 * 
	 * @param link
	 * @return
	 */
	protected abstract void getProductUrl(String link);

	/**
	 * 分析商品明细页面将需要的数据写入文件
	 * 
	 * @param link
	 * @return
	 */
	protected abstract void analysis();

}
