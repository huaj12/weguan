package com.spider.core.service.impl;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicInteger;


import com.spider.core.SystemConfig;
import com.spider.core.bean.Target;
import com.spider.core.service.IAnalysisService;
import com.spider.core.service.IBaseService;
import com.spider.core.service.ISpiderService;
import com.spider.core.task.AnalysisTask;
import com.spider.core.task.SpiderProductTask;
import com.spider.core.utils.RegexUtils;

public abstract class AbstractSpiderService implements ISpiderService {
	protected IBaseService baseService = new BaseService();
	protected IAnalysisService analysisService = new AnalysisService();
	private PrintWriter writer=null;
	public static ConcurrentLinkedQueue<String> queue = new ConcurrentLinkedQueue<String>();
	public static AtomicInteger index =new AtomicInteger();
//	public static int allCount = 0;
	private ExecutorService executor = Executors.newFixedThreadPool(30);

	public void spiderProduct(String link, Target tager) {
		List<String> urls = getPageUrl(link, tager);
		for (String url : urls) {
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
		String domain =SystemConfig.DOMAIN.get(target.getName());
		String pageUrl = baseService.findContent(content,
				RegexUtils.getRegEx(target, "pageUrlRegEx"));
		List<String> list = new ArrayList<String>();
		for (int i = 1; i <= count; i++) {
			String url=domain + pageUrl.replaceAll("\\d", String.valueOf(i));
			list.add(url);
//			System.out.println(url);
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
		if(writer==null){
			try {
				FileOutputStream out=new FileOutputStream(new File("F:"+File.separator+"tuan"+File.separator+target.getName()+".txt"), true);
				writer=new PrintWriter(out);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		}
		while (true) {
			if(queue.size()>0){
				Future<Boolean> fitire=executor.submit(new AnalysisTask(baseService, target, analysisService,writer));
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

	
	public abstract int getPageCount(String content ,Target target);
	

}
