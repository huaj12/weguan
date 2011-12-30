package com.spider.core.task;

import java.util.concurrent.Callable;

import org.apache.commons.lang.StringUtils;


import com.spider.core.bean.Target;
import com.spider.core.service.IBaseService;
import com.spider.core.service.ISpiderService;
import com.spider.core.service.impl.AbstractSpiderService;
import com.spider.core.utils.RegexUtils;

public class AnalysisTask implements Callable<Boolean> {
	private IBaseService baseService;
	private Target target;
	private ISpiderService spiderService;
	public AnalysisTask(IBaseService baseService,Target target,ISpiderService spiderService){
		this.baseService = baseService;
		this.target = target;
		this.spiderService=spiderService;
	}
	public Boolean call() throws Exception {
		String url=AbstractSpiderService.queue.poll();
		if(StringUtils.isEmpty(url)){
			return Boolean.FALSE;
		}
		String content=baseService.getContent(url);
		String img=baseService.findContent(content, RegexUtils.getRegEx(target,
		"img"));
		String address=baseService.findContent(content, RegexUtils.getRegEx(target,
		"address"));
		String city=baseService.findContent(content, RegexUtils.getRegEx(target,
		"city"));
		String original=baseService.findContent(content, RegexUtils.getRegEx(target,
		"original"));
		String price=baseService.findContent(content, RegexUtils.getRegEx(target,
		"price"));
		String discount=baseService.findContent(content, RegexUtils.getRegEx(target,
		"discount"));
		String title=baseService.findContent(content, RegexUtils.getRegEx(target,
		"title"));
		String source=baseService.findContent(content, RegexUtils.getRegEx(target,
		"source"));
		String startDate=baseService.findContent(content, RegexUtils.getRegEx(target,
		"startDate"));
		String endDate=baseService.findContent(content, RegexUtils.getRegEx(target,
		"endDate"));
		endDate=spiderService.getEndDate(endDate, target);
		String targetUrl=baseService.findContent(content, RegexUtils.getRegEx(target,
		"targetUrl"));
		targetUrl=spiderService.getTargetUrl(targetUrl, target);
		System.out.println(title);
		System.out.println(img);
		System.out.println(address);
		System.out.println(city);
		System.out.println(original);
		System.out.println(price);
		System.out.println(discount);
		System.out.println(startDate);
		System.out.println(endDate);
		System.out.println(source);
		System.out.println(targetUrl);
		System.out.println("-------------------------------------------------");
		return Boolean.TRUE;
	}

}
