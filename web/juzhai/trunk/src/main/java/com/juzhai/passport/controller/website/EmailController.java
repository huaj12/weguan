package com.juzhai.passport.controller.website;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.juzhai.core.controller.BaseController;
import com.juzhai.core.exception.NeedLoginException;
import com.juzhai.core.web.filter.CheckLoginFilter;
import com.juzhai.core.web.session.UserContext;
import com.juzhai.notice.bean.NoticeType;
import com.juzhai.passport.bean.ProfileCache;
import com.juzhai.passport.controller.form.EmailForm;
import com.juzhai.passport.exception.ProfileInputException;
import com.juzhai.passport.service.IEmailService;

@Controller
@RequestMapping(value = "profile")
public class EmailController extends BaseController {

	@Autowired
	private IEmailService emailService;
	@Autowired
	private MessageSource messageSource;

	@RequestMapping(value = "/email", method = RequestMethod.GET)
	public String email(HttpServletRequest request, Model model)
			throws NeedLoginException {
		UserContext context = checkLoginForWeb(request);
		EmailForm emailForm = new EmailForm();
		emailForm.setInterestMe(emailService.isSubNotice(context.getUid(),
				NoticeType.INTEREST_ME));
		emailForm.setDatingMe(emailService.isSubNotice(context.getUid(),
				NoticeType.DATING_ME));
		emailForm.setAcceptDating(emailService.isSubNotice(context.getUid(),
				NoticeType.ACCEPT_DATING));
		emailForm.setSysNotice(emailService.isSubNotice(context.getUid(),
				NoticeType.SYS_NOTICE));
		ProfileCache loginUser = (ProfileCache) request
				.getAttribute(CheckLoginFilter.LOGIN_USER_KEY);
		if (null != loginUser) {
			emailForm.setEmail(loginUser.getEmail());
		}
		model.addAttribute("emailForm", emailForm);
		return "web/profile/mail";
	}

	@RequestMapping(value = "/updateEmail", method = RequestMethod.POST)
	public String upateEmail(HttpServletRequest request, Model model,
			EmailForm emailForm) throws NeedLoginException {
		UserContext context = checkLoginForWeb(request);
		try {
			emailService.setupSub(context.getUid(), emailForm);
		} catch (ProfileInputException e) {
			model.addAttribute("errorCode", e.getErrorCode());
			model.addAttribute("errorInfo", messageSource.getMessage(
					e.getErrorCode(), null, Locale.SIMPLIFIED_CHINESE));
			model.addAttribute("emailForm", emailForm);
			return "web/profile/mail";
		}
		return "redirect:/proifle/email";
	}
}
