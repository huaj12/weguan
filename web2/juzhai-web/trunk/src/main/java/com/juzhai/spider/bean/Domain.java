package com.juzhai.spider.bean;

public enum Domain {
	TUAN800("tuan800", "tuan800.com"), DIANPING("dianping", "dianping.com"), DOUBAN(
			"douban", "douban.com");

	private String name;

	private String url;

	private Domain(String name, String url) {
		this.name = name;
		this.url = url;
	}

	public String getName() {
		return name;
	}

	public String getUrl() {
		return url;
	}

}
