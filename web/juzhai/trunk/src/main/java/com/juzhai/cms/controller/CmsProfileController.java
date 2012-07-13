package com.juzhai.cms.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.juzhai.cms.service.IVerifyLogoService;
import com.juzhai.core.controller.BaseController;
import com.juzhai.core.pager.PagerManager;
import com.juzhai.core.web.AjaxResult;
import com.juzhai.index.service.IHighQualityService;
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
	@Autowired
	private IHighQualityService highQualityService;
	@Autowired
	private ThreadPoolTaskExecutor cmsTaskExecutor;

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

	@RequestMapping(value = "/showUser")
	public String listErrorLogo(HttpServletRequest request, Model model) {
		return "cms/profile/show_user";
	}

	@RequestMapping(value = "/queryUser")
	public String queryErrorLogo(HttpServletRequest request, Model model,
			long uid) {
		ProfileCache profile = profileService.getProfileCacheByUid(uid);
		model.addAttribute("isHighQuality",
				highQualityService.isHighQuality(uid));
		model.addAttribute("profile", profile);
		return "cms/profile/show_user";
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

	@RequestMapping(value = "/passLogos")
	@ResponseBody
	public AjaxResult passLogo(HttpServletRequest reqest, Model model,
			String uids) {
		AjaxResult ajaxResult = new AjaxResult();
		try {
			if (StringUtils.isNotEmpty(uids)) {
				for (String str : uids.split(",")) {
					verifyLogoService.passLogo(Long.valueOf(str));
				}
			}
		} catch (Exception e) {
			ajaxResult.setSuccess(false);
		}
		return ajaxResult;
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

	@RequestMapping(value = "/addHighQuality")
	@ResponseBody
	public AjaxResult addHighQuality(HttpServletRequest reqest, Model model,
			long uid) {
		highQualityService.addHighQuality(uid);
		return new AjaxResult();
	}

	@RequestMapping(value = "/removeHighQuality")
	@ResponseBody
	public AjaxResult removeHighQuality(HttpServletRequest reqest, Model model,
			long uid) {
		highQualityService.removeHighQuality(uid);
		return new AjaxResult();
	}

	@RequestMapping(value = "/listHighQuality")
	public String listHighQuality(HttpServletRequest request, Model model,
			@RequestParam(defaultValue = "1") int pageId) {
		PagerManager pager = new PagerManager(pageId, 20,
				highQualityService.countHighQualityUsers());
		List<Long> ids = highQualityService.highQualityUsers(
				pager.getFirstResult(), pager.getMaxResult());
		List<ProfileCache> profileList = new ArrayList<ProfileCache>(ids.size());
		for (Long id : ids) {
			profileList.add(profileService.getProfileCacheByUid(id));
		}
		model.addAttribute("profileList", profileList);
		model.addAttribute("pager", pager);
		return "cms/profile/high_quality";
	}

	@RequestMapping(value = "/realPic")
	@ResponseBody
	public AjaxResult realPic(HttpServletRequest reqest, Model model,
			String imgUrl) {
		AjaxResult ajaxResult = new AjaxResult();
		Boolean flag = verifyLogoService.realPic(imgUrl);
		if (flag != null) {
			ajaxResult.setResult(flag);
		}
		return ajaxResult;
	}
}
