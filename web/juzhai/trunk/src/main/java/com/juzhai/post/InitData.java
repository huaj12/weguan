package com.juzhai.post;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import com.juzhai.act.model.Category;
import com.juzhai.act.service.ICategoryService;

@Component("postInitData")
@Lazy(false)
public class InitData {
	public static final Map<Long, Category> CATEGORY_MAP = new HashMap<Long, Category>();
	@Autowired
	private ICategoryService categoryService;

	@PostConstruct
	public void init() {
		initCategory();
	}

	private void initCategory() {
		List<Category> list = categoryService.getAllCategory();
		for (Category cat : list) {
			CATEGORY_MAP.put(cat.getId(), cat);
		}
	}
}
