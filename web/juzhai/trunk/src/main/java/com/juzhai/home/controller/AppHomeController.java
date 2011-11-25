package com.juzhai.home.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.CollectionUtils;
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
import com.juzhai.act.service.IActService;
import com.juzhai.act.service.IUserActService;
import com.juzhai.core.controller.BaseController;
import com.juzhai.core.exception.NeedLoginException;
import com.juzhai.core.web.session.UserContext;
import com.juzhai.home.bean.Feed;
import com.juzhai.home.bean.ReadFeedType;
import com.juzhai.home.controller.form.AnswerForm;
import com.juzhai.home.exception.IndexException;
import com.juzhai.home.service.IInboxService;
import com.juzhai.passport.InitData;
import com.juzhai.passport.model.Thirdparty;
import com.juzhai.passport.service.IFriendService;

@Controller
@RequestMapping(value = "app")
public class AppHomeController extends BaseController {

	private final Log log = LogFactory.getLog(getClass());

	@Autowired
	private IUserActService userActService;
	@Autowired
	private IInboxService inboxService;
	@Autowired
	private IFriendService friendService;
	@Autowired
	private IActService actService;
	// @Autowired
	// private ILoginService tomcatLoginService;
	@Value("${specific.feed.rate}")
	private int specificFeedRate = 15;
	@Value("${recommend.feed.rate}")
	private int recommendFeedRate = 15;
	@Value("${question.feed.rate}")
	private int questionFeedRate = 15;

	@RequestMapping(value = { "/", "/index" }, method = RequestMethod.GET)
	public String home(HttpServletRequest request, Model model, Boolean isFirst)
			throws NeedLoginException {
		UserContext context = checkLoginForApp(request);
		// queryPoint(context.getUid(), model);
		queryProfile(context.getUid(), model);
		// boolean dayFirstLogin = tomcatLoginService
		// .isDayFirstLoginAndDel(request);
		// if (dayFirstLogin) {
		// showDayFirstLoginTip(model);
		// }
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
		model.addAttribute("actCnt",
				userActService.countUserActByUid(context.getUid()));
		return "home/app/home";
	}

	@RequestMapping(value = "/ajax/showFeed")
	public String showFeed(HttpServletRequest request, Model model)
			throws NeedLoginException {
		UserContext context = checkLoginForApp(request);
		getNextFeed(context, model);
		return "home/app/feed_fragment";
	}

	@RequestMapping(value = "/ajax/respRecommend", method = RequestMethod.POST)
	public String respRecommend(HttpServletRequest request, Model model,
			long actId, int type, boolean isFeed) throws NeedLoginException {
		UserContext context = checkLoginForApp(request);
		ReadFeedType readFeedType = ReadFeedType.valueOf(type);
		userActService.respRecommend(context.getUid(), context.getTpId(),
				actId, readFeedType, isFeed);
		getNextFeed(context, model);
		return "home/app/feed_fragment";
	}

	@RequestMapping(value = "/ajax/respSpecific", method = RequestMethod.POST)
	@Deprecated
	public String respSpecific(HttpServletRequest request, Model model,
			long actId, long friendId, int type) throws NeedLoginException {
		UserContext context = checkLoginForApp(request);
		try {
			if (friendId > 0) {
				ReadFeedType readFeedType = ReadFeedType.valueOf(type);
				userActService.respSpecific(context.getUid(), actId, friendId,
						readFeedType);
			}
		} catch (IndexException e) {
			log.error(e.getMessage(), e);
			return error_500;
		}
		getNextFeed(context, model);
		return "home/app/feed_fragment";
	}

	@RequestMapping(value = "/ajax/respQuestion", method = RequestMethod.POST)
	public String respQuestion(HttpServletRequest request, Model model,
			AnswerForm answerForm) throws NeedLoginException {
		UserContext context = checkLoginForApp(request);
		inboxService.answer(context.getUid(), context.getTpId(),
				answerForm.getQuestionId(), answerForm.getTpIdentity(),
				answerForm.getAnswerId());
		getNextFeed(context, model);
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
	private void getNextFeed(UserContext context, Model model) {
		int randomValue = RandomUtils.nextInt(100);
		if (randomValue < specificFeedRate) {
			getSpecific(context, model, 1);
		} else if (randomValue < specificFeedRate + recommendFeedRate) {
			getRecommend(context, model, 1);
		} else if (randomValue < specificFeedRate + recommendFeedRate
				+ questionFeedRate) {
			// getQuestion(context, model, 1);
		}
	}

	private void getRecommend(UserContext context, Model model, int round) {
		// 获取随机
		Feed feed = inboxService.showRecommend(context.getUid());
		if (null == feed) {
			if (round >= 3) {
				return;
			} else {
				// getQuestion(context, model, round + 1);
				return;
			}
		} else {
			putFeedAndTimes(context, model, feed);
		}
	}

	// private void getQuestion(UserContext context, Model model, int round) {
	// // 获取随机
	// Feed feed = inboxService.showQuestion(context.getUid());
	// if (null == feed) {
	// if (round >= 3) {
	// return;
	// } else {
	// getSpecific(context, model, round + 1);
	// }
	// } else {
	// putFeedAndTimes(context, model, feed);
	// }
	// }

	private void getSpecific(UserContext context, Model model, int round) {
		// 获取指定
		Feed feed = inboxService.showSpecific(context.getUid());
		if (null == feed) {
			if (round >= 3) {
				return;
			} else {
				getRecommend(context, model, round + 1);
			}
		} else {
			putFeedAndTimes(context, model, feed);
		}
	}

	private void putFeedAndTimes(UserContext context, Model model, Feed feed) {
		Thirdparty tp = InitData.TP_MAP.get(context.getTpId());
		if (null != tp && StringUtils.isNotEmpty(tp.getUserHomeUrl())) {
			feed.setTpHomeUrl(tp.getUserHomeUrl());
		}
		if (feed.getAct() != null) {
			long allUserCnt = actService.getTpActPopularity(context.getTpId(),
					feed.getAct().getId());
			model.addAttribute("allUserCnt", allUserCnt);
			if (allUserCnt > 0) {
				List<Long> friendIds = friendService.getAppFriends(context
						.getUid());
				if (CollectionUtils.isNotEmpty(friendIds)) {
					model.addAttribute("friendUserCnt", userActService
							.countFriendUserActByActId(friendIds, feed.getAct()
									.getId()));
				}
			}
		}
		model.addAttribute("feed", feed);
	}
}
