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
import org.springframework.web.bind.annotation.ResponseBody;

import com.juzhai.act.bean.SuitAge;
import com.juzhai.act.bean.SuitGender;
import com.juzhai.act.bean.SuitStatus;
import com.juzhai.act.model.Category;
import com.juzhai.act.service.IUploadImageService;
import com.juzhai.core.controller.BaseController;
import com.juzhai.core.exception.NeedLoginException;
import com.juzhai.core.web.AjaxResult;
import com.juzhai.core.web.session.UserContext;
import com.juzhai.passport.InitData;
import com.juzhai.passport.bean.ProfileCache;
import com.juzhai.passport.exception.ProfileInputException;
import com.juzhai.passport.model.City;
import com.juzhai.passport.model.Profession;
import com.juzhai.passport.model.Profile;
import com.juzhai.passport.model.Province;
import com.juzhai.passport.model.Town;
import com.juzhai.passport.service.IProfileService;

@Controller
@RequestMapping(value = "profile")
public class ProfileContrller extends BaseController {
	@Autowired
	IProfileService profileService;
	@Autowired
	private MessageSource messageSource;
	@Autowired
	private IUploadImageService uploadImageService;

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
			for (int i = 0; i < 3; i++) {
				list.add("");
			}
		}
		model.addAttribute("profile", profile);
		model.addAttribute("featurelist", list);
		model.addAttribute("page", "index");
		return "web/profile/setting";
	}
	
	@RequestMapping(value = "/index/face", method = RequestMethod.GET)
	public String face(HttpServletRequest request, Model model)throws NeedLoginException {
		UserContext context = checkLoginForWeb(request);
		ProfileCache profile=profileService.getProfileCacheByUid(context.getUid());
		model.addAttribute("profile",profile);
		model.addAttribute("page", "face");
		return "web/profile/face";
	}
	@RequestMapping(value = "/upload/logo/cut", method = RequestMethod.POST)
	@ResponseBody
	public AjaxResult cut(HttpServletRequest request,String logo,Integer x,Integer y,Integer height,Integer width, Integer gender,
			Model model) {
		AjaxResult ajaxResult = new AjaxResult();
		UserContext context = null;
		try {
			context = checkLoginForWeb(request);
		} catch (NeedLoginException e1) {
			ajaxResult.setErrorInfo(messageSource.getMessage(
					NeedLoginException.IS_NOT_LOGIN, null,
					Locale.SIMPLIFIED_CHINESE));
			ajaxResult.setSuccess(false);
			return ajaxResult;
		}
		String fileName=uploadImageService.cutUserImage(logo, context.getUid(),x,y,height,width);
		if(StringUtils.isEmpty(fileName)){
			ajaxResult.setSuccess(false);
			return ajaxResult;
		}
		try {
			profileService.setUserLogo(logo, context.getUid());
		} catch (ProfileInputException e) {
			ajaxResult.setError(e.getErrorCode(), messageSource);
			return ajaxResult;
		}
		ajaxResult.setSuccess(true);
		return ajaxResult;
	}
	

	@RequestMapping(value = "/setting/gender", method = RequestMethod.POST)
	@ResponseBody
	public AjaxResult settingGender(HttpServletRequest request, Integer gender,
			Model model) {
		AjaxResult ajaxResult = new AjaxResult();
		UserContext context = null;
		;
		try {
			context = checkLoginForWeb(request);
		} catch (NeedLoginException e1) {
			ajaxResult.setErrorInfo(messageSource.getMessage(
					NeedLoginException.IS_NOT_LOGIN, null,
					Locale.SIMPLIFIED_CHINESE));
			ajaxResult.setSuccess(false);
			return ajaxResult;
		}
		try {
			profileService.setGender(context.getUid(), gender);
		} catch (ProfileInputException e) {
			ajaxResult.setError(e.getErrorCode(), messageSource);
			return ajaxResult;
		}
		ajaxResult.setSuccess(true);
		return ajaxResult;
	}

	@RequestMapping(value = "/setting/nickname", method = RequestMethod.POST)
	@ResponseBody
	public AjaxResult settingNickname(HttpServletRequest request,
			String nickName, Model model) {
		AjaxResult ajaxResult = new AjaxResult();
		UserContext context = null;
		;
		try {
			context = checkLoginForWeb(request);
		} catch (NeedLoginException e1) {
			ajaxResult.setErrorInfo(messageSource.getMessage(
					NeedLoginException.IS_NOT_LOGIN, null,
					Locale.SIMPLIFIED_CHINESE));
			ajaxResult.setSuccess(false);
			return ajaxResult;
		}
		try {
			profileService.setNickName(context.getUid(), nickName);
		} catch (ProfileInputException e) {
			ajaxResult.setError(e.getErrorCode(), messageSource);
			return ajaxResult;
		}
		ajaxResult.setSuccess(true);
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
		;
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
		profile.setFeature(feature);
		try {
			profileService.updateProfile(profile, context.getUid());
		} catch (ProfileInputException e) {
			ajaxResult.setError(e.getErrorCode(), messageSource);
			return ajaxResult;
		}
		ajaxResult.setSuccess(true);
		return ajaxResult;
	}

	private void assembleCiteys(Model model) {
		List<City> citys = new ArrayList<City>();
		List<Province> provinces = new ArrayList<Province>();
		List<Town> towns = new ArrayList<Town>();
		List<Profession> professions = new ArrayList<Profession>();
		for (Entry<Long, City> entry : com.juzhai.passport.InitData.CITY_MAP
				.entrySet()) {
			citys.add(entry.getValue());
		}
		for (Entry<Long, Province> entry : com.juzhai.passport.InitData.PROVINCE_MAP
				.entrySet()) {
			provinces.add(entry.getValue());
		}
		for (Entry<Long, Town> entry : com.juzhai.passport.InitData.TOWN_MAP
				.entrySet()) {
			towns.add(entry.getValue());
		}
		for (Entry<Long, Profession> entry : InitData.PROFESSION_MAP.entrySet()) {
			professions.add(entry.getValue());
		}

		model.addAttribute("towns", towns);
		model.addAttribute("citys", citys);
		model.addAttribute("provinces", provinces);
		model.addAttribute("years", InitData.YEARS);
		model.addAttribute("months", InitData.MONTHS);
		model.addAttribute("days", InitData.DAYS);
		model.addAttribute("professions", professions);
	}
}
