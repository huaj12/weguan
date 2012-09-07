package com.juzhai.lab.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.juzhai.core.controller.BaseController;
import com.juzhai.core.exception.NeedLoginException;
import com.juzhai.core.exception.UploadImageException;
import com.juzhai.core.image.manager.IImageManager;
import com.juzhai.core.web.AjaxResult;

public class IOSController extends BaseController {

	@Autowired
	private MessageSource messageSource;
	@Autowired
	private IImageManager imageManager;

	@RequestMapping(value = "/upload", method = RequestMethod.POST)
	@ResponseBody
	public AjaxResult upload(HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam("photo") MultipartFile photo)
			throws NeedLoginException {
		checkLoginForWeb(request);
		AjaxResult result = new AjaxResult();
		try {
			imageManager.checkImage(photo);
			String[] urls = imageManager.uploadTempImage(photo);
			result.setResult(urls[0]);
		} catch (UploadImageException e) {
			result.setError(e.getErrorCode(), messageSource);
		}
		return result;
	}
}
