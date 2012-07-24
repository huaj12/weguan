package com.juzhai.lab.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
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
import com.juzhai.passport.dao.IUserPositionDao;
import com.juzhai.passport.mapper.UserPositionMapper;
import com.juzhai.passport.model.UserPositionExample;

@Controller
@RequestMapping("app/ios")
public class IOSController extends BaseController {

	@Autowired
	private MessageSource messageSource;
	@Autowired
	private IImageManager imageManager;
	@Autowired
	private UserPositionMapper userPositionMapper;
	@Autowired
	private IUserPositionDao userPositionDao;

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

	@RequestMapping(value = "/updateloc")
	@ResponseBody
	public String updateLocation(HttpServletRequest request,
			HttpServletResponse response) {
		UserPositionExample example = new UserPositionExample();
		example.createCriteria().andUidEqualTo(1L);
		if (userPositionMapper.countByExample(example) > 0) {
			userPositionDao.update(1L, 34.53, 23.56);
		} else {
			userPositionDao.insert(1L, 10.22, 11.22);
		}
		return "success";
	}

}
