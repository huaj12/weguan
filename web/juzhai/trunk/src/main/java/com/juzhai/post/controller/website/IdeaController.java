package com.juzhai.post.controller.website;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

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
import com.juzhai.passport.bean.ProfileCache;
import com.juzhai.passport.service.IInterestUserService;
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
}
