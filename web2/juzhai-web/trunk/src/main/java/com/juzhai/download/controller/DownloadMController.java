package com.juzhai.download.controller;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
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
	private final Log log = LogFactory.getLog(getClass());
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

	@RequestMapping(value = "/ios", method = RequestMethod.GET)
	public String ios(HttpServletRequest request, HttpServletResponse response,
			Model model) {
		downFile(downloadService.getIOSFile(), request, response);
		iosDownloadCounter.incr(null, 1l);
		return null;
	}

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

	private void downFile(File file, HttpServletRequest request,
			HttpServletResponse response) {
		OutputStream toClient = null;
		try {
			String filename = file.getName();
			String ext = filename.substring(filename.lastIndexOf(".") + 1)
					.toUpperCase();
			byte[] buffer = FileUtils.readFileToByteArray(file);
			response.reset();
			response.setContentType(ext);
			response.addHeader("Content-Disposition", "attachment;filename="
					+ new String(filename.getBytes()));
			response.addHeader("Content-Length", "" + file.length());
			toClient = new BufferedOutputStream(response.getOutputStream());
			response.setContentType("application/octet-stream");
			toClient.write(buffer);
			toClient.flush();
			toClient.close();
		} catch (IOException e) {
			log.error("download is error", e);
		} finally {
			try {
				if (toClient != null) {
					toClient.close();
				}
			} catch (IOException e) {
				toClient = null;
			}

		}
	}
}
