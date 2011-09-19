package com.juzhai.app.controller;

import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.juzhai.app.util.AppPlatformUtils;
import com.juzhai.core.controller.BaseController;
import com.juzhai.core.exception.NeedLoginException;
import com.juzhai.core.util.StringUtil;
import com.juzhai.core.web.session.UserContext;
import com.juzhai.passport.InitData;
import com.juzhai.passport.bean.AuthInfo;

@Controller
public class KaiXinController  extends BaseController {
	private final Log log = LogFactory.getLog(getClass());
	
	@RequestMapping(value = {"/kaixinFeed" }, method = RequestMethod.GET)
	public String kaixinFeed(HttpServletRequest request,HttpServletResponse response, Model model,String name) throws NeedLoginException{
		PrintWriter out=null;
		UserContext context = checkLoginForApp(request);
		try{
			
			AuthInfo authInfo =getAuthInfo(context.getUid(),context.getTpId());
			authInfo.setAppSecret(InitData.TP_MAP.get(context.getTpId()).getAppSecret());
		//拼凑参数
		Map<String,String> paramMap=new HashMap<String,String>();
		paramMap.put("method", "actions.sendNewsFeed");
		if(StringUtils.isEmpty(name)){
			paramMap.put("text", "别太宅了，现在就来用【拒宅器】吧！");
		}else{
			paramMap.put("text", "我最近想去 【"+name+"】 ，有人响应么？");
		}
		paramMap.put("linktext", "去看看");
		paramMap.put("link", "http://test.51juzhai.com");
		String query=AppPlatformUtils.buildQuery(paramMap, authInfo.getAppKey(), authInfo.getAppSecret(), authInfo.getSessionKey(), "1.2");
		out=response.getWriter();
		out.println(AppPlatformUtils.urlBase64Encode(query));
		}catch (Exception e) {
			log.error("kaixin send feed is error", e);
		}finally{
			if(out!=null){
				out.close();
			}
		}
		return null;
	}
	
	
	@RequestMapping(value = {"/kaixinRequest" }, method = RequestMethod.GET)
	public String kaixinRequest(HttpServletRequest request,HttpServletResponse response, Model model,String name) throws NeedLoginException{
		PrintWriter out=null;
		UserContext context = checkLoginForApp(request);
		try{
		AuthInfo authInfo =getAuthInfo(context.getUid(),context.getTpId());
		authInfo.setAppSecret(InitData.TP_MAP.get(context.getTpId()).getAppSecret());
		//拼凑参数
		Map<String,String> paramMap=new HashMap<String,String>();
		paramMap.put("method", "actions.sendInvitation");
		paramMap.put("format", "json");
		paramMap.put("mode", "0");
		if(StringUtils.isEmpty(name)){
			paramMap.put("text", "别太宅了，现在就来用【拒宅器】吧！");
		}else{
			paramMap.put("text", "我最近想去 【"+name+"】 ，有人响应么？");
		}
		
		String query=AppPlatformUtils.buildQuery(paramMap, authInfo.getAppKey(), authInfo.getAppSecret(), authInfo.getSessionKey(), "1.2");
		out=response.getWriter();
		out.println(AppPlatformUtils.urlBase64Encode(query));
		}catch (Exception e) {
			log.error("kaixin send Request is error", e);
		}finally{
			if(out!=null){
				out.close();
			}
		}
		return null;
	}
	
	
}
