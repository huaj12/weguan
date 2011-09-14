package com.juzhai.home.controller;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.juzhai.act.bean.ActDealType;
import com.juzhai.act.service.IUserActService;
import com.juzhai.core.controller.BaseController;
import com.juzhai.core.exception.NeedLoginException;
import com.juzhai.core.web.AjaxResult;
import com.juzhai.core.web.session.UserContext;
import com.juzhai.home.bean.Feed;
import com.juzhai.home.exception.IndexException;
import com.juzhai.home.service.IInboxService;
import com.juzhai.passport.exception.ProfileInputException;
import com.juzhai.passport.service.IProfileService;

@Controller
@RequestMapping(value = "app")
public class AppHomeController extends BaseController {

	private final Log log = LogFactory.getLog(getClass());

	@Autowired
	private IUserActService userActService;
	@Autowired
	private IInboxService inboxService;
	@Autowired
	private IProfileService profileService;
	@Autowired
	private MessageSource messageSource;
	@Value("${random.feed.frequency}")
	private int randomFeedFrequency = 4;

	@RequestMapping(value = { "/", "/index" }, method = RequestMethod.GET)
	public String home(HttpServletRequest request, Model model)
			throws NeedLoginException {
		long time1 = System.currentTimeMillis();
		UserContext context = checkLoginForApp(request);
		long time2 = System.currentTimeMillis();
		queryPoint(context.getUid(), model);
		long time3 = System.currentTimeMillis();
		getNextFeed(context.getUid(), 1, model);
		long time4 = System.currentTimeMillis();

		System.out.println("check login:" + (time2 - time1));
		System.out.println("query point:" + (time3 - time2));
		System.out.println("get feed:" + (time4 - time3));
		return "home/app/home";
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
			getRandomFeed(uid, model);
		} else {
			getSpecificFeed(uid, times, model);
		}
		// TODO 显示相互共同好友
	}

	private void getRandomFeed(long uid, Model model) {
		// 获取随机
		Feed feed = inboxService.showGrade(uid);
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

	@RequestMapping(value = "/ajax/subEmail", method = RequestMethod.GET)
	@ResponseBody
	public AjaxResult subEmail(HttpServletRequest request, String email)
			throws NeedLoginException {
		UserContext context = checkLoginForApp(request);
		AjaxResult ajaxResult = new AjaxResult();
		try {
			ajaxResult.setSuccess(profileService.subEmail(context.getUid(),
					email));
		} catch (ProfileInputException e) {
			if (log.isDebugEnabled()) {
				log.debug(e.getMessage(), e);
			}
			ajaxResult.setSuccess(false);
			ajaxResult.setErrorCode(e.getErrorCode());
			ajaxResult.setErrorInfo(messageSource.getMessage(e.getErrorCode(),
					null, e.getErrorCode(), Locale.SIMPLIFIED_CHINESE));
		}
		return ajaxResult;
	}
}
