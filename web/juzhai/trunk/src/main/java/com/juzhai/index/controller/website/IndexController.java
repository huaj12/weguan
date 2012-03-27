package com.juzhai.index.controller.website;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.CollectionUtils;
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
import com.juzhai.home.service.IGuessYouService;
import com.juzhai.index.bean.ShowActOrder;
import com.juzhai.index.bean.ShowIdeaOrder;
import com.juzhai.index.controller.view.IdeaView;
import com.juzhai.index.controller.view.PostWindowView;
import com.juzhai.index.controller.view.QueryUserView;
import com.juzhai.passport.bean.ProfileCache;
import com.juzhai.passport.bean.TpFriend;
import com.juzhai.passport.model.Profile;
import com.juzhai.passport.service.IFriendService;
import com.juzhai.passport.service.IInterestUserService;
import com.juzhai.passport.service.IPassportService;
import com.juzhai.passport.service.IProfileService;
import com.juzhai.passport.service.login.ILoginService;
import com.juzhai.post.controller.view.PostView;
import com.juzhai.post.model.Idea;
import com.juzhai.post.model.Post;
import com.juzhai.post.model.PostWindow;
import com.juzhai.post.service.IIdeaService;
import com.juzhai.post.service.IPostCommentService;
import com.juzhai.post.service.IPostService;
import com.juzhai.post.service.IPostWindowService;

@Controller
public class IndexController extends BaseController {

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
	@Autowired
	private IInterestUserService interestUserService;
	@Autowired
	private IGuessYouService guessYouService;
	@Autowired
	private IPassportService passportService;
	@Autowired
	private IPostCommentService postCommentService;
	@Value("${web.show.ideas.max.rows}")
	private int webShowIdeasMaxRows;
	@Value("${web.show.ideas.user.count}")
	private int webShowIdeasUserCount;
	@Value("${show.invite.users.max.rows}")
	private int showInviteUsersMaxRows;
	@Value("${query.users.right.user.rows}")
	private int queryUsersRightUserRows;
	@Value("${web.show.ideas.ad.count}")
	private int webShowIdeasAdCount;
	@Autowired
	private IPostWindowService postWindowService;

	@RequestMapping(value = { "", "/", "/index" }, method = RequestMethod.GET)
	public String index(HttpServletRequest request, Model model) {
		UserContext context = (UserContext) request.getAttribute("context");
		if (context.hasLogin()) {
			return "redirect:/home";
		} else {
			return welcome(request, model);
		}
	}

	@RequestMapping(value = { "/welcome" }, method = RequestMethod.GET)
	public String welcome(HttpServletRequest request, Model model) {
		List<PostWindow> list = postWindowService.listPostWindow();
		List<PostWindowView> postWindowViews = new ArrayList<PostWindowView>();
		for (PostWindow window : list) {
			PostWindowView view = new PostWindowView();
			view.setPostWindow(window);
			view.setProfileCache(profileService.getProfileCacheByUid(window
					.getUid()));
			postWindowViews.add(view);
		}
		model.addAttribute("postWindowViews", postWindowViews);
		welcomNum(request, model);
		return "web/index/welcome/welcome";
	}

	@RequestMapping(value = { "/welcomenum" }, method = RequestMethod.GET)
	public String welcomNum(HttpServletRequest request, Model model) {
		int totalUserCount = passportService.totalCount();
		model.addAttribute("userCount", totalUserCount);
		int totalPostCount = postService.totalCount();
		model.addAttribute("postCount", totalPostCount);
		int totalInteractCount = postService.responseTotalCount()
				+ postCommentService.totalCount();
		model.addAttribute("interactCount", totalInteractCount);

		model.addAttribute("userNumList", convertToNumList(totalUserCount));
		model.addAttribute("postNumList", convertToNumList(totalPostCount));
		model.addAttribute("interactNumList",
				convertToNumList(totalInteractCount));
		return "web/index/welcome/welcome_num";
	}

	private List<Integer> convertToNumList(int totalCount) {
		List<Integer> numList = new ArrayList<Integer>(7);
		while (true) {
			numList.add(totalCount % 10);
			totalCount = totalCount / 10;
			if (totalCount <= 0) {
				break;
			}
		}
		Collections.reverse(numList);
		return numList;
	}

	@RequestMapping(value = { "/showideas", "showIdeas" }, method = RequestMethod.GET)
	public String showIdeas(HttpServletRequest request, Model model) {
		ProfileCache loginUser = getLoginUserCache(request);
		long city = 0L;
		if (loginUser != null && loginUser.getCity() != null) {
			// && InitData.SPECIAL_CITY_LIST.contains(loginUser.getCity())) {
			city = loginUser.getCity();
		}
		return pageShowIdeas(request, model, ShowActOrder.HOT_TIME.getType(),
				city, 1);
	}

	@RequestMapping(value = { "/showideas/{orderType}_{cityId}/{page}",
			"/showIdeas/{orderType}_{cityId}/{page}" }, method = RequestMethod.GET)
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
			if (idea.getCreateUid() > 0) {
				ideaView.setProfileCache(profileService
						.getProfileCacheByUid(idea.getCreateUid()));
			}
			ideaView.setIdeaUserViews(ideaService.listIdeaUsers(idea.getId(),
					0, webShowIdeasUserCount));
			ideaViewList.add(ideaView);
		}
		ideaAdWidget(cityId, model, webShowIdeasAdCount);
		model.addAttribute("pager", pager);
		model.addAttribute("orderType", orderType);
		model.addAttribute("cityId", cityId);
		model.addAttribute("ideaViewList", ideaViewList);
		model.addAttribute("pageType", "cqw");
		return "web/index/cqw/show_ideas";
	}

	@RequestMapping(value = "/about/{pageType}", method = RequestMethod.GET)
	public String aboutUs(HttpServletRequest request, Model model,
			@PathVariable String pageType) {
		return "web/index/about/" + pageType;
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

	@RequestMapping(value = { "/showusers", "/showUsers" }, method = RequestMethod.GET)
	public String queryUser(HttpServletRequest request, Model model) {
		ProfileCache loginUser = getLoginUserCache(request);
		long city = 0L;
		if (loginUser != null && loginUser.getCity() != null) {
			// && InitData.SPECIAL_CITY_LIST.contains(loginUser.getCity())) {
			city = loginUser.getCity();
		}
		return pageQueryUser(request, model, 1, city, null, null, null);
	}

	@RequestMapping(value = {
			"/queryusers/{cityId}_{sex}_{minStringAge}_{maxStringAge}/{pageId}",
			"/queryUsers/{cityId}_{sex}_{minStringAge}_{maxStringAge}/{pageId}" }, method = RequestMethod.GET)
	public String pageQueryUser(HttpServletRequest request, Model model,
			@PathVariable int pageId, @PathVariable long cityId,
			@PathVariable String sex, @PathVariable String maxStringAge,
			@PathVariable String minStringAge) {
		UserContext context = (UserContext) request.getAttribute("context");
		Integer gender = null;
		if ("male".equals(sex)) {
			gender = 1;
		} else if ("female".equals(sex)) {
			gender = 0;
		}
		int maxAge = getIntAge(maxStringAge);
		int minAge = getIntAge(minStringAge);
		int maxYear = ageToYear(Math.min(minAge, maxAge));
		int minYear = ageToYear(Math.max(minAge, maxAge));
		PagerManager pager = new PagerManager(pageId, 20,
				profileService.countQueryProfile(context.getUid(), gender,
						cityId, minYear, maxYear));
		List<Profile> list = profileService.queryProfile(context.getUid(),
				gender, cityId, minYear, maxYear, pager.getFirstResult(),
				pager.getMaxResult());

		List<Long> uidList = new ArrayList<Long>();
		for (Profile profile : list) {
			uidList.add(profile.getUid());
		}
		Map<Long, Post> userLatestPostMap = postService
				.getMultiUserLatestPosts(uidList);
		List<QueryUserView> userViews = new ArrayList<QueryUserView>(
				list.size());
		for (Profile profile : list) {
			QueryUserView view = new QueryUserView();
			view.setOnline(loginService.isOnline(profile.getUid()));
			view.setProfile(profile);
			view.setPost(userLatestPostMap.get(profile.getUid()));
			if (context.hasLogin()) {
				view.setHasInterest(interestUserService.isInterest(
						context.getUid(), profile.getUid()));
			}
			userViews.add(view);
		}
		newUserWidget(0L, model, queryUsersRightUserRows);
		model.addAttribute("userViews", userViews);
		model.addAttribute("pager", pager);
		model.addAttribute("cityId", cityId);
		model.addAttribute("sex", sex);
		model.addAttribute("maxStringAge", maxStringAge);
		model.addAttribute("minStringAge", minStringAge);
		model.addAttribute("pageType", "zbe");
		return "web/index/zbe/query_user";
	}

	private int getIntAge(String stringAge) {
		int age = 0;
		try {
			age = Integer.parseInt(stringAge);
		} catch (Exception e) {
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

	@RequestMapping(value = "/rescueuser", method = RequestMethod.GET)
	public String rescueUser(HttpServletRequest request, Model model)
			throws NeedLoginException {
		UserContext context = checkLoginForWeb(request);
		PostView postView = guessYouService.randomRescue(context.getUid());
		model.addAttribute("postView", postView);
		model.addAttribute("pageType", "rescue");
		return "web/index/rescue/index";
	}

	@RequestMapping(value = "/changerescueuser", method = RequestMethod.POST)
	public String changeRescueUser(HttpServletRequest request, Model model,
			long rescueUid) throws NeedLoginException {
		UserContext context = checkLoginForWeb(request);
		guessYouService.removeFromRescueUsers(context.getUid(), rescueUid);
		PostView postView = guessYouService.randomRescue(context.getUid());
		model.addAttribute("postView", postView);
		return "web/index/rescue/card";
	}
}
