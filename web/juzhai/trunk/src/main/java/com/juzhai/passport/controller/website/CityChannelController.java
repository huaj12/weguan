package com.juzhai.passport.controller.website;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.juzhai.core.web.AjaxResult;
import com.juzhai.core.web.cookies.CookiesManager;
import com.juzhai.core.web.filter.CityChannelFilter;
import com.juzhai.core.web.util.HttpRequestUtil;

@Controller
public class CityChannelController {

	@Value(value = "${channel.cookie.max.age}")
	private int channelCookieMaxAge;

	@ResponseBody
	@RequestMapping(value = "/switchChannel", method = RequestMethod.GET)
	public AjaxResult switchChannel(HttpServletRequest request,
			HttpServletResponse response, Model model, long cityId) {
		HttpRequestUtil.setSessionAttribute(request,
				CityChannelFilter.SESSION_CHANNEL_NAME, cityId);
		CookiesManager.setCookie(request, response,
				CookiesManager.CHANNEL_NAME, String.valueOf(cityId),
				channelCookieMaxAge);
		AjaxResult result = new AjaxResult();
		result.setSuccess(true);
		return result;
	}
}
