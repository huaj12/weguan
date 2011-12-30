package com.spider.tuan800;

import java.util.Date;

import org.apache.commons.lang.StringUtils;

import com.spider.core.bean.Target;
import com.spider.core.service.IBaseService;
import com.spider.core.service.impl.BaseService;
import com.spider.core.service.impl.SpiderService;
import com.spider.core.utils.RegexUtils;
import com.spider.core.utils.URLEncodeUtils;

public class Tuan800Runner {
	public static void main(String []args){
		
//		Target tager=Target.TUAN800;
//		IBaseService baseService = new BaseService();
//		String content=baseService.getContent("http://www.tuan800.com/deal/shandianpi_2099839");
//		String img=baseService.findContent(content, RegexUtils.getRegEx(tager,
//		"img"));
//		String address=baseService.findContent(content, RegexUtils.getRegEx(tager,
//		"address"));
//		String city=baseService.findContent(content, RegexUtils.getRegEx(tager,
//		"city"));
//		String original=baseService.findContent(content, RegexUtils.getRegEx(tager,
//		"original"));
//		String price=baseService.findContent(content, RegexUtils.getRegEx(tager,
//		"price"));
//		String discount=baseService.findContent(content, RegexUtils.getRegEx(tager,
//		"discount"));
//		String title=baseService.findContent(content, RegexUtils.getRegEx(tager,
//		"title"));
//		String source=baseService.findContent(content, RegexUtils.getRegEx(tager,
//		"source"));
//		String startDate=baseService.findContent(content, RegexUtils.getRegEx(tager,
//		"startDate"));
//		String endDate=baseService.findContent(content, RegexUtils.getRegEx(tager,
//		"endDate"));
//		String targetUrl=baseService.findContent(content, RegexUtils.getRegEx(tager,
//		"targetUrl"));
//		String targetContent=baseService.getContent(targetUrl);
//		System.out.println(targetUrl);
//		String u=baseService.findContent(targetContent, RegexUtils.getRegEx(tager,
//		"realTargetUrl"));
//		if(StringUtils.isEmpty(u)){
//			System.out.println(baseService.findContent(targetContent, RegexUtils.getRegEx(tager,
//			"realTargetUrl2")));
//		}else{
//			System.out.println(URLEncodeUtils.decodeURL(u));
//		}
		
//		System.out.println(img);
//		System.out.println(address);
//		System.out.println(city);
//		System.out.println(original);
//		System.out.println(price);
//		System.out.println(discount);
//		System.out.println(title);
//		System.out.println(startDate);
//		System.out.println(endDate);
//		System.out.println(source);
		new SpiderService().spiderProduct("http://www.tuan800.com/shanghai/shenghuoyule", Target.TUAN800);
	}
}
