package com.juzhai.act.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.juzhai.act.InitData;
import com.juzhai.act.mapper.ActCategoryMapper;
import com.juzhai.act.model.Act;
import com.juzhai.act.model.ActCategory;
import com.juzhai.act.model.ActCategoryExample;
import com.juzhai.act.model.Category;
import com.juzhai.act.service.IActService;
import com.juzhai.act.service.ICategoryService;

@Service
public class CategoryService implements ICategoryService {

	@Autowired
	private ActCategoryMapper actCategoryMapper;
	@Autowired
	private IActService actService;
	@Value("${show.category.act.size}")
	private int showCategoryActSize = 30;

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

	@Override
	public List<Act> geActsByCategoryId(long categoryId, int size) {
		ActCategoryExample example = new ActCategoryExample();
		example.createCriteria().andCategoryIdEqualTo(categoryId);
		List<ActCategory> actCategoryList = actCategoryMapper
				.selectByExample(example);
		if (CollectionUtils.isEmpty(actCategoryList)) {
			return Collections.emptyList();
		}
		List<Long> actIds = new ArrayList<Long>();
		for (ActCategory actCategory : actCategoryList) {
			if (!actService.isShieldAct(actCategory.getActId())) {
				actIds.add(actCategory.getActId());
			}
		}
		List<Act> actList = actService.getActListByIds(actIds);
		Collections.sort(actList, new Comparator<Act>() {
			@Override
			public int compare(Act act1, Act act2) {
				if (act1 == null || act2 == null) {
					return 0;
				}
				if (act1.getPopularity() == act2.getPopularity()) {
					return Long.valueOf(act1.getId() - act2.getId()).intValue();
				} else {
					return act1.getPopularity() - act2.getPopularity();
				}
			}
		});
		return actList.subList(0, Math.min(actList.size(), size));
	}

	@Override
	public void updateCategoryActList() {
		for (long categoryId : InitData.CATEGORY_MAP.keySet()) {
			InitData.CATEGORY_ACT_LIST_MAP.put(categoryId,
					geActsByCategoryId(categoryId, showCategoryActSize));
		}
	}
}
