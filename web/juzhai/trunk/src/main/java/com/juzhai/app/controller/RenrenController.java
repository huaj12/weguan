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

import com.juzhai.account.service.IAccountService;
import com.juzhai.act.model.Act;
import com.juzhai.act.service.IActService;
import com.juzhai.app.bean.TpMessageKey;
import com.juzhai.app.util.AppPlatformUtils;
import com.juzhai.core.SystemConfig;
import com.juzhai.core.controller.BaseController;
import com.juzhai.core.web.session.UserContext;
import com.juzhai.msg.bean.ActMsg;
import com.juzhai.msg.bean.ActMsg.MsgType;
import com.juzhai.msg.service.IMsgMessageService;
import com.juzhai.msg.service.IMsgService;
import com.juzhai.passport.InitData;
import com.juzhai.passport.bean.AuthInfo;
import com.juzhai.passport.model.Thirdparty;
import com.juzhai.passport.model.TpUser;
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
	private IMsgService<ActMsg> msgService;
	@Autowired
	private IAccountService accountService;
	@Value("${renren.feed.redirect.uri}")
	private String feedRedirectUri;
	@Value("${renren.request.redirect.uri}")
	private String reuqestRedirectUri;

	@RequestMapping(value = { "/renrenFeed" }, method = RequestMethod.GET)
	public String kaixinFeed(HttpServletRequest request,
			HttpServletResponse response, Model model, String name) {
		PrintWriter out = null;

		try {
			UserContext context = checkLoginForApp(request);
			Thirdparty tp = InitData.TP_MAP.get(context.getTpId());
			String action_link = "http://apps.renren.com/juzhaiqi/";
			String title = "发布了拒宅召集令";
			String action_name = "立即查看";
			String description="";
		    if(StringUtils.isEmpty(name)){
		    	description ="我最近不想对着电脑发呆了；谁想陪我出去玩?";
		    }else{
		    	description = "我最近想去【" + name + "】;有人响应吗？";
		    }
		
			String image = "";
			// String redirect_uri="http://apps.renren.com/juzhaiqi/";
			response.setContentType("text/plain");
			out = response.getWriter();
			out.println("{'url':'" + action_link + "','name':'" + title
					+ "','action_name':'" + action_name + "','action_link':'"
					+ action_link + "','description':'" + description + "'}");
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
			HttpServletResponse response, Model model, String name) {
		PrintWriter out = null;

		try {
			UserContext context = checkLoginForApp(request);
			Act act = actService.getActByName(name);
			Thirdparty tp = InitData.TP_MAP.get(context.getTpId());
			String accept_url=tp.getAppUrl();
			String accept_label="立即查看";
			String actiontext="拒宅邀请";
			String selector_mode="";
			String app_msg="";
			String callBack="";
			if(StringUtils.isEmpty(name)){
			    selector_mode="naf";
				app_msg="还在对着电脑发呆吧；快来拒宅器看看好友们最近都有什么出去玩的计划吧。";	
			}else{
				selector_mode="all";
				app_msg="最近有空么？一起出去玩吧。";
				callBack=",'redirect_uri':'"+SystemConfig
				.getDomain(tp == null ? null : tp.getName())+reuqestRedirectUri+"?name="
				+ act.getId()
				+ "&tpId="
				+ context.getTpId()+"'";
			}
			String send_btn_label="发送邀请";
			response.setContentType("text/plain");
			out = response.getWriter();
			out.println("{'accept_url':'"+accept_url+"','accept_label':'"+accept_label+"','actiontext':'"+actiontext+"','app_msg':'"+app_msg+"','selector_mode':'"+selector_mode+"','send_btn_label':'"+send_btn_label+"','selector_mode':'"+selector_mode+"'"+callBack+"}");
		} catch (Exception e) {
			log.error("kaixin send feed is error", e);
		} finally {
			if (out != null) {
				out.close();
			}
		}
		return null;
	}

	@RequestMapping(value = "renrenRequestCallBack")
	public String dialogSysnewsCallBack(HttpServletRequest request,
			Model model) {
		String queryString = request.getQueryString();
		return null;
	}

}
