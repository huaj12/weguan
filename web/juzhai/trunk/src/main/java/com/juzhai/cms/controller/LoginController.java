package com.juzhai.cms.controller;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.juzhai.core.encrypt.DESUtils;
import com.juzhai.passport.model.Passport;
import com.juzhai.passport.service.IPassportService;
import com.juzhai.passport.service.login.ILoginService;

@Controller
@RequestMapping("/cms")
public class LoginController {

	private final Log log = LogFactory.getLog(getClass());

	private IPassportService passportService;
	@Autowired
	private ILoginService tomcatLoginService;
	@Value("${cms.secret}")
	private String cmsSecret;

	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String login(HttpServletRequest request, Model model, String token) {
		long uid = 0;
		try {
			uid = Long.parseLong(DESUtils.decryptToString(cmsSecret.getBytes(),
					token));
		} catch (Exception e) {
			log.error("decrypt cms login error.", e);
			// TODO 报错页面
			return "";
		}
		if (uid > 0) {
			Passport passport = passportService.getPassportByUid(uid);
			if (passport != null && passport.getAdmin()) {
				tomcatLoginService.cmsLogin(request, uid, 0L, true);
				// CMS首页
				return "";
			}
		}
		// TODO 报错页面
		return "";
	}
}
