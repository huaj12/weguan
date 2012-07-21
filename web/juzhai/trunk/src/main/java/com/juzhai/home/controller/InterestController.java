package com.juzhai.home.controller;

import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.juzhai.core.controller.BaseController;
import com.juzhai.core.exception.NeedLoginException;
import com.juzhai.core.web.AjaxResult;
import com.juzhai.core.web.session.UserContext;
import com.juzhai.passport.exception.InterestUserException;
import com.juzhai.passport.service.IInterestUserService;
import com.juzhai.post.bean.PurposeType;
import com.juzhai.post.model.Post;
import com.juzhai.post.service.impl.PostService;
import com.juzhai.stats.counter.service.ICounter;

@Controller
@RequestMapping(value = "home")
public class InterestController extends BaseController {

	@Autowired
	private IInterestUserService interestUserService;
	@Autowired
	private MessageSource messageSource;
	@Autowired
	private PostService postService;
	@Autowired
	private ICounter clickRescueGirlDialogCounter;

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
	@RequestMapping(value = "/auto/interests", method = RequestMethod.POST)
	public AjaxResult interest(HttpServletRequest request, String uids,
			Model model) throws NeedLoginException {
		UserContext context = checkLoginForWeb(request);
		AjaxResult result = new AjaxResult();
		String content = null;
		List<Post> posts = postService.listUserPost(context.getUid(), null, 0,
				1);
		if (posts != null && posts.size() > 0) {
			content = messageSource.getMessage(
					PurposeType.getWordMessageKey(3), null,
					Locale.SIMPLIFIED_CHINESE)
					+ ":" + posts.get(0).getContent();

		}
		String[] uidStrs = uids.split(",");
		for (String uidStr : uidStrs) {
			try {
				interestUserService.interestUser(context.getUid(),
						Long.valueOf(uidStr), content);
			} catch (InterestUserException e) {
			}
		}
		clickRescueGirlDialogCounter.incr(null, 1l);
		result.setSuccess(true);
		return result;
	}
}
