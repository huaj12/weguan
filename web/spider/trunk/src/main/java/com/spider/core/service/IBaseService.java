package com.spider.core.service;

import java.util.List;

public interface IBaseService {
	
	String findContent(String url,String regEx);
	
	List<String> findContents(String url,String regEx);
}
