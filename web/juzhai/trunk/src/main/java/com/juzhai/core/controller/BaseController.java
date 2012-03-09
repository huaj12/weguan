package com.juzhai.core.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.ui.Model;

import com.juzhai.act.bean.SuitAge;
import com.juzhai.act.bean.SuitGender;
import com.juzhai.act.bean.SuitStatus;
import com.juzhai.act.model.Category;
import com.juzhai.act.service.IActCategoryService;
import com.juzhai.core.exception.NeedLoginException;
import com.juzhai.core.web.ErrorPageDispatcher;
import com.juzhai.core.web.filter.CheckLoginFilter;
import com.juzhai.core.web.filter.CityChannelFilter;
import com.juzhai.core.web.session.UserContext;
import com.juzhai.core.web.util.HttpRequestUtil;
import com.juzhai.index.bean.ShowIdeaOrder;
import com.juzhai.index.controller.view.IdeaView;
import com.juzhai.passport.InitData;
import com.juzhai.passport.bean.AuthInfo;
import com.juzhai.passport.bean.ProfileCache;
import com.juzhai.passport.service.IProfileService;
import com.juzhai.passport.service.ITpUserAuthService;
import com.juzhai.post.model.Ad;
import com.juzhai.post.model.Idea;
import com.juzhai.post.service.IAdService;
import com.juzhai.post.service.IIdeaService;
import com.juzhai.post.service.IPostService;

public class BaseController {

	protected final Log log = LogFactory.getLog(getClass());

	protected final String error_404 = ErrorPageDispatcher.ERROR_404;
	protected final String error_500 = ErrorPageDispatcher.ERROR_500;

	private static final String MYACT_PAGE_TIPS = "myAct.page.tips";
	private static final String DAY_FIRST_LOGIN_TIP = "day.first.login.tip";
	private static final String NOT_SUB_EMAIL_TIP = "not.sub.email.tip";

	// @Autowired
	// private LoginSessionManager loginSessionManager;
	// @Autowired
	// private IAccountService accountService;
	@Autowired
	private ITpUserAuthService tpUserAuthService;
	@Autowired
	private IProfileService profileService;
	@Autowired
	private MessageSource messageSource;
	@Autowired
	private IActCategoryService actCategoryService;
	@Autowired
	private IIdeaService ideaService;
	@Autowired
	private IAdService adService;
	@Autowired
	private IPostService postService;

	protected UserContext checkLoginForApp(HttpServletRequest request)
			throws NeedLoginException {
		return checkLogin(request, NeedLoginException.RunType.APP);
	}

	protected UserContext checkLoginForWeb(HttpServletRequest request)
			throws NeedLoginException {
		return checkLogin(request, NeedLoginException.RunType.WEB);
	}

	private UserContext checkLogin(HttpServletRequest request,
			NeedLoginException.RunType runType) throws NeedLoginException {
		// UserContext context = loginSessionManager.getUserContext(request);
		UserContext context = (UserContext) request.getAttribute("context");
		if (context == null || !context.hasLogin()) {
			throw new NeedLoginException(runType);
		}
		return context;
	}

	protected ProfileCache getLoginUserCache(HttpServletRequest request) {
		ProfileCache loginUser = (ProfileCache) request
				.getAttribute(CheckLoginFilter.LOGIN_USER_KEY);
		return loginUser;
	}

	// protected void queryPoint(long uid, Model model) {
	// model.addAttribute("point", accountService.queryPoint(uid));
	// }

	public AuthInfo getAuthInfo(long uid, long tpId) {
		return tpUserAuthService.getAuthInfo(uid, tpId);
	}

	protected ProfileCache queryProfile(long uid, Model model) {
		ProfileCache profileCache = profileService.getProfileCacheByUid(uid);
		if (null != model) {
			model.addAttribute("profile", profileCache);
		}
		return profileCache;
	}

	protected void showMyActTips(Model model) {
		fetchTips(model, MYACT_PAGE_TIPS);
	}

	protected void showDayFirstLoginTip(Model model) {
		fetchTips(model, DAY_FIRST_LOGIN_TIP);
	}

	protected void showNotSubEmailTip(Model model) {
		fetchTips(model, NOT_SUB_EMAIL_TIP);
	}

	private void fetchTips(Model model, String tipsKey) {
		String tipStrings = messageSource.getMessage(tipsKey, null,
				Locale.SIMPLIFIED_CHINESE);
		if (StringUtils.isNotEmpty(tipStrings)) {
			String[] tips = tipStrings.split("\\|");
			model.addAttribute("tips", tips);
		}
	}

	protected long fetchCityId(HttpServletRequest request) {
		return HttpRequestUtil.getSessionAttributeAsLong(request,
				CityChannelFilter.SESSION_CHANNEL_NAME, 0L);
	}

	protected void loadCategoryList(Model model) {
		model.addAttribute("categoryList",
				com.juzhai.post.InitData.CATEGORY_MAP.values());
	}

	protected void assembleBaseDates(Model model) {
		model.addAttribute("towns", InitData.TOWN_MAP.values());
		List<Category> categoryList = actCategoryService.findAllCategory();
		model.addAttribute("categoryList", categoryList);
		model.addAttribute("citys", InitData.CITY_MAP.values());
		model.addAttribute("provinces", InitData.PROVINCE_MAP.values());
		model.addAttribute("suitAges", SuitAge.values());
		model.addAttribute("suitGenders", SuitGender.values());
		model.addAttribute("suitStatus", SuitStatus.values());
	}

	protected void assembleCitys(Model model) {
		model.addAttribute("towns", InitData.TOWN_MAP.values());
		model.addAttribute("citys", InitData.CITY_MAP.values());
		model.addAttribute("provinces", InitData.PROVINCE_MAP.values());
	}

	// protected void ideaWidget(UserContext context, long cityId, Model model,
	// int count) {
	// // TODO 是否要改成未发布的最新idea
	// if (count <= 0) {
	// count = 5;
	// }
	// List<Idea> ideaList = ideaService.listIdeaByCity(cityId,
	// ShowIdeaOrder.HOT_TIME, 0, count);
	// if (CollectionUtils.isEmpty(ideaList) && cityId > 0) {
	// ideaList = ideaService.listIdeaByCity(0L, ShowIdeaOrder.HOT_TIME,
	// 0, count);
	// }
	//
	// List<IdeaView> ideaViewList = new ArrayList<IdeaView>();
	// for (Idea idea : ideaList) {
	// IdeaView ideaView = new IdeaView();
	// ideaView.setIdea(idea);
	// ideaView.setHasUsed(ideaService.isUseIdea(context.getUid(),
	// idea.getId()));
	// if (idea.getCreateUid() > 0) {
	// ideaView.setProfileCache(profileService
	// .getProfileCacheByUid(idea.getCreateUid()));
	// }
	// ideaViewList.add(ideaView);
	// }
	// model.addAttribute("ideaViewList", ideaViewList);
	// }

	protected void newUserWidget(long cityId, Model model, int count) {
		if (count <= 0) {
			count = 12;
		}
		model.addAttribute("profileList", profileService
				.listProfileByCityIdOrderCreateTime(cityId, 0, count));
	}

	protected void ideaAdWidget(long cityId, Model model, int count) {
		if (count <= 0) {
			count = 2;
		}
		List<Ad> list = adService.listAd(cityId, 0, count);
		if (CollectionUtils.isEmpty(list) && cityId <= 0) {
			list = adService.listAd(0, 0, count);
		}
		model.addAttribute("ads", list);
	}

	protected void showHomeLogo(UserContext context, Model model) {
		model.addAttribute("postCount",
				postService.countUserPost(context.getUid()));
		model.addAttribute("responseCount",
				postService.getAllResponseCnt(context.getUid()));
		model.addAttribute("completion",
				profileService.getProfileCompletion(context.getUid()));
	}
}
