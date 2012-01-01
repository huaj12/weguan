package com.spider.core.service.impl;

import java.text.ParseException;
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
	public int getPageCount(String content, Target target) {
		String allPage = baseService.findContent(content,
				RegexUtils.getRegEx(target, "allPageRegEx"));
		System.out.println(allPage);
		int count = 0;
		try {
			// allCount = Integer.parseInt(allPage);
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
