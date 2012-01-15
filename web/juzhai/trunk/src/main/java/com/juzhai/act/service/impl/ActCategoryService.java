package com.juzhai.act.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.juzhai.act.InitData;
import com.juzhai.act.mapper.ActCategoryMapper;
import com.juzhai.act.mapper.CategoryMapper;
import com.juzhai.act.model.Act;
import com.juzhai.act.model.ActCategory;
import com.juzhai.act.model.ActCategoryExample;
import com.juzhai.act.model.Category;
import com.juzhai.act.model.CategoryExample;
import com.juzhai.act.service.IActCategoryService;
import com.juzhai.act.service.IActService;
import com.juzhai.act.service.ISynonymActService;

@Service
public class ActCategoryService implements IActCategoryService {

	@Autowired
	private ActCategoryMapper actCategoryMapper;
	@Autowired
	private CategoryMapper categoryMapper;
	@Autowired
	private IActService actService;
	@Autowired
	private ISynonymActService synonymActService;
	@Value("${show.category.act.size}")
	private int showCategoryActSize = 30;

	@Override
	public void updateActCategory(long actId, List<Long> categoryIds) {
		if (actService.actExist(actId)) {
			ActCategoryExample example = new ActCategoryExample();
			example.createCriteria().andActIdEqualTo(actId);
			actCategoryMapper.deleteByExample(example);
			Date cDate = new Date();
			for (long categoryId : categoryIds) {
				ActCategory actCategory = new ActCategory();
				actCategory.setActId(actId);
				actCategory.setCategoryId(categoryId);
				actCategory.setCreateTime(cDate);
				actCategoryMapper.insertSelective(actCategory);
			}
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
			if (!synonymActService.isShieldAct(actCategory.getActId())) {
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
					return act2.getPopularity() - act1.getPopularity();
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

	@Override
	public void deleteActCategory(long actId) {
		ActCategoryExample example = new ActCategoryExample();
		example.createCriteria().andActIdEqualTo(actId);
		actCategoryMapper.deleteByExample(example);
	}

	@Override
	public boolean isExistAct(long catId) {
		ActCategoryExample example = new ActCategoryExample();
		example.createCriteria().andCategoryIdEqualTo(catId);
		if (actCategoryMapper.countByExample(example) > 0) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public List<Category> findAllCategory() {
		CategoryExample example = new CategoryExample();
		example.setOrderByClause("sequence asc,id asc");
		List<Category> list = categoryMapper.selectByExample(example);
		return list;
	}
}
