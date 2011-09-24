package com.juzhai.passport.controller;

import java.security.SecureRandom;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.juzhai.core.controller.BaseController;
import com.juzhai.core.exception.NeedLoginException;
import com.juzhai.core.web.AjaxResult;
import com.juzhai.core.web.session.UserContext;
import com.juzhai.passport.bean.ProfileCache;
import com.juzhai.passport.exception.ProfileInputException;
import com.juzhai.passport.service.IProfileService;

@Controller
public class EmailController extends BaseController {

	private final Log log = LogFactory.getLog(getClass());

	@Autowired
	private IProfileService profileService;
	@Autowired
	private MessageSource messageSource;

	@RequestMapping(value = "/app/ajax/subEmail", method = RequestMethod.POST)
	@ResponseBody
	public AjaxResult subEmail(HttpServletRequest request, String email)
			throws NeedLoginException {
		UserContext context = checkLoginForApp(request);
		AjaxResult ajaxResult = new AjaxResult();
		try {
			ProfileCache profileCache = queryProfile(context.getUid(), null);
			boolean isSuccess = true;
			if (!StringUtils.equals(profileCache.getEmail(),
					StringUtils.trim(email))
					|| profileCache.getSubEmail() == null
					|| !profileCache.getSubEmail()) {
				if (log.isDebugEnabled()) {
					log.debug("sub new email.[uid: " + context.getUid()
							+ ", email: " + email + "]");
				}
				isSuccess = profileService.subEmail(context.getUid(), email);
			}
			ajaxResult.setSuccess(isSuccess);
		} catch (ProfileInputException e) {
			if (log.isDebugEnabled()) {
				log.debug(e.getMessage(), e);
			}
			ajaxResult.setSuccess(false);
			ajaxResult.setErrorCode(e.getErrorCode());
			ajaxResult.setErrorInfo(messageSource.getMessage(e.getErrorCode(),
					null, e.getErrorCode(), Locale.SIMPLIFIED_CHINESE));
		}
		return ajaxResult;
	}

	@RequestMapping(value = "/unsubEmail", method = RequestMethod.GET)
	public String unsubEmail(HttpServletRequest request, Model model,
			String token) {
		SecureRandom sr = new SecureRandom();
		//TODO 退订成功页面
		return "";
	}
}
