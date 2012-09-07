package com.juzhai.index.bean;

public class SiteMap {
	private String url;
	private String time;
	private String changefreq="daily";
	private String priority;

	public SiteMap() {

	}
	

	public SiteMap(String url, String time,String priority,String changefreq) {
		this.time = time;
		this.url = url;
		this.priority=priority;
		this.changefreq=changefreq;
	}
	

	public String getPriority() {
		return priority;
	}


	public void setPriority(String priority) {
		this.priority = priority;
	}


	public String getChangefreq() {
		return changefreq;
	}


	public void setChangefreq(String changefreq) {
		this.changefreq = changefreq;
	}


	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

}
