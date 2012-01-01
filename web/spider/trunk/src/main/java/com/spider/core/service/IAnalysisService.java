package com.spider.core.service;

import com.spider.core.bean.Target;

public interface IAnalysisService {

	String getStartDate(String date, Target target);

	String getEndDate(String date, Target target);

	String getTargetUrl(String url, Target target);
	
	String getAddress(String date, Target target);
}
