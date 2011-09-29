package com.juzhai.home.controller;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.juzhai.act.service.IUserActService;
import com.juzhai.core.controller.BaseController;
import com.juzhai.core.exception.NeedLoginException;
import com.juzhai.core.web.session.UserContext;
import com.juzhai.home.bean.Feed;
import com.juzhai.home.bean.ReadFeedType;
import com.juzhai.home.controller.form.AnswerForm;
import com.juzhai.home.exception.IndexException;
import com.juzhai.home.service.IInboxService;
import com.juzhai.passport.service.login.ILoginService;

@Controller
@RequestMapping(value = "app")
public class AppHomeController extends BaseController {

	private final Log log = LogFactory.getLog(getClass());

	@Autowired
	private IUserActService userActService;
	@Autowired
	private IInboxService inboxService;
	@Autowired
	private ILoginService tomcatLoginService;
	@Value("${random.feed.frequency}")
	private int randomFeedFrequency = 4;

	@RequestMapping(value = { "/", "/index" }, method = RequestMethod.GET)
	public String home(HttpServletRequest request, Model model)
			throws NeedLoginException {
		UserContext context = checkLoginForApp(request);
		queryPoint(context.getUid(), model);
		queryProfile(context.getUid(), model);
		boolean dayFirstLogin = tomcatLoginService
				.isDayFirstLoginAndDel(request);
		if (dayFirstLogin) {
			showDayFirstLoginTip(model);
		}
		return "home/app/home";
	}

	@RequestMapping(value = "/ajax/showFeed", method = RequestMethod.GET)
	public String showFeed(HttpServletRequest request, Model model)
			throws NeedLoginException {
		UserContext context = checkLoginForApp(request);
		long time3 = System.currentTimeMillis();
		getNextFeed(context.getUid(), 1, model);
		long time4 = System.currentTimeMillis();
		System.out.println("get feed:" + (time4 - time3));
		return "home/app/feed_fragment";
	}

	@RequestMapping(value = "/ajax/respFeed", method = RequestMethod.POST)
	@ResponseBody
	public String respFeed(HttpServletRequest request, Model model, long actId,
			long friendId, int type, int times) throws NeedLoginException {
		UserContext context = checkLoginForApp(request);
		try {
			if (friendId > 0) {
				ReadFeedType readFeedType = ReadFeedType.valueOf(type);
				userActService.respFeed(context.getUid(), actId, friendId,
						readFeedType);

			}
		} catch (IndexException e) {
			log.error(e.getMessage(), e);
			return error_500;
		}
		getNextFeed(context.getUid(), times, model);
		return "home/feed_fragment";
	}

	@RequestMapping(value = "/ajax/answer", method = RequestMethod.POST)
	@ResponseBody
	public String answer(HttpServletRequest request, Model model,
			AnswerForm answerForm, int times) throws NeedLoginException {
		UserContext context = checkLoginForApp(request);
		inboxService.answer(context.getUid(), context.getTpId(),
				answerForm.getQuestionId(), answerForm.getTpIdentity(),
				answerForm.getAnswerId());
		getNextFeed(context.getUid(), times, model);
		return "home/feed_fragment";
	}

	/**
	 * 获取下一个feed
	 * 
	 * @param uid
	 *            当前用户ID
	 * @param times
	 *            第几次
	 * @param model
	 *            调用请求的Model
	 */
	private void getNextFeed(long uid, int times, Model model) {
		if (times > randomFeedFrequency) {
			getQuestion(uid, model);
		} else {
			getSpecificFeed(uid, times, model);
		}
		// TODO 显示相互共同好友
	}

	private void getQuestion(long uid, Model model) {
		// 获取随机
		Feed feed = inboxService.showQuestion(uid);
		model.addAttribute("feed", feed);
		model.addAttribute("times", 1);
	}

	private void getSpecificFeed(long uid, int times, Model model) {
		// 获取指定
		Feed feed = inboxService.showFirst(uid);
		if (null == feed) {
			getQuestion(uid, model);
		} else {
			model.addAttribute("feed", feed);
			model.addAttribute("times", times + 1);
		}
	}
}
