package com.juzhai.post.controller.website;

import java.util.Collections;
import java.util.HashMap;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.juzhai.core.controller.BaseController;
import com.juzhai.core.exception.NeedLoginException;
import com.juzhai.core.image.JzImageSizeType;
import com.juzhai.core.pager.PagerManager;
import com.juzhai.core.util.DateFormat;
import com.juzhai.core.web.AjaxResult;
import com.juzhai.core.web.jstl.JzResourceFunction;
import com.juzhai.core.web.session.UserContext;
import com.juzhai.index.controller.view.IdeaView;
import com.juzhai.passport.bean.LogoVerifyState;
import com.juzhai.passport.bean.ProfileCache;
import com.juzhai.passport.service.IInterestUserService;
import com.juzhai.passport.service.IProfileService;
import com.juzhai.post.controller.view.IdeaUserView;
import com.juzhai.post.model.Idea;
import com.juzhai.post.service.IIdeaService;

@Controller
@RequestMapping(value = "idea")
public class IdeaController extends BaseController {

	@Autowired
	private IIdeaService ideaService;
	@Autowired
	private IInterestUserService interestUserService;
	@Autowired
	private IProfileService profileService;
	@Value("${idea.user.max.rows}")
	private int ideaUserMaxRows;
	@Value("${idea.detail.ad.count}")
	private int ideaDetailAdCount;
	@Value("${idea.widget.idea.user.count}")
	private int ideaWidgetIdeaUserCount;
	@Value("${idea.detail.recent.ideas.count}")
	private int ideaDetailRecentIdeasCount;

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
		Idea idea = ideaService.getIdeaById(ideaId);
		if (null == idea) {
			return error_404;
		}
		if (idea.getCreateUid() > 0) {
			model.addAttribute("ideaCreateUser",
					profileService.getProfileCacheByUid(idea.getCreateUid()));
		}
		model.addAttribute("idea", idea);
		if (context.hasLogin()) {
			boolean hasUsed = ideaService.isUseIdea(context.getUid(), ideaId);
			model.addAttribute("hasUsed", hasUsed);
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
		model.addAttribute("cityId", cityId);
		model.addAttribute("genderType", genderType);
		loadRecentIdeas(context.getUid(), ideaDetailRecentIdeasCount,
				Collections.singletonList(ideaId), model);
		ideaAdWidget(cityId, model, ideaDetailAdCount);
		return "web/idea/detail";
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
}
