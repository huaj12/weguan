package com.juzhai.cms.controller;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.juzhai.core.encrypt.DESUtils;
import com.juzhai.core.web.session.UserContext;
import com.juzhai.passport.model.Passport;
import com.juzhai.passport.service.ILoginService;
import com.juzhai.passport.service.IPassportService;

@Controller
@RequestMapping("/cms")
public class CmsLoginController {
	private final Log log = LogFactory.getLog(getClass());

	private static final String ERROR = "cms/error";

	@Autowired
	private IPassportService passportService;
	@Autowired
	private ILoginService loginService;
	@Value("${cms.secret}")
	private String cmsSecret;

	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String login(HttpServletRequest request,
			HttpServletResponse response, Model model, String token) {
		UserContext context = (UserContext) request.getAttribute("context");
		if (context.hasLogin() && context.isAdmin()) {
			return "cms/index";
		}
		long uid = 0;
		try {
			uid = Long.parseLong(DESUtils.decryptToString(cmsSecret.getBytes(),
					token));
		} catch (Exception e) {
			log.error("decrypt cms login error.", e);
			return ERROR;
		}
		if (uid > 0) {
			Passport passport = passportService.getPassportByUid(uid);
			if (passport != null && passport.getAdmin()) {
				loginService.cmsLogin(request, response, uid, 0L);
				// CMS首页
				return "cms/index";
			}
		}
		return ERROR;
	}

	public static void main(String[] args) {
		try {
			System.out.println(DESUtils.encryptToHexString(
					"51juzhai.wujiajun.max.kooks".getBytes(), "3".getBytes()));
		} catch (InvalidKeyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidKeySpecException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalBlockSizeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (BadPaddingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
