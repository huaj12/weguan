package com.juzhai.post.service.impl;

import java.util.Map;

import org.springframework.stereotype.Service;

import com.juzhai.post.InitData;
import com.juzhai.post.model.Category;
import com.juzhai.post.service.IPostDataRemoteService;

@Service
public class PostDataService implements IPostDataRemoteService {

	@Override
	public Map<Long, Category> getCategoryMap() {
		return InitData.CATEGORY_MAP;
	}

}
