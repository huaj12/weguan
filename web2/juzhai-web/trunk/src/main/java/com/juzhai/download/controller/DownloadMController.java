package com.juzhai.download.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.juzhai.core.bean.DeviceName;
import com.juzhai.core.stats.counter.service.ICounter;
import com.juzhai.core.web.session.UserContext;
import com.juzhai.core.web.util.HttpRequestUtil;
import com.juzhai.download.service.IDownloadService;

@Controller
@RequestMapping("download")
public class DownloadMController {
	@Autowired
	private IDownloadService downloadService;
	@Autowired
	private ICounter iosDownloadCounter;
	@Autowired
	private ICounter androidDownloadCounter;

	@RequestMapping(value = "/index", method = RequestMethod.GET)
	public String index(Model model) {
		return "web/download/index";
	}

	// @RequestMapping(value = "/ios", method = RequestMethod.GET)
	// public String ios(HttpServletRequest request, HttpServletResponse
	// response,
	// Model model) {
	// downFile(downloadService.getIOSFile(), request, response);
	// return null;
	// }

	@RequestMapping(value = "/android", method = RequestMethod.GET)
	public String android(HttpServletRequest request,
			HttpServletResponse response, Model model) {
		androidDownloadCounter.incr(null, 1l);
		return "redirect:" + downloadService.getAndroidWebPath();
	}

	@RequestMapping(value = "/mobile", method = RequestMethod.GET)
	public String mobile(HttpServletRequest request,
			HttpServletResponse response, Model model) {
		String url = null;
		UserContext context = (UserContext) request.getAttribute("context");
		DeviceName deviceName = HttpRequestUtil.getClientName(context);
		switch (deviceName) {
		case ANDROID:
			url = "redirect:" + downloadService.getAndroidWebPath();
			androidDownloadCounter.incr(null, 1l);
			break;
		case IPHONE:
			url = "redirect:" + downloadService.getIOSWebPath();
			iosDownloadCounter.incr(null, 1l);
			break;
		}
		return url;
	}
}
