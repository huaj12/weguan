package com.juzhai.home.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.juzhai.core.controller.BaseController;
import com.juzhai.core.exception.NeedLoginException;
import com.juzhai.core.pager.PagerManager;
import com.juzhai.core.web.AjaxResult;
import com.juzhai.core.web.session.UserContext;
import com.juzhai.home.service.IBlacklistService;
import com.juzhai.passport.bean.ProfileCache;
import com.juzhai.passport.exception.InputBlacklistException;
import com.juzhai.passport.service.IProfileService;

@Controller
@RequestMapping(value = "/home")
public class BlacklistController extends BaseController {
	private final Log log = LogFactory.getLog(getClass());
	@Autowired
	private MessageSource messageSource;
	@Autowired
	IBlacklistService blacklistService;
	@Autowired
	IProfileService profileService;
	@Value("${blacklist.max.rows}")
	private int blacklistMaxRows;

	@RequestMapping(value = { "/blacklist/", "/blacklist" }, method = RequestMethod.GET)
	public String index(HttpServletRequest request, Model model)
			throws NeedLoginException {
		return page(request, model, 1);
	}

	@RequestMapping(value = "/blacklist/{page}", method = RequestMethod.GET)
	public String page(HttpServletRequest request, Model model,
			@PathVariable int page) throws NeedLoginException {
		UserContext context = checkLoginForWeb(request);
		PagerManager pager = new PagerManager(page, blacklistMaxRows,
				blacklistService.countBlacklist(context.getUid()));
		List<Long> ids = blacklistService.blacklist(context.getUid(),
				pager.getFirstResult(), pager.getMaxResult());
		List<ProfileCache> profiles = new ArrayList<ProfileCache>(ids.size());
		for (Long id : ids) {
			profiles.add(profileService.getProfileCacheByUid(id));
		}
		model.addAttribute("profileView", profiles);
		model.addAttribute("pager", pager);
		return "web/home/blacklist/blacklist";
	}

	@RequestMapping(value = "/blacklist/cancel", method = RequestMethod.POST)
	@ResponseBody
	public AjaxResult cancel(HttpServletRequest request,
			@RequestParam(defaultValue = "0") long shieldUid)
			throws NeedLoginException {
		UserContext context = checkLoginForWeb(request);
		AjaxResult ajaxResult = new AjaxResult();
		try {
			blacklistService.cancel(context.getUid(), shieldUid);
		} catch (InputBlacklistException e) {
			ajaxResult.setError(e.getErrorCode(), messageSource);
		}
		return ajaxResult;
	}

	@RequestMapping(value = "/blacklist/shield", method = RequestMethod.POST)
	@ResponseBody
	public AjaxResult shield(HttpServletRequest request,
			@RequestParam(defaultValue = "0") long shieldUid)
			throws NeedLoginException {
		UserContext context = checkLoginForWeb(request);
		AjaxResult ajaxResult = new AjaxResult();
		try {
			blacklistService.shield(context.getUid(), shieldUid);
		} catch (InputBlacklistException e) {
			ajaxResult.setError(e.getErrorCode(), messageSource);
		}
		return ajaxResult;
	}

}
