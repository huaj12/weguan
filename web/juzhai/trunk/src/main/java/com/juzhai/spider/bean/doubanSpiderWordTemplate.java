package com.juzhai.spider.bean;

public enum doubanSpiderWordTemplate {
	YEAR("douban.spider.word.year"), MONTH("douban.spider.word.month"), DAY(
			"douban.spider.word.day"), TO("douban.spider.word.to"), WEEKLY(
			"douban.spider.word.weekly"), EVERYDAY(
			"douban.spider.word.everyday");
	private String name;

	private doubanSpiderWordTemplate(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}
}
