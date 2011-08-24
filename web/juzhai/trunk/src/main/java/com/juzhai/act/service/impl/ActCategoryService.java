package com.juzhai.act.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.junit.Assert;
import org.springframework.stereotype.Service;

import com.juzhai.act.InitData;
import com.juzhai.act.model.ActCategory;
import com.juzhai.act.service.IActCategoryService;

@Service
public class ActCategoryService implements IActCategoryService {

	@Override
	public List<ActCategory> listHotCategories() {
		return new ArrayList<ActCategory>(InitData.ACT_CATEGORY_MAP.values());
	}

	@Override
	public List<ActCategory> listHotCategories(int size) {
		Assert.assertTrue("hot category size must greater than zero", size > 0);
		List<ActCategory> list = listHotCategories();
		if (CollectionUtils.isEmpty(list)) {
			return Collections.emptyList();
		} else {
			return list.subList(0, Math.min(list.size(), size));
		}
	}
}
