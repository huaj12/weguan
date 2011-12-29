package com.spider.tuan800;

import com.spider.core.bean.Target;
import com.spider.core.service.impl.SpiderService;

public class Tuan800Runner {
	public static void main(String []args){
		new SpiderService().spiderProduct("http://www.tuan800.com/shanghai/shenghuoyule", Target.TUAN800);
	}
}
