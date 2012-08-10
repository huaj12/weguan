package com.juzhai.mobile.passport.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.juzhai.core.controller.BaseController;
import com.juzhai.core.exception.JuzhaiException;
import com.juzhai.core.web.AjaxResult;
import com.juzhai.core.web.session.UserContext;
import com.juzhai.mobile.passport.controller.viewHelper.IUserMViewHelper;
import com.juzhai.passport.controller.form.RegisterForm;
import com.juzhai.passport.exception.PassportAccountException;
import com.juzhai.passport.service.ILoginService;
import com.juzhai.passport.service.IProfileService;
import com.juzhai.passport.service.IRegisterService;

@Controller
@RequestMapping("m/passport")
public class RegisterMController extends BaseController {

	@Autowired
	private MessageSource messageSource;
	@Autowired
	private IRegisterService registerService;
	@Autowired
	private ILoginService loginService;
	@Autowired
	private IProfileService profileService;
	@Autowired
	private IUserMViewHelper userMViewHelper;

	@RequestMapping(value = "/register", method = RequestMethod.POST)
	@ResponseBody
	public AjaxResult register(HttpServletRequest request,
			HttpServletResponse response, RegisterForm registerForm) {
		UserContext context = (UserContext) request.getAttribute("context");
		AjaxResult result = new AjaxResult();
		if (context.hasLogin()) {
			result.setError(JuzhaiException.ILLEGAL_OPERATION, messageSource);
			return result;
		}
		try {
			long uid = registerService.register(registerForm.getAccount(),
					registerForm.getNickname(), registerForm.getPwd(),
					registerForm.getConfirmPwd(), registerForm.getInviterUid());
			loginService.autoLogin(request, response, uid, true);
			result.setResult(userMViewHelper.createUserMView(context,
					profileService.getProfileCacheByUid(uid), false));
			try {
				registerService.sendAccountMail(uid);
			} catch (PassportAccountException e) {
			}
		} catch (JuzhaiException e) {
			result.setError(e.getErrorCode(), messageSource);
		}
		return result;
	}
}
