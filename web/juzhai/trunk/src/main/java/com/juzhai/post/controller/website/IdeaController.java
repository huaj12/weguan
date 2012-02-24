package com.juzhai.post.controller.website;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.juzhai.core.controller.BaseController;
import com.juzhai.core.exception.NeedLoginException;
import com.juzhai.core.image.JzImageSizeType;
import com.juzhai.core.pager.PagerManager;
import com.juzhai.core.util.DateFormat;
import com.juzhai.core.web.AjaxResult;
import com.juzhai.core.web.jstl.JzResourceFunction;
import com.juzhai.core.web.session.UserContext;
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

	@RequestMapping(value = "/{ideaId}", method = RequestMethod.GET)
	public String detail(HttpServletRequest request, Model model,
			@PathVariable long ideaId) {
		return pageIdeaUser(request, model, ideaId, 1);
	}

	@RequestMapping(value = "/{ideaId}/user/{page}", method = RequestMethod.GET)
	public String pageIdeaUser(HttpServletRequest request, Model model,
			@PathVariable long ideaId, @PathVariable int page) {
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
		Long cityId = 0L;
		if (context.hasLogin()) {
			ProfileCache profile = getLoginUserCache(request);
			if (null != profile) {
				cityId = profile.getCity();
			}
		}
		ideaAdWidget(cityId, model, ideaDetailAdCount);

		PagerManager pager = new PagerManager(page, ideaUserMaxRows,
				ideaService.countIdeaUsers(ideaId));
		List<IdeaUserView> ideaUserViewList = ideaService.listIdeaUsers(ideaId,
				pager.getFirstResult(), pager.getMaxResult());
		for (IdeaUserView view : ideaUserViewList) {
			if (context.hasLogin()) {
				view.setHasInterest(interestUserService.isInterest(
						context.getUid(), view.getProfileCache().getUid()));
			}
		}
		model.addAttribute("pager", pager);
		model.addAttribute("ideaUserViewList", ideaUserViewList);
		model.addAttribute("pageType", "cqw");
		return "web/idea/detail";
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
				if (null != idea.getDate()) {
					map.put("dateTime", DateFormat.SDF.format(idea.getDate()));
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
