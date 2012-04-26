package com.juzhai.cms.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.juzhai.cms.service.IVerifyLogoService;
import com.juzhai.core.controller.BaseController;
import com.juzhai.core.pager.PagerManager;
import com.juzhai.core.web.AjaxResult;
import com.juzhai.passport.bean.LogoVerifyState;
import com.juzhai.passport.bean.ProfileCache;
import com.juzhai.passport.model.Profile;
import com.juzhai.passport.service.IProfileService;

@Controller
@RequestMapping("/cms/profile")
public class CmsProfileController extends BaseController {

	@Autowired
	private IVerifyLogoService verifyLogoService;
	@Autowired
	private IProfileService profileService;

	@RequestMapping(value = "/listVerifyingLogo")
	public String listVerifyingLogo(HttpServletRequest request, Model model,
			@RequestParam(defaultValue = "1") int pageId) {
		model.addAttribute("type", "listVerifyingLogo");
		return listVerifyLogo(model, pageId, LogoVerifyState.VERIFYING);
	}

	@RequestMapping(value = "/listVerifiedLogo")
	public String listVerifiedLogo(HttpServletRequest request, Model model,
			@RequestParam(defaultValue = "1") int pageId) {
		model.addAttribute("type", "listVerifiedLogo");
		return listVerifyLogo(model, pageId, LogoVerifyState.VERIFIED);
	}

	@RequestMapping(value = "/listUnVerifiedLogo")
	public String listUnVerifiedLogo(HttpServletRequest request, Model model,
			@RequestParam(defaultValue = "1") int pageId) {
		model.addAttribute("type", "listUnVerifiedLogo");
		return listVerifyLogo(model, pageId, LogoVerifyState.UNVERIFIED);
	}

	@RequestMapping(value = "/listErrorLogo")
	public String listErrorLogo(HttpServletRequest request, Model model) {
		return "cms/profile/error_logo";
	}

	@RequestMapping(value = "/queryErrorLogo")
	public String queryErrorLogo(HttpServletRequest request, Model model,
			long uid) {
		ProfileCache profile = profileService.getProfileCacheByUid(uid);
		model.addAttribute("profile", profile);
		return "cms/profile/error_logo";
	}

	private String listVerifyLogo(Model model, int pageId,
			LogoVerifyState logoVerifyState) {
		PagerManager pager = new PagerManager(pageId, 30,
				verifyLogoService.countVerifyLogoProfile(logoVerifyState));
		List<Profile> profileList = verifyLogoService.listVerifyLogoProfile(
				logoVerifyState, pager.getFirstResult(), pager.getMaxResult());
		model.addAttribute("pager", pager);
		model.addAttribute("profileList", profileList);
		return "cms/profile/verify_logo";
	}

	@RequestMapping(value = "/passLogo")
	@ResponseBody
	public AjaxResult passLogo(HttpServletRequest reqest, Model model, long uid) {
		verifyLogoService.passLogo(uid);
		return new AjaxResult();
	}

	@RequestMapping(value = "/denyLogo")
	@ResponseBody
	public AjaxResult denyLogo(HttpServletRequest reqest, Model model, long uid) {
		verifyLogoService.denyLogo(uid);
		return new AjaxResult();
	}

	@RequestMapping(value = "/removeLogo")
	@ResponseBody
	public AjaxResult removeLogo(HttpServletRequest reqest, Model model,
			long uid) {
		verifyLogoService.removeLogo(uid);
		return new AjaxResult();
	}
}
