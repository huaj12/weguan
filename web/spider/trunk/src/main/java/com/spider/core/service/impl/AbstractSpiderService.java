package com.spider.core.service.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.management.Query;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;

import com.spider.core.bean.Queue;
import com.spider.core.bean.Target;
import com.spider.core.service.IBaseService;
import com.spider.core.service.ISpiderService;
import com.spider.core.task.AnalysisTask;
import com.spider.core.task.SpiderProductTask;
import com.spider.core.utils.RegexUtils;

public abstract class AbstractSpiderService implements ISpiderService {
	protected IBaseService baseService = new BaseService();
	public static Queue queue = new Queue();
	public static int index = 0;
	public static int allCount = 0;
	private ExecutorService executor = Executors.newFixedThreadPool(30);

	public void spiderProduct(String link, Target tager) {
		List<String> urls = getPageUrl(link, tager);
		for (String url : urls) {
//			System.out.println(url);
			// 用多线程处理getProductUrl将明细页面url放入队列里
			// analysis从队列里取数据且进行分析
			executor.submit(new SpiderProductTask(baseService, url, tager));
		}
		analysis(tager);
		
	}

	/**
	 * 根据商品列表页面获取分页url
	 * 
	 * @param url
	 * @return
	 */
	public List<String> getPageUrl(String link, Target tager) {
		String content = baseService.getContent(link);
		String allPage = baseService.findContent(content,
				RegexUtils.getRegEx(tager, "allPageRegEx"));
		String pageUrl = baseService.findContent(content,
				RegexUtils.getRegEx(tager, "pageUrlRegEx"));

		int count = 0;
		try {
			allCount = Integer.parseInt(allPage);
			count = Integer.parseInt(allPage)
					/ Integer.parseInt(RegexUtils.getRegEx(tager, "count"));
			if (Integer.parseInt(allPage)
					% Integer.parseInt(RegexUtils.getRegEx(tager, "count")) != 0) {
				count++;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		String domain = RegexUtils.getRegEx(tager, "domain");
		List<String> list = new ArrayList<String>();
		for (int i = 1; i <= count; i++) {
			pageUrl = pageUrl.replaceAll("\\d", String.valueOf(i));
			list.add(domain + pageUrl);
		}

		return list;
	};

	/**
	 * /** 分析商品明细页面将需要的数据写入文件
	 * 
	 * @param link
	 * @return
	 */
	protected void analysis(Target target) {
		System.out.println(allCount);
		while (allCount != index) {
			if(queue.getSize()>0){
				executor.submit(new AnalysisTask(baseService, target, this));
			}
		}
	}

	public String getStartDate(String date, Target tager) {
		return date;
	}

	public String getEndDate(String date, Target tager) {
		return date;
	}

	public String getTargetUrl(String url, Target tager) {
		return url;
	}

	

}
