package com.juzhai.mobile.idea.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.juzhai.core.controller.BaseController;
import com.juzhai.core.exception.NeedLoginException;
import com.juzhai.core.image.JzImageSizeType;
import com.juzhai.core.pager.PagerManager;
import com.juzhai.core.util.DateFormat;
import com.juzhai.core.web.jstl.JzResourceFunction;
import com.juzhai.core.web.session.UserContext;
import com.juzhai.index.bean.ShowIdeaOrder;
import com.juzhai.mobile.common.web.response.ListJsonResult;
import com.juzhai.mobile.idea.controller.view.IdeaMView;
import com.juzhai.mobile.idea.controller.view.IdeaUserMView;
import com.juzhai.mobile.passport.controller.viewHelper.IUserMViewHelper;
import com.juzhai.passport.bean.ProfileCache;
import com.juzhai.passport.model.City;
import com.juzhai.passport.model.Town;
import com.juzhai.post.InitData;
import com.juzhai.post.controller.view.IdeaUserView;
import com.juzhai.post.model.Category;
import com.juzhai.post.model.Idea;
import com.juzhai.post.service.IIdeaService;

@Controller
@RequestMapping("mobile/idea")
public class IdeaMController extends BaseController {

	@Autowired
	private IIdeaService ideaService;
	@Autowired
	private IUserMViewHelper userMViewHelper;
	// @Value("${web.show.ideas.max.rows}")
	private int webShowIdeasMaxRows = 1;
	// @Value("mobile.idea.user.max.rows")
	private int mobileIdeaUserMaxRows = 1;

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	@ResponseBody
	public ListJsonResult ideaList(HttpServletRequest request, long categoryId,
			String orderType, int page) throws NeedLoginException {
		UserContext context = checkLoginForWeb(request);
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
			if (idea.getStartTime() != null) {
				ideaMView.setStartTime(DateFormat.SDF.format(idea
						.getStartTime()));
			}
			if (idea.getEndTime() != null) {
				ideaMView.setEndTime(DateFormat.SDF.format(idea.getEndTime()));
			}
			ideaViewList.add(ideaMView);
		}

		ListJsonResult result = new ListJsonResult();
		result.setResult(pager, ideaViewList);
		return result;
	}

	@RequestMapping(value = "/users", method = RequestMethod.GET)
	@ResponseBody
	public ListJsonResult ideaUsers(HttpServletRequest request, long ideaId,
			int page) throws NeedLoginException {
		UserContext context = checkLoginForWeb(request);
		PagerManager pager = new PagerManager(page, mobileIdeaUserMaxRows,
				ideaService.countIdeaUsers(ideaId, null, null));
		List<IdeaUserView> ideaUserViewList = ideaService.listIdeaUsers(ideaId,
				null, null, pager.getFirstResult(), pager.getMaxResult());
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
}
