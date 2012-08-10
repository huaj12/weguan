package com.juzhai.mobile.home.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.juzhai.core.controller.BaseController;
import com.juzhai.core.exception.NeedLoginException;
import com.juzhai.core.pager.PagerManager;
import com.juzhai.core.web.AjaxResult;
import com.juzhai.core.web.session.UserContext;
import com.juzhai.mobile.common.web.response.ListJsonResult;
import com.juzhai.mobile.passport.controller.view.UserMView;
import com.juzhai.mobile.passport.controller.viewHelper.IUserMViewHelper;
import com.juzhai.mobile.post.controller.view.PostMView;
import com.juzhai.mobile.post.controller.viewHelper.IPostMViewHelper;
import com.juzhai.passport.bean.ProfileCache;
import com.juzhai.passport.exception.InterestUserException;
import com.juzhai.passport.service.IInterestUserService;
import com.juzhai.passport.service.IProfileService;
import com.juzhai.passport.service.IUserGuideService;
import com.juzhai.post.model.Post;
import com.juzhai.post.service.IPostService;

@Controller
@RequestMapping(value = "m/home")
public class HomeMController extends BaseController {

	@Autowired
	private IPostService postService;
	@Autowired
	private IProfileService profileService;
	@Autowired
	private IUserGuideService userGuideService;
	@Autowired
	private IInterestUserService interestUserService;
	@Autowired
	private IPostMViewHelper postMViewHelper;
	@Autowired
	private IUserMViewHelper userMViewHelper;
	@Autowired
	private MessageSource messageSource;
	@Value("${mobile.my.post.max.rows}")
	private int mobileMyPostMaxRows = 2;
	@Value("${mobile.interest.user.max.rows}")
	private int mobileInterestUserMaxRows = 1;

	@RequestMapping(value = "", method = RequestMethod.GET)
	@ResponseBody
	public ListJsonResult home(HttpServletRequest request, Long uid, int page)
			throws NeedLoginException {
		UserContext context = checkLoginForWeb(request);
		if (uid == null || uid <= 0) {
			uid = context.getUid();
		}

		PagerManager pager = new PagerManager(page, mobileMyPostMaxRows,
				postService.countUserPost(uid));
		List<Post> postList = postService.listUserPost(uid, null,
				pager.getFirstResult(), pager.getMaxResult());
		List<PostMView> postViewList = new ArrayList<PostMView>(postList.size());
		for (Post post : postList) {
			postViewList.add(postMViewHelper.createPostMView(context, post));
		}

		ListJsonResult result = new ListJsonResult();
		result.setResult(pager, postViewList);

		return result;
	}

	@RequestMapping(value = "/refresh", method = RequestMethod.GET)
	@ResponseBody
	public AjaxResult homeRefresh(HttpServletRequest request)
			throws NeedLoginException {
		UserContext context = checkLoginForWeb(request);
		AjaxResult result = new AjaxResult();
		result.setResult(userMViewHelper.createUserMView(context,
				profileService.getProfileCacheByUid(context.getUid()),
				userGuideService.isCompleteGuide(context.getUid())));
		return result;
	}

	@RequestMapping(value = "/interestList", method = RequestMethod.GET)
	@ResponseBody
	public ListJsonResult listInterestUser(HttpServletRequest request, int page)
			throws NeedLoginException {
		UserContext context = checkLoginForWeb(request);
		PagerManager pager = new PagerManager(page, mobileInterestUserMaxRows,
				interestUserService.countInterestUser(context.getUid()));
		List<ProfileCache> list = interestUserService.listInterestUser(
				context.getUid(), pager.getFirstResult(), pager.getMaxResult());

		List<UserMView> userViewList = new ArrayList<UserMView>(list.size());
		for (ProfileCache profileCache : list) {
			UserMView userView = userMViewHelper.createUserMView(context,
					profileCache, false);
			userViewList.add(userView);
		}

		ListJsonResult result = new ListJsonResult();
		result.setResult(pager, userViewList);
		return result;
	}

	@RequestMapping(value = "/interestMeList", method = RequestMethod.GET)
	@ResponseBody
	public ListJsonResult listInterestMeUser(HttpServletRequest request,
			int page) throws NeedLoginException {
		UserContext context = checkLoginForWeb(request);
		PagerManager pager = new PagerManager(page, mobileInterestUserMaxRows,
				interestUserService.countInterestMeUser(context.getUid()));
		List<ProfileCache> list = interestUserService.listInterestMeUser(
				context.getUid(), pager.getFirstResult(), pager.getMaxResult());

		List<UserMView> userViewList = new ArrayList<UserMView>(list.size());
		for (ProfileCache profileCache : list) {
			UserMView userView = userMViewHelper.createUserMView(context,
					profileCache, false);
			userViewList.add(userView);
		}

		ListJsonResult result = new ListJsonResult();
		result.setResult(pager, userViewList);
		return result;
	}

	@ResponseBody
	@RequestMapping(value = "/interest", method = RequestMethod.POST)
	public AjaxResult interest(HttpServletRequest request, long uid, Model model)
			throws NeedLoginException {
		UserContext context = checkLoginForWeb(request);
		AjaxResult result = new AjaxResult();
		try {
			interestUserService.interestUser(context.getUid(), uid);
			result.setSuccess(true);
		} catch (InterestUserException e) {
			result.setError(e.getErrorCode(), messageSource);
		}
		return result;
	}

	@ResponseBody
	@RequestMapping(value = "/removeInterest", method = RequestMethod.POST)
	public AjaxResult removeInterest(HttpServletRequest request, long uid,
			Model model) throws NeedLoginException {
		UserContext context = checkLoginForWeb(request);
		AjaxResult result = new AjaxResult();
		result.setSuccess(true);
		interestUserService.removeInterestUser(context.getUid(), uid);
		return result;
	}

	@ResponseBody
	@RequestMapping(value = "/invite", method = RequestMethod.POST)
	public AjaxResult invite(HttpServletRequest request, String content)
			throws NeedLoginException {
		UserContext context = checkLoginForWeb(request);
		return new AjaxResult(true);
	}
}
