package com.juzhai.lab.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.juzhai.core.controller.BaseController;
import com.juzhai.core.exception.JuzhaiException;
import com.juzhai.core.exception.NeedLoginException;
import com.juzhai.core.exception.UploadImageException;
import com.juzhai.core.image.JzImageSizeType;
import com.juzhai.core.image.LogoSizeType;
import com.juzhai.core.image.manager.IImageManager;
import com.juzhai.core.pager.PagerManager;
import com.juzhai.core.util.DateFormat;
import com.juzhai.core.web.AjaxResult;
import com.juzhai.core.web.jstl.JzResourceFunction;
import com.juzhai.core.web.session.UserContext;
import com.juzhai.index.bean.ShowIdeaOrder;
import com.juzhai.lab.controller.view.IdeaMView;
import com.juzhai.lab.controller.view.PostMView;
import com.juzhai.lab.controller.view.UserMView;
import com.juzhai.passport.bean.ProfileCache;
import com.juzhai.passport.controller.form.LoginForm;
import com.juzhai.passport.controller.form.RegisterForm;
import com.juzhai.passport.dao.IUserPositionDao;
import com.juzhai.passport.exception.PassportAccountException;
import com.juzhai.passport.exception.ReportAccountException;
import com.juzhai.passport.mapper.UserPositionMapper;
import com.juzhai.passport.model.City;
import com.juzhai.passport.model.Constellation;
import com.juzhai.passport.model.Profession;
import com.juzhai.passport.model.Profile;
import com.juzhai.passport.model.Town;
import com.juzhai.passport.model.UserPositionExample;
import com.juzhai.passport.service.IInterestUserService;
import com.juzhai.passport.service.ILoginService;
import com.juzhai.passport.service.IProfileService;
import com.juzhai.passport.service.IRegisterService;
import com.juzhai.passport.service.IUserGuideService;
import com.juzhai.post.InitData;
import com.juzhai.post.bean.PurposeType;
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
	@Value("${web.show.ideas.max.rows}")
	private int webShowIdeasMaxRows;
	@Value("${web.show.users.max.rows}")
	private int webShowUsersMaxRows;
	@Value("${web.my.post.max.rows}")
	private int webMyPostMaxRows;

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
		} catch (PassportAccountException e) {
			result.setError(e.getErrorCode(), messageSource);
			return result;
		}
		if (uid <= 0) {
			result.setError(JuzhaiException.SYSTEM_ERROR, messageSource);
		}
		// if (!userGuideService.isCompleteGuide(uid)) {
		// result.setError(JuzhaiException.SYSTEM_ERROR, messageSource);
		// }
		ProfileCache profileCache = profileService.getProfileCacheByUid(uid);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("nickname", profileCache.getNickname());
		map.put("gender", profileCache.getGender());
		result.setResult(map);
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
			UserMView userView = createUserMView(profile);
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
		if (null != context && context.hasLogin()
				&& context.getUid() != post.getCreateUid()) {
			postView.setHasResp(postService.isResponsePost(context.getUid(),
					post.getId()));
		}
		return postView;
	}

	private UserMView createUserMView(Profile profile) {
		UserMView userView = new UserMView();
		userView.setUid(profile.getUid());
		userView.setNickname(profile.getNickname());
		if (null != profile.getGender()) {
			userView.setGender(profile.getGender());
		}
		userView.setBirthYear(profile.getBirthYear());
		userView.setBirthMonth(profile.getBirthMonth());
		userView.setBirthDay(profile.getBirthDay());
		userView.setFeature(profile.getFeature());
		City city = com.juzhai.common.InitData.CITY_MAP.get(profile.getCity());
		if (null != city) {
			userView.setCityName(city.getName());
		}
		Town town = com.juzhai.common.InitData.TOWN_MAP.get(profile.getTown());
		if (null != town) {
			userView.setTownName(town.getName());
		}
		userView.setLogo(JzResourceFunction.userLogo(profile.getUid(),
				profile.getLogoPic(), LogoSizeType.MIDDLE.getType()));
		Constellation con = com.juzhai.passport.InitData.CONSTELLATION_MAP
				.get(profile.getConstellationId());
		if (null != con) {
			userView.setConstellation(con.getName());
		}
		userView.setProfessionId(profile.getProfessionId());
		userView.setProfession(profile.getProfession());
		userView.setInterestUserCount(interestUserService
				.countInterestUser(userView.getUid()));
		userView.setInterestMeCount(interestUserService
				.countInterestMeUser(userView.getUid()));
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

	@RequestMapping(value = "/home", method = RequestMethod.GET)
	@ResponseBody
	public AjaxResult home(HttpServletRequest request, int page) {
		UserContext context;
		try {
			context = checkLoginForWeb(request);
		} catch (NeedLoginException e) {
			return AjaxResult.ERROR_RESULT;
		}
		Map<String, Object> mapResult = new HashMap<String, Object>(3);
		AjaxResult result = new AjaxResult();
		result.setResult(mapResult);
		Profile profile = profileService.getProfile(context.getUid());
		UserMView userView = createUserMView(profile);
		mapResult.put("userView", userView);

		PagerManager pager = new PagerManager(page, webMyPostMaxRows,
				postService.countUserPost(profile.getUid()));
		List<Post> postList = postService.listUserPost(profile.getUid(), null,
				pager.getFirstResult(), pager.getMaxResult());
		List<PostMView> postViewList = new ArrayList<PostMView>(postList.size());
		for (Post post : postList) {
			postViewList.add(createPostMView(context, post));
		}
		mapResult.put("postViewList", postViewList);
		mapResult.put("pager", pager);

		return result;
	}
}
