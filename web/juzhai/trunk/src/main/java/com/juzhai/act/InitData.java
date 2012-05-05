/*
 *The code is written by 51juzhai, All rights is reserved
 */
package com.juzhai.act;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.codehaus.jackson.JsonGenerationException;
import org.springframework.beans.factory.annotation.Autowired;

import com.juzhai.act.model.Category;
import com.juzhai.act.service.ICategoryService;

//@Component("actInitData")
public class InitData {

	private static final Log log = LogFactory.getLog(InitData.class);

	public static final Map<Long, Category> CATEGORY_MAP = new LinkedHashMap<Long, Category>();
	@Autowired
	private ICategoryService categoryService;

	@PostConstruct
	public void init() throws JsonGenerationException {
		// initActMap();
		// 分类
		// initCategoryMap();
		// 分类下的显示项目
		// initCategoryActListMap();
		// initRandomActMap();
		// 推荐分类的随机百分比
		// initRecommendCategoryRateMap();
		// initRecommendActMap();
		// intiQuestionMap();
		// initSynonymMap();
	}

	private void initCategoryMap() {
		categoryService.updateShowCategories();
	}

}
