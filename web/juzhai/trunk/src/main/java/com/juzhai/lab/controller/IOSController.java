package com.juzhai.lab.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
import com.juzhai.core.image.manager.IImageManager;
import com.juzhai.core.pager.PagerManager;
import com.juzhai.core.web.AjaxResult;
import com.juzhai.core.web.jstl.JzResourceFunction;
import com.juzhai.core.web.session.UserContext;
import com.juzhai.index.bean.ShowIdeaOrder;
import com.juzhai.index.controller.view.CategoryView;
import com.juzhai.lab.controller.view.IdeaMView;
import com.juzhai.passport.bean.ProfileCache;
import com.juzhai.passport.controller.form.LoginForm;
import com.juzhai.passport.dao.IUserPositionDao;
import com.juzhai.passport.exception.PassportAccountException;
import com.juzhai.passport.exception.ReportAccountException;
import com.juzhai.passport.mapper.UserPositionMapper;
import com.juzhai.passport.model.City;
import com.juzhai.passport.model.Town;
import com.juzhai.passport.model.UserPositionExample;
import com.juzhai.passport.service.ILoginService;
import com.juzhai.passport.service.IProfileService;
import com.juzhai.passport.service.IUserGuideService;
import com.juzhai.post.InitData;
import com.juzhai.post.model.Category;
import com.juzhai.post.model.Idea;
import com.juzhai.post.service.IIdeaService;

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
	@Value("${web.show.ideas.max.rows}")
	private int webShowIdeasMaxRows;

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
		if (!userGuideService.isCompleteGuide(uid)) {
			result.setError(JuzhaiException.SYSTEM_ERROR, messageSource);
		}
		ProfileCache profileCache = profileService.getProfileCacheByUid(uid);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("nickname", profileCache.getNickname());
		map.put("gender", profileCache.getGender());
		result.setResult(map);
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
			ideaViewList.add(ideaMView);
		}
		List<Category> categoryList = new ArrayList<Category>(
				InitData.CATEGORY_MAP.values());
		List<CategoryView> categoryViewList = new ArrayList<CategoryView>(
				categoryList.size());
		for (Category category : categoryList) {
			int count = category.getId() == categoryId ? pager
					.getTotalResults() : ideaService
					.countIdeaByCityAndCategory(cityId, category.getId());
			categoryViewList.add(new CategoryView(category, count));
		}
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("ideaViewList", ideaViewList);
		result.put("pager", pager);

		AjaxResult ajaxResult = new AjaxResult();
		ajaxResult.setResult(result);
		return ajaxResult;
	}
}
