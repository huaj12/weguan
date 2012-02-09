package com.juzhai.index.controller.website;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.juzhai.core.controller.BaseController;
import com.juzhai.core.exception.NeedLoginException;
import com.juzhai.core.pager.PagerManager;
import com.juzhai.core.web.session.UserContext;
import com.juzhai.index.bean.ShowActOrder;
import com.juzhai.index.bean.ShowIdeaOrder;
import com.juzhai.index.controller.view.IdeaView;
import com.juzhai.index.controller.view.QueryUserView;
import com.juzhai.passport.InitData;
import com.juzhai.passport.bean.TpFriend;
import com.juzhai.passport.model.Profile;
import com.juzhai.passport.service.IFriendService;
import com.juzhai.passport.service.IProfileService;
import com.juzhai.passport.service.login.ILoginService;
import com.juzhai.post.model.Idea;
import com.juzhai.post.model.Post;
import com.juzhai.post.service.IAdService;
import com.juzhai.post.service.IIdeaService;
import com.juzhai.post.service.IPostService;

@Controller
public class NewIndexController extends BaseController {

	private final Log log = LogFactory.getLog(getClass());
	@Autowired
	private ILoginService loginService;
	@Autowired
	private IPostService postService;
	@Autowired
	private IIdeaService ideaService;
	@Autowired
	private IProfileService profileService;
	@Autowired
	private IFriendService friendService;
	@Value("${web.show.ideas.max.rows}")
	private int webShowIdeasMaxRows;
	@Value("${show.invite.users.max.rows}")
	private int showInviteUsersMaxRows;
	@Autowired
	private IAdService adService;

	@RequestMapping(value = { "", "/", "/index" }, method = RequestMethod.GET)
	public String index(HttpServletRequest request, Model model) {
		return showIdeas(request, model);
	}

	@RequestMapping(value = "/showIdeas", method = RequestMethod.GET)
	public String showIdeas(HttpServletRequest request, Model model) {
		return pageShowIdeas(request, model, ShowActOrder.HOT_TIME.getType(),
				0L, 1);
	}

	@RequestMapping(value = "/showIdeas/{orderType}_{cityId}/{page}", method = RequestMethod.GET)
	public String pageShowIdeas(HttpServletRequest request, Model model,
			@PathVariable String orderType, @PathVariable long cityId,
			@PathVariable int page) {
		UserContext context = (UserContext) request.getAttribute("context");
		ShowIdeaOrder order = ShowIdeaOrder.getShowIdeaOrderByType(orderType);
		PagerManager pager = new PagerManager(page, webShowIdeasMaxRows,
				ideaService.countIdeaByCity(cityId));
		List<Idea> ideaList = ideaService.listIdeaByCity(cityId, order,
				pager.getFirstResult(), pager.getMaxResult());
		List<IdeaView> ideaViewList = new ArrayList<IdeaView>();
		for (Idea idea : ideaList) {
			IdeaView ideaView = new IdeaView();
			ideaView.setIdea(idea);
			if (context.hasLogin()) {
				ideaView.setHasUsed(ideaService.isUseIdea(context.getUid(),
						idea.getId()));
			}
			ideaView.setIdeaUserViews(ideaService.listIdeaUsers(idea.getId(),
					0, 5));
			ideaViewList.add(ideaView);
		}
		model.addAttribute("ads", adService.listAd(cityId, 0, 2));
		model.addAttribute("pager", pager);
		model.addAttribute("orderType", orderType);
		model.addAttribute("cityId", cityId);
		model.addAttribute("ideaViewList", ideaViewList);
		return "web/index/cqw/show_ideas";
	}

	@RequestMapping(value = "/aboutUs", method = RequestMethod.GET)
	public String aboutUs(HttpServletRequest request, Model model) {
		return "web/index/about_us";
	}

	@RequestMapping(value = "/showInviteUsers", method = RequestMethod.GET)
	public String followUser(HttpServletRequest request, Model model)
			throws NeedLoginException {
		UserContext context = checkLoginForWeb(request);

		List<TpFriend> inviteUserList = friendService
				.getUnInstallFriends(context.getUid());
		if (CollectionUtils.isNotEmpty(inviteUserList)) {
			int toIndex = Math.min(showInviteUsersMaxRows,
					inviteUserList.size());
			model.addAttribute("inviteUserList",
					inviteUserList.subList(0, toIndex));
			PagerManager pager = new PagerManager(1, showInviteUsersMaxRows,
					inviteUserList.size());
			model.addAttribute("totalPage", pager.getTotalPage());
		} else {
			model.addAttribute("inviteUserList", Collections.emptyList());
			model.addAttribute("totalPage", 0);
		}
		return "web/index/zbe/show_invite_users";
	}

	@RequestMapping(value = "/pageInviteUser", method = RequestMethod.GET)
	public String pageInviteUser(HttpServletRequest request, Model model,
			int page) throws NeedLoginException {
		UserContext context = checkLoginForWeb(request);
		// 邀请的人
		List<TpFriend> inviteUserList = friendService
				.getUnInstallFriends(context.getUid());
		if (page <= 0) {
			page = 1;
		}
		if (CollectionUtils.isNotEmpty(inviteUserList)) {
			int fromIndex = (page - 1) * showInviteUsersMaxRows;
			int toIndex = Math.min(fromIndex + showInviteUsersMaxRows,
					inviteUserList.size());
			model.addAttribute("inviteUserList",
					inviteUserList.subList(fromIndex, toIndex));
		} else {
			model.addAttribute("inviteUserList", Collections.emptyList());
		}
		return "web/index/zbe/invite_user_list";
	}

	@RequestMapping(value = "/queryUser", method = RequestMethod.GET)
	public String queryUser(Model model) {
		return pageQueryUser(model, 1, 0, null, null, null);
	}

	@RequestMapping(value = "/queryUser/{cityId}_{sex}_{minStringAge}_{maxStringAge}/{pageId}", method = RequestMethod.GET)
	public String pageQueryUser(Model model, @PathVariable int pageId,
			@PathVariable long cityId, @PathVariable String sex,
			@PathVariable String maxStringAge, @PathVariable String minStringAge) {
		Integer gender = null;
		if ("male".equals(sex)) {
			gender = 1;
		} else if ("female".equals(sex)) {
			gender = 0;
		}
		int minYear = 0;
		int maxYear = 0;
		int maxAge = getIntAge(maxStringAge);
		int minAge = getIntAge(minStringAge);
		if (minAge > maxAge) {
			maxYear = ageToYear(minAge);
			minYear = ageToYear(maxAge);
		} else {
			minYear = ageToYear(minAge);
			maxYear = ageToYear(maxAge);
		}
		PagerManager pager = new PagerManager(pageId,
				profileService.countQueryProfile(gender, cityId, minYear,
						maxYear));
		List<Profile> list = profileService.queryProfile(gender, cityId,
				minYear, maxYear, pager.getFirstResult(), pager.getMaxResult());
		List<QueryUserView> userViews = new ArrayList<QueryUserView>();
		for (Profile profile : list) {
			long uid = profile.getUid();
			Post post = postService.getUserLatestPost(uid);
			QueryUserView userView = new QueryUserView(profile,
					loginService.isOnline(uid), post);
			userViews.add(userView);
		}
		model.addAttribute("userViews", userViews);
		model.addAttribute("citys", InitData.CITY_MAP.values());
		model.addAttribute("pager", pager);
		model.addAttribute("cityId", cityId);
		model.addAttribute("sex", sex);
		model.addAttribute("maxStringAge", maxStringAge);
		model.addAttribute("minStringAge", minStringAge);
		return "web/index/zbe/query_user";
	}

	private int getIntAge(String stringAge) {
		int age = 0;
		try {
			age = Integer.parseInt(stringAge);
		} catch (Exception e) {
		}
		if (age > 80 || age < 16) {
			age = 0;
		}
		return age;
	}

	private int ageToYear(int age) {
		if (age == 0)
			return 0;
		Calendar cal = Calendar.getInstance();
		int year = cal.get(Calendar.YEAR);
		return year - age;
	}

}
