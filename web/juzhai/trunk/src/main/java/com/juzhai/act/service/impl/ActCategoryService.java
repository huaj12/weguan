package com.juzhai.act.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.junit.Assert;
import org.springframework.stereotype.Service;

import com.juzhai.act.InitData;
import com.juzhai.act.model.Act;
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

	@Override
	public Map<Long, List<Act>> getHotActListMap(long tpId,
			List<ActCategory> actCategoryList) {
		Map<Long, List<Act>> allHotActListMap = InitData.HOT_ACT_LIST_MAP
				.get(tpId);
		Map<Long, List<Act>> returnMap = new LinkedHashMap<Long, List<Act>>();
		for (ActCategory actCategory : actCategoryList) {
			List<Act> actList = allHotActListMap.get(actCategory.getId());
			if (null != actList) {
				returnMap.put(actCategory.getId(), new ArrayList<Act>(actList));
			}
		}
		return null;
	}
}
