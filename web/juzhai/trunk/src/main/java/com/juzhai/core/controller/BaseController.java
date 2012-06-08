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

import com.juzhai.activity.controller.ActivityController;
import com.juzhai.common.InitData;
import com.juzhai.core.encrypt.DESUtils;
import com.juzhai.core.exception.NeedLoginException;
import com.juzhai.core.web.ErrorPageDispatcher;
import com.juzhai.core.web.filter.CheckLoginFilter;
import com.juzhai.core.web.session.UserContext;
import com.juzhai.home.controller.view.RecommendUserView;
import com.juzhai.home.service.IBlacklistService;
import com.juzhai.home.service.IGuessYouService;
import com.juzhai.index.controller.view.IdeaView;
import com.juzhai.passport.bean.AuthInfo;
import com.juzhai.passport.bean.ProfileCache;
import com.juzhai.passport.model.Passport;
import com.juzhai.passport.model.Profile;
import com.juzhai.passport.service.IInterestUserService;
import com.juzhai.passport.service.IPassportService;
import com.juzhai.passport.service.IProfileService;
import com.juzhai.passport.service.IRegisterService;
import com.juzhai.passport.service.ITpUserAuthService;
import com.juzhai.post.controller.view.PostView;
import com.juzhai.post.model.Ad;
import com.juzhai.post.model.Idea;
import com.juzhai.post.model.Post;
import com.juzhai.post.service.IAdService;
import com.juzhai.post.service.IIdeaService;
import com.juzhai.post.service.IPostService;
import com.juzhai.search.service.ISearchHotService;

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
	// @Autowired
	// private IActCategoryService actCategoryService;
	@Autowired
	private IIdeaService ideaService;
	@Autowired
	private IAdService adService;
	@Autowired
	private IPostService postService;
	@Autowired
	private IBlacklistService blacklistService;
	@Autowired
	private IPassportService passportService;
	@Autowired
	private IRegisterService registerService;
	@Autowired
	private IGuessYouService guessYouService;
	@Autowired
	private IInterestUserService interestUserService;
	@Autowired
	private ISearchHotService searchHotService;

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

	protected void loadCategoryList(Model model) {
		model.addAttribute("categoryList",
				com.juzhai.post.InitData.CATEGORY_MAP.values());
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

	protected void recommendUserWidget(long uid, int count, Model model) {
		List<Profile> list = guessYouService.recommendUsers(uid, count);
		List<RecommendUserView> recommendUserViewList = new ArrayList<RecommendUserView>(
				list.size());
		for (Profile profile : list) {
			RecommendUserView view = new RecommendUserView();
			view.setProfile(profile);
			if (uid > 0) {
				view.setHasInterest(interestUserService.isInterest(uid,
						profile.getUid()));
			}
			recommendUserViewList.add(view);
		}
		model.addAttribute("recommendUserViewList", recommendUserViewList);
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
		// model.addAttribute("responseCount",
		// postService.getAllResponseCnt(context.getUid()));
		model.addAttribute("completion",
				profileService.getProfileCompletion(context.getUid()));
		model.addAttribute("interestCount",
				interestUserService.countInterestUser(context.getUid()));
		Passport passport = passportService.getPassportByUid(context.getUid());
		if (null != passport) {
			model.addAttribute("hasNotAccount",
					!registerService.hasAccount(passport));
			model.addAttribute("hasNotActive",
					!registerService.hasActiveEmail(passport));
		}
	}

	protected long decryptInviteUid(String token) {
		if (StringUtils.isEmpty(token)) {
			return 0L;
		}
		long realUid = 0L;
		try {
			realUid = Long.valueOf(DESUtils.decryptToString(
					ActivityController.INVITE_SECRET.getBytes(), token));
		} catch (Exception e) {
			log.error("decrypt inviter uid error.", e);
			return 0L;
		}
		return realUid;
	}

	protected void loadFaces(Model model) {
		model.addAttribute("faceList", InitData.FACE_MAP.values());
	}

	protected void isShield(Model model, long createUid, long shieldUid) {
		model.addAttribute("isShield",
				blacklistService.isShield(createUid, shieldUid));
	}

	protected void loadRecentIdeas(long uid, int count,
			List<Long> excludeIdeaIds, Model model) {
		Long cityId = null;
		if (uid > 0) {
			ProfileCache profile = profileService.getProfileCacheByUid(uid);
			if (null != profile) {
				cityId = profile.getCity();
			}
		}
		List<Idea> list = ideaService.listRecentIdeas(uid, cityId,
				excludeIdeaIds, 0, count);
		List<IdeaView> recentIdeaViewList = new ArrayList<IdeaView>(list.size());
		for (Idea idea : list) {
			IdeaView ideaView = new IdeaView();
			ideaView.setIdea(idea);
			if (idea.getCreateUid() > 0) {
				ideaView.setProfileCache(profileService
						.getProfileCacheByUid(idea.getCreateUid()));
			}
			recentIdeaViewList.add(ideaView);
		}
		model.addAttribute("recentIdeaViewList", recentIdeaViewList);
	}

	// 获取热词
	// TODO (done) hots？热XX？
	protected void hotWordsWidget(Model model, long city, int count) {
		model.addAttribute("hots",
				searchHotService.getSearchHotByCity(city, count));
	}

	// 正在找伴的小宅
	// TODO (done) 名字取的好点，类似于“ideaAdWidget”
	protected void userPostWidget(Model model, long uid, long city, int count) {
		List<PostView> listView = new ArrayList<PostView>();
		List<Post> list = postService.listNewestPost(uid, city, null, null, 0,
				count);
		for (Post post : list) {
			ProfileCache cache = profileService.getProfileCacheByUid(post
					.getCreateUid());
			PostView view = new PostView();
			view.setPost(post);
			view.setProfileCache(cache);
			listView.add(view);
		}
		model.addAttribute("postView", listView);
	}

}
