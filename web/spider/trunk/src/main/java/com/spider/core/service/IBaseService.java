package com.spider.core.service;

import java.util.List;

public interface IBaseService {
	
	String findContentByUrl(String url,String regEx);
	
	String findContent(String content,String regEx);
	
	String getContent(String url);
	
	List<String> findContents(String url,String regEx);
	
	
}
