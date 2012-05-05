package com.juzhai.post;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import com.juzhai.post.mapper.IdeaMapper;
import com.juzhai.post.model.Category;
import com.juzhai.post.model.Idea;
import com.juzhai.post.model.IdeaExample;
import com.juzhai.post.service.ICategoryService;

@Component("postInitData")
@Lazy(false)
public class InitData {

	/**
	 * 分类-其他的Id
	 */
	public static final long OTHER_CATEGORY_ID = 8L;
	public static final Map<Long, Category> CATEGORY_MAP = new LinkedHashMap<Long, Category>();
	public static final Map<Long, List<Idea>> RANDOM_IDEA = new HashMap<Long, List<Idea>>();

	@Autowired
	private ICategoryService categoryService;
	@Autowired
	private IdeaMapper ideaMapper;

	@PostConstruct
	public void init() {
		initCategory();
		initRandomIdea();
	}

	private void initCategory() {
		List<Category> list = categoryService.getAllCategory();
		for (Category cat : list) {
			CATEGORY_MAP.put(cat.getId(), cat);
		}
	}

	private void initRandomIdea() {
		IdeaExample example = new IdeaExample();
		example.createCriteria().andRandomEqualTo(true);
		List<Idea> ideaList = ideaMapper.selectByExample(example);
		for (Idea idea : ideaList) {
			List<Idea> list = RANDOM_IDEA.get(idea.getCity());
			if (null == list) {
				list = new ArrayList<Idea>();
				RANDOM_IDEA.put(idea.getCity(), list);
			}
			list.add(idea);
		}
	}
}
