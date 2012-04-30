package com.juzhai.search.controller;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.juzhai.act.InitData;
import com.juzhai.act.model.Act;
import com.juzhai.act.service.IActService;
import com.juzhai.act.service.IUserActService;
import com.juzhai.cms.service.IUserActionService;
import com.juzhai.core.controller.BaseController;
import com.juzhai.core.exception.NeedLoginException;
import com.juzhai.core.pager.PagerManager;
import com.juzhai.core.web.session.UserContext;
import com.juzhai.index.controller.view.QueryUserView;
import com.juzhai.passport.bean.ProfileCache;
import com.juzhai.passport.model.Profile;
import com.juzhai.passport.service.IInterestUserService;
import com.juzhai.passport.service.ILoginService;
import com.juzhai.passport.service.IProfileService;
import com.juzhai.post.controller.view.PostView;
import com.juzhai.post.model.Post;
import com.juzhai.post.service.IPostService;
import com.juzhai.search.bean.Education;
import com.juzhai.search.controller.view.SearchActView;
import com.juzhai.search.service.IActSearchService;
import com.juzhai.search.service.IPostSearchService;
import com.juzhai.search.service.IProfileSearchService;
import com.juzhai.search.service.ISearchHotService;

@Controller
public class SearchController extends BaseController {
	private final Log log = LogFactory.getLog(getClass());
	@Autowired
	private IUserActionService userActionService;
	@Autowired
	private IActService actService;
	@Autowired
	private IActSearchService actSearchService;
	@Autowired
	private IProfileService profileService;
	@Autowired
	private IUserActService userActService;
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
	private ISearchHotService searchHotService;
	@Value("${web.search.act.max.rows}")
	private int webSearchActMaxRows;
	@Value("${query.users.right.user.rows}")
	private int queryUsersRightUserRows;

	@RequestMapping(value = "/queryAct", method = RequestMethod.GET)
	public String searchAct(Model model, String name, HttpServletRequest request)
			throws NeedLoginException {
		UserContext context = checkLoginForApp(request);
		Long actId = InitData.SYNONYM_ACT.get(name);
		if (actId == null) {
			Act act = actService.getActByName(name);
			if (act != null) {
				actId = act.getId();
			}
		}
		if (actId != null) {
			return "redirect:/app/showAct/" + actId;
		} else {
			long uid = context.getUid();
			ProfileCache cache = profileService.getProfileCacheByUid(uid);
			if (cache != null) {
				if (userActionService.searchActActionMaximum(uid)) {
					userActionService.createSearchActAction(name, uid,
							cache.getNickname());
				} else {
					log.error("uid:" + uid + " Exceeds the limit");
				}
			} else {
				log.error("searchAct uid is not exist");
			}
			model.addAttribute("name", name);
			return "app/search/search";
		}
	}

	@RequestMapping(value = "/searchAutoMatch", method = RequestMethod.GET)
	@ResponseBody
	public List<Map<String, Object>> autoSearch(String q, int limit, Model model) {
		if (log.isDebugEnabled()) {
			log.debug("search q:" + q + ", limit:" + limit + ".");
		}
		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		if (StringUtils.isNotEmpty(q)) {
			List<String> queryResults = actSearchService.indexSearchName(q,
					limit);
			for (String name : queryResults) {
				Map<String, Object> nameMap = new HashMap<String, Object>();
				nameMap.put("name", name);
				result.add(nameMap);
			}
		}
		return result;
	}

	@RequestMapping(value = "/searchActs", method = RequestMethod.GET)
	public String searchActs(HttpServletRequest request, Model model,
			String searchWords) {
		return pageSearchActs(request, model, 1, searchWords);
	}

	@RequestMapping(value = "/searchActs/{page}", method = RequestMethod.GET)
	public String pageSearchActs(HttpServletRequest request, Model model,
			@PathVariable int page, String searchWords) {
		UserContext context = (UserContext) request.getAttribute("context");
		if (StringUtils.isEmpty(searchWords)) {
			return "redirect:/showActs";
		}
		PagerManager pager = new PagerManager(page, webSearchActMaxRows);
		Map<Integer, List<Act>> searchResult = actSearchService.searchActs(
				searchWords, pager.getFirstResult(), pager.getMaxResult());
		int totalResults = 0;
		List<Act> actList = null;
		for (Map.Entry<Integer, List<Act>> entry : searchResult.entrySet()) {
			totalResults = entry.getKey();
			actList = entry.getValue();
		}
		pager.setTotalResults(totalResults);
		List<SearchActView> searchActViewList = new ArrayList<SearchActView>();
		if (CollectionUtils.isNotEmpty(actList)) {
			for (Act act : actList) {
				SearchActView view = new SearchActView();
				view.setAct(act);
				if (context.hasLogin()) {
					view.setHasUsed(userActService.hasAct(context.getUid(),
							act.getId()));
				}
				searchActViewList.add(view);
			}
		}
		model.addAttribute("searchActViewList", searchActViewList);
		model.addAttribute("pager", pager);
		model.addAttribute("searchWords", searchWords);
		return "web/search/search_acts";
	}

	@RequestMapping(value = { "/findusers", "/findUsers" }, method = RequestMethod.GET)
	public String findUsers(HttpServletRequest request, Model model) {
		return seachUsers(request, model, 1, 0, null, null, null, null, null,
				null, 0, 0, 0);
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
		Integer gender = null;
		if ("male".equals(sex)) {
			gender = 1;
		} else if ("female".equals(sex)) {
			gender = 0;
		}
		int maxAge = getInt(maxStringAge);
		int minAge = getInt(minStringAge);
		int maxYear = ageToYear(Math.min(minAge, maxAge));
		int minYear = ageToYear(Math.max(minAge, maxAge));
		int maxHeight = Math.max(getInt(minStringHeight),
				getInt(maxStringHeight));
		int minHeight = Math.min(getInt(minStringHeight),
				getInt(maxStringHeight));
		List<Long> constellationId = Collections.emptyList();
		if (StringUtils.isNotEmpty(constellationIds)) {
			constellationId = new ArrayList<Long>();
			for (String str : constellationIds.split(",")) {
				constellationId.add(Long.valueOf(str));
			}
		}
		List<String> educationList = new ArrayList<String>();
		if (educations != 0) {
			for (Education edu : Education.values()) {
				if (edu.getType() >= educations) {
					String eduVlue = com.juzhai.common.InitData.EDUCATION_MAP
							.get(edu.getType());
					if (StringUtils.isNotEmpty(eduVlue)) {
						educationList.add(eduVlue);
					}
				}
			}
		}
		PagerManager pager = new PagerManager(pageId, 2,
				profileSearchService.countQqueryProfile(city, town, gender,
						minYear, maxYear, educationList, minMonthlyIncome,
						maxMonthlyIncome, true, null, constellationId, null,
						null, minHeight, maxHeight));
		List<Profile> list = profileSearchService.queryProfile(city, town,
				gender, minYear, maxYear, educationList, minMonthlyIncome,
				maxMonthlyIncome, true, null, constellationId, null, null,
				minHeight, maxHeight, pager.getFirstResult(),
				pager.getMaxResult());
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
				com.juzhai.common.InitData.EDUCATION_MAP);
		model.addAttribute("strConstellationIds",
				StringUtils.join(constellationId, ","));
		model.addAttribute("pageType", "zbe");
		model.addAttribute("hots", searchHotService.getSearchHotByCity(city));
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
		Integer gender = null;
		if ("male".equals(sex)) {
			gender = 1;
		} else if ("female".equals(sex)) {
			gender = 0;
		}
		PagerManager pager = new PagerManager(pageId, 2,
				postSearchService.countSearchPosts(queryString, gender));
		List<Post> postList = postSearchService.searchPosts(queryString,
				gender, pager.getFirstResult(), pager.getMaxResult());
		List<PostView> postViewList = postService.assembleUserPostViewList(
				null, postList);
		model.addAttribute("pager", pager);
		model.addAttribute("postViewList", postViewList);
		model.addAttribute("queryString", queryString);
		model.addAttribute("sex", sex);
		loadFaces(model);
		model.addAttribute("hots", searchHotService.getSearchHotByCity(city));
		return "web/search/search_posts";
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

}
