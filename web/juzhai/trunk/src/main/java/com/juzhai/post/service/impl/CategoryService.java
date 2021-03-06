package com.juzhai.post.service.impl;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.juzhai.cms.controller.form.CategoryForm;
import com.juzhai.cms.controller.form.CategoryListFrom;
import com.juzhai.post.mapper.CategoryMapper;
import com.juzhai.post.mapper.IdeaMapper;
import com.juzhai.post.mapper.PostMapper;
import com.juzhai.post.mapper.RawIdeaMapper;
import com.juzhai.post.model.Category;
import com.juzhai.post.model.CategoryExample;
import com.juzhai.post.model.IdeaExample;
import com.juzhai.post.model.PostExample;
import com.juzhai.post.model.RawIdeaExample;
import com.juzhai.post.service.ICategoryService;

@Service
public class CategoryService implements ICategoryService {
	private final Log log = LogFactory.getLog(getClass());

	@Autowired
	private CategoryMapper categoryMapper;
	@Autowired
	private IdeaMapper ideaMapper;
	@Autowired
	private RawIdeaMapper rawIdeaMapper;
	@Autowired
	private PostMapper postMapper;

	@Override
	public List<Category> getAllCategory() {
		CategoryExample example = new CategoryExample();
		example.setOrderByClause("sequence asc,id asc");
		List<Category> list = categoryMapper.selectByExample(example);
		return list;
	}

	@Override
	public void updateCategory(CategoryListFrom listform) {
		if (listform == null || listform.getCategoryFroms() == null) {
			return;
		}
		List<CategoryForm> catFroms = listform.getCategoryFroms();
		for (int i = 0; i < catFroms.size(); i++) {
			String hide = catFroms.get(i).getHide();
			Long catId = catFroms.get(i).getId();
			String name = catFroms.get(i).getName();
			String icon = catFroms.get(i).getIcon();
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
					record.setIcon(icon);
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
				record.setIcon(icon);
				categoryMapper.insert(record);
			}
		}

	}

	private boolean isUse(Long id) {
		IdeaExample ideaExample = new IdeaExample();
		ideaExample.createCriteria().andCategoryIdEqualTo(id);
		if (ideaMapper.countByExample(ideaExample) > 0) {
			return true;
		}
		RawIdeaExample rawIdeaExample = new RawIdeaExample();
		rawIdeaExample.createCriteria().andCategoryIdEqualTo(id);
		if (rawIdeaMapper.countByExample(rawIdeaExample) > 0) {
			return true;
		}
		PostExample postExample = new PostExample();
		postExample.createCriteria().andCategoryIdEqualTo(id);
		if (postMapper.countByExample(postExample) > 0) {
			return true;
		}
		return false;
	}

	@Override
	public boolean deleteCategory(Long id) {
		if (id == null) {
			return false;
		}
		if (!isUse(id)) {
			categoryMapper.deleteByPrimaryKey(id);
			return true;
		} else {
			return false;
		}

	}
}
