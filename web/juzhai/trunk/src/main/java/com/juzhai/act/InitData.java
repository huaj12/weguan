/*
 *The code is written by 51juzhai, All rights is reserved
 */
package com.juzhai.act;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.StringTokenizer;
import java.util.TreeMap;

import javax.annotation.PostConstruct;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.codehaus.jackson.JsonGenerationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.juzhai.act.mapper.CategoryMapper;
import com.juzhai.act.mapper.HotActMapper;
import com.juzhai.act.mapper.QuestionMapper;
import com.juzhai.act.mapper.SynonymActMapper;
import com.juzhai.act.model.Act;
import com.juzhai.act.model.Category;
import com.juzhai.act.model.CategoryExample;
import com.juzhai.act.model.HotAct;
import com.juzhai.act.model.HotActExample;
import com.juzhai.act.model.Question;
import com.juzhai.act.model.QuestionExample;
import com.juzhai.act.model.SynonymAct;
import com.juzhai.act.model.SynonymActExample;
import com.juzhai.act.service.IActCategoryService;
import com.juzhai.act.service.IActService;
import com.juzhai.core.util.JackSonSerializer;

@Component("actInitData")
public class InitData {

	private static final Log log = LogFactory.getLog(InitData.class);

	// TODO 考虑放到Redis中
	// public static final Map<Long, Act> ACT_MAP = new LinkedHashMap<Long,
	// Act>();
	public static final Map<Long, Category> CATEGORY_MAP = new LinkedHashMap<Long, Category>();
	public static final Map<Long, List<Act>> CATEGORY_ACT_LIST_MAP = new HashMap<Long, List<Act>>();
	public static final Map<String, Map<Long, Set<Long>>> TP_RECOMMEND_ACT_MAP = new HashMap<String, Map<Long, Set<Long>>>();
	public static final Map<String, Map<Long, Integer>> RECOMMEND_CATEGORY_RATE_MAP = new HashMap<String, Map<Long, Integer>>();
	// 指向词
	public static Map<String, Long> SYNONYM_ACT = new HashMap<String, Long>();
	/**
	 * key:0：女女；1：男女；2：男男
	 */
	public static final Map<Integer, List<Act>> RANDOM_ACT_MAP = new HashMap<Integer, List<Act>>();
	public static final SortedMap<Long, Question> QUESTION_MAP = new TreeMap<Long, Question>();

	// @Autowired
	// private ActMapper actMapper;
	@Autowired
	private CategoryMapper categoryMapper;
	// @Autowired
	// private HotActMapper hotActMapper;
	// @Autowired
	// private RandomActMapper randomActMapper;
	@Autowired
	private QuestionMapper questionMapper;
	@Autowired
	private IActService actService;
	@Autowired
	private IActCategoryService actCategoryService;
	@Autowired
	private HotActMapper hotActMapper;
	@Autowired
	private SynonymActMapper synonymActMapper;

	@Value("${recommend.category.rates}")
	private String recommendCategoryRates;

	@PostConstruct
	public void init() throws JsonGenerationException {
		// initActMap();
		// 分类
		initCategoryMap();
		// 分类下的显示项目
		initCategoryActListMap();
		// initRandomActMap();
		// 推荐分类的随机百分比
		initRecommendCategoryRateMap();
		initRecommendActMap();
		intiQuestionMap();
		initSynonymMap();
	}

	private void initSynonymMap() {
		List<SynonymAct> synonymActs = synonymActMapper
				.selectByExample(new SynonymActExample());
		for (SynonymAct syn : synonymActs) {
			SYNONYM_ACT.put(syn.getName(), syn.getActId());
		}
	}

	private void initRecommendCategoryRateMap() throws JsonGenerationException {
		Map<String, Map<String, Integer>> rateMap = JackSonSerializer
				.toMap(recommendCategoryRates);
		for (Map.Entry<String, Map<String, Integer>> entry : rateMap.entrySet()) {
			String key = entry.getKey();
			Map<Long, Integer> value = RECOMMEND_CATEGORY_RATE_MAP.get(key);
			if (value == null) {
				value = new LinkedHashMap<Long, Integer>();
				RECOMMEND_CATEGORY_RATE_MAP.put(key, value);
			}
			int rate = 0;
			for (Map.Entry<String, Integer> entry2 : entry.getValue()
					.entrySet()) {
				rate += entry2.getValue();
				value.put(Long.valueOf(entry2.getKey()), rate);
			}
		}

		// StringTokenizer st = new StringTokenizer(recommendCategoryRates,
		// "|");
		// int rate = 0;
		// while (st.hasMoreTokens()) {
		// String[] categoryRate = StringUtils.split(st.nextToken(), ":");
		// if (categoryRate.length == 2) {
		// try {
		// rate += Integer.valueOf(categoryRate[1]);
		// RECOMMEND_CATEGORY_RATE_MAP.put(
		// Long.valueOf(categoryRate[0]), rate);
		// } catch (NumberFormatException e) {
		// log.error(e.getMessage(), e);
		// }
		// }
		// }
	}

	private void initRecommendActMap() {
		HotActExample example = new HotActExample();
		example.createCriteria().andActiveEqualTo(true);
		List<HotAct> hotActList = hotActMapper.selectByExample(example);
		List<Long> actIds = new ArrayList<Long>();
		for (HotAct hotAct : hotActList) {
			actIds.add(hotAct.getActId());
		}
		List<Act> recommendActList = actService.getActListByIds(actIds);
		Date cDate = new Date();
		for (Act act : recommendActList) {
			if (null != act.getEndTime() && act.getEndTime().before(cDate)) {
				continue;
			}
			if (StringUtils.isNotEmpty(act.getCategoryIds())) {
				StringTokenizer st = new StringTokenizer(act.getCategoryIds(),
						",");
				while (st.hasMoreTokens()) {
					try {
						long actCategoryId = Long.valueOf(st.nextToken());
						for (Map.Entry<String, Map<Long, Integer>> entry : RECOMMEND_CATEGORY_RATE_MAP
								.entrySet()) {
							Map<Long, Set<Long>> recommendActMap = TP_RECOMMEND_ACT_MAP
									.get(entry.getKey());
							if (null == recommendActMap) {
								recommendActMap = new HashMap<Long, Set<Long>>();
								TP_RECOMMEND_ACT_MAP.put(entry.getKey(),
										recommendActMap);
							}
							if (entry.getValue().containsKey(actCategoryId)) {
								Set<Long> actIdSet = recommendActMap
										.get(actCategoryId);
								if (null == actIdSet) {
									actIdSet = new HashSet<Long>();
									recommendActMap
											.put(actCategoryId, actIdSet);
								}
								actIdSet.add(act.getId());
							}
						}
					} catch (Exception e) {
						log.error(e.getMessage(), e);
					}
				}
			}
		}
	}

	private void initCategoryActListMap() {
		actCategoryService.updateCategoryActList();
	}

	private void intiQuestionMap() {
		List<Question> list = questionMapper
				.selectByExample(new QuestionExample());
		for (Question question : list) {
			QUESTION_MAP.put(question.getId(), question);
		}
	}

	// private void initHotActListMap() {
	// HotActExample example = new HotActExample();
	// example.setOrderByClause("sequence asc");
	// List<HotAct> hotActList = hotActMapper.selectByExample(example);
	// HOT_ACT_LIST_MAP.clear();
	// for (HotAct hotAct : hotActList) {
	// Map<Long, List<Act>> categoryActListMap = HOT_ACT_LIST_MAP
	// .get(hotAct.getTpId());
	// if (null == categoryActListMap) {
	// categoryActListMap = new LinkedHashMap<Long, List<Act>>();
	// HOT_ACT_LIST_MAP.put(hotAct.getTpId(), categoryActListMap);
	// }
	// Act act = ACT_MAP.get(hotAct.getActId());
	// if (null != act) {
	// addActToCategories(act, categoryActListMap);
	// }
	// }
	// }

	// private void initActMap() {
	// ActExample example = new ActExample();
	// // example.createCriteria().andActiveEqualTo(true);
	// example.setOrderByClause("popularity desc,id asc");
	// List<Act> list = actMapper.selectByExample(example);
	// ACT_MAP.clear();
	// for (Act act : list) {
	// ACT_MAP.put(act.getId(), act);
	// }
	// }

	private void initCategoryMap() {
		CategoryExample example = new CategoryExample();
		example.createCriteria().andSequenceNotEqualTo(0);
		example.setOrderByClause("sequence asc,id asc");
		List<Category> list = categoryMapper.selectByExample(example);
		CATEGORY_MAP.clear();
		for (Category category : list) {
			CATEGORY_MAP.put(category.getId(), category);
		}
	}

	// private void initActCategoryActList() {
	// ACT_CATEGORY_ACT_LIST_MAP.clear();
	// for (Act act : ACT_MAP.values()) {
	// addActToCategories(act, ACT_CATEGORY_ACT_LIST_MAP);
	// }
	// }

	// private void initRandomActMap() {
	// RANDOM_ACT_MAP.put(0, new ArrayList<Act>());
	// RANDOM_ACT_MAP.put(1, new ArrayList<Act>());
	// RANDOM_ACT_MAP.put(2, new ArrayList<Act>());
	//
	// RandomActExample example = new RandomActExample();
	// List<RandomAct> list = randomActMapper.selectByExample(example);
	// for (RandomAct ra : list) {
	// RANDOM_ACT_MAP.get(ra.getRoleCode()).add(
	// actService.getActById(ra.getActId()));
	// }
	// }

	// public void loadAct(Act act) {
	// InitData.ACT_MAP.put(act.getId(), act);
	// addActToCategories(act, ACT_CATEGORY_ACT_LIST_MAP);
	// }
}
