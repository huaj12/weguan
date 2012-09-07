package com.juzhai.passport.controller;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.juzhai.core.controller.BaseController;
import com.juzhai.core.exception.NeedLoginException;
import com.juzhai.core.web.session.UserContext;
import com.juzhai.passport.InitData;
import com.juzhai.passport.bean.LogoVerifyState;
import com.juzhai.passport.bean.ProfileCache;
import com.juzhai.passport.controller.form.SettingForm;
import com.juzhai.passport.exception.ProfileInputException;
import com.juzhai.passport.model.Profile;
import com.juzhai.passport.model.UserGuide;
import com.juzhai.passport.service.IProfileService;
import com.juzhai.passport.service.IUserGuideService;

@Controller
@RequestMapping(value = "home")
public class UserGuideController extends BaseController {

	private final Log log = LogFactory.getLog(getClass());

	@Autowired
	private IUserGuideService userGuideService;
	@Autowired
	private IProfileService profileService;
	@Autowired
	private MessageSource messageSource;

	@RequestMapping(value = "/guide", method = RequestMethod.GET)
	public String guide(HttpServletRequest request, Model model)
			throws NeedLoginException {
		UserContext context = checkLoginForWeb(request);
		Profile loginUser = profileService.getProfile(context.getUid());
		if (loginUser == null) {
			return error_500;
		}
		UserGuide userGuide = userGuideService.getUserGuide(context.getUid());
		if (userGuide != null && userGuide.getComplete()) {
			return "redirect:/../home";
		}
		SettingForm settingForm = new SettingForm();
		settingForm.setGender(loginUser.getGender());
		settingForm.setProvince(loginUser.getProvince());
		settingForm.setCity(loginUser.getCity());
		settingForm.setTown(loginUser.getTown());
		settingForm.setBirthYear(loginUser.getBirthYear());
		settingForm.setBirthMonth(loginUser.getBirthMonth());
		settingForm.setBirthDay(loginUser.getBirthDay());
		settingForm.setBirthSecret(loginUser.getBirthSecret());
		settingForm.setProfessionId(loginUser.getProfessionId());
		settingForm.setProfession(loginUser.getProfession());
		model.addAttribute("settingForm", settingForm);
		model.addAttribute("professions", InitData.PROFESSION_MAP.values());
		return "web/profile/guide";
	}

	@RequestMapping(value = "/guide/next", method = RequestMethod.POST)
	public String next(HttpServletRequest request, SettingForm settingForm,
			Model model) throws NeedLoginException {
		UserContext context = checkLoginForWeb(request);
		UserGuide userGuide = userGuideService.getUserGuide(context.getUid());
		if (null != userGuide && userGuide.getComplete()) {
			return "redirect:/../home";
		}
		// save
		Profile profile = new Profile();
		profile.setUid(context.getUid());
		profile.setGender(settingForm.getGender());
		profile.setProvince(settingForm.getProvince());
		profile.setCity(settingForm.getCity());
		profile.setTown(settingForm.getTown() == null ? -1 : settingForm
				.getTown());
		profile.setBirthYear(settingForm.getBirthYear());
		profile.setBirthMonth(settingForm.getBirthMonth());
		profile.setBirthDay(settingForm.getBirthDay());
		Boolean birthSecret = settingForm.getBirthSecret();
		if (settingForm.getBirthSecret() == null) {
			birthSecret = false;
		}
		profile.setBirthSecret(birthSecret);
		profile.setProfession(settingForm.getProfession());
		Long professionId = settingForm.getProfessionId();
		if (professionId == null) {
			professionId = 0l;
		}
		profile.setProfessionId(professionId);
		try {
			profileService.nextGuide(profile);
		} catch (ProfileInputException e) {
			if (log.isDebugEnabled()) {
				log.debug(e.getMessage(), e);
			}
			model.addAttribute("errorCode", e.getErrorCode());
			model.addAttribute("errorInfo", messageSource.getMessage(
					e.getErrorCode(), null, Locale.SIMPLIFIED_CHINESE));
			model.addAttribute("settingForm", settingForm);
			model.addAttribute("professions", InitData.PROFESSION_MAP.values());
			return "web/profile/guide";
		}
		if (userGuide == null) {
			userGuideService.createAndCompleteGuide(context.getUid());
		} else {
			userGuideService.completeGuide(context.getUid());
		}
		ProfileCache profileCache = profileService.getProfileCacheByUid(context
				.getUid());
		if (null == profileCache) {
			return error_500;
		} else if (profileCache.getLogoVerifyState() != LogoVerifyState.VERIFIED
				.getType()) {
			return "redirect:/../profile/index/face";
		} else {
			return "redirect:/../home";
		}
	}
}
