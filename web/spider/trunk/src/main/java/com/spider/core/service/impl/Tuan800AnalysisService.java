package com.spider.core.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang.StringUtils;

import com.spider.core.bean.Target;
import com.spider.core.utils.RegexUtils;
import com.spider.core.utils.URLEncodeUtils;

public class Tuan800AnalysisService extends AbstractAnalysisService {
	@Override
	public String getStartDate(String time, Target tager) {
		try {
			Date d = new Date();
			d.setTime(Long.valueOf(time));
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			return sdf.format(d);
		} catch (Exception e) {
			return "";
		}
	}

	@Override
	public String getEndDate(String time, Target tager) {
		try {
			Date d = new Date();
			d.setTime(Long.valueOf(time));
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			return sdf.format(d);
		} catch (Exception e) {
			return "";
		}

	}

	@Override
	public String getTargetUrl(String url, Target target) {
		String targetUrl = "";
		try {
			String targetContent = baseService.getContent(url);
			String u = baseService.findContent(targetContent,
					RegexUtils.getRegEx(target, "realTargetUrl"));
			if (StringUtils.isEmpty(u)) {
				targetUrl = baseService.findContent(targetContent,
						RegexUtils.getRegEx(target, "realTargetUrl2"));
			} else {
				targetUrl = URLEncodeUtils.decodeURL(u);
			}
			targetUrl = targetUrl.replaceAll(target.getName(), "51juzhai");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return targetUrl;
	}

	@Override
	public String getAddress(String date, Target tager) {
		return URLEncodeUtils.decodeUnicode(date);
	}
	
}
