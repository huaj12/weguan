package com.juzhai.passport.controller.app;

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
import com.juzhai.core.encrypt.DESUtils;
import com.juzhai.core.exception.JuzhaiException;
import com.juzhai.core.exception.NeedLoginException;
import com.juzhai.core.web.AjaxResult;
import com.juzhai.core.web.session.UserContext;
import com.juzhai.passport.InitData;
import com.juzhai.passport.bean.ProfileCache;
import com.juzhai.passport.exception.ProfileInputException;
import com.juzhai.passport.service.IEmailService;
import com.juzhai.passport.service.IProfileService;

@Controller
public class AppEmailController extends BaseController {

	private final Log log = LogFactory.getLog(getClass());

	@Autowired
	private IProfileService profileService;
	@Autowired
	private IEmailService emailService;
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
				isSuccess = emailService.subEmail(context.getUid(), email);
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
			String token, long uid) {
		long realUid = decryptUid(token, uid);
		if (realUid <= 0) {
			return error_500;
		}
		ProfileCache profileCache = profileService
				.getProfileCacheByUid(realUid);
		if (profileCache == null) {
			return error_500;
		}
		model.addAttribute("profile", profileCache);
		model.addAttribute("token", token);
		model.addAttribute("tpMap", InitData.TP_MAP);
		return "web/mail/unsub";
	}

	@RequestMapping(value = "/unsubEmail", method = RequestMethod.POST)
	@ResponseBody
	public AjaxResult unsubEmailSubmit(HttpServletRequest request, Model model,
			String token, long uid) {
		long realUid = decryptUid(token, uid);
		AjaxResult ajaxResult = new AjaxResult();
		if (realUid > 0) {
			boolean isSuccess = emailService.unsubEmail(realUid);
			if (isSuccess) {
				ajaxResult.setSuccess(isSuccess);
				return ajaxResult;
			}
		}

		ajaxResult.setSuccess(false);
		ajaxResult.setErrorCode(JuzhaiException.ILLEGAL_OPERATION);
		ajaxResult.setErrorInfo(messageSource.getMessage(
				JuzhaiException.ILLEGAL_OPERATION, null,
				Locale.SIMPLIFIED_CHINESE));
		return ajaxResult;
	}

	private long decryptUid(String token, long uid) {
		byte[] scretKey = profileService.getUserSecretKey(uid);
		if (null == scretKey) {
			return 0;
		}
		long realUid = 0;
		try {
			realUid = Long.valueOf(DESUtils.decryptToString(scretKey, token));
		} catch (Exception e) {
			log.error("unsub email decrypt error.[uid=" + uid + "]", e);
			return 0;
		}
		return realUid;
	}

	@RequestMapping(value = "statOpenEmail", method = RequestMethod.GET)
	@ResponseBody
	public String statOpenEmail(String token, long uid) {
		long realUid = decryptUid(token, uid);
		if (realUid == uid) {
			emailService.statOpenEmail();
		}
		return StringUtils.EMPTY;
	}
}
