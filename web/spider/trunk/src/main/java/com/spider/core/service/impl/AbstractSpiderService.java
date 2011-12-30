package com.spider.core.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicInteger;


import com.spider.core.bean.Target;
import com.spider.core.service.IBaseService;
import com.spider.core.service.ISpiderService;
import com.spider.core.task.AnalysisTask;
import com.spider.core.task.SpiderProductTask;
import com.spider.core.utils.RegexUtils;

public abstract class AbstractSpiderService implements ISpiderService {
	protected IBaseService baseService = new BaseService();
	public static ConcurrentLinkedQueue<String> queue = new ConcurrentLinkedQueue<String>();
	public static AtomicInteger index =new AtomicInteger();
//	public static int allCount = 0;
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
	public List<String> getPageUrl(String link, Target target) {
		String content = baseService.getContent(link);
		int count=getPageCount(content, target);
		String domain = RegexUtils.getRegEx(target, "domain");
		String pageUrl = baseService.findContent(content,
				RegexUtils.getRegEx(target, "pageUrlRegEx"));
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
//		System.out.println(allCount);
		while (true) {
			if(queue.size()>0){
				Future<Boolean> fitire=executor.submit(new AnalysisTask(baseService, target, this));
				try {
					if(!fitire.get()){
						break;
					}
				} catch (Exception e) {
					e.printStackTrace();
				} 
			}
		}
	}

	public String getStartDate(String date, Target tager) {
		return date;
	}

	public String getEndDate(String date, Target tager) {
		return date;
	}

	public String getTargetUrl(String url, Target target) {
		return url;
	}
	
	public abstract int getPageCount(String content ,Target target);
	

}
