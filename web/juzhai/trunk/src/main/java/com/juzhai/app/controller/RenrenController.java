package com.juzhai.app.controller;

import java.io.PrintWriter;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.juzhai.account.service.IAccountService;
import com.juzhai.act.model.Act;
import com.juzhai.act.service.IActService;
import com.juzhai.act.service.IUserActService;
import com.juzhai.app.bean.TpMessageKey;
import com.juzhai.core.SystemConfig;
import com.juzhai.core.controller.BaseController;
import com.juzhai.core.util.TextTruncateUtil;
import com.juzhai.core.web.jstl.JzCoreFunction;
import com.juzhai.core.web.session.UserContext;
import com.juzhai.msg.bean.ActMsg;
import com.juzhai.msg.service.IMsgMessageService;
import com.juzhai.msg.service.IMsgService;
import com.juzhai.passport.InitData;
import com.juzhai.passport.model.Thirdparty;
import com.juzhai.passport.service.ITpUserAuthService;
import com.juzhai.passport.service.ITpUserService;

@Controller
public class RenrenController extends BaseController {
	private final Log log = LogFactory.getLog(getClass());
	@Autowired
	private MessageSource messageSource;
	@Autowired
	private IActService actService;
	@Autowired
	private IMsgMessageService msgMessageService;
	@Autowired
	private ITpUserService tpUserService;
	@Autowired
	private ITpUserAuthService tpUserAuthService;
	@Autowired
	private IUserActService userActService;
	@Value("${show.feed.count}")
	private int feedCount = 3;

	@RequestMapping(value = { "/renrenFeed" }, method = RequestMethod.GET)
	public String kaixinFeed(HttpServletRequest request,
			HttpServletResponse response, Model model,
			@RequestParam(defaultValue = "0") long id) {
		PrintWriter out = null;

		try {
			UserContext context = checkLoginForApp(request);
			Thirdparty tp = InitData.TP_MAP.get(context.getTpId());
			String action_link = tp.getAppUrl();
			
			String action_name = messageSource
					.getMessage(TpMessageKey.FEED_LINKTEXT, null,
							Locale.SIMPLIFIED_CHINESE);
			String description = "";
			String title = "";
			String message="";
			Act act = actService.getActById(id);
			if (act == null) {
				message = messageSource.getMessage(
						TpMessageKey.FEED_TEXT_DEFAULT, null,
						Locale.SIMPLIFIED_CHINESE);
			} else {
				title=messageSource.getMessage(TpMessageKey.FEED_NAME, new Object[]{act.getName()},
						Locale.SIMPLIFIED_CHINESE);
				description=TextTruncateUtil.truncate(act.getIntro(), 100, "...") ;
				int count = userActService.countUserActByActId(
						context.getTpId(), id);
				if (count > feedCount) {
					message = messageSource.getMessage(
							TpMessageKey.FEED_TEXT,
							new Object[] { act.getName(), count - 1 },
							Locale.SIMPLIFIED_CHINESE);
				} else {
					message = messageSource.getMessage(
							TpMessageKey.FEED_TEXT_COUNT_DEFAULT,
							new Object[] { act.getName() },
							Locale.SIMPLIFIED_CHINESE);
				}
			}
			String logo = "";
			if (act != null) {
				logo = act.getLogo();
			}
			String picurl = JzCoreFunction.actLogo(id, logo, 120);
			response.setContentType("text/plain");
			out = response.getWriter();
			out.println("{'message':'" + message + "','image':'" + picurl + "','url':'" + action_link
					+ "','name':'" + title + "','action_name':'" + action_name
					+ "','action_link':'" + action_link + "','description':'"
					+ description + "'}");
		} catch (Exception e) {
			log.error("renren send feed is error", e);
		} finally {
			if (out != null) {
				out.close();
			}
		}
		return null;
	}

	@RequestMapping(value = { "/renrenRequest" }, method = RequestMethod.GET)
	public String renrenRequest(HttpServletRequest request,
			HttpServletResponse response, Model model,
			@RequestParam(defaultValue = "0") long id) {
		PrintWriter out = null;

		try {
			UserContext context = checkLoginForApp(request);

			Thirdparty tp = InitData.TP_MAP.get(context.getTpId());
			String accept_url = tp.getAppUrl();
			String accept_label = messageSource.getMessage(
					TpMessageKey.RENREN_INVITE_ACCEPT_LABEL, null,
					Locale.SIMPLIFIED_CHINESE);
			String actiontext = messageSource.getMessage(
					TpMessageKey.RENREN_INVITE_ACTIONTEXT, null,
					Locale.SIMPLIFIED_CHINESE);
			String send_btn_label = messageSource.getMessage(
					TpMessageKey.RENREN_INVITE_SEND_LABEL, null,
					Locale.SIMPLIFIED_CHINESE);
			String selector_mode = "";
			String app_msg = "";
			Act act = actService.getActById(id);
			if (act == null) {
				selector_mode = "naf";
				app_msg = messageSource.getMessage(
						TpMessageKey.INVITE_TEXT_DEFAULT, null,
						Locale.SIMPLIFIED_CHINESE);
			} else {
				selector_mode = "all";
				app_msg = messageSource.getMessage(TpMessageKey.INVITE_TEXT,
						new Object[] { act.getName() },
						Locale.SIMPLIFIED_CHINESE);
			}
			response.setContentType("text/plain");
			out = response.getWriter();
			out.println("{'accept_url':'" + accept_url + "','accept_label':'"
					+ accept_label + "','actiontext':'" + actiontext
					+ "','app_msg':'" + app_msg + "','selector_mode':'"
					+ selector_mode + "','send_btn_label':'" + send_btn_label
					+ "','selector_mode':'" + selector_mode + "'}");
		} catch (Exception e) {
			log.error("kaixin send feed is error", e);
		} finally {
			if (out != null) {
				out.close();
			}
		}
		return null;
	}

}
