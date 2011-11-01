package com.juzhai.act.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;

import com.juzhai.act.InitData;
import com.juzhai.act.model.Category;
import com.juzhai.act.service.ICategoryService;

@Service
public class CategoryService implements ICategoryService {

	@Override
	public List<Category> listCategories(int size) {
		if (size <= 0) {
			return Collections.emptyList();
		}
		List<Category> list = new ArrayList<Category>(
				InitData.CATEGORY_MAP.values());
		if (CollectionUtils.isEmpty(list)) {
			return Collections.emptyList();
		} else {
			return list.subList(0, Math.min(list.size(), size));
		}
	}
}
