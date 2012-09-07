package com.juzhai.promotion.controller;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

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

import com.juzhai.core.SystemConfig;
import com.juzhai.core.exception.UploadImageException;
import com.juzhai.passport.bean.AuthInfo;
import com.juzhai.passport.bean.ProfileCache;
import com.juzhai.passport.service.IProfileService;
import com.juzhai.promotion.PromotionConfig;
import com.juzhai.promotion.service.IPromotionImageService;
import com.qq.connect.AccessToken;
import com.qq.connect.InfoToken;
import com.qq.connect.RedirectToken;
import com.qq.connect.RequestToken;
import com.qq.connect.ShareToken;

@Controller
@RequestMapping(value = "occasional")
public class PromotionController {
	private final Log log = LogFactory.getLog(this.getClass());
	@Autowired
	private IProfileService profileService;
	@Autowired
	private MessageSource messageSource;
	@Autowired
	private IPromotionImageService promotionImageService;
	private String appKey = "100258588";
	private String appSecret = "a2cc637f7b69e63dcc6337818b34acdf";

	@RequestMapping(value = { "/", "" }, method = RequestMethod.GET)
	public String begin(Model model, HttpServletRequest request) {
		AuthInfo authInfo = (AuthInfo) request.getSession().getAttribute(
				"promotion_authInfo");
		if (authInfo != null) {
			return "redirect:/begin";
		}
		return "web/promotion/occasional/begin";
	}

	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String login(Model model, HttpServletRequest request) {
		AuthInfo authInfo = (AuthInfo) request.getSession().getAttribute(
				"promotion_authInfo");
		if (authInfo != null) {
			return "redirect:/begin";
		}
		String turnTo = SystemConfig.getDomain("app.qq") + "/access";
		String url = null;
		try {
			RequestToken rt = new RequestToken(appKey, appSecret);
			Map<String, String> tokens = rt.getRequestToken();
			request.getSession().setAttribute(tokens.get("oauth_token"),
					tokens.get("oauth_token_secret"));
			RedirectToken ret = new RedirectToken(appKey, appSecret);
			url = ret.getRedirectURL(tokens, turnTo);
		} catch (Exception e) {
			log.error("app QQ content  login is error." + e.getMessage());
		}
		return "redirect:" + url;
	}

	@RequestMapping(value = "/access", method = RequestMethod.GET)
	public String access(HttpServletRequest request) {
		AuthInfo authInfo = (AuthInfo) request.getSession().getAttribute(
				"promotion_authInfo");
		if (authInfo != null) {
			return "redirect:/begin";
		}
		String oauth_token = request.getParameter("oauth_token");
		String oauth_vericode = request.getParameter("oauth_vericode");
		String accessToken = getOAuthAccessTokenFromCode(request, oauth_token
				+ "," + oauth_vericode);
		String[] str = accessToken.split(",");
		String uid = str[2];
		authInfo = new AuthInfo();
		authInfo.setToken(str[0]);
		authInfo.setTokenSecret(str[1]);
		authInfo.setTpIdentity(uid);
		authInfo.setAppKey(appKey);
		authInfo.setAppSecret(appSecret);
		InfoToken info = new InfoToken(authInfo.getAppKey(),
				authInfo.getAppSecret());
		try {
			Map<String, String> map = info.getInfo(authInfo.getToken(),
					authInfo.getTokenSecret(), authInfo.getTpIdentity());
			String nickname = map.get("nickname");
			String gender = map.get("gender");
			String male = messageSource.getMessage("gender.male", null,
					Locale.SIMPLIFIED_CHINESE);
			// 获取异性性别
			int sex = 0;
			if (male.equals(gender)) {
				sex = 0;
			} else {
				sex = 1;
			}
			request.getSession().setAttribute("nickname", nickname);
			request.getSession().setAttribute("sex", sex);
		} catch (Exception e) {
			log.error("app qq access is error", e);
		}
		request.getSession().setAttribute("promotion_authInfo", authInfo);
		return "redirect:/begin";
	}

	@RequestMapping(value = "/begin", method = RequestMethod.GET)
	public String begin(HttpServletRequest request) {
		AuthInfo authInfo = (AuthInfo) request.getSession().getAttribute(
				"promotion_authInfo");
		if (authInfo == null) {
			return "redirect:/";
		}
		return "web/promotion/occasional/step1";
	}

	// 计算并发布结果
	@RequestMapping(value = "/ajax/send", method = RequestMethod.GET)
	public String step3(HttpServletRequest request, Model model) {
		AuthInfo authInfo = (AuthInfo) request.getSession().getAttribute(
				"promotion_authInfo");
		if (authInfo == null) {
			return "redirect:/";
		}
		try {
			String nickname = (String) request.getSession().getAttribute(
					"nickname");
			Integer sex = (Integer) request.getSession().getAttribute("sex");
			// 获取匹配内容
			String address = PromotionConfig.randomOccasional();
			long uid = PromotionConfig.randomUser(sex);
			if (uid > 0) {
				String title = messageSource.getMessage("occasional.title",
						null, Locale.SIMPLIFIED_CHINESE);
				String link = SystemConfig.getDomain("app.qq");
				String textBegin = messageSource.getMessage(
						"occasional.text.begin", null,
						Locale.SIMPLIFIED_CHINESE);
				String textEnd = messageSource.getMessage(
						"occasional.text.end", null, Locale.SIMPLIFIED_CHINESE);
				String imageUrl = null;
				try {
					imageUrl = promotionImageService.getOccasionalImageUrl(uid,
							nickname, address, textBegin, textEnd);
				} catch (UploadImageException e) {
					log.error("app qq getOccasionalImageUrl is error", e);
				}
				if (StringUtils.isNotEmpty(imageUrl)) {
					sendMessage(authInfo, title, null, link, imageUrl);
				}
				ProfileCache cache = profileService.getProfileCacheByUid(uid);
				model.addAttribute("profile", cache);
				model.addAttribute("content", address);
			}
		} catch (Exception e) {
			log.error("qq  app occasional send", e);
		}
		return "web/promotion/occasional/step3";
	}

	private String getOAuthAccessTokenFromCode(HttpServletRequest request,
			String code) {
		String[] str = code.split(",");
		String oauth_token = str[0];
		String oauth_vericode = str[1];
		String accessToken = null;
		String oauthTokenSecret = (String) request.getSession().getAttribute(
				oauth_token);
		try {
			Map<String, String> map = new AccessToken(appKey, appSecret)
					.getAccessToken(oauth_token, oauthTokenSecret,
							oauth_vericode);
			accessToken = map.get("oauth_token") + ","
					+ map.get("oauth_token_secret") + "," + map.get("openid");
		} catch (Exception e) {
			log.error("app QQ content getOAuthAccessTokenFromCode is error."
					+ e.getMessage());
		}
		return accessToken;
	}

	private void sendMessage(AuthInfo authInfo, String title, String text,
			String link, String imageUrl) {
		ShareToken share = new ShareToken(authInfo.getAppKey(),
				authInfo.getAppSecret());
		try {
			Map<String, String> map = new HashMap<String, String>();
			map.put("title", title);
			map.put("url", link);
			if (StringUtils.isNotEmpty(text)) {
				map.put("summary", text);
			}
			if (StringUtils.isNotEmpty(imageUrl)) {
				map.put("images", imageUrl);
			}
			Map<String, Object> code = share.addShare(authInfo.getToken(),
					authInfo.getTokenSecret(), authInfo.getTpIdentity(), map);
			if (!"ok".equals(String.valueOf(code.get("msg")))) {
				log.error("app QQ content sendMessage is error. msg="
						+ String.valueOf(code.get("msg")) + "| ret="
						+ ((Integer) code.get("ret")));
			}
		} catch (Exception e) {
			log.error("app QQ content sendMessage is error.");
		}
	}
}
