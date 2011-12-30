package com.spider.core.service.impl;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.spider.core.bean.Target;
import com.spider.core.service.IBaseService;
import com.spider.core.utils.RegexUtils;
import com.spider.core.utils.URLEncodeUtils;

public class Tuan800SpiderService extends AbstractSpiderService {

	@Override
	public String getStartDate(String date, Target tager) {
		return super.getStartDate(date, tager);
	}

	@Override
	public String getEndDate(String time, Target tager) {
		Date d=new Date();
		d.setTime(Long.valueOf(time));
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return sdf.format(d);
	}

	@Override
	public String getTargetUrl(String url, Target target) {
		String targetContent = baseService.getContent(url);
		String u = baseService.findContent(targetContent,
				RegexUtils.getRegEx(target, "realTargetUrl"));
		if (StringUtils.isEmpty(u)) {
			return baseService.findContent(targetContent,
					RegexUtils.getRegEx(target, "realTargetUrl2"));
		} else {
			return URLEncodeUtils.decodeURL(u);
		}
	}

	@Override
	public int getPageCount(String content, Target target) {
		String allPage = baseService.findContent(content,
				RegexUtils.getRegEx(target, "allPageRegEx"));
		int count = 0;
		try {
//			allCount = Integer.parseInt(allPage);
			count = Integer.parseInt(allPage)
					/ Integer.parseInt(RegexUtils.getRegEx(target, "count"));
			if (Integer.parseInt(allPage)
					% Integer.parseInt(RegexUtils.getRegEx(target, "count")) != 0) {
				count++;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return count;
	}

}
