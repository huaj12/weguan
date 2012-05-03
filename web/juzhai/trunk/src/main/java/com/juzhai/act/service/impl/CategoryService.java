package com.juzhai.act.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.juzhai.act.InitData;
import com.juzhai.act.mapper.CategoryMapper;
import com.juzhai.act.model.Category;
import com.juzhai.act.model.CategoryExample;
import com.juzhai.act.service.ICategoryService;
import com.juzhai.cms.controller.form.CategoryForm;
import com.juzhai.cms.controller.form.CategoryLiatFrom;

@Service
public class CategoryService implements ICategoryService {
	private final Log log = LogFactory.getLog(getClass());

	@Autowired
	private CategoryMapper categoryMapper;

	// @Autowired
	// private IActCategoryService actCategoryService;

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
	public void updateShowCategories() {
		CategoryExample example = new CategoryExample();
		example.createCriteria().andSequenceNotEqualTo(0);
		example.setOrderByClause("sequence asc,id asc");
		List<Category> list = categoryMapper.selectByExample(example);
		InitData.CATEGORY_MAP.clear();
		for (Category category : list) {
			InitData.CATEGORY_MAP.put(category.getId(), category);
		}
	}

	@Override
	public List<Category> getAllCategory() {
		CategoryExample example = new CategoryExample();
		example.setOrderByClause("sequence asc,id asc");
		List<Category> list = categoryMapper.selectByExample(example);
		return list;
	}

	@Override
	public void updateCategory(CategoryLiatFrom listform) {
		if (listform == null || listform.getCategoryFroms() == null) {
			return;
		}
		List<CategoryForm> catFroms = listform.getCategoryFroms();
		for (int i = 0; i < catFroms.size(); i++) {
			String hide = catFroms.get(i).getHide();
			Long catId = catFroms.get(i).getId();
			String name = catFroms.get(i).getName();
			Integer sequence = catFroms.get(i).getSequence();
			if (sequence == null) {
				sequence = 0;
			}
			if (catId != null) {
				Category record = categoryMapper.selectByPrimaryKey(catId);
				if (null != record) {
					record.setId(catId);
					record.setName(name);
					if (StringUtils.isNotEmpty(hide)) {
						record.setSequence(0);
					} else {
						record.setSequence(sequence);
					}
					record.setLastModifyTime(new Date());
					categoryMapper.updateByPrimaryKey(record);
				}
			} else {
				Category record = new Category();
				record.setName(name);
				if (StringUtils.isNotEmpty(hide)) {
					record.setSequence(0);
				} else {
					record.setSequence(sequence);
				}
				record.setLastModifyTime(new Date());
				record.setCreateTime(new Date());
				categoryMapper.insert(record);
			}
		}

	}

	@Override
	public boolean deleteCategory(Long id) {
		if (id == null) {
			return false;
		}
		// if (!actCategoryService.isExistAct(id)) {
		// TODO (review) 有内容无法删除
		categoryMapper.deleteByPrimaryKey(id);
		return true;
		// } else {
		// return false;
		// }

	}
}
