package com.juzhai.app.controller;

import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.juzhai.act.model.Act;
import com.juzhai.act.service.IActService;
import com.juzhai.app.bean.TpMessageKey;
import com.juzhai.app.util.AppPlatformUtils;
import com.juzhai.core.controller.BaseController;
import com.juzhai.core.web.session.UserContext;
import com.juzhai.msg.AppConfig;
import com.juzhai.passport.InitData;
import com.juzhai.passport.bean.AuthInfo;

@Controller
public class KaiXinController extends BaseController {
	private final Log log = LogFactory.getLog(getClass());
	@Autowired
	private MessageSource messageSource;
	@Autowired
	private IActService actService;

	@RequestMapping(value = { "/kaixinFeed" }, method = RequestMethod.GET)
	public String kaixinFeed(HttpServletRequest request,
			HttpServletResponse response, Model model, String name) {
		PrintWriter out = null;

		try {
			UserContext context = checkLoginForApp(request);
			String text="";
			if (StringUtils.isEmpty(name)) {
				text=messageSource.getMessage(
						TpMessageKey.FEED_TEXT_DEFAULT, null,
						Locale.SIMPLIFIED_CHINESE);
			} else {
				text= messageSource.getMessage(
						TpMessageKey.FEED_TEXT, new Object[] { name },
						Locale.SIMPLIFIED_CHINESE);
			}
			String linktext= messageSource.getMessage(
					TpMessageKey.FEED_LINKTEXT, null, Locale.SIMPLIFIED_CHINESE);
			String link=messageSource.getMessage(
					TpMessageKey.FEED_LINK, null, Locale.SIMPLIFIED_CHINESE);
			String feedRedirect_uri=messageSource.getMessage(
					TpMessageKey.FEED_REDORECT_URI, new Object[]{context.getTpId()}, Locale.SIMPLIFIED_CHINESE);
			response.setContentType("text/plain");
			out = response.getWriter();
			out.println("http://api.kaixin001.com/dialog/feed?display=iframe&redirect_uri="+feedRedirect_uri+"&linktext="+linktext+"&link="+link+"&text="+text+"&app_id=100012402&need_redirect=0");
		} catch (Exception e) {
			log.error("kaixin send feed is error", e);
		} finally {
			if (out != null) {
				out.close();
			}
		}
		return null;
	}

	@RequestMapping(value = { "/kaixinRequest" }, method = RequestMethod.GET)
	public String kaixinRequest(HttpServletRequest request,
			HttpServletResponse response, Model model, String name) {
		PrintWriter out = null;
		try {
			UserContext context = checkLoginForApp(request);
			AuthInfo authInfo = getAuthInfo(context.getUid(), context.getTpId());
			authInfo.setAppSecret(InitData.TP_MAP.get(context.getTpId())
					.getAppSecret());
			// 拼凑参数
			Map<String, String> paramMap = new HashMap<String, String>();
			paramMap.put("method", "actions.sendInvitation");
			paramMap.put("format", "json");
			paramMap.put("mode", "0");
			if (StringUtils.isEmpty(name)) {
				paramMap.put("text", messageSource.getMessage(
						TpMessageKey.INVITE_TEXT_DEFAULT, null,
						Locale.SIMPLIFIED_CHINESE));
			} else {
				paramMap.put("text", messageSource.getMessage(
						TpMessageKey.INVITE_TEXT, new Object[] { name },
						Locale.SIMPLIFIED_CHINESE));
			}

			String query = AppPlatformUtils.buildQuery(paramMap,
					authInfo.getAppKey(), authInfo.getAppSecret(),
					authInfo.getSessionKey(), "1.2");
			response.setContentType("text/plain");
			out = response.getWriter();
			out.println(AppPlatformUtils.urlBase64Encode(query));
		} catch (Exception e) {
			log.error("kaixin send Request is error", e);
		} finally {
			if (out != null) {
				out.close();
			}
		}
		return null;
	}
	
	@RequestMapping(value = { "/dialogSysnews" }, method = RequestMethod.GET)
	public String dialogSysnews(HttpServletRequest request,
			HttpServletResponse response, Model model, String name) {
		PrintWriter out = null;
		try {
			Act act=actService.getActByName(name);
			UserContext context = checkLoginForApp(request);
			String	text=messageSource.getMessage(
						TpMessageKey.SYSNEW_DIALOG_TEXT, null,
						Locale.SIMPLIFIED_CHINESE);
			String linktext= messageSource.getMessage(
					TpMessageKey.FEED_LINKTEXT, null, Locale.SIMPLIFIED_CHINESE);
			String link=messageSource.getMessage(
					TpMessageKey.FEED_LINK, null, Locale.SIMPLIFIED_CHINESE);
			String sysnewRedirect_uri=messageSource.getMessage(
					TpMessageKey.SYSNEW_REDORECT_URI, new Object[]{act.getId()+"-"+context.getTpId()}, Locale.SIMPLIFIED_CHINESE);
			response.setContentType("text/plain");
			out = response.getWriter();
			out.println("http://api.kaixin001.com/dialog/sysnews?display=iframe&linktext="
			+ linktext
			+ "&text="
			+ text
			+ "&link="
			+ link
			+ "&app_id=100012402&redirect_uri="+sysnewRedirect_uri+"&need_redirect=0");
		} catch (Exception e) {
			log.error("kaixin dialogSysnews is error", e);
		} finally {
			if (out != null) {
				out.close();
			}
		}
		return null;
	}
}
