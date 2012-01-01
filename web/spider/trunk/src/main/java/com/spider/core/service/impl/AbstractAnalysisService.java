package com.spider.core.service.impl;

import com.spider.core.bean.Target;
import com.spider.core.service.IAnalysisService;
import com.spider.core.service.IBaseService;

public abstract class AbstractAnalysisService implements IAnalysisService {
	protected IBaseService baseService = new BaseService();

	public String getStartDate(String date, Target tager) {
		return date;
	}

	public String getEndDate(String date, Target tager) {
		return date;
	}

	public String getTargetUrl(String url, Target tager) {
		return url;
	}

	public String getAddress(String date, Target tager) {
		return date;
	}

}
