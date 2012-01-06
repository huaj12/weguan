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

import com.juzhai.act.model.Act;
import com.juzhai.act.service.IActService;
import com.juzhai.app.bean.TpMessageKey;
import com.juzhai.core.controller.BaseController;
import com.juzhai.core.exception.NeedLoginException;
import com.juzhai.core.web.AjaxResult;
import com.juzhai.core.web.jstl.JzResourceFunction;
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
	IWeiboIviteService weiboIviteService;
	@Autowired
	private IActService actService;

	@RequestMapping(value = { "/weibo/invite" }, method = RequestMethod.GET)
	public String weiboIvite(HttpServletRequest request,
			HttpServletResponse response, Model model, String uids,@RequestParam(defaultValue = "0")long actId)
			throws NeedLoginException {
		UserContext context = checkLoginForWeb(request);
		String message = "";
		String picurl="";
		Act act=null;
		try {
			act = actService.getActById(actId);
			String logo = "";
			if (act != null) {
				logo = act.getLogo();
				 picurl = JzResourceFunction.actLogo(actId, logo, 120);
			}
			List<String> list=weiboIviteService.getInviteReceiverName(uids, context.getTpId(), context.getUid());
			message = messageSource.getMessage(
					TpMessageKey.WEIBO_CONNECT_INVITE_TEXT,
					new Object[] { StringUtils.join(list, " ") },
					Locale.SIMPLIFIED_CHINESE);
		} catch (Exception e) {
			log.error("weiboIvite is error."+e.getMessage());
		}
		model.addAttribute("act", act);
		model.addAttribute("logo", picurl);
		model.addAttribute("message", message);
		return "web/plug/invite/plug_invite_dialog";
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
