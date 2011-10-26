package com.juzhai.home.controller;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.RandomUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.juzhai.act.controller.view.UserActView;
import com.juzhai.act.service.IUserActService;
import com.juzhai.core.controller.BaseController;
import com.juzhai.core.exception.NeedLoginException;
import com.juzhai.core.web.session.UserContext;
import com.juzhai.home.bean.Feed;
import com.juzhai.home.bean.Feed.FeedType;
import com.juzhai.home.bean.ReadFeedType;
import com.juzhai.home.controller.form.AnswerForm;
import com.juzhai.home.exception.IndexException;
import com.juzhai.home.service.IInboxService;
import com.juzhai.passport.InitData;
import com.juzhai.passport.model.Thirdparty;
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
	@Value("${invite.feed.rate}")
	private int inviteFeedRate = 15;

	@RequestMapping(value = { "/", "/index" }, method = RequestMethod.GET)
	public String home(HttpServletRequest request, Model model, Boolean isFirst)
			throws NeedLoginException {
		UserContext context = checkLoginForApp(request);
		// queryPoint(context.getUid(), model);
		queryProfile(context.getUid(), model);
		boolean dayFirstLogin = tomcatLoginService
				.isDayFirstLoginAndDel(request);
		if (dayFirstLogin) {
			showDayFirstLoginTip(model);
		}
		if (isFirst != null && isFirst) {
			List<UserActView> userActViewList = userActService.pageUserActView(
					context.getUid(), 0, 5);
			StringBuilder actNames = new StringBuilder();
			for (int i = 0; i < userActViewList.size(); i++) {
				if (i > 0) {
					actNames.append("、");
				}
				actNames.append(userActViewList.get(i).getAct().getName());
			}
			model.addAttribute("actNames", actNames.toString());
		}
		return "home/app/home";
	}

	@RequestMapping(value = "/ajax/showFeed")
	public String showFeed(HttpServletRequest request, Model model)
			throws NeedLoginException {
		UserContext context = checkLoginForApp(request);
		getNextFeed(context, 1, model, true);
		return "home/app/feed_fragment";
	}

	@RequestMapping(value = "/ajax/respFeed", method = RequestMethod.POST)
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
		getNextFeed(context, times, model, false);
		return "home/app/feed_fragment";
	}

	@RequestMapping(value = "/ajax/answer", method = RequestMethod.POST)
	public String answer(HttpServletRequest request, Model model,
			AnswerForm answerForm, int times) throws NeedLoginException {
		UserContext context = checkLoginForApp(request);
		inboxService.answer(context.getUid(), context.getTpId(),
				answerForm.getQuestionId(), answerForm.getTpIdentity(),
				answerForm.getAnswerId());
		getNextFeed(context, times, model, false);
		return "home/app/feed_fragment";
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
	private void getNextFeed(UserContext context, int times, Model model,
			boolean excludeInvite) {
		if (times > randomFeedFrequency) {
			getQuestion(context, model, false, excludeInvite);
		} else {
			getSpecificFeed(context, times, model, false, excludeInvite);
		}
	}

	private void getQuestion(UserContext context, Model model, boolean isFinal,
			boolean excludeInvite) {
		// 获取随机
		Feed feed = inboxService.showQuestion(context.getUid());
		if (null == feed) {
			if (isFinal) {
				return;
			} else {
				getSpecificFeed(context, 1, model, true, false);
			}
		} else {
			if (isFinal && !excludeInvite) {
				int randomValue = RandomUtils.nextInt(100);
				if (randomValue < inviteFeedRate) {
					// 显示邀请
					feed = new Feed(FeedType.INVITE, new Date());
				}
			}
			putFeedAndTimes(context, 1, model, feed);
		}
	}

	private void getSpecificFeed(UserContext context, int times, Model model,
			boolean isFinal, boolean excludeInvite) {
		// 获取指定
		Feed feed = inboxService.showFirst(context.getUid());
		if (null == feed) {
			if (isFinal) {
				return;
			} else {
				getQuestion(context, model, true, excludeInvite);
			}
		} else {
			putFeedAndTimes(context, times + 1, model, feed);
		}
	}

	private void putFeedAndTimes(UserContext context, int times, Model model,
			Feed feed) {
		Thirdparty tp = InitData.TP_MAP.get(context.getTpId());
		if (null != tp && StringUtils.isNotEmpty(tp.getUserHomeUrl())) {
			feed.setTpHomeUrl(tp.getUserHomeUrl());
		}
		model.addAttribute("feed", feed);
		model.addAttribute("times", times);
	}
}
