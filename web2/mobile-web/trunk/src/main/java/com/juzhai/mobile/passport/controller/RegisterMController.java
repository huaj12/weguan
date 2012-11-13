package com.juzhai.mobile.passport.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.juzhai.core.bean.DeviceName;
import com.juzhai.core.controller.BaseController;
import com.juzhai.core.exception.JuzhaiException;
import com.juzhai.core.web.AjaxResult;
import com.juzhai.core.web.session.UserContext;
import com.juzhai.core.web.util.HttpRequestUtil;
import com.juzhai.mobile.passport.controller.form.RegisterMForm;
import com.juzhai.mobile.passport.controller.viewHelper.IUserMViewHelper;
import com.juzhai.passport.exception.IosDeviceException;
import com.juzhai.passport.exception.PassportAccountException;
import com.juzhai.passport.service.IIosDeviceRemoteService;
import com.juzhai.passport.service.ILoginService;
import com.juzhai.passport.service.IProfileRemoteService;
import com.juzhai.passport.service.IRegisterRemoteService;

@Controller
@RequestMapping("passport")
public class RegisterMController extends BaseController {

	@Autowired
	private MessageSource messageSource;
	@Autowired
	private IRegisterRemoteService registerRemoteService;
	@Autowired
	private ILoginService loginService;
	@Autowired
	private IProfileRemoteService profileRemoteService;
	@Autowired
	private IUserMViewHelper userMViewHelper;
	@Autowired
	private IIosDeviceRemoteService iosDeviceRemoteService;

	@RequestMapping(value = "/register", method = RequestMethod.POST)
	@ResponseBody
	public AjaxResult register(HttpServletRequest request,
			HttpServletResponse response, RegisterMForm registerMForm) {
		UserContext context = (UserContext) request.getAttribute("context");
		AjaxResult result = new AjaxResult();
		if (context.hasLogin()) {
			result.setError(JuzhaiException.ILLEGAL_OPERATION, messageSource);
			return result;
		}
		try {
			DeviceName deviceName = HttpRequestUtil.getClientName(context);
			if (deviceName == null) {
				result.setError(JuzhaiException.SYSTEM_ERROR, messageSource);
				return result;
			}
			long uid = registerRemoteService.register(
					registerMForm.getAccount(), registerMForm.getNickname(),
					registerMForm.getPwd(), registerMForm.getConfirmPwd(),
					registerMForm.getInviterUid(), deviceName);
			loginService.autoLogin(request, response, uid, true);
			result.setResult(userMViewHelper.createUserMView(context,
					profileRemoteService.getProfileCacheByUid(uid), false));
			try {
				registerRemoteService.sendAccountMail(uid);
			} catch (PassportAccountException e) {
			}
		} catch (JuzhaiException e) {
			result.setError(e.getErrorCode(), messageSource);
		}
		return result;
	}

	@RequestMapping(value = "getbackpwd", method = RequestMethod.POST)
	@ResponseBody
	public AjaxResult resetmail(HttpServletRequest request, Model model,
			String account) {
		account = StringUtils.trim(account);
		// 发送邮件
		registerRemoteService.sendResetPwdMail(account);
		return new AjaxResult();
	}

	@RequestMapping(value = "register/device", method = RequestMethod.POST)
	@ResponseBody
	public AjaxResult registerDevice(HttpServletRequest request, Model model,
			String deviceToken, Long uid) {
		AjaxResult result = new AjaxResult();
		try {
			iosDeviceRemoteService.registerDevice(deviceToken, uid);
		} catch (IosDeviceException e) {
			result.setError(e.getErrorCode(), messageSource);
		}
		return result;
	}
}
