package com.spider.core.task;

import java.io.PrintWriter;
import java.util.concurrent.Callable;

import org.apache.commons.lang.StringUtils;


import com.spider.core.bean.Target;
import com.spider.core.service.IAnalysisService;
import com.spider.core.service.IBaseService;
import com.spider.core.service.ISpiderService;
import com.spider.core.service.impl.AbstractSpiderService;
import com.spider.core.utils.RegexUtils;

public class AnalysisTask implements Callable<Boolean> {
	private IBaseService baseService;
	private Target target;
	private IAnalysisService analysisService;
	private PrintWriter writer;
	public AnalysisTask(IBaseService baseService,Target target,IAnalysisService analysisService,PrintWriter writer){
		this.baseService = baseService;
		this.target = target;
		this.analysisService=analysisService;
		this.writer=writer;
	}
	public Boolean call() throws Exception {
		String url=AbstractSpiderService.queue.poll();
		if(StringUtils.isEmpty(url)){
			return Boolean.FALSE;
		}
		
		try{
		AbstractSpiderService.index.getAndIncrement();
		System.out.println(AbstractSpiderService.index);
		String content=baseService.getContent(url);
		String img=baseService.findContent(content, RegexUtils.getRegEx(target,
		"img"));
		String address=baseService.findContent(content, RegexUtils.getRegEx(target,
		"address"));
		address=analysisService.getAddress(address, target);
		String circle=baseService.findContent(content, RegexUtils.getRegEx(target,
		"circle"));
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
		startDate=analysisService.getStartDate(startDate, target);
		String endDate=baseService.findContent(content, RegexUtils.getRegEx(target,
		"endDate"));
		endDate=analysisService.getEndDate(endDate, target);
		String targetUrl=baseService.findContent(content, RegexUtils.getRegEx(target,
		"targetUrl"));
		targetUrl=analysisService.getTargetUrl(targetUrl, target);
		String category=baseService.findContent(content, RegexUtils.getRegEx(target,
		"category"));
		StringBuffer fileContent=new StringBuffer() ;
		fileContent.append("-------------------------------------------------");
		fileContent.append("\r\n");
		fileContent.append("title="+title);
		fileContent.append("\r\n");
		fileContent.append("img="+img);
		fileContent.append("\r\n");
		fileContent.append("circle="+circle);
		fileContent.append("\r\n");
		fileContent.append("address="+address);
		fileContent.append("\r\n");
		fileContent.append("city="+city);
		fileContent.append("\r\n");
		fileContent.append("original="+original);
		fileContent.append("\r\n");
		fileContent.append("price="+price);
		fileContent.append("\r\n");
		fileContent.append("discount="+discount);
		fileContent.append("\r\n");
		fileContent.append("startDate="+startDate);
		fileContent.append("\r\n");
		fileContent.append("endDate="+endDate);
		fileContent.append("\r\n");
		fileContent.append("source="+source);
		fileContent.append("\r\n");
		fileContent.append("targetUrl="+targetUrl);
		fileContent.append("\r\n");
		fileContent.append("from="+target.getName());
		fileContent.append("\r\n");
		fileContent.append("fromLink="+url);
		fileContent.append("\r\n");
		fileContent.append("category="+category);
		fileContent.append("\r\n");
		synchronized(writer){
			writer.write(fileContent.toString());
		}
//		System.out.println("||||||||||||||||||"+AbstractSpiderService.queue.size());
		}catch (Throwable e) {
			e.printStackTrace();
		}
		return Boolean.TRUE;
	}

}
