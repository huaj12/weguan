package com.juzhai.mobile.web.filter;

import java.io.IOException;
import java.net.URLEncoder;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.rubyeye.xmemcached.MemcachedClient;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.juzhai.core.Constants;
import com.juzhai.core.cache.MemcachedKeyGenerator;
import com.juzhai.core.exception.NeedLoginException;
import com.juzhai.core.exception.NeedLoginException.RunType;
import com.juzhai.core.web.session.LoginSessionManager;
import com.juzhai.core.web.session.UserContext;
import com.juzhai.core.web.util.HttpRequestUtil;
import com.juzhai.passport.service.ILoginService;
import com.juzhai.passport.service.IUserOnlineRemoteService;

@Component
public class CheckLoginFilter implements Filter {

	public final Log log = LogFactory.getLog(getClass());

	public static final String LOGIN_USER_KEY = "loginUser";
	public static final String IS_FROM_QQ_PLUS = "isQplus";

	@Autowired
	private LoginSessionManager loginSessionManager;
	@Autowired
	private ILoginService loginService;
	@Autowired
	private IUserOnlineRemoteService userOnlineService;
	@Autowired
	private MemcachedClient memcachedClient;
	@Value(value = "${user.online.expire.time}")
	private int userOnlineExpireTime = 3600;

	@Override
	public void destroy() {
	}

	@Override
	public void init(FilterConfig config) throws ServletException {
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain filterChain) throws IOException, ServletException {
		HttpServletResponse rep = (HttpServletResponse) response;
		HttpServletRequest req = (HttpServletRequest) request;
		try {
			UserContext context = loginSessionManager
					.getUserContextAndUpdateExpire(req);
			req.setAttribute("context", context);

			// TODO 尝试自动登录（应该交给Passport模块负责）
			if (!context.hasLogin()) {
				loginService.persistentAutoLogin(req, rep);
			}
			context = (UserContext) req.getAttribute("context");

			// 如果登录则判读是否被锁定，更新在线状态，loginUser放入Request
			if (context.hasLogin()) {
				try {
					memcachedClient.set(MemcachedKeyGenerator
							.genUserOnlineKey(context.getUid()),
							userOnlineExpireTime, true);
				} catch (Exception e) {
					log.error(e.getMessage(), e);
				}
				userOnlineService.setLastUserOnlineTime(context.getUid());
			}

			// 判断是否来自q+
			String userAgent = context.getUserAgentPermanentCode();
			req.setAttribute(
					IS_FROM_QQ_PLUS,
					StringUtils.isEmpty(userAgent) ? false : userAgent
							.contains("Qplus"));

			filterChain.doFilter(request, response);
		} catch (Exception e) {
			if (e.getCause() instanceof NeedLoginException) {
				needLoginHandle(req, rep, (NeedLoginException) e.getCause());
			} else {
				throw new ServletException(e.getMessage(), e);
			}
		}
	}

	/**
	 * 需要登录处理
	 * 
	 * @param request
	 * @param response
	 * @param e
	 * @throws IOException
	 */
	private void needLoginHandle(HttpServletRequest request,
			HttpServletResponse response, NeedLoginException e)
			throws IOException {
		if (isAjaxRequest(request)) {
			response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
		} else if (isIOSRequest(request)) {
			response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
		} else {
			if (RunType.APP.equals(e.getRunType())) {
				String returnLink = URLEncoder.encode(
						HttpRequestUtil.getRemoteUrl(request), Constants.UTF8);
				response.sendRedirect("/app/login?returnLink=" + returnLink);
			} else {
				String redirectURI = "/login";
				String turnTo = HttpRequestUtil.getRemoteUrl(request);
				if (StringUtils.isNotEmpty(turnTo)) {
					redirectURI = redirectURI + "?turnTo=" + turnTo;
				}
				response.sendRedirect(redirectURI);
			}
		}
	}

	/**
	 * @return 是否ajax请求
	 */
	private boolean isAjaxRequest(HttpServletRequest request) {
		String requestedWith = request.getHeader("x-requested-with");
		if (StringUtils.equals(requestedWith, "XMLHttpRequest")) {
			return true;
		}
		return false;
	}

	private boolean isIOSRequest(HttpServletRequest request) {
		String userAgent = request.getHeader("User-Agent");
		if (StringUtils.isEmpty(userAgent)) {
			return false;
		}
		return userAgent.contains("iPhone OS") || userAgent.contains("iPad OS");
	}
}
