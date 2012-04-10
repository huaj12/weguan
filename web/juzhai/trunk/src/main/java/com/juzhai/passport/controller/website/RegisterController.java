package com.juzhai.passport.controller.website;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

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
import com.juzhai.passport.service.ILoginService;
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
	private IActiveCodeService activeCodeService;

	@RequestMapping(value = "register", method = RequestMethod.GET)
	public String getRegister(HttpServletRequest request, Model model,
			RegisterForm registerForm) {
		UserContext context = (UserContext) request.getAttribute("context");
		if (context.hasLogin()) {
			return "redirect:/home";
		} else {
			String key = verifyCodeService.getVerifyCodeKey();
			registerForm.setVerifyKey(key);
			model.addAttribute("registerForm", registerForm);
			return "web/register/register";
		}
	}

	@RequestMapping(value = "register", method = RequestMethod.POST)
	public String postRegister(HttpServletRequest request, Model model,
			RegisterForm registerForm) {
		UserContext context = (UserContext) request.getAttribute("context");
		long uid = 0L;
		if (context.hasLogin()) {
			return "redirect:/home";
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
				model.addAttribute("error", messageSource.getMessage(
						e.getErrorCode(), null, Locale.SIMPLIFIED_CHINESE));
				model.addAttribute("registerForm", registerForm);
				return "web/register/register";
			}
			loginService.autoLogin(request, uid);
			// TODO 发邮件
			return "redirect:/home/guide";
		}
	}

	@RequestMapping(value = "account", method = RequestMethod.GET)
	public String account(HttpServletRequest request, Model model)
			throws NeedLoginException {
		UserContext context = checkLoginForWeb(request);
		try {
			// 设置账号
			if (!registerService.hasAccount(context.getUid())) {
				return "";
			}
			// 激活邮箱
			else if (registerService.hasActiveEmail(context.getUid())) {
				return "";
			}
			// 修改密码
			else {
				return "";
			}
		} catch (PassportAccountException e) {
			return error_404;
		}
	}

	@RequestMapping(value = "setaccount", method = RequestMethod.GET)
	public String setAccount(HttpServletRequest request, Model model,
			RegisterForm registerForm) throws NeedLoginException {
		UserContext context = checkLoginForWeb(request);
		try {
			registerService.setAccount(context.getUid(),
					registerForm.getAccount(), registerForm.getPwd(),
					registerForm.getConfirmPwd());
		} catch (PassportAccountException e) {
			model.addAttribute("errorCode", e.getErrorCode());
			model.addAttribute("error", messageSource.getMessage(
					e.getErrorCode(), null, Locale.SIMPLIFIED_CHINESE));
			model.addAttribute("registerForm", registerForm);
			return "";
		}
		// TODO 发邮件
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

	@RequestMapping(value = "resetPwd", method = RequestMethod.GET)
	public String getResetPwd(HttpServletRequest request, Model model,
			ResetPwdForm resetPwdForm) {
		if (!activeCodeService.check(resetPwdForm.getUid(),
				resetPwdForm.getToken(), ActiveCodeType.RESET_PWD)) {
			// 链接无效，请重发邮件
			return "";
		} else {
			// 进入重设页面
			model.addAttribute("resetPwdForm", resetPwdForm);
			return "";
		}
	}

	@RequestMapping(value = "resetPwd", method = RequestMethod.POST)
	public String postResetPwd(HttpServletRequest request, Model model,
			ResetPwdForm resetPwdForm) {
		try {
			registerService.resetPwd(resetPwdForm.getUid(),
					resetPwdForm.getPwd(), resetPwdForm.getConfirmPwd(),
					resetPwdForm.getToken());
		} catch (PassportAccountException e) {
			model.addAttribute("errorCode", e.getErrorCode());
			model.addAttribute("error", messageSource.getMessage(
					e.getErrorCode(), null, Locale.SIMPLIFIED_CHINESE));
			if (StringUtils.equals(e.getErrorCode(),
					PassportAccountException.PWD_LENGTH_ERROR)
					|| StringUtils.equals(e.getErrorCode(),
							PassportAccountException.CONFIRM_PWD_ERROR)) {
				// reset页面
				model.addAttribute("resetPwdForm", resetPwdForm);
				return "";
			} else {
				// 链接无效，请重发邮件
				return "";
			}
		}
		return "redirect:/login";
	}

	@RequestMapping(value = "resetmail", method = RequestMethod.GET)
	public String getResetmail(HttpServletRequest request, Model model) {
		return "";
	}

	@RequestMapping(value = "resetmail", method = RequestMethod.POST)
	public String postResetmail(HttpServletRequest request, Model model,
			String account) {
		return "";
	}

	public String sendActiveMail(HttpServletRequest request, Model model)
			throws NeedLoginException {
		UserContext context = checkLoginForWeb(request);
		// 发送邮件
		return "";
	}
}
