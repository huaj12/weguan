package com.juzhai.spider.share.bean;

public enum DoubanSpiderWordTemplate {
	YEAR("douban.spider.word.year"), MONTH("douban.spider.word.month"), DAY(
			"douban.spider.word.day"), TO("douban.spider.word.to"), WEEKLY(
			"douban.spider.word.weekly"), EVERYDAY(
			"douban.spider.word.everyday");
	private String name;

	private DoubanSpiderWordTemplate(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}
}
