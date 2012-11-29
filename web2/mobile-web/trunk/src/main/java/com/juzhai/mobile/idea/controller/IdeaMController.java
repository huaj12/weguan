package com.juzhai.mobile.idea.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.juzhai.core.controller.BaseController;
import com.juzhai.core.exception.NeedLoginException;
import com.juzhai.core.pager.PagerManager;
import com.juzhai.core.web.AjaxResult;
import com.juzhai.core.web.session.UserContext;
import com.juzhai.idea.bean.ShowIdeaOrder;
import com.juzhai.idea.controller.view.IdeaUserView;
import com.juzhai.idea.service.IIdeaRemoteService;
import com.juzhai.mobile.common.web.response.ListJsonResult;
import com.juzhai.mobile.idea.controller.view.IdeaMView;
import com.juzhai.mobile.idea.controller.view.IdeaUserMView;
import com.juzhai.mobile.idea.controller.viewHelper.IIdeaMViewHelper;
import com.juzhai.mobile.passport.controller.viewHelper.IUserMViewHelper;
import com.juzhai.passport.bean.ProfileCache;
import com.juzhai.passport.service.IProfileRemoteService;
import com.juzhai.post.model.Idea;

@Controller
@RequestMapping("idea")
public class IdeaMController extends BaseController {

	@Autowired
	private IIdeaRemoteService ideaService;
	@Autowired
	private IUserMViewHelper userMViewHelper;
	@Autowired
	private IProfileRemoteService profileService;
	@Autowired
	private IIdeaMViewHelper ideaMViewHelper;
	@Value("${mobile.show.ideas.max.rows}")
	private int mobileShowIdeasMaxRows = 1;
	@Value("${mobile.idea.user.max.rows}")
	private int mobileIdeaUserMaxRows = 1;

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	@ResponseBody
	public ListJsonResult ideaList(HttpServletRequest request, long categoryId,
			String orderType, int page) throws NeedLoginException {
		UserContext context = checkLoginForWeb(request);
		ProfileCache loginUser = profileService.getProfileCacheByUid(context
				.getUid());
		long cityId = 0L;
		if (loginUser != null && loginUser.getCity() != null) {
			cityId = loginUser.getCity();
		}
		PagerManager pager = null;
		List<Idea> ideaList = null;
		ShowIdeaOrder order = ShowIdeaOrder.getShowIdeaOrderByType(orderType);
		if (null != order) {
			pager = new PagerManager(page, mobileShowIdeasMaxRows,
					ideaService.countIdeaByCityAndCategory(cityId, categoryId));
			ideaList = ideaService.listIdeaByCityAndCategory(cityId,
					categoryId, order, pager.getFirstResult(),
					pager.getMaxResult());
		} else {
			pager = new PagerManager(page, mobileShowIdeasMaxRows,
					ideaService.countIdeaWindow(cityId, categoryId));
			ideaList = ideaService.listIdeaWindow(cityId, categoryId,
					pager.getFirstResult(), pager.getMaxResult());
		}

		List<IdeaMView> ideaViewList = new ArrayList<IdeaMView>(ideaList.size());
		for (Idea idea : ideaList) {
			ideaViewList.add(ideaMViewHelper.createPostMView(context, idea));
		}

		ListJsonResult result = new ListJsonResult();
		result.setResult(pager, ideaViewList);
		return result;
	}

	@RequestMapping(value = "/users", method = RequestMethod.GET)
	@ResponseBody
	public ListJsonResult ideaUsers(HttpServletRequest request, long ideaId,
			Long cityId, Integer gender, int page) throws NeedLoginException {
		UserContext context = checkLoginForWeb(request);
		PagerManager pager = new PagerManager(page, mobileIdeaUserMaxRows,
				ideaService.countIdeaUsers(ideaId, cityId, gender));
		List<IdeaUserView> ideaUserViewList = ideaService.listIdeaUsers(ideaId,
				cityId, gender, pager.getFirstResult(), pager.getMaxResult());
		List<IdeaUserMView> list = new ArrayList<IdeaUserMView>(
				ideaUserViewList.size());
		for (IdeaUserView ideaUserView : ideaUserViewList) {
			IdeaUserMView ideaUserMView = new IdeaUserMView();
			ideaUserMView.setIdeaId(ideaId);
			ideaUserMView.setUserView(userMViewHelper.createUserMView(context,
					ideaUserView.getProfileCache(), false));
			ideaUserMView.setCreateTime(ideaUserView.getCreateTime().getTime());
			list.add(ideaUserMView);
		}

		ListJsonResult result = new ListJsonResult();
		result.setResult(pager, list);
		return result;
	}

	@RequestMapping(value = "/shareIdea", method = RequestMethod.POST)
	@ResponseBody
	public AjaxResult shareIdea(HttpServletRequest request, String content,
			long ideaId) throws NeedLoginException {
		UserContext context = checkLoginForWeb(request);
		ideaService.shareIdea(context.getUid(), context.getTpId(), content,
				ideaId);
		return new AjaxResult(true);

	}
}
