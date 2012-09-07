package com.juzhai.post.service;

import java.util.Map;

import com.juzhai.post.model.Category;

public interface IPostDataRemoteService {

	Map<Long, Category> getCategoryMap();
}
