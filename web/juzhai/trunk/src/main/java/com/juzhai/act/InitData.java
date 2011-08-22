/*
 *The code is written by 51juzhai, All rights is reserved
 */
package com.juzhai.act;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.annotation.PostConstruct;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.juzhai.act.mapper.ActCategoryMapper;
import com.juzhai.act.mapper.ActMapper;
import com.juzhai.act.mapper.RandomActMapper;
import com.juzhai.act.model.Act;
import com.juzhai.act.model.ActCategory;
import com.juzhai.act.model.ActCategoryExample;
import com.juzhai.act.model.ActExample;
import com.juzhai.act.model.RandomAct;
import com.juzhai.act.model.RandomActExample;

@Component("actInitData")
public class InitData {

	private static final Log log = LogFactory.getLog(InitData.class);

	public static final Map<Long, Act> ACT_MAP = new LinkedHashMap<Long, Act>();
	public static final Map<Long, ActCategory> ACT_CATEGORY_MAP = new LinkedHashMap<Long, ActCategory>();
	public static final Map<Long, List<Act>> ACT_CATEGORY_ACT_LIST_MAP = new LinkedHashMap<Long, List<Act>>();
	/**
	 * key:0：女女；1：男女；2：男男
	 */
	public static final Map<Integer, List<Act>> RANDOM_ACT_MAP = new HashMap<Integer, List<Act>>();

	@Autowired
	private ActMapper actMapper;
	@Autowired
	private ActCategoryMapper actCategoryMapper;
	@Autowired
	private RandomActMapper randomActMapper;

	@PostConstruct
	public void init() {
		initActMap();
		initActCategoryMap();
		initActCategoryActList();
		initRandomActMap();
	}

	private void initActMap() {
		ActExample example = new ActExample();
		example.createCriteria().andActiveEqualTo(true);
		example.setOrderByClause("popularity desc,id asc");
		List<Act> list = actMapper.selectByExample(example);
		ACT_MAP.clear();
		for (Act act : list) {
			ACT_MAP.put(act.getId(), act);
		}
	}

	private void initActCategoryMap() {
		ActCategoryExample example = new ActCategoryExample();
		example.setOrderByClause("sequence asc,id asc");
		List<ActCategory> list = actCategoryMapper.selectByExample(example);
		ACT_CATEGORY_MAP.clear();
		for (ActCategory actCategory : list) {
			ACT_CATEGORY_MAP.put(actCategory.getId(), actCategory);
		}
	}

	private void initActCategoryActList() {
		ACT_CATEGORY_ACT_LIST_MAP.clear();
		for (Act act : ACT_MAP.values()) {
			addActToCategories(act);
		}
	}

	private void addActToCategories(Act act) {
		if (StringUtils.isNotEmpty(act.getCategoryIds())) {
			StringTokenizer st = new StringTokenizer(act.getCategoryIds(), ",");
			while (st.hasMoreTokens()) {
				String actCategoryId = st.nextToken();
				try {
					List<Act> list = ACT_CATEGORY_ACT_LIST_MAP.get(Long
							.valueOf(actCategoryId));
					if (null == list) {
						list = new CopyOnWriteArrayList<Act>();
						ACT_CATEGORY_ACT_LIST_MAP.put(
								Long.valueOf(actCategoryId), list);
					}
					list.add(act);
				} catch (Exception e) {
					log.error(e.getMessage(), e);
				}
			}
		}
	}

	private void initRandomActMap() {
		RANDOM_ACT_MAP.put(0, new ArrayList<Act>());
		RANDOM_ACT_MAP.put(1, new ArrayList<Act>());
		RANDOM_ACT_MAP.put(2, new ArrayList<Act>());

		RandomActExample example = new RandomActExample();
		List<RandomAct> list = randomActMapper.selectByExample(example);
		for (RandomAct ra : list) {
			RANDOM_ACT_MAP.get(ra.getRoleCode())
					.add(ACT_MAP.get(ra.getActId()));
		}
	}

	public void loadAct(Act act) {
		InitData.ACT_MAP.put(act.getId(), act);
		addActToCategories(act);
	}
}
