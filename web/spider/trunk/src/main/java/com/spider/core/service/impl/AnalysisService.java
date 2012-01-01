package com.spider.core.service.impl;

import org.apache.commons.lang.StringUtils;

import com.spider.core.bean.Target;
import com.spider.core.service.IAnalysisService;
import com.spider.core.service.ISpiderService;

public class AnalysisService implements IAnalysisService{

	public String getStartDate(String date, Target tager) {
		return getAnalysisService(tager).getStartDate(date, tager);
	}

	public String getEndDate(String date, Target tager) {
		return getAnalysisService(tager).getEndDate(date, tager);
	}

	public String getTargetUrl(String url, Target tager) {
		return getAnalysisService(tager).getTargetUrl(url, tager);
	}
	
	private IAnalysisService getAnalysisService(Target tager) {
		String joinType = StringUtils.upperCase(String.valueOf(tager.getName()
				.charAt(0))) + StringUtils.substring(tager.getName(), 1);
		String beanName = joinType + this.getClass().getSimpleName();
		IAnalysisService analysisService = null;
		try {
			analysisService = (IAnalysisService) Class.forName(
					"com.spider.core.service.impl." + beanName).newInstance();
		} catch (Exception e) {
		}
		return analysisService;
	}

	public String getAddress(String date, Target tager) {
		
		return getAnalysisService(tager).getAddress(date, tager);
	}

}
