package com.juzhai.lab.controller;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.juzhai.core.bean.UseLevel;
import com.juzhai.core.controller.BaseController;
import com.juzhai.core.exception.JuzhaiException;
import com.juzhai.core.exception.NeedLoginException;
import com.juzhai.core.exception.NeedLoginException.RunType;
import com.juzhai.core.exception.UploadImageException;
import com.juzhai.core.image.JzImageSizeType;
import com.juzhai.core.image.LogoSizeType;
import com.juzhai.core.image.manager.IImageManager;
import com.juzhai.core.pager.PagerManager;
import com.juzhai.core.util.DateFormat;
import com.juzhai.core.web.AjaxResult;
import com.juzhai.core.web.jstl.JzResourceFunction;
import com.juzhai.core.web.session.UserContext;
import com.juzhai.home.bean.DialogContentTemplate;
import com.juzhai.home.controller.view.DialogContentView;
import com.juzhai.home.controller.view.DialogView;
import com.juzhai.home.exception.DialogException;
import com.juzhai.home.model.DialogContent;
import com.juzhai.home.service.IDialogService;
import com.juzhai.index.bean.ShowIdeaOrder;
import com.juzhai.lab.controller.form.ProfileMForm;
import com.juzhai.lab.controller.view.DialogContentMView;
import com.juzhai.lab.controller.view.DialogMView;
import com.juzhai.lab.controller.view.IdeaMView;
import com.juzhai.lab.controller.view.IdeaUserMView;
import com.juzhai.lab.controller.view.PostMView;
import com.juzhai.lab.controller.view.UserMView;
import com.juzhai.notice.bean.NoticeType;
import com.juzhai.notice.service.INoticeService;
import com.juzhai.passport.bean.AuthInfo;
import com.juzhai.passport.bean.ProfileCache;
import com.juzhai.passport.controller.form.LoginForm;
import com.juzhai.passport.controller.form.RegisterForm;
import com.juzhai.passport.dao.IUserPositionDao;
import com.juzhai.passport.exception.InterestUserException;
import com.juzhai.passport.exception.PassportAccountException;
import com.juzhai.passport.exception.ProfileInputException;
import com.juzhai.passport.exception.ReportAccountException;
import com.juzhai.passport.mapper.UserPositionMapper;
import com.juzhai.passport.model.City;
import com.juzhai.passport.model.Constellation;
import com.juzhai.passport.model.Profession;
import com.juzhai.passport.model.Profile;
import com.juzhai.passport.model.Province;
import com.juzhai.passport.model.Thirdparty;
import com.juzhai.passport.model.Town;
import com.juzhai.passport.model.UserGuide;
import com.juzhai.passport.model.UserPositionExample;
import com.juzhai.passport.service.IInterestUserService;
import com.juzhai.passport.service.ILoginService;
import com.juzhai.passport.service.IPassportService;
import com.juzhai.passport.service.IProfileService;
import com.juzhai.passport.service.IRegisterService;
import com.juzhai.passport.service.IUserGuideService;
import com.juzhai.platform.bean.Terminal;
import com.juzhai.platform.service.IUserService;
import com.juzhai.post.InitData;
import com.juzhai.post.bean.PurposeType;
import com.juzhai.post.controller.form.PostForm;
import com.juzhai.post.controller.view.IdeaUserView;
import com.juzhai.post.exception.InputPostException;
import com.juzhai.post.model.Category;
import com.juzhai.post.model.Idea;
import com.juzhai.post.model.Post;
import com.juzhai.post.service.IIdeaService;
import com.juzhai.post.service.IPostService;

@Controller
@RequestMapping("app/ios")
public class IOSController extends BaseController {

	@Autowired
	private ILoginService loginService;
	@Autowired
	private MessageSource messageSource;
	@Autowired
	private IUserGuideService userGuideService;
	@Autowired
	private IProfileService profileService;
	@Autowired
	private IImageManager imageManager;
	@Autowired
	private UserPositionMapper userPositionMapper;
	@Autowired
	private IUserPositionDao userPositionDao;
	@Autowired
	private IIdeaService ideaService;
	@Autowired
	private IInterestUserService interestUserService;
	@Autowired
	private IRegisterService registerService;
	@Autowired
	private IPostService postService;
	@Autowired
	private IPassportService passportService;
	@Autowired
	private IUserService userService;
	@Autowired
	private IDialogService dialogService;
	@Autowired
	private INoticeService noticeService;
	// @Value("${web.show.ideas.max.rows}")
	private int webShowIdeasMaxRows = 1;
	// @Value("${web.show.users.max.rows}")
	private int webShowUsersMaxRows = 1;
	// @Value("${web.my.post.max.rows}")
	private int webMyPostMaxRows = 2;
	// @Value("mobile.interest.user.max.rows")
	private int mobileInterestUserMaxRows = 1;
	// @Value("mobile.dialog.max.rows")
	private int mobileDialogMaxRows = 1;
	// @Value("mobile.idea.user.max.rows")
	private int mobileIdeaUserMaxRows = 1;
	// @Value("mobile.dialog.content.max.rows")
	private int mobileDialogContentsMaxRows = 10;
	private int mobileRefreshDialogContentsCount = 10;

	@RequestMapping(value = "/tpLogin/{tpId}")
	public String webLogin(HttpServletRequest request,
			HttpServletResponse response, Model model, @PathVariable long tpId)
			throws UnsupportedEncodingException {
		Thirdparty tp = com.juzhai.passport.InitData.TP_MAP.get(tpId);
		if (null == tp) {
			return "404";
		}
		String url = userService.getAuthorizeURLforCode(request, response, tp,
				Terminal.MOBILE, null, null);
		if (StringUtils.isEmpty(url)) {
			return "404";
		}
		return "redirect:" + url + "&display=mobile";
	}

	@RequestMapping(value = "tpAccess/{tpId}")
	@ResponseBody
	public AjaxResult webAccess(HttpServletRequest request,
			HttpServletResponse response, @PathVariable long tpId,
			String turnTo, String incode, String error_code, Model model)
			throws UnsupportedEncodingException, MalformedURLException,
			ReportAccountException {
		AjaxResult result = new AjaxResult();
		Thirdparty tp = com.juzhai.passport.InitData.TP_MAP.get(tpId);
		if (null == tp) {
			result.setError(JuzhaiException.ILLEGAL_OPERATION, messageSource);
			return result;
		}
		if (StringUtils.isNotEmpty(error_code)) {
			result.setError(JuzhaiException.SYSTEM_ERROR, messageSource);
			return result;
		}
		long uid = 0;
		UserContext context = (UserContext) request.getAttribute("context");
		if (context.hasLogin()) {
			uid = context.getUid();
		} else {
			if (null != tp) {
				if (log.isDebugEnabled()) {
					log.debug("thirdparty access [tpName=" + tp.getName()
							+ ", joinType=" + tp.getJoinType() + "]");
				}
				AuthInfo authInfo = null;
				uid = userService.access(request, response, authInfo, tp,
						decryptInviteUid(incode));
			}
			if (uid <= 0) {
				log.error("access failed.[tpName=" + tp.getName()
						+ ", joinType=" + tp.getJoinType() + "].");
				result.setError(JuzhaiException.SYSTEM_ERROR, messageSource);
				return result;
			}
			try {
				loginService.login(request, response, uid, tp.getId(),
						RunType.CONNET);
				context = (UserContext) request.getAttribute("context");
			} catch (PassportAccountException e) {
				result.setError(e.getErrorCode(), messageSource);
				return result;
			}
		}
		result.setResult(createUserMView(context,
				profileService.getProfileCacheByUid(uid),
				userGuideService.isCompleteGuide(uid)));
		return result;
	}

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	@ResponseBody
	public AjaxResult login(HttpServletRequest request,
			HttpServletResponse response, LoginForm loginForm)
			throws ReportAccountException {
		AjaxResult result = new AjaxResult();
		UserContext context = (UserContext) request.getAttribute("context");
		if (context.hasLogin()) {
			return result;
		}
		long uid = 0L;
		try {
			uid = loginService.login(request, response, loginForm.getAccount(),
					loginForm.getPassword(), loginForm.isRemember());
			context = (UserContext) request.getAttribute("context");
		} catch (PassportAccountException e) {
			result.setError(e.getErrorCode(), messageSource);
			return result;
		}
		if (uid <= 0) {
			result.setError(JuzhaiException.SYSTEM_ERROR, messageSource);
		}
		// Profile profile = profileService.getProfile(uid);
		result.setResult(createUserMView(context,
				profileService.getProfileCacheByUid(uid),
				userGuideService.isCompleteGuide(uid)));
		return result;
	}

	@RequestMapping(value = "/register", method = RequestMethod.POST)
	@ResponseBody
	public AjaxResult register(HttpServletRequest request,
			HttpServletResponse response, RegisterForm registerForm) {
		UserContext context = (UserContext) request.getAttribute("context");
		AjaxResult result = new AjaxResult();
		if (context.hasLogin()) {
			result.setError(JuzhaiException.ILLEGAL_OPERATION, messageSource);
			return result;
		}
		try {
			long uid = registerService.register(registerForm.getAccount(),
					registerForm.getNickname(), registerForm.getPwd(),
					registerForm.getConfirmPwd(), registerForm.getInviterUid());
			loginService.autoLogin(request, response, uid);
			result.setResult(createUserMView(context,
					profileService.getProfileCacheByUid(uid), false));
			try {
				registerService.sendAccountMail(uid);
			} catch (PassportAccountException e) {
			}
		} catch (JuzhaiException e) {
			result.setError(e.getErrorCode(), messageSource);
		}
		return result;
	}

	@RequestMapping(value = "/logout")
	@ResponseBody
	public AjaxResult logout(HttpServletRequest request,
			HttpServletResponse response) {
		AjaxResult result = new AjaxResult();
		try {
			UserContext context = checkLoginForWeb(request);
			loginService.logout(request, response, context.getUid());
		} catch (NeedLoginException e) {
		}
		return result;
	}

	@RequestMapping(value = "/upload", method = RequestMethod.POST)
	@ResponseBody
	public AjaxResult upload(HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam("photo") MultipartFile photo)
			throws NeedLoginException {
		checkLoginForWeb(request);
		AjaxResult result = new AjaxResult();
		try {
			imageManager.checkImage(photo);
			String[] urls = imageManager.uploadTempImage(photo);
			result.setResult(urls[0]);
		} catch (UploadImageException e) {
			result.setError(e.getErrorCode(), messageSource);
		}
		return result;
	}

	@RequestMapping(value = "/updateloc")
	@ResponseBody
	public String updateLocation(HttpServletRequest request,
			HttpServletResponse response) {
		UserPositionExample example = new UserPositionExample();
		example.createCriteria().andUidEqualTo(1L);
		if (userPositionMapper.countByExample(example) > 0) {
			userPositionDao.update(1L, 34.53, 23.56);
		} else {
			userPositionDao.insert(1L, 10.22, 11.22);
		}
		return "success";
	}

	@RequestMapping(value = "/ideaList", method = RequestMethod.GET)
	@ResponseBody
	public AjaxResult ideaList(HttpServletRequest request, long categoryId,
			String orderType, int page) {
		UserContext context = (UserContext) request.getAttribute("context");
		ProfileCache loginUser = getLoginUserCache(request);
		long cityId = 0L;
		if (loginUser != null && loginUser.getCity() != null) {
			cityId = loginUser.getCity();
		}
		ShowIdeaOrder order = ShowIdeaOrder.getShowIdeaOrderByType(orderType);
		PagerManager pager = new PagerManager(page, webShowIdeasMaxRows,
				ideaService.countIdeaByCityAndCategory(cityId, categoryId));
		List<Idea> ideaList = ideaService
				.listIdeaByCityAndCategory(cityId, categoryId, order,
						pager.getFirstResult(), pager.getMaxResult());
		List<IdeaMView> ideaViewList = new ArrayList<IdeaMView>(ideaList.size());
		for (Idea idea : ideaList) {
			IdeaMView ideaMView = new IdeaMView();
			ideaMView.setIdeaId(idea.getId());
			ideaMView.setContent(idea.getContent());
			ideaMView.setPlace(idea.getPlace());
			ideaMView.setCharge(idea.getCharge());
			if (StringUtils.isNotEmpty(idea.getPic())) {
				ideaMView.setPic(JzResourceFunction.ideaPic(idea.getId(),
						idea.getPic(), JzImageSizeType.MIDDLE.getType()));
			}
			City city = com.juzhai.common.InitData.CITY_MAP.get(idea.getCity());
			if (null != city) {
				ideaMView.setCityName(city.getName());
			}
			Town town = com.juzhai.common.InitData.TOWN_MAP.get(idea.getTown());
			if (null != town) {
				ideaMView.setTownName(town.getName());
			}
			Category category = InitData.CATEGORY_MAP.get(idea.getCategoryId());
			if (null != category) {
				ideaMView.setCategoryName(category.getName());
			}
			ideaMView.setUseCount(idea.getUseCount());
			if (context.hasLogin()) {
				ideaMView.setHasUsed(ideaService.isUseIdea(context.getUid(),
						idea.getId()));
			}
			ideaMView.setBigPic(JzResourceFunction.ideaPic(idea.getId(),
					idea.getPic(), JzImageSizeType.BIG.getType()));
			ideaViewList.add(ideaMView);
		}
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("ideaViewList", ideaViewList);
		result.put("pager", pager);

		AjaxResult ajaxResult = new AjaxResult();
		ajaxResult.setResult(result);
		return ajaxResult;
	}

	@RequestMapping(value = "/userList", method = RequestMethod.GET)
	@ResponseBody
	public AjaxResult userList(HttpServletRequest request, Integer gender,
			String orderType, int page) {
		UserContext context = (UserContext) request.getAttribute("context");
		ProfileCache loginUser = getLoginUserCache(request);
		long cityId = 0L;
		if (loginUser != null && loginUser.getCity() != null) {
			cityId = loginUser.getCity();
		}
		PagerManager pager = new PagerManager(page, webShowUsersMaxRows,
				profileService.countQueryProfile(context.getUid(), gender,
						cityId, 0L, 0, 0));
		List<Profile> profileList = profileService.queryProfile(
				context.getUid(), gender, cityId, 0L, 0, 0,
				pager.getFirstResult(), pager.getMaxResult());

		List<Long> uidList = new ArrayList<Long>();
		for (Profile profile : profileList) {
			uidList.add(profile.getUid());
		}
		Map<Long, Post> userLatestPostMap = postService
				.getMultiUserLatestPosts(uidList);

		List<UserMView> userViewList = new ArrayList<UserMView>(
				profileList.size());
		for (Profile profile : profileList) {
			UserMView userView = createUserMView(context,
					profileService.getProfileCacheByUid(profile.getUid()),
					false);
			Post post = userLatestPostMap.get(profile.getUid());
			if (post == null) {
				continue;
			}
			PostMView postView = createPostMView(context, post);
			userView.setPostView(postView);
			userViewList.add(userView);
		}
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("userViewList", userViewList);
		result.put("pager", pager);

		AjaxResult ajaxResult = new AjaxResult();
		ajaxResult.setResult(result);
		return ajaxResult;
	}

	private PostMView createPostMView(UserContext context, Post post) {
		PostMView postView = new PostMView();
		postView.setPostId(post.getId());
		postView.setContent(post.getContent());
		postView.setPic(JzResourceFunction.postPic(post.getId(),
				post.getIdeaId(), post.getPic(),
				JzImageSizeType.MIDDLE.getType()));
		postView.setBigPic(JzResourceFunction.postPic(post.getId(),
				post.getIdeaId(), post.getPic(), JzImageSizeType.BIG.getType()));
		postView.setPlace(post.getPlace());
		postView.setPurpose(messageSource.getMessage(
				PurposeType.getWordMessageKey(post.getPurposeType()), null,
				Locale.SIMPLIFIED_CHINESE));
		postView.setRespCnt(post.getResponseCnt());
		if (null != post.getDateTime()) {
			postView.setDate(DateFormat.SDF.format(post.getDateTime()));
		}
		Category category = InitData.CATEGORY_MAP.get(post.getCategoryId());
		if (null != category) {
			postView.setCategoryName(category.getName());
		}
		if (null != context && context.hasLogin()
				&& context.getUid() != post.getCreateUid()) {
			postView.setHasResp(postService.isResponsePost(context.getUid(),
					post.getId()));
		}
		return postView;
	}

	// private UserMView createUserMView(UserContext context, Profile profile,
	// boolean isCompleteGuide) {
	// UserMView userView = new UserMView();
	// userView.setHasGuided(isCompleteGuide);
	// userView.setUid(profile.getUid());
	// userView.setNickname(profile.getNickname());
	// if (null != profile.getGender()) {
	// userView.setGender(profile.getGender());
	// }
	// userView.setBirthYear(profile.getBirthYear());
	// userView.setBirthMonth(profile.getBirthMonth());
	// userView.setBirthDay(profile.getBirthDay());
	// userView.setFeature(profile.getFeature());
	// Province province = com.juzhai.common.InitData.PROVINCE_MAP.get(profile
	// .getProvince());
	// if (null != province) {
	// userView.setProvinceId(province.getId());
	// userView.setProvinceName(province.getName());
	// }
	// City city = com.juzhai.common.InitData.CITY_MAP.get(profile.getCity());
	// if (null != city) {
	// userView.setCityId(city.getId());
	// userView.setCityName(city.getName());
	// }
	// Town town = com.juzhai.common.InitData.TOWN_MAP.get(profile.getTown());
	// if (null != town) {
	// userView.setTownId(town.getId());
	// userView.setTownName(town.getName());
	// }
	// userView.setLogo(JzResourceFunction.userLogo(profile.getUid(),
	// profile.getLogoPic(), LogoSizeType.MIDDLE.getType()));
	// userView.setSmallLogo(JzResourceFunction.userLogo(profile.getUid(),
	// profile.getLogoPic(), LogoSizeType.SMALL.getType()));
	// userView.setBigLogo(JzResourceFunction.userLogo(profile.getUid(),
	// profile.getLogoPic(), LogoSizeType.BIG.getType()));
	// userView.setNewLogo(JzResourceFunction.userLogo(profile.getUid(),
	// profile.getNewLogoPic(), LogoSizeType.MIDDLE.getType()));
	// userView.setLogoVerifyState(profile.getLogoVerifyState());
	// Constellation con = com.juzhai.passport.InitData.CONSTELLATION_MAP
	// .get(profile.getConstellationId());
	// if (null != con) {
	// userView.setConstellation(con.getName());
	// }
	// userView.setProfessionId(profile.getProfessionId());
	// userView.setProfession(profile.getProfession());
	// userView.setInterestUserCount(interestUserService
	// .countInterestUser(userView.getUid()));
	// userView.setInterestMeCount(interestUserService
	// .countInterestMeUser(userView.getUid()));
	//
	// return userView;
	// }

	private UserMView createUserMView(UserContext context,
			ProfileCache profileCache, boolean isCompleteGuide) {
		UserMView userView = new UserMView();
		userView.setHasGuided(isCompleteGuide);
		userView.setUid(profileCache.getUid());
		userView.setNickname(profileCache.getNickname());
		if (null != profileCache.getGender()) {
			userView.setGender(profileCache.getGender());
		}
		userView.setBirthYear(profileCache.getBirthYear());
		userView.setBirthMonth(profileCache.getBirthMonth());
		userView.setBirthDay(profileCache.getBirthDay());
		userView.setFeature(profileCache.getFeature());
		Province province = com.juzhai.common.InitData.PROVINCE_MAP
				.get(profileCache.getProvince());
		if (null != province) {
			userView.setProvinceId(province.getId());
			userView.setProvinceName(province.getName());
		}
		City city = com.juzhai.common.InitData.CITY_MAP.get(profileCache
				.getCity());
		if (null != city) {
			userView.setCityId(city.getId());
			userView.setCityName(city.getName());
		}
		Town town = com.juzhai.common.InitData.TOWN_MAP.get(profileCache
				.getTown());
		if (null != town) {
			userView.setTownId(town.getId());
			userView.setTownName(town.getName());
		}
		userView.setLogo(JzResourceFunction.userLogo(profileCache.getUid(),
				profileCache.getLogoPic(), LogoSizeType.MIDDLE.getType()));
		userView.setSmallLogo(JzResourceFunction.userLogo(
				profileCache.getUid(), profileCache.getLogoPic(),
				LogoSizeType.SMALL.getType()));
		userView.setBigLogo(JzResourceFunction.userLogo(profileCache.getUid(),
				profileCache.getLogoPic(), LogoSizeType.BIG.getType()));
		userView.setNewLogo(JzResourceFunction.userLogo(profileCache.getUid(),
				profileCache.getNewLogoPic(), LogoSizeType.MIDDLE.getType()));
		userView.setLogoVerifyState(profileCache.getLogoVerifyState());
		Constellation con = com.juzhai.passport.InitData.CONSTELLATION_MAP
				.get(profileCache.getConstellationId());
		if (null != con) {
			userView.setConstellation(con.getName());
		}
		userView.setProfessionId(profileCache.getProfessionId());
		userView.setProfession(profileCache.getProfession());
		if (context != null && context.hasLogin()) {
			if (context.getUid() == profileCache.getUid().longValue()) {
				userView.setInterestUserCount(interestUserService
						.countInterestUser(userView.getUid()));
				userView.setInterestMeCount(interestUserService
						.countInterestMeUser(userView.getUid()));
				userView.setPostCount(postService.countUserPost(userView
						.getUid()));
			} else {
				userView.setHasInterest(interestUserService.isInterest(
						context.getUid(), profileCache.getUid()));
			}
		}
		return userView;
	}

	@RequestMapping(value = "/categoryList", method = RequestMethod.GET)
	@ResponseBody
	public AjaxResult loadCategoryList(HttpServletRequest request) {
		List<Category> categoryList = new ArrayList<Category>(
				InitData.CATEGORY_MAP.values());
		List<Map<Long, String>> mapList = new ArrayList<Map<Long, String>>(
				categoryList.size());
		for (Category cat : categoryList) {
			Map<Long, String> categoryMap = new HashMap<Long, String>(2);
			categoryMap.put(cat.getId(), cat.getName());
			mapList.add(categoryMap);
		}
		AjaxResult result = new AjaxResult();
		result.setResult(mapList);
		return result;
	}

	@RequestMapping(value = "/professionList", method = RequestMethod.GET)
	@ResponseBody
	public AjaxResult loadProfessionList(HttpServletRequest request) {
		List<Profession> professionList = new ArrayList<Profession>(
				com.juzhai.passport.InitData.PROFESSION_MAP.values());
		List<Map<Long, String>> mapList = new ArrayList<Map<Long, String>>(
				professionList.size());
		for (Profession p : professionList) {
			Map<Long, String> professionMap = new HashMap<Long, String>(2);
			professionMap.put(p.getId(), p.getName());
			mapList.add(professionMap);
		}
		AjaxResult result = new AjaxResult();
		result.setResult(mapList);
		return result;
	}

	@RequestMapping(value = "/provinceCityList", method = RequestMethod.GET)
	@ResponseBody
	public AjaxResult loadProvinceCityList(HttpServletRequest request) {
		Map<String, List<Map<String, Object>>> resultMap = new HashMap<String, List<Map<String, Object>>>(
				3);
		List<Map<String, Object>> provinceList = new ArrayList<Map<String, Object>>();
		List<Map<String, Object>> cityList = new ArrayList<Map<String, Object>>();
		resultMap.put("provinceList", provinceList);
		resultMap.put("cityList", cityList);
		for (Map.Entry<Long, Province> entry : com.juzhai.common.InitData.PROVINCE_MAP
				.entrySet()) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("provinceId", entry.getValue().getId());
			map.put("provinceName", entry.getValue().getName());
			provinceList.add(map);
		}
		for (Map.Entry<Long, City> entry : com.juzhai.common.InitData.CITY_MAP
				.entrySet()) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("cityId", entry.getValue().getId());
			map.put("cityName", entry.getValue().getName());
			map.put("provinceId", entry.getValue().getProvinceId());
			cityList.add(map);
		}

		AjaxResult result = new AjaxResult();
		result.setResult(resultMap);
		return result;
	}

	@RequestMapping(value = "/home", method = RequestMethod.GET)
	@ResponseBody
	public AjaxResult home(HttpServletRequest request, Long uid, int page) {
		UserContext context;
		try {
			context = checkLoginForWeb(request);
		} catch (NeedLoginException e) {
			return AjaxResult.ERROR_RESULT;
		}
		if (uid == null || uid <= 0) {
			uid = context.getUid();
		}
		Map<String, Object> mapResult = new HashMap<String, Object>(3);
		AjaxResult result = new AjaxResult();
		result.setResult(mapResult);

		PagerManager pager = new PagerManager(page, webMyPostMaxRows,
				postService.countUserPost(uid));
		List<Post> postList = postService.listUserPost(uid, null,
				pager.getFirstResult(), pager.getMaxResult());
		List<PostMView> postViewList = new ArrayList<PostMView>(postList.size());
		for (Post post : postList) {
			postViewList.add(createPostMView(context, post));
		}
		mapResult.put("postViewList", postViewList);
		mapResult.put("pager", pager);

		return result;
	}

	@RequestMapping(value = "/home/refresh", method = RequestMethod.GET)
	@ResponseBody
	public AjaxResult homeRefresh(HttpServletRequest request) {
		UserContext context;
		try {
			context = checkLoginForWeb(request);
		} catch (NeedLoginException e) {
			return AjaxResult.ERROR_RESULT;
		}
		AjaxResult result = new AjaxResult();
		result.setResult(createUserMView(context,
				profileService.getProfileCacheByUid(context.getUid()),
				userGuideService.isCompleteGuide(context.getUid())));
		return result;
	}

	@RequestMapping(value = "/profile/save", method = RequestMethod.POST)
	@ResponseBody
	public AjaxResult saveProfile(HttpServletRequest request,
			ProfileMForm profileForm) {
		UserContext context;
		try {
			context = checkLoginForWeb(request);
		} catch (NeedLoginException e) {
			return AjaxResult.ERROR_RESULT;
		}
		AjaxResult result = new AjaxResult();
		try {
			profileService.updateLogoAndProfile(context.getUid(), profileForm);
			result.setResult(createUserMView(context,
					profileService.getProfileCacheByUid(context.getUid()),
					userGuideService.isCompleteGuide(context.getUid())));
		} catch (ProfileInputException e) {
			result.setError(e.getErrorCode(), messageSource);
		} catch (UploadImageException e) {
			result.setError(e.getErrorCode(), messageSource);
		}
		return result;
	}

	@RequestMapping(value = "/profile/guide", method = RequestMethod.POST)
	@ResponseBody
	public AjaxResult guideProfile(HttpServletRequest request,
			ProfileMForm profileForm) {
		UserContext context;
		try {
			context = checkLoginForWeb(request);
		} catch (NeedLoginException e) {
			return AjaxResult.ERROR_RESULT;
		}
		AjaxResult result = new AjaxResult();
		try {
			profileService.updateLogoAndProfile(context.getUid(), profileForm);
			UserGuide userGuide = userGuideService.getUserGuide(context
					.getUid());
			if (userGuide == null) {
				userGuideService.createAndCompleteGuide(context.getUid());
			} else {
				userGuideService.completeGuide(context.getUid());
			}
			// 完成引导后提升用户使用等级
			passportService.setUseLevel(context.getUid(), UseLevel.Level1);
			result.setResult(createUserMView(context,
					profileService.getProfileCacheByUid(context.getUid()), true));
		} catch (ProfileInputException e) {
			result.setError(e.getErrorCode(), messageSource);
		} catch (UploadImageException e) {
			result.setError(e.getErrorCode(), messageSource);
		}
		return result;
	}

	@RequestMapping(value = "/interestList", method = RequestMethod.GET)
	@ResponseBody
	public AjaxResult listInterestUser(HttpServletRequest request, int page) {
		UserContext context;
		try {
			context = checkLoginForWeb(request);
		} catch (NeedLoginException e) {
			return AjaxResult.ERROR_RESULT;
		}
		PagerManager pager = new PagerManager(page, mobileInterestUserMaxRows,
				interestUserService.countInterestUser(context.getUid()));
		List<ProfileCache> list = interestUserService.listInterestUser(
				context.getUid(), pager.getFirstResult(), pager.getMaxResult());

		List<UserMView> userViewList = new ArrayList<UserMView>(list.size());
		for (ProfileCache profileCache : list) {
			UserMView userView = createUserMView(context, profileCache, false);
			userViewList.add(userView);
		}

		Map<String, Object> result = new HashMap<String, Object>();
		result.put("userViewList", userViewList);
		result.put("pager", pager);

		AjaxResult ajaxResult = new AjaxResult();
		ajaxResult.setResult(result);
		return ajaxResult;
	}

	@RequestMapping(value = "/interestMeList", method = RequestMethod.GET)
	@ResponseBody
	public AjaxResult listInterestMeUser(HttpServletRequest request, int page) {
		UserContext context;
		try {
			context = checkLoginForWeb(request);
		} catch (NeedLoginException e) {
			return AjaxResult.ERROR_RESULT;
		}
		PagerManager pager = new PagerManager(page, mobileInterestUserMaxRows,
				interestUserService.countInterestMeUser(context.getUid()));
		List<ProfileCache> list = interestUserService.listInterestMeUser(
				context.getUid(), pager.getFirstResult(), pager.getMaxResult());

		List<UserMView> userViewList = new ArrayList<UserMView>(list.size());
		for (ProfileCache profileCache : list) {
			UserMView userView = createUserMView(context, profileCache, false);
			userViewList.add(userView);
		}

		Map<String, Object> result = new HashMap<String, Object>();
		result.put("userViewList", userViewList);
		result.put("pager", pager);

		AjaxResult ajaxResult = new AjaxResult();
		ajaxResult.setResult(result);
		return ajaxResult;
	}

	@RequestMapping(value = "/respPost", method = RequestMethod.POST)
	@ResponseBody
	public AjaxResult respPost(HttpServletRequest request, long postId) {
		UserContext context;
		try {
			context = checkLoginForWeb(request);
		} catch (NeedLoginException e) {
			return AjaxResult.ERROR_RESULT;
		}
		AjaxResult result = new AjaxResult();
		try {
			postService.responsePost(context.getUid(), postId,
					StringUtils.EMPTY);
		} catch (InputPostException e) {
			result.setError(e.getErrorCode(), messageSource);
		}
		return result;
	}

	@RequestMapping(value = "/sendPost", method = RequestMethod.POST)
	@ResponseBody
	public AjaxResult sendPost(HttpServletRequest request, Model model,
			PostForm postForm) throws NeedLoginException {
		UserContext context = checkLoginForWeb(request);
		AjaxResult result = new AjaxResult();
		try {
			long postId = postService.createPost(context.getUid(), postForm);
			if (postId > 0 && context.getTpId() > 0) {
				postService.synchronizeWeibo(context.getUid(),
						context.getTpId(), postId);
			}
		} catch (InputPostException e) {
			result.setError(e.getErrorCode(), messageSource);
		} catch (UploadImageException e) {
			result.setError(e.getErrorCode(), messageSource);
		}
		return result;
	}

	@RequestMapping(value = "/dialogList", method = RequestMethod.GET)
	@ResponseBody
	public AjaxResult dialogList(HttpServletRequest request, Model model,
			int page) {
		UserContext context;
		try {
			context = checkLoginForWeb(request);
		} catch (NeedLoginException e) {
			return AjaxResult.ERROR_RESULT;
		}
		PagerManager pager = new PagerManager(page, mobileDialogMaxRows,
				dialogService.countDialong(context.getUid()));
		List<DialogView> dialogViewlist = dialogService.listDialog(
				context.getUid(), pager.getFirstResult(), pager.getMaxResult());
		List<DialogMView> list = new ArrayList<DialogMView>(
				dialogViewlist.size());
		for (DialogView dialogView : dialogViewlist) {
			DialogMView dialogMView = new DialogMView();
			dialogMView.setDialogId(dialogView.getDialog().getId());
			dialogMView.setReceiverUid(dialogView.getDialogContent()
					.getReceiverUid());
			dialogMView.setLatestContent(dialogView.getDialogContent()
					.getContent());
			dialogMView.setCreateTime(dialogView.getDialogContent()
					.getCreateTime().getTime());
			dialogMView.setDialogContentCount(dialogView.getDialogContentCnt());
			dialogMView.setTargetUser(createUserMView(context,
					dialogView.getTargetProfile(), false));

			list.add(dialogMView);
		}
		noticeService.emptyNotice(context.getUid(), NoticeType.DIALOG);
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("dialogViewList", list);
		result.put("pager", pager);

		AjaxResult ajaxResult = new AjaxResult();
		ajaxResult.setResult(result);
		return ajaxResult;
	}

	@RequestMapping(value = "/deleteDialog", method = RequestMethod.POST)
	@ResponseBody
	public AjaxResult deleteDialog(HttpServletRequest request, long dialogId) {
		UserContext context;
		try {
			context = checkLoginForWeb(request);
		} catch (NeedLoginException e) {
			return AjaxResult.ERROR_RESULT;
		}
		boolean success = dialogService
				.deleteDialog(context.getUid(), dialogId);
		AjaxResult result = new AjaxResult();
		result.setSuccess(success);
		return result;
	}

	@ResponseBody
	@RequestMapping(value = "/interest", method = RequestMethod.POST)
	public AjaxResult interest(HttpServletRequest request, long uid, Model model)
			throws NeedLoginException {
		UserContext context;
		try {
			context = checkLoginForWeb(request);
		} catch (NeedLoginException e) {
			return AjaxResult.ERROR_RESULT;
		}
		AjaxResult result = new AjaxResult();
		try {
			interestUserService.interestUser(context.getUid(), uid);
			result.setSuccess(true);
		} catch (InterestUserException e) {
			result.setError(e.getErrorCode(), messageSource);
		}
		return result;
	}

	@ResponseBody
	@RequestMapping(value = "/removeInterest", method = RequestMethod.POST)
	public AjaxResult removeInterest(HttpServletRequest request, long uid,
			Model model) throws NeedLoginException {
		UserContext context;
		try {
			context = checkLoginForWeb(request);
		} catch (NeedLoginException e) {
			return AjaxResult.ERROR_RESULT;
		}
		AjaxResult result = new AjaxResult();
		result.setSuccess(true);
		interestUserService.removeInterestUser(context.getUid(), uid);
		return result;
	}

	@RequestMapping(value = "/ideaUsers", method = RequestMethod.GET)
	@ResponseBody
	public AjaxResult ideaUsers(HttpServletRequest request, long ideaId,
			int page) {
		UserContext context;
		try {
			context = checkLoginForWeb(request);
		} catch (NeedLoginException e) {
			return AjaxResult.ERROR_RESULT;
		}
		PagerManager pager = new PagerManager(page, mobileIdeaUserMaxRows,
				ideaService.countIdeaUsers(ideaId, null, null));
		List<IdeaUserView> ideaUserViewList = ideaService.listIdeaUsers(ideaId,
				null, null, pager.getFirstResult(), pager.getMaxResult());
		List<IdeaUserMView> list = new ArrayList<IdeaUserMView>(
				ideaUserViewList.size());
		for (IdeaUserView ideaUserView : ideaUserViewList) {
			IdeaUserMView ideaUserMView = new IdeaUserMView();
			ideaUserMView.setIdeaId(ideaId);
			ideaUserMView.setUserView(createUserMView(context,
					ideaUserView.getProfileCache(), false));
			ideaUserMView.setCreateTime(ideaUserView.getCreateTime().getTime());
			list.add(ideaUserMView);
		}

		Map<String, Object> result = new HashMap<String, Object>();
		result.put("ideaUserViewList", list);
		result.put("pager", pager);

		AjaxResult ajaxResult = new AjaxResult();
		ajaxResult.setResult(result);
		return ajaxResult;
	}

	@RequestMapping(value = "/sendDate", method = RequestMethod.POST)
	@ResponseBody
	public AjaxResult sendDate(HttpServletRequest request, long targetUid,
			long ideaId) {
		UserContext context;
		try {
			context = checkLoginForWeb(request);
		} catch (NeedLoginException e) {
			return AjaxResult.ERROR_RESULT;
		}
		AjaxResult result = new AjaxResult();
		Idea idea = ideaService.getIdeaById(ideaId);
		if (idea == null || targetUid <= 0) {
			result.setError(JuzhaiException.ILLEGAL_OPERATION, messageSource);
			return result;
		}
		if (StringUtils.isNotEmpty(idea.getContent())) {
			try {
				dialogService.sendDatingSMS(context, targetUid,
						DialogContentTemplate.PRIVATE_DATE, idea.getContent());
			} catch (DialogException e) {
				result.setError(e.getErrorCode(), messageSource);
			}
		}
		return result;
	}

	@RequestMapping(value = "/dialogContentList", method = RequestMethod.GET)
	@ResponseBody
	public AjaxResult dialogContentList(HttpServletRequest request, int page,
			long uid) {
		UserContext context;
		try {
			context = checkLoginForWeb(request);
		} catch (NeedLoginException e) {
			return AjaxResult.ERROR_RESULT;
		}
		PagerManager pager = new PagerManager(page,
				mobileDialogContentsMaxRows, dialogService.countDialogContent(
						context.getUid(), uid));
		List<DialogContentView> dialogContentViewList = dialogService
				.listDialogContent(context.getUid(), uid,
						pager.getFirstResult(), pager.getMaxResult());
		List<DialogContentMView> list = new ArrayList<DialogContentMView>(
				dialogContentViewList.size());
		for (DialogContentView dialogContentView : dialogContentViewList) {
			list.add(DialogContentMView
					.converFromDialogContentView(dialogContentView));
		}
		noticeService.emptyNotice(context.getUid(), NoticeType.DIALOG);
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("dialogContentViewList", list);
		result.put("pager", pager);

		AjaxResult ajaxResult = new AjaxResult();
		ajaxResult.setResult(result);
		return ajaxResult;
	}

	@RequestMapping(value = "/refreshDialogContent", method = RequestMethod.GET)
	@ResponseBody
	public AjaxResult refreshDialogContent(HttpServletRequest request, long uid) {
		UserContext context;
		try {
			context = checkLoginForWeb(request);
		} catch (NeedLoginException e) {
			return AjaxResult.ERROR_RESULT;
		}
		List<DialogContentView> dialogContentViewList = dialogService
				.listDialogContent(context.getUid(), uid, 0,
						mobileRefreshDialogContentsCount);
		List<DialogContentMView> list = new ArrayList<DialogContentMView>(
				dialogContentViewList.size());
		for (DialogContentView dialogContentView : dialogContentViewList) {
			list.add(DialogContentMView
					.converFromDialogContentView(dialogContentView));
		}
		AjaxResult ajaxResult = new AjaxResult();
		ajaxResult.setResult(list);
		return ajaxResult;
	}

	@RequestMapping(value = "/sendSms", method = RequestMethod.POST)
	@ResponseBody
	public AjaxResult sendSms(HttpServletRequest request, String content,
			long uid) {
		UserContext context;
		try {
			context = checkLoginForWeb(request);
		} catch (NeedLoginException e) {
			return AjaxResult.ERROR_RESULT;
		}
		AjaxResult result = new AjaxResult();
		try {
			long dialogContentId = dialogService.sendSMS(context, uid, content);
			DialogContent dialogContent = dialogService
					.getDialogContent(dialogContentId);
			result.setResult(DialogContentMView
					.converFromDialogContent(dialogContent));
		} catch (DialogException e) {
			result.setError(e.getErrorCode(), messageSource);
		}
		return result;
	}

	@RequestMapping(value = "/notice/nums", method = RequestMethod.GET)
	@ResponseBody
	public AjaxResult noticeNums(HttpServletRequest request, Model model)
			throws NeedLoginException {
		UserContext context;
		try {
			context = checkLoginForWeb(request);
		} catch (NeedLoginException e) {
			return AjaxResult.ERROR_RESULT;
		}
		AjaxResult result = new AjaxResult();
		result.setResult(noticeService.getNoticeNum(context.getUid(),
				NoticeType.DIALOG));
		return result;
	}
}
