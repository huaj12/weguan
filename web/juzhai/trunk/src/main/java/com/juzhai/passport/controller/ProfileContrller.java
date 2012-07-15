package com.juzhai.passport.controller;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.juzhai.core.controller.BaseController;
import com.juzhai.core.exception.NeedLoginException;
import com.juzhai.core.exception.UploadImageException;
import com.juzhai.core.web.AjaxResult;
import com.juzhai.core.web.session.UserContext;
import com.juzhai.passport.InitData;
import com.juzhai.passport.controller.form.SettingForm;
import com.juzhai.passport.exception.ProfileInputException;
import com.juzhai.passport.model.Profile;
import com.juzhai.passport.service.IProfileImageService;
import com.juzhai.passport.service.IProfileService;

@Controller
@RequestMapping(value = "profile")
public class ProfileContrller extends BaseController {
	@Autowired
	private IProfileService profileService;
	@Autowired
	private MessageSource messageSource;
	@Autowired
	private IProfileImageService profileImageService;

	@RequestMapping(value = "/index", method = RequestMethod.GET)
	public String index(HttpServletRequest request, Model model)
			throws NeedLoginException {
		UserContext context = checkLoginForWeb(request);
		Profile profile = profileService.getProfile(context.getUid());
		assembleCitys(model);
		model.addAttribute("professions", InitData.PROFESSION_MAP.values());
		model.addAttribute("profile", profile);
		return "web/profile/setting";
	}

	@RequestMapping(value = "/index/face", method = RequestMethod.GET)
	public String face(HttpServletRequest request, Model model)
			throws NeedLoginException {
		UserContext context = checkLoginForWeb(request);
		queryProfile(context.getUid(), model);
		return "web/profile/face";
	}

	@RequestMapping(value = "/logo/upload", method = RequestMethod.POST)
	public String addActImage(HttpServletRequest request, Model model,
			@RequestParam("profileLogo") MultipartFile profileLogo) {
		AjaxResult result = new AjaxResult();
		try {
			UserContext context = checkLoginForWeb(request);
			try {
				String[] urls = profileImageService.uploadLogo(
						context.getUid(), profileLogo);
				result.setResult(urls);
			} catch (UploadImageException e) {
				result.setError(e.getErrorCode(), messageSource);
			}
		} catch (NeedLoginException e) {
			result.setError(NeedLoginException.IS_NOT_LOGIN, messageSource);
		}
		model.addAttribute("result", result.toJson());
		return "web/common/ajax/ajax_result";
	}

	@RequestMapping(value = "/logo/cut", method = RequestMethod.POST)
	public String cut(HttpServletRequest request, String filePath,
			@RequestParam(defaultValue = "0") int scaledW,
			@RequestParam(defaultValue = "0") int scaledH,
			@RequestParam(defaultValue = "0") Integer x,
			@RequestParam(defaultValue = "0") Integer y,
			@RequestParam(defaultValue = "0") Integer h,
			@RequestParam(defaultValue = "0") Integer w, Model model)
			throws NeedLoginException {
		UserContext context = checkLoginForWeb(request);
		try {
			profileService.updateLogo(context.getUid(), filePath, scaledW,
					scaledH, x, y, w, h);
		} catch (UploadImageException e) {
			model.addAttribute("errorCode", e.getErrorCode());
			model.addAttribute("error", messageSource.getMessage(
					e.getErrorCode(), null, Locale.SIMPLIFIED_CHINESE));
			return face(request, model);
		}
		return "redirect:/home";
	}

	@RequestMapping(value = "/setting/gender", method = RequestMethod.POST)
	@ResponseBody
	public AjaxResult settingGender(HttpServletRequest request, Integer gender,
			Model model) throws NeedLoginException {
		UserContext context = checkLoginForWeb(request);
		AjaxResult ajaxResult = new AjaxResult();
		try {
			profileService.setGender(context.getUid(), gender);
		} catch (ProfileInputException e) {
			ajaxResult.setError(e.getErrorCode(), messageSource);
		}
		return ajaxResult;
	}

	@RequestMapping(value = "/setting/nickname", method = RequestMethod.POST)
	@ResponseBody
	public AjaxResult settingNickname(HttpServletRequest request,
			String nickName, Model model) throws NeedLoginException {
		UserContext context = checkLoginForWeb(request);
		AjaxResult ajaxResult = new AjaxResult();
		try {
			profileService.setNickName(context.getUid(), nickName);
		} catch (ProfileInputException e) {
			ajaxResult.setError(e.getErrorCode(), messageSource);
		}
		return ajaxResult;
	}

	@RequestMapping(value = "/setting", method = RequestMethod.POST)
	@ResponseBody
	public AjaxResult setting(HttpServletRequest request, Model model,
			SettingForm settingForm) throws NeedLoginException {
		AjaxResult ajaxResult = new AjaxResult();
		UserContext context = checkLoginForWeb(request);
		Profile profile = new Profile();
		profile.setUid(context.getUid());
		profile.setProvince(settingForm.getProvince());
		profile.setCity(settingForm.getCity());
		profile.setTown(settingForm.getTown());
		profile.setBirthYear(settingForm.getBirthYear());
		profile.setBirthMonth(settingForm.getBirthMonth());
		profile.setBirthDay(settingForm.getBirthDay());
		Boolean birthSecret = settingForm.getBirthSecret();
		if (settingForm.getBirthSecret() == null) {
			birthSecret = false;
		}
		Long professionId = settingForm.getProfessionId();
		if (professionId == null) {
			professionId = 0l;
		}
		profile.setBirthSecret(birthSecret);
		profile.setProfession(settingForm.getProfession());
		profile.setProfessionId(professionId);
		profile.setFeature(settingForm.getFeature());

		profile.setBlog(settingForm.getBlog());
		profile.setHeight(settingForm.getHeight());
		profile.setBloodType(settingForm.getBloodType());
		profile.setEducation(settingForm.getEducation());
		profile.setHouse(settingForm.getHouse());
		profile.setCar(settingForm.getCar());
		profile.setHome(settingForm.getHome());
		profile.setMinMonthlyIncome(settingForm.getMinMonthlyIncome());
		profile.setMaxMonthlyIncome(settingForm.getMaxMonthlyIncome());
		try {
			profileService.updateProfile(profile);
		} catch (ProfileInputException e) {
			ajaxResult.setError(e.getErrorCode(), messageSource);
		}
		return ajaxResult;
	}

	@RequestMapping(value = "/wait/rescue/user", method = RequestMethod.GET)
	public String waitRescueUser(HttpServletRequest request, Model model)
			throws NeedLoginException {
		UserContext context = checkLoginForWeb(request);
		// List<String> genders = userPreferenceService.getUserAnswer(
		// context.getUid(),
		// SiftTypePreference.GENDER.getPreferenceId());
		// Integer gender = null;
		// if (genders != null && genders.size() == 1) {
		// String sex = genders.get(0);
		// if (StringUtils.equals(sex, "1")) {
		// gender = 1;
		// } else if (StringUtils.equals(sex, "0")) {
		// gender = 0;
		// }
		// }
		model.addAttribute("profiles",
				profileService.waitRescueUser(0, context.getUid()));
		return "web/post/wait_rescue_user";
	}
}
