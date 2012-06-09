package com.juzhai.post.controller.website;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.codehaus.jackson.JsonGenerationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.juzhai.common.InitData;
import com.juzhai.core.controller.BaseController;
import com.juzhai.core.exception.NeedLoginException;
import com.juzhai.core.exception.UploadImageException;
import com.juzhai.core.image.JzImageSizeType;
import com.juzhai.core.pager.PagerManager;
import com.juzhai.core.util.DateFormat;
import com.juzhai.core.util.JackSonSerializer;
import com.juzhai.core.web.AjaxResult;
import com.juzhai.core.web.jstl.JzDataFunction;
import com.juzhai.core.web.jstl.JzResourceFunction;
import com.juzhai.core.web.session.UserContext;
import com.juzhai.index.controller.view.IdeaView;
import com.juzhai.passport.bean.LogoVerifyState;
import com.juzhai.passport.bean.ProfileCache;
import com.juzhai.passport.model.City;
import com.juzhai.passport.service.IInterestUserService;
import com.juzhai.passport.service.IProfileService;
import com.juzhai.post.controller.form.RawIdeaForm;
import com.juzhai.post.controller.view.IdeaUserView;
import com.juzhai.post.exception.InputIdeaException;
import com.juzhai.post.exception.InputRawIdeaException;
import com.juzhai.post.model.Idea;
import com.juzhai.post.model.IdeaDetail;
import com.juzhai.post.service.IIdeaImageService;
import com.juzhai.post.service.IIdeaService;
import com.juzhai.post.service.IRawIdeaService;
import com.juzhai.post.service.impl.IdeaDetailService;
import com.juzhai.spider.share.exception.SpiderIdeaException;
import com.juzhai.spider.share.service.ISpiderIdeaService;
import com.juzhai.stats.counter.service.ICounter;

@Controller
@RequestMapping(value = "idea")
public class IdeaController extends BaseController {

	@Autowired
	private IIdeaService ideaService;
	@Autowired
	private IInterestUserService interestUserService;
	@Autowired
	private IProfileService profileService;
	@Autowired
	private IIdeaImageService ideaImageService;
	@Autowired
	private IdeaDetailService ideaDetailService;
	@Autowired
	private MessageSource messageSource;
	@Autowired
	private ISpiderIdeaService spiderIdeaService;
	@Autowired
	private IRawIdeaService rawIdeaService;
	@Autowired
	private ICounter openIdeaDialogCounter;
	@Value("${idea.user.max.rows}")
	private int ideaUserMaxRows;
	@Value("${idea.detail.ad.count}")
	private int ideaDetailAdCount;
	@Value("${idea.widget.idea.user.count}")
	private int ideaWidgetIdeaUserCount;
	@Value("${idea.detail.recent.ideas.count}")
	private int ideaDetailRecentIdeasCount;
	@Value("${idea.interest.max.rows}")
	private int ideaInterestMaxRows;

	@RequestMapping(value = "/{ideaId}", method = RequestMethod.GET)
	public String detail(HttpServletRequest request, Model model,
			@PathVariable long ideaId) {
		// ProfileCache loginUser = getLoginUserCache(request);
		// String genderType = "all";
		// long cityId = 0L;
		// if (null != loginUser) {
		// cityId = loginUser.getCity();
		// List<String> genders = userPreferenceService.getUserAnswer(
		// loginUser.getUid(),
		// SiftTypePreference.GENDER.getPreferenceId());
		// if (genders != null && genders.size() == 1) {
		// String gender = genders.get(0);
		// if (StringUtils.equals(gender, "1")) {
		// genderType = "male";
		// } else if (StringUtils.equals(gender, "0")) {
		// genderType = "female";
		// }
		// }
		// }
		return pageIdeaUser(request, model, ideaId, 1, 0L, "all");
	}

	@RequestMapping(value = "/{ideaId}/user/{page}", method = RequestMethod.GET)
	public String oldPageIdeaUser(HttpServletRequest request, Model model,
			@PathVariable long ideaId, @PathVariable int page) {
		return "redirect:/idea/" + ideaId;
	}

	@RequestMapping(value = "/{ideaId}/user/{cityId}_{genderType}/{page}", method = RequestMethod.GET)
	public String pageIdeaUser(HttpServletRequest request, Model model,
			@PathVariable long ideaId, @PathVariable int page,
			@PathVariable long cityId, @PathVariable String genderType) {
		UserContext context = (UserContext) request.getAttribute("context");
		if (!ideaDetail(model, request, ideaId)) {
			return error_404;
		}
		Integer gender = null;
		if (StringUtils.equals(genderType, "male")) {
			gender = 1;
		} else if (StringUtils.equals(genderType, "female")) {
			gender = 0;
		}
		PagerManager pager = new PagerManager(page, ideaUserMaxRows,
				ideaService.countIdeaUsers(ideaId, cityId, gender));
		List<IdeaUserView> ideaUserViewList = ideaService.listIdeaUsers(ideaId,
				cityId, gender, pager.getFirstResult(), pager.getMaxResult());
		for (IdeaUserView view : ideaUserViewList) {
			if (context.hasLogin()) {
				view.setHasInterest(interestUserService.isInterest(
						context.getUid(), view.getProfileCache().getUid()));
			}
		}
		model.addAttribute("pager", pager);
		model.addAttribute("ideaUserViewList", ideaUserViewList);
		model.addAttribute("pageType", "cqw");
		loadRecentIdeas(context.getUid(), ideaDetailRecentIdeasCount,
				Collections.singletonList(ideaId), model);
		ideaAdWidget(cityId, model, ideaDetailAdCount);
		model.addAttribute("tabType", "ideaUser");
		model.addAttribute("cityId", cityId);
		return "web/idea/detail";
	}

	@RequestMapping(value = "/{ideaId}/interest", method = RequestMethod.GET)
	public String interest(HttpServletRequest request, Model model,
			@PathVariable long ideaId) {
		return pageIdeaInterest(request, model, ideaId, 1);
	}

	@RequestMapping(value = "/{ideaId}/interest/{page}", method = RequestMethod.GET)
	public String pageIdeaInterest(HttpServletRequest request, Model model,
			@PathVariable long ideaId, @PathVariable int page) {
		UserContext context = (UserContext) request.getAttribute("context");
		if (!ideaDetail(model, request, ideaId)) {
			return error_404;
		}
		Idea idea = (Idea) model.asMap().get("idea");
		long city = 0l;
		if (context.hasLogin()) {
			ProfileCache loginUser = getLoginUserCache(request);
			if (loginUser != null && loginUser.getCity() != null) {
				city = loginUser.getCity();
			}
		}
		PagerManager pager = new PagerManager(page, ideaInterestMaxRows,
				idea.getInterestCnt());
		List<IdeaUserView> ideaUserViewList = ideaService.listIdeaInterest(
				ideaId, pager.getFirstResult(), pager.getMaxResult());
		for (IdeaUserView view : ideaUserViewList) {
			if (context.hasLogin()) {
				view.setHasInterest(interestUserService.isInterest(
						context.getUid(), view.getProfileCache().getUid()));
			}
		}
		model.addAttribute("pager", pager);
		model.addAttribute("ideaUserViewList", ideaUserViewList);
		model.addAttribute("pageType", "cqw");
		loadRecentIdeas(context.getUid(), ideaDetailRecentIdeasCount,
				Collections.singletonList(idea.getId()), model);
		ideaAdWidget(city, model, ideaDetailAdCount);
		model.addAttribute("tabType", "ideaInterest");
		return "web/idea/detail";
	}

	// TODO (done) 方法里做的可不仅仅是detail相关的工作，为什么叫initIdeaDetail呢？这个方法封装的思路不对。
	private boolean ideaDetail(Model model, HttpServletRequest request,
			long ideaId) {
		Idea idea = ideaService.getIdeaById(ideaId);
		if (null == idea) {
			return false;
		}
		UserContext context = (UserContext) request.getAttribute("context");
		if (idea.getCreateUid() > 0) {
			model.addAttribute("ideaCreateUser",
					profileService.getProfileCacheByUid(idea.getCreateUid()));
		}
		model.addAttribute("idea", idea);
		IdeaDetail ideaDetail = ideaDetailService.getIdeaDetail(idea.getId());
		if (null != ideaDetail) {
			model.addAttribute("ideaDetail", ideaDetail);
		}
		if (context.hasLogin()) {
			boolean hasUsed = ideaService.isUseIdea(context.getUid(),
					idea.getId());
			model.addAttribute("hasUsed", hasUsed);
			model.addAttribute("hasInterest",
					ideaService.isInterestIdea(context.getUid(), idea.getId()));
		}
		return true;
	}

	@RequestMapping(value = "/presendidea", method = RequestMethod.GET)
	public String prepareSendIdea(HttpServletRequest request, Model model,
			long ideaId) throws NeedLoginException {
		checkLoginForWeb(request);
		ProfileCache loginUser = getLoginUserCache(request);
		if (StringUtils.isEmpty(loginUser.getLogoPic())
				&& loginUser.getLogoVerifyState() != LogoVerifyState.VERIFYING
						.getType()) {
			return "/web/profile/face_dialog_" + loginUser.getLogoVerifyState();
		}
		Idea idea = ideaService.getIdeaById(ideaId);
		if (null == idea) {
			return error_404;
		}
		openIdeaDialogCounter.incr(null, 1);
		model.addAttribute("idea", idea);
		return "web/idea/send_idea";
	}

	@RequestMapping(value = "/showwidget", method = RequestMethod.GET)
	public String showIdeaWidget(HttpServletRequest request, Model model,
			@RequestParam(defaultValue = "1") int page) {
		UserContext context = (UserContext) request.getAttribute("context");
		Long cityId = 0L;
		if (context.hasLogin()) {
			ProfileCache profile = getLoginUserCache(request);
			if (null != profile) {
				cityId = profile.getCity();
			}
		}
		List<Idea> ideaList = ideaService.listUnUsedIdea(context.getUid(),
				cityId, page - 1, 1);
		if (CollectionUtils.isNotEmpty(ideaList)) {
			Idea idea = ideaList.get(0);
			IdeaView ideaView = new IdeaView();
			ideaView.setIdea(idea);
			if (idea.getCreateUid() > 0) {
				ideaView.setProfileCache(profileService
						.getProfileCacheByUid(idea.getCreateUid()));
			}
			ideaView.setIdeaUserViews(ideaService.listIdeaAllUsers(
					idea.getId(), 0, ideaWidgetIdeaUserCount));
			model.addAttribute("ideaView", ideaView);
		}
		return "web/home/index/idea_widget_fragment";
	}

	@RequestMapping(value = "/random")
	@ResponseBody
	public AjaxResult randomIdea(HttpServletRequest request, Model model)
			throws NeedLoginException {
		checkLoginForWeb(request);
		ProfileCache loginUser = getLoginUserCache(request);
		AjaxResult result = new AjaxResult();
		if (loginUser != null) {
			Idea idea = ideaService
					.getRandomIdea(loginUser.getCity() == null ? 0 : loginUser
							.getCity());
			if (null != idea) {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("id", idea.getId());
				map.put("content", idea.getContent());
				if (null == idea.getStartTime() && null != idea.getEndTime()) {
					map.put("dateTime",
							DateFormat.SDF.format(idea.getEndTime()));
				}
				if (StringUtils.isNotEmpty(idea.getPlace())) {
					map.put("place", idea.getPlace());
				}
				map.put("categoryId", idea.getCategoryId());
				map.put("pic", idea.getPic());
				if (StringUtils.isNotEmpty(idea.getPic())) {
					map.put("picUrl",
							JzResourceFunction.ideaPic(idea.getId(),
									idea.getPic(),
									JzImageSizeType.MIDDLE.getType()));
				}
				result.setResult(map);
				return result;
			}
		}
		result.setSuccess(false);
		return result;
	}

	@RequestMapping(value = "/kindEditor/upload")
	public String kindEditorUpload(HttpServletRequest request,
			@RequestParam("imgFile") MultipartFile imgFile, Model model)
			throws JsonGenerationException {
		Map<String, Object> map = null;
		try {
			checkLoginForWeb(request);
			String[] urls = ideaImageService.uploadTempIdeaImg(imgFile);
			map = new HashMap<String, Object>();
			map.put("error", 0);
			map.put("url", urls[0]);
		} catch (UploadImageException e) {
			map = getError(e.getErrorCode());
		} catch (NeedLoginException e) {
			map = getError(e.getErrorCode());
		}
		String jsonString = JackSonSerializer.toString(map);
		model.addAttribute("result", jsonString);
		return "web/common/ajax/ajax_result";
	}

	@RequestMapping(value = "/logo/upload", method = RequestMethod.POST)
	public String addIdeaImage(HttpServletRequest request, Model model,
			@RequestParam("rawIdeaLogo") MultipartFile rawActLogo) {
		AjaxResult result = new AjaxResult();
		try {
			checkLoginForWeb(request);
			try {
				String[] urls = ideaImageService.uploadTempIdeaImg(rawActLogo);
				result.setResult(urls);
			} catch (UploadImageException e) {
				result.setError(e.getErrorCode(), messageSource);
			}
		} catch (NeedLoginException e) {
			result.setError(NeedLoginException.IS_NOT_LOGIN, messageSource);
		}
		model.addAttribute("result", result.toJson());
		return "web/common/ajax/ajax_result";
	}

	private Map<String, Object> getError(String errorCode) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("error", 1);
		map.put("message", messageSource.getMessage(errorCode, null,
				Locale.SIMPLIFIED_CHINESE));
		return map;
	}

	@RequestMapping(value = "/select/category", method = RequestMethod.GET)
	public String createCategory(HttpServletRequest request, Model model)
			throws NeedLoginException {
		checkLoginForWeb(request);
		loadCategoryList(model);
		return "web/idea/select_category";
	}

	@RequestMapping(value = { "/create/{categoryId}" }, method = RequestMethod.GET)
	public String userCreate(HttpServletRequest request, Model model,
			@PathVariable Long categoryId) throws NeedLoginException {
		UserContext context = checkLoginForWeb(request);
		ProfileCache cache = profileService.getProfileCacheByUid(context
				.getUid());
		model.addAttribute("city", cache.getCity());
		model.addAttribute("province", cache.getProvince());
		model.addAttribute("categoryId", categoryId);
		return "web/idea/user_create_idea";
	}

	@RequestMapping(value = { "/update/{ideaId}" }, method = RequestMethod.GET)
	public String userUpdate(HttpServletRequest request, Model model,
			@PathVariable Long ideaId) throws NeedLoginException {
		checkLoginForWeb(request);
		Idea idea = ideaService.getIdeaById(ideaId);
		model.addAttribute("idea", idea);
		model.addAttribute("town", idea.getTown());
		model.addAttribute("city", idea.getCity());
		City city = InitData.CITY_MAP.get(idea.getCity());
		if (city != null) {
			model.addAttribute("province", city.getProvinceId());
		}
		model.addAttribute("categoryId", idea.getCategoryId());
		model.addAttribute("detail", ideaDetailService.getIdeaDetail(ideaId));
		return "web/idea/user_create_idea";
	}

	@RequestMapping(value = "/save", method = RequestMethod.POST)
	@ResponseBody
	public AjaxResult create(HttpServletRequest request, RawIdeaForm rawIdeaForm)
			throws NeedLoginException {
		UserContext context = checkLoginForWeb(request);
		AjaxResult ajaxResult = new AjaxResult();
		try {
			if (rawIdeaForm.getIdeaId() != null) {
				rawIdeaForm.setCorrectionUid(context.getUid());
			} else {
				rawIdeaForm.setCreateUid(context.getUid());
			}

			rawIdeaService.createRawIdea(rawIdeaForm);
		} catch (InputRawIdeaException e) {
			ajaxResult.setError(e.getErrorCode(), messageSource);
		}
		return ajaxResult;
	}

	@RequestMapping(value = "/share", method = RequestMethod.POST)
	@ResponseBody
	public AjaxResult share(HttpServletRequest request, String url)
			throws NeedLoginException {
		UserContext context = checkLoginForWeb(request);
		Long cityId = 0L;
		if (context.hasLogin()) {
			ProfileCache profile = getLoginUserCache(request);
			if (null != profile) {
				cityId = profile.getCity();
			}
		}
		AjaxResult ajaxResult = new AjaxResult();
		try {
			int shareCount = spiderIdeaService.isCrawl(context.getUid());
			String result = spiderIdeaService.crawl(url);
			spiderIdeaService.setCrawlCount(context.getUid(), shareCount);
			// 无城市时获取分享人所在城市
			RawIdeaForm rawIdeaForm = JackSonSerializer.toBean(result,
					RawIdeaForm.class);
			if (rawIdeaForm.getCity() == null) {
				rawIdeaForm.setCity(cityId);
				rawIdeaForm.setCityName(JzDataFunction.cityName(cityId));
			}

			ajaxResult.setResult(rawIdeaForm);
		} catch (SpiderIdeaException e) {
			ajaxResult.setError(e.getErrorCode(), messageSource);
		} catch (JsonGenerationException e) {
			ajaxResult.setSuccess(false);
		}
		return ajaxResult;
	}

	@RequestMapping(value = "/interest", method = RequestMethod.POST)
	@ResponseBody
	public AjaxResult responsePost(HttpServletRequest request, Model model,
			long ideaId) throws NeedLoginException {
		UserContext context = checkLoginForWeb(request);
		AjaxResult result = new AjaxResult();
		try {
			ideaService.interestIdea(context.getUid(), ideaId);
		} catch (InputIdeaException e) {
			result.setError(e.getErrorCode(), messageSource);
		}
		return result;
	}

}
