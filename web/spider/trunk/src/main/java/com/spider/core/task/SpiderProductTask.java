package com.spider.core.task;

import java.util.List;
import java.util.concurrent.Callable;

import com.spider.core.bean.Target;
import com.spider.core.service.IBaseService;
import com.spider.core.service.impl.AbstractSpiderService;
import com.spider.core.utils.RegexUtils;

public class SpiderProductTask implements Callable<Boolean> {
	private IBaseService baseService;
	private String link;
	private Target target;

	public SpiderProductTask(IBaseService baseService, String link,
			Target target) {
		this.baseService = baseService;
		this.link = link;
		this.target = target;
	}

	public Boolean call() throws Exception {
		try {
			List<String> list = baseService.findContents(link,
					RegexUtils.getRegEx(target, "productUrl"));
			for (String s : list) {
//				System.out.println("========="+AbstractSpiderService.queue.size());
				AbstractSpiderService.queue.add(s);
			}
		} catch (Throwable e) {
			return Boolean.FALSE;
		}
		return true;
	}

}
