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

import com.juzhai.download.service.IDownloadService;

@Controller
@RequestMapping("download")
public class DownloadMController {
	private final Log log = LogFactory.getLog(getClass());
	@Autowired
	private IDownloadService downloadService;

	@RequestMapping(value = "/index", method = RequestMethod.GET)
	public String index(Model model) {
		return "web/download/index";
	}

	@RequestMapping(value = "/ios", method = RequestMethod.GET)
	public String show(HttpServletRequest request,
			HttpServletResponse response, Model model) {
		OutputStream toClient = null;
		try {
			File file = downloadService.getIOSFile();
			String filename = file.getName();
			String ext = filename.substring(filename.lastIndexOf(".") + 1)
					.toUpperCase();
			byte[] buffer = FileUtils.readFileToByteArray(file);
			// 清空response
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
		return null;
	}
}
