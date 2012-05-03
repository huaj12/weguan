package com.juzhai.search.controller;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.juzhai.core.controller.BaseController;
import com.juzhai.core.pager.PagerManager;
import com.juzhai.core.web.session.UserContext;
import com.juzhai.index.controller.view.QueryUserView;
import com.juzhai.passport.bean.ProfileCache;
import com.juzhai.passport.model.Profile;
import com.juzhai.passport.service.IInterestUserService;
import com.juzhai.passport.service.ILoginService;
import com.juzhai.post.controller.helper.PostViewHelper;
import com.juzhai.post.controller.view.PostView;
import com.juzhai.post.model.Post;
import com.juzhai.post.service.IPostService;
import com.juzhai.preference.bean.SiftTypePreference;
import com.juzhai.preference.service.IUserPreferenceService;
import com.juzhai.search.bean.Education;
import com.juzhai.search.bean.LuceneResult;
import com.juzhai.search.controller.form.SearchProfileForm;
import com.juzhai.search.service.IPostSearchService;
import com.juzhai.search.service.IProfileSearchService;

@Controller
public class SearchController extends BaseController {
	@Autowired
	private ILoginService loginService;
	@Autowired
	private IPostService postService;
	@Autowired
	private IInterestUserService interestUserService;
	@Autowired
	private IProfileSearchService profileSearchService;
	@Autowired
	private IPostSearchService postSearchService;
	@Autowired
	private PostViewHelper postViewHelper;
	@Autowired
	private IUserPreferenceService userPreferenceService;
	// @Value("${web.search.act.max.rows}")
	// private int webSearchActMaxRows;
	@Value("${query.users.right.user.rows}")
	private int queryUsersRightUserRows;
	@Value("${search.user.rows}")
	private int searchUserRows;
	@Value("${search.post.rows}")
	private int searchPostRows;
	@Value("${search.user.hot.rows}")
	private int searchUserHotRows;
	@Value("${search.post.hot.rows}")
	private int searchPostHotRows;

	// @RequestMapping(value = "/queryAct", method = RequestMethod.GET)
	// public String searchAct(Model model, String name, HttpServletRequest
	// request)
	// throws NeedLoginException {
	// UserContext context = checkLoginForApp(request);
	// Long actId = InitData.SYNONYM_ACT.get(name);
	// if (actId == null) {
	// Act act = actService.getActByName(name);
	// if (act != null) {
	// actId = act.getId();
	// }
	// }
	// if (actId != null) {
	// return "redirect:/app/showAct/" + actId;
	// } else {
	// long uid = context.getUid();
	// ProfileCache cache = profileService.getProfileCacheByUid(uid);
	// if (cache != null) {
	// if (userActionService.searchActActionMaximum(uid)) {
	// userActionService.createSearchActAction(name, uid,
	// cache.getNickname());
	// } else {
	// log.error("uid:" + uid + " Exceeds the limit");
	// }
	// } else {
	// log.error("searchAct uid is not exist");
	// }
	// model.addAttribute("name", name);
	// return "app/search/search";
	// }
	// }
	//
	// @RequestMapping(value = "/searchAutoMatch", method = RequestMethod.GET)
	// @ResponseBody
	// public List<Map<String, Object>> autoSearch(String q, int limit, Model
	// model) {
	// if (log.isDebugEnabled()) {
	// log.debug("search q:" + q + ", limit:" + limit + ".");
	// }
	// List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
	// if (StringUtils.isNotEmpty(q)) {
	// List<String> queryResults = actSearchService.indexSearchName(q,
	// limit);
	// for (String name : queryResults) {
	// Map<String, Object> nameMap = new HashMap<String, Object>();
	// nameMap.put("name", name);
	// result.add(nameMap);
	// }
	// }
	// return result;
	// }
	//
	// @RequestMapping(value = "/searchActs", method = RequestMethod.GET)
	// public String searchActs(HttpServletRequest request, Model model,
	// String searchWords) {
	// return pageSearchActs(request, model, 1, searchWords);
	// }
	//
	// @RequestMapping(value = "/searchActs/{page}", method = RequestMethod.GET)
	// public String pageSearchActs(HttpServletRequest request, Model model,
	// @PathVariable int page, String searchWords) {
	// UserContext context = (UserContext) request.getAttribute("context");
	// if (StringUtils.isEmpty(searchWords)) {
	// return "redirect:/showActs";
	// }
	// PagerManager pager = new PagerManager(page, webSearchActMaxRows);
	// Map<Integer, List<Act>> searchResult = actSearchService.searchActs(
	// searchWords, pager.getFirstResult(), pager.getMaxResult());
	// int totalResults = 0;
	// List<Act> actList = null;
	// for (Map.Entry<Integer, List<Act>> entry : searchResult.entrySet()) {
	// totalResults = entry.getKey();
	// actList = entry.getValue();
	// }
	// pager.setTotalResults(totalResults);
	// List<SearchActView> searchActViewList = new ArrayList<SearchActView>();
	// if (CollectionUtils.isNotEmpty(actList)) {
	// for (Act act : actList) {
	// SearchActView view = new SearchActView();
	// view.setAct(act);
	// if (context.hasLogin()) {
	// view.setHasUsed(userActService.hasAct(context.getUid(),
	// act.getId()));
	// }
	// searchActViewList.add(view);
	// }
	// }
	// model.addAttribute("searchActViewList", searchActViewList);
	// model.addAttribute("pager", pager);
	// model.addAttribute("searchWords", searchWords);
	// return "web/search/search_acts";
	// }

	@RequestMapping(value = { "/findusers", "/findUsers" }, method = RequestMethod.GET)
	public String findUsers(HttpServletRequest request, Model model) {
		String genderType = "all";
		String minAge = "0";
		String maxAge = "0";
		try {
			UserContext context = checkLoginForWeb(request);
			List<String> genders = userPreferenceService.getUserAnswer(
					context.getUid(),
					SiftTypePreference.GENDER.getPreferenceId());
			if (genders != null && genders.size() == 1) {
				String gender = genders.get(0);
				if (StringUtils.equals(gender, "1")) {
					genderType = "male";
				} else if (StringUtils.equals(gender, "0")) {
					genderType = "female";
				}
			}
			List<String> ages = userPreferenceService.getUserAnswer(
					context.getUid(), SiftTypePreference.AGE.getPreferenceId());
			minAge = ages.get(0);
			maxAge = ages.get(1);
		} catch (Exception e) {
		}
		return seachUsers(request, model, 1, 0, genderType, maxAge, minAge,
				"0", "0", "0", 0, 0, 0);
	}

	@RequestMapping(value = { "/seachusers/{town}_{sex}_{minStringAge}_{maxStringAge}_{minStringHeight}_{maxStringHeight}_{constellationIds}_{educations}_{minMonthlyIncome}_{maxMonthlyIncome}/{pageId}" }, method = RequestMethod.GET)
	public String seachUsers(HttpServletRequest request, Model model,
			@PathVariable int pageId, @PathVariable long town,
			@PathVariable String sex, @PathVariable String maxStringAge,
			@PathVariable String minStringAge,
			@PathVariable String minStringHeight,
			@PathVariable String maxStringHeight,
			@PathVariable String constellationIds,
			@PathVariable int educations, @PathVariable int minMonthlyIncome,
			@PathVariable int maxMonthlyIncome) {
		ProfileCache loginUser = getLoginUserCache(request);
		long city = 0L;
		if (loginUser != null && loginUser.getCity() != null) {
			city = loginUser.getCity();
		}
		UserContext context = (UserContext) request.getAttribute("context");
		Integer gender = getSex(sex);
		int maxAge = getInt(maxStringAge);
		int minAge = getInt(minStringAge);
		int maxYear = ageToYear(Math.min(minAge, maxAge));
		int minYear = ageToYear(Math.max(minAge, maxAge));
		int maxHeight = Math.max(getInt(minStringHeight),
				getInt(maxStringHeight));
		int minHeight = Math.min(getInt(minStringHeight),
				getInt(maxStringHeight));
		List<Long> constellationId = getConstellation(constellationIds);
		List<String> educationList = Collections.emptyList();
		if (educations != 0) {
			for (Education edu : Education.values()) {
				if (edu.getType() >= educations) {
					String eduVlue = com.juzhai.search.InitData.EDUCATION_MAP
							.get(edu.getType());
					if (StringUtils.isNotEmpty(eduVlue)) {
						if (CollectionUtils.isEmpty(educationList)) {
							educationList = new ArrayList<String>();
						}
						educationList.add(eduVlue);
					}
				}
			}
		}
		PagerManager pager = new PagerManager(pageId, searchUserRows);
		SearchProfileForm form = new SearchProfileForm(city, town, gender,
				minYear, maxYear, educationList, minMonthlyIncome,
				maxMonthlyIncome, true, null, constellationId, null, null,
				minHeight, maxHeight);
		LuceneResult<Profile> result = profileSearchService.queryProfile(form,
				pager.getFirstResult(), pager.getMaxResult());
		pager.setTotalResults(result.getTotalHits());
		List<Profile> list = result.getResult();
		List<Long> uidList = new ArrayList<Long>();
		for (Profile profile : list) {
			uidList.add(profile.getUid());
		}
		Map<Long, Post> userLatestPostMap = postService
				.getMultiUserLatestPosts(uidList);
		List<QueryUserView> userViews = new ArrayList<QueryUserView>(
				list.size());
		for (Profile profile : list) {
			QueryUserView view = new QueryUserView();
			view.setOnline(loginService.isOnline(profile.getUid()));
			view.setProfile(profile);
			view.setPost(userLatestPostMap.get(profile.getUid()));
			if (context.hasLogin()) {
				view.setHasInterest(interestUserService.isInterest(
						context.getUid(), profile.getUid()));
			}
			userViews.add(view);
		}
		newUserWidget(0L, model, queryUsersRightUserRows);
		model.addAttribute("userViews", userViews);
		model.addAttribute("pager", pager);
		model.addAttribute("cityId", city);
		model.addAttribute("sex", sex);
		model.addAttribute("townId", town);
		model.addAttribute("maxStringAge", maxStringAge);
		model.addAttribute("minStringAge", minStringAge);
		model.addAttribute("maxStringHeight", maxStringHeight);
		model.addAttribute("minStringHeight", minStringHeight);
		model.addAttribute("constellations",
				com.juzhai.passport.InitData.CONSTELLATION_MAP.values());
		model.addAttribute("educationId", educations);
		model.addAttribute("monthlyIncome", minMonthlyIncome + "-"
				+ maxMonthlyIncome);
		model.addAttribute("minMonthlyIncome", minMonthlyIncome);
		model.addAttribute("maxMonthlyIncome", maxMonthlyIncome);
		model.addAttribute("constellationIds", constellationId);
		model.addAttribute("educations",
				com.juzhai.search.InitData.EDUCATION_MAP);
		model.addAttribute("strConstellationIds",
				StringUtils.join(constellationId, ","));
		model.addAttribute("pageType", "zbe");
		getHots(model, city, searchUserHotRows);
		return "web/search/seach_user";
	}

	@RequestMapping(value = { "/findposts", "/findPosts" }, method = RequestMethod.GET)
	public String findPosts(HttpServletRequest request, String queryString,
			Model model) {
		return seachPosts(request, model, 1, null, queryString);
	}

	@RequestMapping(value = { "/seachposts/{queryString}_{sex}/{pageId}" }, method = RequestMethod.GET)
	public String seachPosts(HttpServletRequest request, Model model,
			@PathVariable int pageId, @PathVariable String sex,
			@PathVariable String queryString) {
		ProfileCache loginUser = getLoginUserCache(request);
		long city = 0L;
		if (loginUser != null && loginUser.getCity() != null) {
			city = loginUser.getCity();
		}
		Integer gender = getSex(sex);
		PagerManager pager = new PagerManager(pageId, searchPostRows);
		LuceneResult<Post> result = postSearchService.searchPosts(queryString,
				gender, pager.getFirstResult(), pager.getMaxResult());
		pager.setTotalResults(result.getTotalHits());
		List<Post> postList = result.getResult();
		List<PostView> postViewList = postViewHelper.assembleUserPostViewList(
				null, postList);
		model.addAttribute("pager", pager);
		model.addAttribute("postViewList", postViewList);
		model.addAttribute("queryString", queryString);
		model.addAttribute("sex", sex);
		loadFaces(model);
		getHots(model, city, searchPostHotRows);
		return "web/search/search_posts";
	}

	private Integer getSex(String sex) {
		Integer gender = null;
		if ("male".equals(sex)) {
			gender = 1;
		} else if ("female".equals(sex)) {
			gender = 0;
		}
		return gender;
	}

	private int getInt(String stringAge) {
		int age = 0;
		try {
			age = Integer.parseInt(stringAge);
		} catch (Exception e) {
		}
		return age;
	}

	private int ageToYear(int age) {
		if (age == 0)
			return 0;
		Calendar cal = Calendar.getInstance();
		int year = cal.get(Calendar.YEAR);
		return year - age;
	}

	private List<Long> getConstellation(String constellationIds) {
		List<Long> constellationId = Collections.emptyList();
		if (StringUtils.isNotEmpty(constellationIds)) {
			constellationId = new ArrayList<Long>();
			for (String str : constellationIds.split(",")) {
				Long cid = 0l;
				try {
					cid = Long.valueOf(str);
				} catch (Exception e) {
				}
				if (cid != 0) {
					constellationId.add(cid);
				}
			}
		}
		return constellationId;
	}

}
