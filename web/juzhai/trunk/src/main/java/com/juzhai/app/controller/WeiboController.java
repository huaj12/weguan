package com.juzhai.app.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import weibo4j.Friendships;
import weibo4j.Weibo;
import weibo4j.model.WeiboException;

import com.juzhai.act.model.Act;
import com.juzhai.act.service.IActService;
import com.juzhai.act.service.IUserActService;
import com.juzhai.app.bean.TpMessageKey;
import com.juzhai.app.service.IAppService;
import com.juzhai.app.service.IWeiboService;
import com.juzhai.core.controller.BaseController;
import com.juzhai.core.exception.NeedLoginException;
import com.juzhai.core.util.TextTruncateUtil;
import com.juzhai.core.web.AjaxResult;
import com.juzhai.core.web.jstl.JzCoreFunction;
import com.juzhai.core.web.session.UserContext;
import com.juzhai.msg.service.IMsgMessageService;
import com.juzhai.passport.InitData;
import com.juzhai.passport.bean.AuthInfo;
import com.juzhai.passport.model.Thirdparty;
import com.juzhai.passport.model.TpUser;
import com.juzhai.passport.service.ITpUserAuthService;
import com.juzhai.passport.service.ITpUserService;
import com.juzhai.platform.service.IMessageService;

@Controller
public class WeiboController extends BaseController {
	private final Log log = LogFactory.getLog(getClass());
	@Autowired
	private MessageSource messageSource;
	@Autowired
	private IActService actService;
	@Autowired
	private IMessageService messageService;
	@Autowired
	private IWeiboService weiboService;
	@Autowired
	private ITpUserAuthService tpUserAuthService;
	@Autowired
	private IUserActService userActService;
	@Autowired
	private IAppService appService;
	@Value("${show.feed.count}")
	private int feedCount = 3;

	@RequestMapping(value = { "/weiboFeed" }, method = RequestMethod.GET)
	@ResponseBody
	public Map<String, String> weiboFeed(HttpServletRequest request,
			HttpServletResponse response, Model model,
			@RequestParam(defaultValue = "0") long id) {
		Map<String, String> map = new HashMap<String, String>();
		try {
			UserContext context = checkLoginForApp(request);
			Act act = actService.getActById(id);
			int count = userActService.countUserActByActId(context.getTpId(),
					id);
			if (count == 0) {
				count = 1;
			}
			String message = messageSource.getMessage(
					TpMessageKey.WEIBO_FEED_TEXT_COUNT, new Object[] { count,
							act.getName() },
					Locale.SIMPLIFIED_CHINESE);
			String logo = "";
			if (act != null) {
				logo = act.getLogo();
			}
			String picurl = JzCoreFunction.actLogo(id, logo, 120);
			map.put("actId", String.valueOf(act.getId()));
			map.put("message", message);
			map.put("picurl", picurl);
		} catch (Exception e) {
			log.error("renren send feed is error", e);
			return map;
		}
		return map;
	}

	@RequestMapping(value = { "/weiboRequest" }, method = RequestMethod.GET)
	@ResponseBody
	public Map<String, String> weiboRequest(HttpServletRequest request,
			HttpServletResponse response, Model model) {
		Map<String, String> map = new HashMap<String, String>();
		try {
			UserContext context = checkLoginForApp(request);
			Thirdparty tp = InitData.TP_MAP.get(context.getTpId());

			String picurl = JzCoreFunction.actLogo(0, null, 120);
			String message = messageSource.getMessage(
					TpMessageKey.WEIBO_INVITE_TEXT, null,
					Locale.SIMPLIFIED_CHINESE);
			map.put("message", message);
			map.put("picurl", picurl);
		} catch (Exception e) {
			log.error("kaixin send feed is error", e);
			return map;
		}
		return map;
	}

	@RequestMapping(value = "/sendFeed")
	@ResponseBody
	public AjaxResult sendFeed(HttpServletRequest request, String content,
			Long actId, String isFollow) throws NeedLoginException {
		UserContext context = checkLoginForApp(request);
		AjaxResult result = new AjaxResult();
		try {
			if (actId == null) {
				actId = 0l;
			}
			AuthInfo authInfo = tpUserAuthService.getAuthInfo(context.getUid(),
					context.getTpId());
			if (StringUtils.isNotEmpty(isFollow)) {
				weiboService.follow(context.getTpId(), context.getUid());
			}
			Thirdparty tp = InitData.TP_MAP.get(context.getTpId());
			String link = "";
			if (tp != null) {
				link = tp.getAppUrl();
			}
			if (weiboService.sendFeed(context.getTpId(), context.getUid(), content, actId)) {
				result.setSuccess(true);
			} else {
				result.setSuccess(false);
			}
		} catch (Exception e) {
			result.setSuccess(false);
			log.error("send feed is error" + e.getMessage());
		}
		return result;
	}

	@RequestMapping(value = "/sendRequest")
	@ResponseBody
	public AjaxResult sendRequest(HttpServletRequest request, String content)
			throws NeedLoginException {
		UserContext context = checkLoginForApp(request);
		AjaxResult result = new AjaxResult();
		try {
			if (StringUtils.isEmpty(content)) {
				result.setSuccess(false);
			} else {
				if (weiboService.sendWeiboRequest(context.getTpId(),
						context.getUid(), content)) {
					result.setSuccess(true);
				} else {
					result.setSuccess(false);
				}
			}
		} catch (Exception e) {
			result.setSuccess(false);
			log.error("send request is error" + e.getMessage());
		}
		return result;
	}
}
