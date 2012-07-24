package com.juzhai.mobile.passport.controller;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.juzhai.core.bean.UseLevel;
import com.juzhai.core.controller.BaseController;
import com.juzhai.core.exception.NeedLoginException;
import com.juzhai.core.exception.UploadImageException;
import com.juzhai.core.web.AjaxResult;
import com.juzhai.core.web.session.UserContext;
import com.juzhai.lab.controller.form.ProfileMForm;
import com.juzhai.mobile.passport.controller.viewHelper.IUserMViewHelper;
import com.juzhai.passport.exception.ProfileInputException;
import com.juzhai.passport.model.UserGuide;
import com.juzhai.passport.service.IPassportService;
import com.juzhai.passport.service.IProfileService;
import com.juzhai.passport.service.IUserGuideService;

@Controller
@RequestMapping("mobile/profile")
public class ProfileMController extends BaseController {

	@Autowired
	private IProfileService profileService;
	@Autowired
	private MessageSource messageSource;
	@Autowired
	private IUserGuideService userGuideService;
	@Autowired
	private IUserMViewHelper userMViewHelper;
	@Autowired
	private IPassportService passportService;

	@RequestMapping(value = "/validate/nickexist", method = RequestMethod.POST)
	@ResponseBody
	public AjaxResult nicknameExist(HttpServletRequest request, String nickname)
			throws NeedLoginException {
		UserContext context = checkLoginForWeb(request);
		nickname = StringUtils.trim(nickname);
		boolean exist = profileService.isExistNickname(nickname,
				context.getUid());
		AjaxResult result = new AjaxResult();
		result.setResult(exist);
		if (exist) {
			result.setError(ProfileInputException.PROFILE_NICKNAME_IS_EXIST,
					messageSource);
		}
		return result;
	}

	@RequestMapping(value = "/save", method = RequestMethod.POST)
	@ResponseBody
	public AjaxResult saveProfile(HttpServletRequest request,
			ProfileMForm profileForm) throws NeedLoginException {
		UserContext context = checkLoginForWeb(request);
		AjaxResult result = new AjaxResult();
		try {
			profileService.updateLogoAndProfile(context.getUid(), profileForm);
			result.setResult(userMViewHelper.createUserMView(context,
					profileService.getProfileCacheByUid(context.getUid()),
					userGuideService.isCompleteGuide(context.getUid())));
		} catch (ProfileInputException e) {
			result.setError(e.getErrorCode(), messageSource);
		} catch (UploadImageException e) {
			result.setError(e.getErrorCode(), messageSource);
		}
		return result;
	}

	@RequestMapping(value = "/guide", method = RequestMethod.POST)
	@ResponseBody
	public AjaxResult guideProfile(HttpServletRequest request,
			ProfileMForm profileForm) throws NeedLoginException {
		UserContext context = checkLoginForWeb(request);
		AjaxResult result = new AjaxResult();
		try {
			profileService.updateLogoAndProfile(context.getUid(), profileForm);
			UserGuide userGuide = userGuideService.getUserGuide(context
					.getUid());
			if (userGuide == null) {
				userGuideService.createAndCompleteGuide(context.getUid());
			} else {
				userGuideService.completeGuide(context.getUid());
			}
			// 完成引导后提升用户使用等级
			passportService.setUseLevel(context.getUid(), UseLevel.Level1);
			result.setResult(userMViewHelper
					.createUserMView(context, profileService
							.getProfileCacheByUid(context.getUid()), true));
		} catch (ProfileInputException e) {
			result.setError(e.getErrorCode(), messageSource);
		} catch (UploadImageException e) {
			result.setError(e.getErrorCode(), messageSource);
		}
		return result;
	}
}
