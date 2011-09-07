package com.juzhai.home.controller;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.juzhai.act.bean.ActDealType;
import com.juzhai.act.service.IUserActService;
import com.juzhai.core.controller.BaseController;
import com.juzhai.core.exception.NeedLoginException;
import com.juzhai.core.web.session.UserContext;
import com.juzhai.home.bean.Feed;
import com.juzhai.home.exception.IndexException;
import com.juzhai.home.service.IInboxService;

@Controller
@RequestMapping(value = "app")
public class AppHomeController extends BaseController {

	private final Log log = LogFactory.getLog(getClass());

	@Autowired
	private IUserActService userActService;
	@Autowired
	private IInboxService inboxService;
	@Value("${random.feed.frequency}")
	private int randomFeedFrequency = 4;

	@RequestMapping(value = { "/", "/index" }, method = RequestMethod.GET)
	public String home(HttpServletRequest request, Model model)
			throws NeedLoginException {
		UserContext context = checkLoginForApp(request);
		getNextFeed(context.getUid(), 1, model);
		return "home/home";
	}

	@RequestMapping(value = "/ajax/dealFeed", method = RequestMethod.GET)
	@ResponseBody
	public String ajaxDealFeed(HttpServletRequest request, Model model,
			long actId, long friendId, String tpIdentity, int type, int times)
			throws NeedLoginException {
		UserContext context = checkLoginForApp(request);
		ActDealType actDealType = ActDealType.valueOf(type);
		try {
			if (friendId > 0) {
				userActService.respFeed(context.getUid(), actId, friendId,
						actDealType);

			} else if (StringUtils.isNotEmpty(tpIdentity)) {
				userActService.respRandom(context.getUid(), actId, tpIdentity,
						context.getTpId(), actDealType);
			}
		} catch (IndexException e) {
			log.error(e.getMessage(), e);
			return error_500;
		}
		getNextFeed(context.getUid(), times, model);
		return "home/feedFragment";
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
			getRandomFeed(uid, model);
		} else {
			getSpecificFeed(uid, times, model);
		}
		// TODO 显示相互共同好友
	}

	private void getRandomFeed(long uid, Model model) {
		// 获取随机
		Feed feed = inboxService.showRandam(uid);
		model.addAttribute("feed", feed);
		model.addAttribute("times", 1);
	}

	private void getSpecificFeed(long uid, int times, Model model) {
		// 获取指定
		Feed feed = inboxService.showFirst(uid);
		if (null == feed) {
			getRandomFeed(uid, model);
		} else {
			model.addAttribute("feed", feed);
			model.addAttribute("times", times + 1);
		}
	}
}
