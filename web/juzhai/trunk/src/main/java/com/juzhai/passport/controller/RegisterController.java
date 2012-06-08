package com.juzhai.passport.controller;

import java.util.Locale;

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

import com.juzhai.common.bean.ActiveCodeType;
import com.juzhai.common.service.IActiveCodeService;
import com.juzhai.core.controller.BaseController;
import com.juzhai.core.exception.JuzhaiException;
import com.juzhai.core.exception.NeedLoginException;
import com.juzhai.core.web.AjaxResult;
import com.juzhai.core.web.session.UserContext;
import com.juzhai.passport.controller.form.RegisterForm;
import com.juzhai.passport.controller.form.ResetPwdForm;
import com.juzhai.passport.exception.PassportAccountException;
import com.juzhai.passport.exception.ProfileInputException;
import com.juzhai.passport.model.Passport;
import com.juzhai.passport.service.ILoginService;
import com.juzhai.passport.service.IPassportService;
import com.juzhai.passport.service.IProfileService;
import com.juzhai.passport.service.IRegisterService;
import com.juzhai.verifycode.service.IVerifyCodeService;

@Controller
@RequestMapping("passport")
public class RegisterController extends BaseController {

	@Autowired
	private IVerifyCodeService verifyCodeService;
	@Autowired
	private IRegisterService registerService;
	@Autowired
	private MessageSource messageSource;
	@Autowired
	private ILoginService loginService;
	@Autowired
	private IProfileService profileService;
	@Autowired
	private IActiveCodeService activeCodeService;
	@Autowired
	private IPassportService passportService;

	@RequestMapping(value = "/validate/nickexist", method = RequestMethod.POST)
	@ResponseBody
	public AjaxResult nicknameExist(HttpServletRequest request, String nickname) {
		nickname = StringUtils.trim(nickname);
		boolean exist = profileService.isExistNickname(nickname, 0);
		AjaxResult result = new AjaxResult();
		result.setResult(exist);
		if (exist) {
			result.setError(ProfileInputException.PROFILE_NICKNAME_IS_EXIST,
					messageSource);
		}
		return result;
	}

	@RequestMapping(value = "/validate/accountexist", method = RequestMethod.POST)
	@ResponseBody
	public AjaxResult accountExist(HttpServletRequest request, String account) {
		account = StringUtils.trim(account);
		boolean exist = registerService.existAccount(account);
		AjaxResult result = new AjaxResult();
		result.setResult(exist);
		if (exist) {
			result.setError(PassportAccountException.ACCOUNT_EXIST,
					messageSource);
		}
		return result;
	}

	@RequestMapping(value = "register", method = RequestMethod.GET)
	public String getRegister(HttpServletRequest request, Model model,
			RegisterForm registerForm) {
		UserContext context = (UserContext) request.getAttribute("context");
		if (context.hasLogin()) {
			return "redirect:/index";
		} else {
			String key = verifyCodeService.getVerifyCodeKey();
			registerForm.setVerifyKey(key);
			model.addAttribute("registerForm", registerForm);
			model.addAttribute("t", System.currentTimeMillis());
			return "web/register/register";
		}
	}

	@RequestMapping(value = "register", method = RequestMethod.POST)
	public String postRegister(HttpServletRequest request,
			HttpServletResponse response, Model model, RegisterForm registerForm) {
		UserContext context = (UserContext) request.getAttribute("context");
		long uid = 0L;
		if (context.hasLogin()) {
			return "redirect:/index";
		} else {
			try {
				if (!verifyCodeService.verify(registerForm.getVerifyKey(),
						registerForm.getVerifyCode())) {
					throw new PassportAccountException(
							PassportAccountException.VERIFY_CODE_ERROR);
				}
				uid = registerService.register(registerForm.getAccount(),
						registerForm.getNickname(), registerForm.getPwd(),
						registerForm.getConfirmPwd(),
						registerForm.getInviterUid());
			} catch (JuzhaiException e) {
				model.addAttribute("errorCode", e.getErrorCode());
				model.addAttribute("errorInfo", messageSource.getMessage(
						e.getErrorCode(), null, Locale.SIMPLIFIED_CHINESE));

				String key = verifyCodeService.getVerifyCodeKey();
				registerForm.setVerifyKey(key);
				model.addAttribute("registerForm", registerForm);
				model.addAttribute("t", System.currentTimeMillis());
				return "web/register/register";
			}
			loginService.autoLogin(request, response, uid);
			try {
				registerService.sendAccountMail(uid);
			} catch (PassportAccountException e) {
			}
			return "redirect:/home/guide";
		}
	}

	@RequestMapping(value = "/active", method = RequestMethod.GET)
	public String active(HttpServletRequest request, Model model, String code) {
		if (registerService.activeAccount(code)) {
			// 激活成功
			return "web/register/account/active_success";
		} else {
			// 链接失效
			return "web/register/account/active_error";
		}
	}

	@RequestMapping(value = "account", method = RequestMethod.GET)
	public String account(HttpServletRequest request, Model model)
			throws NeedLoginException {
		UserContext context = checkLoginForWeb(request);
		try {
			// 设置账号
			if (!registerService.hasAccount(context.getUid())) {
				return "web/register/account/set_account";
			}
			// 激活邮箱
			else if (!registerService.hasActiveEmail(context.getUid())) {
				model.addAttribute("account",
						passportService.getPassportByUid(context.getUid())
								.getLoginName());
				return "web/register/account/active_account";
			}
			// 修改密码
			else {
				model.addAttribute("account",
						passportService.getPassportByUid(context.getUid())
								.getLoginName());
				return "web/register/account/modify_pwd";
			}
		} catch (PassportAccountException e) {
			return error_404;
		}
	}

	@RequestMapping(value = "setaccount", method = RequestMethod.POST)
	public String setAccount(HttpServletRequest request, Model model,
			RegisterForm registerForm) throws NeedLoginException {
		UserContext context = checkLoginForWeb(request);
		try {
			registerService.setAccount(context.getUid(),
					registerForm.getAccount(), registerForm.getPwd(),
					registerForm.getConfirmPwd());
		} catch (PassportAccountException e) {
			model.addAttribute("errorCode", e.getErrorCode());
			model.addAttribute("errorInfo", messageSource.getMessage(
					e.getErrorCode(), null, Locale.SIMPLIFIED_CHINESE));
			model.addAttribute("account", registerForm.getAccount());
			return "web/register/account/set_account";
		}
		// 发邮件
		try {
			registerService.sendAccountMail(context.getUid());
		} catch (PassportAccountException e) {
		}
		return "redirect:/passport/account";
	}

	@RequestMapping(value = "modifypwd", method = RequestMethod.POST)
	@ResponseBody
	public AjaxResult modifyPwd(HttpServletRequest request, Model model,
			String oldPwd, String newPwd, String confirmPwd)
			throws NeedLoginException {
		UserContext context = checkLoginForWeb(request);
		AjaxResult result = new AjaxResult();
		try {
			registerService.modifyPwd(context.getUid(), oldPwd, newPwd,
					confirmPwd);
		} catch (PassportAccountException e) {
			result.setError(e.getErrorCode(), messageSource);
		}
		return result;
	}

	@RequestMapping(value = "getbackpwd", method = RequestMethod.GET)
	public String getbackpwd(HttpServletRequest request, Model model) {
		UserContext context = (UserContext) request.getAttribute("context");
		if (context.hasLogin()) {
			return "redirect:/index";
		}
		return "web/register/reset/getback_pwd";
	}

	@RequestMapping(value = "getbackpwd", method = RequestMethod.POST)
	public String resetmail(HttpServletRequest request, Model model,
			String account) {
		UserContext context = (UserContext) request.getAttribute("context");
		if (context.hasLogin()) {
			return "redirect:/index";
		}
		account = StringUtils.trim(account);
		// 发送邮件
		registerService.sendResetPwdMail(account);
		model.addAttribute("account", account);
		return "web/register/reset/getback_mail_success";
	}

	@RequestMapping(value = "resetpwd", method = RequestMethod.GET)
	public String getResetPwd(HttpServletRequest request, Model model,
			ResetPwdForm resetPwdForm) {
		long uid = activeCodeService.check(resetPwdForm.getToken(),
				ActiveCodeType.RESET_PWD);
		if (uid <= 0) {
			// 链接无效，请重发邮件
			return "web/register/reset/reset_error";
		} else {
			// 进入重设页面
			Passport passport = passportService.getPassportByUid(uid);
			if (null == passport) {
				return error_500;
			}
			resetPwdForm.setUid(uid);
			model.addAttribute("resetPwdForm", resetPwdForm);
			model.addAttribute("account", passport.getLoginName());
			return "web/register/reset/reset";
		}
	}

	@RequestMapping(value = "resetpwd", method = RequestMethod.POST)
	public String postResetPwd(HttpServletRequest request, Model model,
			ResetPwdForm resetPwdForm) {
		try {
			registerService.resetPwd(resetPwdForm.getUid(),
					resetPwdForm.getPwd(), resetPwdForm.getConfirmPwd(),
					resetPwdForm.getToken());
		} catch (PassportAccountException e) {
			model.addAttribute("errorCode", e.getErrorCode());
			model.addAttribute("errorInfo", messageSource.getMessage(
					e.getErrorCode(), null, Locale.SIMPLIFIED_CHINESE));
			if (StringUtils.equals(e.getErrorCode(),
					PassportAccountException.PWD_LENGTH_ERROR)
					|| StringUtils.equals(e.getErrorCode(),
							PassportAccountException.CONFIRM_PWD_ERROR)) {
				// reset页面
				model.addAttribute("resetPwdForm", resetPwdForm);
				return "web/register/reset/reset";
			} else {
				// 链接无效，请重发邮件
				return "web/register/reset/reset_error";
			}
		}
		return "web/register/reset/reset_success";
	}

	@ResponseBody
	@RequestMapping(value = "/sendactive", method = RequestMethod.POST)
	public AjaxResult sendActiveMail(HttpServletRequest request, Model model)
			throws NeedLoginException {
		UserContext context = checkLoginForWeb(request);
		AjaxResult result = new AjaxResult();
		try {
			registerService.sendAccountMail(context.getUid());
		} catch (PassportAccountException e) {
			result.setError(e.getErrorCode(), messageSource);
		}
		return result;
	}

	private String getMailDomain(String email) {
		if (email.endsWith("@126.com"))
			return "http://email.126.com";
		if (email.endsWith("@163.com"))
			return "http://email.163.com";
		if (email.endsWith("@qq.com"))
			return "https://mail.qq.com";
		if (email.endsWith("@sina.com.cn") || email.endsWith("@sina.com"))
			return "http://mail.sina.com.cn";
		if (email.endsWith("@sohu.com"))
			return "http://mail.sohu.com";
		if (email.endsWith("@hotmail.com"))
			return "http://login.live.com";
		if (email.endsWith("@gmail.com"))
			return "http://www.gmail.com";
		if (email.endsWith("@yahoo.com.cn") || email.endsWith("@yahoo.com"))
			return "http://mail.cn.yahoo.com";
		return null;
	}
}
