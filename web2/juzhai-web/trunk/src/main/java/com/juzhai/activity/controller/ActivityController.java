package com.juzhai.activity.controller;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.juzhai.core.controller.BaseController;
import com.juzhai.passport.service.IPassportService;
import com.juzhai.passport.service.IProfileService;

@Controller
@RequestMapping("activity")
public class ActivityController extends BaseController {
	private final Log log = LogFactory.getLog(getClass());

	public static final String INVITE_SECRET = "invite.secret";

	@Autowired
	private IPassportService passportService;
	@Autowired
	private IProfileService profileService;

	@RequestMapping(value = "/lhq", method = RequestMethod.GET)
	public String lhq(HttpServletRequest request, Model model) {
		return "redirect:/../home";
		// ProfileCache loginUser = (ProfileCache) request
		// .getAttribute(CheckLoginFilter.LOGIN_USER_KEY);
		// if (null != loginUser) {
		// try {
		// String token = DESUtils.encryptToHexString(
		// INVITE_SECRET.getBytes(),
		// String.valueOf(loginUser.getUid()).getBytes());
		// model.addAttribute("token", token);
		// } catch (Exception e) {
		// log.error("encrypt uid error.[uid=" + loginUser.getUid() + "]",
		// e);
		// }
		// List<Long> uids = passportService.listInviteUsers(loginUser
		// .getUid());
		// List<ProfileCache> userList = new ArrayList<ProfileCache>(
		// uids.size());
		// int logoVerifyCount = 0;
		// for (Long uid : uids) {
		// ProfileCache profileCache = profileService
		// .getProfileCacheByUid(uid);
		// if (null != profileCache) {
		// userList.add(profileCache);
		// if (StringUtils.isNotEmpty(profileCache.getLogoPic())) {
		// logoVerifyCount++;
		// }
		// }
		// }
		// model.addAttribute("userList", userList);
		// model.addAttribute("logoVerifyCount", logoVerifyCount);
		// }
		// return "web/activity/lhq";
	}

	@RequestMapping(value = "/invite/{token}", method = RequestMethod.GET)
	public String invite(HttpServletRequest request, Model model,
			@PathVariable String token) {
		return "redirect:/../home";
		// UserContext context = (UserContext) request.getAttribute("context");
		// if (context.hasLogin() || StringUtils.isEmpty(token)) {
		// return "redirect:/../home";
		// }
		// long uid = decryptInviteUid(token);
		// model.addAttribute("profile",
		// profileService.getProfileCacheByUid(uid));
		// model.addAttribute("token", token);
		// return "web/activity/invite";
	}
}
