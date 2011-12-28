package com.spider.core.service.impl;

import org.apache.commons.lang.StringUtils;

import com.spider.core.bean.Target;
import com.spider.core.service.ISpiderService;

public class SpiderService implements ISpiderService {

	public void spiderProduct(String url, Target tager) {
		getUserServiceBean(tager).spiderProduct(url, tager);
	}

	private ISpiderService getUserServiceBean(Target tager) {
		String joinType = StringUtils.upperCase(String.valueOf(tager.getName()
				.charAt(0))) + StringUtils.substring(tager.getName(), 1);
		String beanName = joinType + this.getClass().getSimpleName();
		ISpiderService spiderService = null;
		try {
			spiderService = (ISpiderService) Class.forName("com.spider.core.service.impl."+beanName)
					.newInstance();
		} catch (Exception e) {
		}
		return spiderService;
	}
	
	public static void main(String []args){
		new SpiderService().getUserServiceBean(Target.TUAN800);
	}

}
