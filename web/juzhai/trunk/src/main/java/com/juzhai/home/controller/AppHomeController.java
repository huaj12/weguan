package com.juzhai.home.controller;

import java.util.SortedMap;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.juzhai.act.bean.ActDealType;
import com.juzhai.act.model.Act;
import com.juzhai.act.service.IInboxService;
import com.juzhai.act.service.IUserActService;
import com.juzhai.core.controller.BaseController;
import com.juzhai.core.exception.NeedLoginException;
import com.juzhai.core.web.session.UserContext;
import com.juzhai.home.exception.IndexException;
import com.juzhai.passport.bean.ProfileCache;
import com.juzhai.passport.bean.TpFriend;

@Controller
@RequestMapping(value = "app")
public class AppHomeController extends BaseController {

	private final Log log = LogFactory.getLog(getClass());

	@Autowired
	private IUserActService userActService;
	@Autowired
	private IInboxService inboxService;

	@RequestMapping(value = { "/", "/index" }, method = RequestMethod.GET)
	public String home(HttpServletRequest request, Model model)
			throws NeedLoginException {
		UserContext context = checkLoginForApp(request);
		if (!getNextFeed(context.getUid(), 1, model)) {
			return error_500;
		}
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
				userActService.respFeed(context.getUid(), actId, tpIdentity,
						context.getTpId(), actDealType);
			}
		} catch (IndexException e) {
			log.error(e.getMessage(), e);
			return error_500;
		}
		if (!getNextFeed(context.getUid(), times, model)) {
			return error_500;
		}
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
	private boolean getNextFeed(long uid, int times, Model model) {
		if (times >= 4) {
			// 获取随机
			SortedMap<TpFriend, Act> random = inboxService.showRandam(uid);
			if (MapUtils.isEmpty(random)) {
				return false;
			}
			TpFriend tpFriend = random.firstKey();
			model.addAttribute("tpFriend", tpFriend);
			model.addAttribute("act", random.get(tpFriend));
			model.addAttribute("times", 1);
		} else {
			// 获取指定
			SortedMap<ProfileCache, Act> first = inboxService.showFirst(uid);
			if (MapUtils.isEmpty(first)) {
				return getNextFeed(uid, 4, model);
			} else {
				ProfileCache key = first.firstKey();
				model.addAttribute("friendProfile", key);
				model.addAttribute("act", first.get(key));
				model.addAttribute("times", times + 1);
			}
		}

		// TODO 显示相互共同好友
		return true;
	}
}
