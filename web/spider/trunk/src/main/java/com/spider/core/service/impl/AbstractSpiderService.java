package com.spider.core.service.impl;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;

import com.spider.core.bean.Target;
import com.spider.core.service.IBaseService;
import com.spider.core.service.ISpiderService;
import com.spider.core.utils.RegexUtils;

public abstract class AbstractSpiderService implements ISpiderService {
	private IBaseService baseService = new BaseService();

	public void spiderProduct(String link, Target tager) {
		List<String> urls = getPageUrl(link,tager);
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
	public List<String> getPageUrl(String link,Target tager) {
		Map<String,String> map=RegexUtils.TARGET_REGEX.get(tager.getName());
		String regEx=map.get("tuan800_pageUrlRegEx");
		String allPage=baseService.findContent(link, regEx);
		System.out.println(allPage);
		return Collections.emptyList();
	};

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
