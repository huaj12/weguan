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
		// TODO 直接在页面上用c:set
		// model.addAttribute("page", "index");
		return "web/profile/setting";
	}

	@RequestMapping(value = "/index/face", method = RequestMethod.GET)
	public String face(HttpServletRequest request, Model model)
			throws NeedLoginException {
		UserContext context = checkLoginForWeb(request);
		queryProfile(context.getUid(), model);
		// model.addAttribute("page", "face");
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
	public AjaxResult setting(HttpServletRequest request, Model model,
			String feature, String profession, Long professionId,
			Integer birthYear, Integer birthMonth, Integer birthDay,
			Boolean birthSecret, Long province, Long city, Long town) {
		AjaxResult ajaxResult = new AjaxResult();
		UserContext context = null;
		// TODO (review)“;”复制代码也要注意复制的对不对
		;
		// TODO (review)ajax也是通过filter来抛出未登录的异常，js里通过401的返回值处理
		try {
			context = checkLoginForWeb(request);
		} catch (NeedLoginException e1) {
			ajaxResult.setErrorInfo(messageSource.getMessage(
					NeedLoginException.IS_NOT_LOGIN, null,
					Locale.SIMPLIFIED_CHINESE));
			ajaxResult.setSuccess(false);
			return ajaxResult;
		}
		Profile profile = new Profile();
		profile.setProvince(province);
		profile.setCity(city);
		// TODO (review) town=-1表示没有选择 =0表示选择其他 >0表示具体的区，是否有注意到这点？
		profile.setTown(town);
		profile.setBirthYear(birthYear);
		profile.setBirthMonth(birthMonth);
		profile.setBirthDay(birthDay);
		if (birthSecret == null) {
			birthSecret = false;
		}
		if (professionId == null) {
			professionId = 0l;
		}
		profile.setBirthSecret(birthSecret);
		profile.setProfession(profession);
		profile.setProfessionId(professionId);
		// TODO (review) 用户输入的内容就存在逗号怎么办？
		profile.setFeature(feature);
		try {
			profileService.updateProfile(profile, context.getUid());
		} catch (ProfileInputException e) {
			ajaxResult.setError(e.getErrorCode(), messageSource);
			return ajaxResult;
		}
		// TODO (review) new完之后默认就是true，所以这里不需要赋值，另外catch里的return可以删掉，少一行代码
		ajaxResult.setSuccess(true);
		return ajaxResult;
	}

	private void assembleCiteys(Model model) {
		// TODO (review) 为什么要循环？values()不行吗
		List<City> citys = new ArrayList<City>();
		List<Province> provinces = new ArrayList<Province>();
		List<Town> towns = new ArrayList<Town>();
		List<Profession> professions = new ArrayList<Profession>();
		for (Entry<Long, City> entry : InitData.CITY_MAP.entrySet()) {
			citys.add(entry.getValue());
		}
		for (Entry<Long, Province> entry : InitData.PROVINCE_MAP.entrySet()) {
			provinces.add(entry.getValue());
		}
		for (Entry<Long, Town> entry : InitData.TOWN_MAP.entrySet()) {
			towns.add(entry.getValue());
		}
		for (Entry<Long, Profession> entry : InitData.PROFESSION_MAP.entrySet()) {
			professions.add(entry.getValue());
		}

		model.addAttribute("towns", towns);
		model.addAttribute("citys", citys);
		model.addAttribute("provinces", provinces);
		model.addAttribute("professions", professions);
		model.addAttribute("years", InitData.YEARS);
		model.addAttribute("months", InitData.MONTHS);
		model.addAttribute("days", InitData.DAYS);
	}
}
