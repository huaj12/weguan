package com.juzhai.passport.controller.website;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.juzhai.act.exception.UploadImageException;
import com.juzhai.core.controller.BaseController;
import com.juzhai.core.exception.NeedLoginException;
import com.juzhai.core.web.AjaxResult;
import com.juzhai.core.web.session.UserContext;
import com.juzhai.passport.InitData;
import com.juzhai.passport.controller.form.SettingForm;
import com.juzhai.passport.exception.ProfileInputException;
import com.juzhai.passport.model.City;
import com.juzhai.passport.model.Profession;
import com.juzhai.passport.model.Profile;
import com.juzhai.passport.model.Province;
import com.juzhai.passport.model.Town;
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
		assembleCiteys(model);
		List<String> list = new ArrayList<String>(4);
		if (StringUtils.isNotEmpty(profile.getFeature())) {
			for (String s : profile.getFeature().split(",")) {
				list.add(s);
			}
		} else {
			// TODO (review)确认是否有bug
			for (int i = 0; i < 3; i++) {
				list.add("");
			}
		}
		model.addAttribute("profile", profile);
		model.addAttribute("featureList", list);
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
	@ResponseBody
	public AjaxResult addActImage(HttpServletRequest request, Model model,
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
		return result;
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
		}
		return face(request, model);
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
	// TODO (done) 参数过多封装form，并且controller里注意哪些代码应该放入service
	public AjaxResult setting(HttpServletRequest request, Model model,SettingForm settingForm)
			throws NeedLoginException {
		AjaxResult ajaxResult = new AjaxResult();
		UserContext context = null;
		// TODO (done) js里遇到401应该如何处理，参加我在js里写的代码
		context = checkLoginForWeb(request);
		Profile profile = new Profile();
		profile.setProvince(settingForm.getProvince());
		profile.setCity(settingForm.getCity());
		profile.setTown(settingForm.getTown());
		profile.setBirthYear(settingForm.getBirthYear());
		profile.setBirthMonth(settingForm.getBirthMonth());
		profile.setBirthDay(settingForm.getBirthDay());
		Boolean birthSecret=settingForm.getBirthSecret();
		if (settingForm.getBirthSecret() == null) {
			birthSecret = false;
		}
		Long professionId=settingForm.getProfessionId();
		if (professionId == null) {
			professionId = 0l;
		}
		profile.setBirthSecret(birthSecret);
		profile.setProfession(settingForm.getProfession());
		profile.setProfessionId(professionId);
		// TODO (review) 用户输入的内容就存在逗号怎么办？
		profile.setFeature(settingForm.getFeature());
		profile.setUid(context.getUid());
		try {
			profileService.updateProfile(profile);
		} catch (ProfileInputException e) {
			ajaxResult.setError(e.getErrorCode(), messageSource);
		}
		return ajaxResult;
	}

}
