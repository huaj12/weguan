package com.juzhai.plug.controller.website;

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
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.juzhai.app.bean.TpMessageKey;
import com.juzhai.core.controller.BaseController;
import com.juzhai.core.exception.NeedLoginException;
import com.juzhai.core.web.AjaxResult;
import com.juzhai.core.web.session.UserContext;
import com.juzhai.passport.bean.ProfileCache;
import com.juzhai.passport.service.IProfileService;
import com.juzhai.plug.service.IWeiboIviteService;

@Controller
@RequestMapping("/plug")
public class WeiboInviteController extends BaseController {
	private final Log log = LogFactory.getLog(getClass());
	@Autowired
	private MessageSource messageSource;
	@Autowired
	private IProfileService profileService;
	@Autowired
	IWeiboIviteService weiboIviteService;

	@RequestMapping(value = { "/weibo/invite" }, method = RequestMethod.GET)
	@ResponseBody
	public Map<String, String> weiboIvite(HttpServletRequest request,
			HttpServletResponse response, Model model, String uids)
			throws NeedLoginException {
		Map<String, String> map = new HashMap<String, String>();
		UserContext context = checkLoginForWeb(request);
		String message = "";
		try {
			List<String> list = new ArrayList<String>();
			if (StringUtils.isNotEmpty(uids)) {
				for (String uid : uids.split(",")) {
					ProfileCache profileCache = profileService
							.getProfileCacheByUid(Long.valueOf(uid));
					if (profileCache != null) {
						list.add("@" + profileCache.getNickname());
					}
				}
			}
			message = messageSource.getMessage(
					TpMessageKey.WEIBO_CONNECT_INVITE_TEXT,
					new Object[] { StringUtils.join(list, " ") },
					Locale.SIMPLIFIED_CHINESE);

		} catch (Exception e) {
			log.error("weibo Ivite  is error", e);
		}
		map.put("message", message);
		return map;
	}

	@RequestMapping(value = { "/weibo/invite/send" }, method = RequestMethod.POST)
	@ResponseBody
	public AjaxResult snedWeiboInvite(HttpServletRequest request,
			HttpServletResponse response, Model model, String content,
			@RequestParam(defaultValue = "0") long actId)
			throws NeedLoginException {
		AjaxResult ajaxResult = new AjaxResult();
		UserContext context = checkLoginForWeb(request);
		try {
			ajaxResult.setSuccess(weiboIviteService.sendWeiboIvite(content,
					actId, context.getTpId(), context.getUid()));
		} catch (Exception e) {
			log.error("weibo Ivite  is error", e);
			ajaxResult.setSuccess(false);
		}
		return ajaxResult;
	}

}
