package com.juzhai.plug.controller.website;

import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.juzhai.core.controller.BaseController;
import com.juzhai.core.exception.NeedLoginException;
import com.juzhai.core.web.AjaxResult;
import com.juzhai.core.web.session.UserContext;
import com.juzhai.passport.service.IInviteService;

@Controller
@RequestMapping("/plug")
public class InviteController extends BaseController {
	private final Log log = LogFactory.getLog(getClass());
	@Autowired
	private IInviteService inviteService;

	@RequestMapping(value = { "/show/invite" }, method = RequestMethod.GET)
	public String showIvite(HttpServletRequest request,
			HttpServletResponse response, Model model, String uids, String names)
			throws NeedLoginException {
		UserContext context = checkLoginForWeb(request);
		model.addAttribute("message", inviteService.showInvite(uids, names,
				context.getTpId(), context.getUid()));
		model.addAttribute("fuids", uids);
		return "web/plug/invite/plug_invite_dialog";
	}

	@RequestMapping(value = { "/invite/send" }, method = RequestMethod.POST)
	@ResponseBody
	public AjaxResult snedWeiboInvite(HttpServletRequest request,
			HttpServletResponse response, Model model, String content,
			String fuids) throws NeedLoginException {
		AjaxResult ajaxResult = new AjaxResult();
		UserContext context = checkLoginForWeb(request);
		List<String> list = null;
		if (StringUtils.isNotEmpty(fuids)) {
			list = Arrays.asList(fuids.split(","));
		}
		try {
			ajaxResult.setSuccess(inviteService.sendIvite(content,
					context.getTpId(), context.getUid(), list));
		} catch (Exception e) {
			log.error("weibo Ivite  is error", e);
			ajaxResult.setSuccess(false);
		}

		return ajaxResult;
	}
	

}
