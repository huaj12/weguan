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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.juzhai.act.exception.UploadImageException;
import com.juzhai.core.SystemConfig;
import com.juzhai.passport.InitData;
import com.juzhai.passport.bean.AuthInfo;
import com.juzhai.passport.bean.ProfileCache;
import com.juzhai.passport.model.Thirdparty;
import com.juzhai.passport.service.IProfileService;
import com.juzhai.promotion.PromotionConfig;
import com.juzhai.promotion.service.IPromotionImageService;
import com.qq.connect.AccessToken;
import com.qq.connect.InfoToken;
import com.qq.connect.RedirectToken;
import com.qq.connect.RequestToken;
import com.qq.connect.ShareToken;

@Controller
@RequestMapping(value = "promotion")
public class PromotionController {
	private final Log log = LogFactory.getLog(this.getClass());
	private final static Map<String, String> tokenMap = new HashMap<String, String>();
	@Autowired
	private IProfileService profileService;
	@Autowired
	private MessageSource messageSource;
	@Autowired
	private IPromotionImageService promotionImageService;

	@RequestMapping(value = "/{path}/{tpId}", method = RequestMethod.GET)
	public String begin(@PathVariable String path, @PathVariable long tpId,
			Model model) {
		Thirdparty tp = InitData.TP_MAP.get(tpId);
		String turnTo = SystemConfig.getDomain() + "/promotion/" + path
				+ "/step1/" + tpId;

		String url = null;
		try {
			RequestToken rt = new RequestToken(tp.getAppKey(),
					tp.getAppSecret());
			Map<String, String> tokens = rt.getRequestToken();
			tokenMap.put(tokens.get("oauth_token"),
					tokens.get("oauth_token_secret"));
			RedirectToken ret = new RedirectToken(tp.getAppKey(),
					tp.getAppSecret());
			url = ret.getRedirectURL(tokens, turnTo);
		} catch (Exception e) {
			log.equals("app QQ content " + path + " begin is error."
					+ e.getMessage());
		}
		model.addAttribute("url", url);
		return "web/promotion/" + path + "/begin";
	}

	@RequestMapping(value = "/{path}/step1/{tpId}", method = RequestMethod.GET)
	public String step1(HttpServletRequest request, @PathVariable long tpId,
			@PathVariable String path) {
		Thirdparty tp = InitData.TP_MAP.get(tpId);
		String oauth_token = request.getParameter("oauth_token");
		String oauth_vericode = request.getParameter("oauth_vericode");
		String accessToken = getOAuthAccessTokenFromCode(tp, oauth_token + ","
				+ oauth_vericode);
		String[] str = accessToken.split(",");
		String uid = str[2];
		AuthInfo authInfo = new AuthInfo();
		authInfo.setThirdparty(tp);
		authInfo.setToken(str[0]);
		authInfo.setTokenSecret(str[1]);
		authInfo.setTpIdentity(uid);
		request.getSession().setAttribute("promotion_authInfo", authInfo);
		request.getSession().setAttribute("tpId", tp.getId());
		return "web/promotion/" + path + "/step1";
	}

	@RequestMapping(value = "/{path}/step2/", method = RequestMethod.GET)
	public String step2(HttpServletRequest request, @PathVariable String path,
			Model model) {
		AuthInfo authInfo = (AuthInfo) request.getSession().getAttribute(
				"promotion_authInfo");
		if (authInfo == null) {
			return "redirect:/promotion/" + path + "/begin";
		}
		model.addAttribute("path", path);
		return "web/promotion/" + path + "/step2";
	}

	// 计算并发布结果
	@RequestMapping(value = "/{path}/step3/", method = RequestMethod.GET)
	public String step3(HttpServletRequest request, @PathVariable String path,
			Model model) {
		AuthInfo authInfo = (AuthInfo) request.getSession().getAttribute(
				"promotion_authInfo");
		String tpId = String.valueOf(request.getSession().getAttribute("tpId"));
		if (authInfo == null) {
			return "redirect:/promotion/" + path + "/begin";
		}
		InfoToken info = new InfoToken(authInfo.getAppKey(),
				authInfo.getAppSecret());
		try {
			Map<String, String> map = info.getInfo(authInfo.getToken(),
					authInfo.getTokenSecret(), authInfo.getTpIdentity());
			String nickname = map.get("nickname");
			// 获取匹配内容
			String address = PromotionConfig.randomOccasional();
			String male = messageSource.getMessage("gender.male", null,
					Locale.SIMPLIFIED_CHINESE);
			// 获取异性性别
			int sex = 0;
			if (male.equals(map.get("gender"))) {
				sex = 0;
			} else {
				sex = 1;
			}
			long uid = PromotionConfig.randomUser(sex);
			ProfileCache cache = profileService.getProfileCacheByUid(uid);
			String title = messageSource.getMessage(path + ".title",
					new Object[] { address }, Locale.SIMPLIFIED_CHINESE);
			String link = messageSource.getMessage(path + ".link",
					new Object[] { path, tpId }, Locale.SIMPLIFIED_CHINESE);
			String textBegin = messageSource.getMessage(path + ".text.begin",
					null, Locale.SIMPLIFIED_CHINESE);
			String textEnd = messageSource.getMessage(path + ".text.end", null,
					Locale.SIMPLIFIED_CHINESE);
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
			model.addAttribute("profile", cache);
			model.addAttribute("content", address);
		} catch (Exception e) {
			log.error("qq  app " + path + "step3", e);
			return null;
		}
		return "web/promotion/" + path + "/step3";
	}

	private String getOAuthAccessTokenFromCode(Thirdparty tp, String code) {
		String[] str = code.split(",");
		String oauth_token = str[0];
		String oauth_vericode = str[1];
		String accessToken = null;
		try {
			Map<String, String> map = new AccessToken(tp.getAppKey(),
					tp.getAppSecret()).getAccessToken(oauth_token,
					tokenMap.get(oauth_token), oauth_vericode);
			tokenMap.remove(oauth_token);
			accessToken = map.get("oauth_token") + ","
					+ map.get("oauth_token_secret") + "," + map.get("openid");
		} catch (Exception e) {
			log.equals("app QQ content getOAuthAccessTokenFromCode is error."
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
