package com.juzhai.act.controller.website;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.juzhai.act.exception.UploadImageException;
import com.juzhai.act.model.RawAct;
import com.juzhai.act.service.IRawActService;
import com.juzhai.act.service.IUploadImageService;
import com.juzhai.core.controller.BaseController;
import com.juzhai.core.exception.NeedLoginException;
import com.juzhai.core.web.AjaxResult;
import com.juzhai.core.web.session.UserContext;

@Controller
@RequestMapping(value = "act")
public class RawActController extends BaseController {

	@Autowired
	private IUploadImageService uploadImageService;
	@Autowired
	private MessageSource messageSource;
	@Autowired
	IRawActService rawActService;

	@RequestMapping(value = "/kindEditor/upload")
	@ResponseBody
	public Map<String, String> kindEditorUpload(HttpServletRequest request) {
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		MultipartFile imgFile = multipartRequest.getFile("imgFile");
		Map<String, String> map = new HashMap<String, String>();
		String url = "";
		if (imgFile != null && imgFile.getSize() > 0) {
			UUID uuid = UUID.randomUUID();
			String fileName = uuid.toString() + "."
					+ uploadImageService.getImgType(imgFile);
			try {
				url = uploadImageService.uploadEditorTempImg(fileName, imgFile);
			} catch (UploadImageException e) {
				getError(e.getMessage());
			}
		} else {
			return getError(messageSource.getMessage(
					UploadImageException.UPLOAD_FILE_ISNULL, null,
					Locale.SIMPLIFIED_CHINESE));
		}
		map.put("error", "0");
		map.put("url", url);
		return map;
	}

	@RequestMapping(value = "/ajax/temp/addActImage", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, String> addActImage(HttpServletRequest request,
			Model model, MultipartFile imgFile) {
		Map<String, String> map = new HashMap<String, String>();
		String url = "";
		if (imgFile != null && imgFile.getSize() > 0) {
			UUID uuid = UUID.randomUUID();
			String fileName = uuid.toString() + "."
					+ uploadImageService.getImgType(imgFile);
			try {
				url = uploadImageService.uploadActTempImg(fileName, imgFile);
			} catch (UploadImageException e) {
				getError(e.getMessage());
			}
		} else {
			return getError(messageSource.getMessage(
					UploadImageException.UPLOAD_FILE_ISNULL, null,
					Locale.SIMPLIFIED_CHINESE));
		}
		map.put("error", "0");
		map.put("url", url);
		return map;
	}

	@RequestMapping(value = "/showAddRawAct", method = RequestMethod.GET)
	public String showAddRawAct(HttpServletRequest request, Model model)
			throws NeedLoginException {

		return "/web/act/showAddRawAct";

	}

	@RequestMapping(value = "/web/ajax/addAct", method = RequestMethod.POST)
	@ResponseBody
	public AjaxResult addAct(HttpServletRequest request, Model model,
			RawAct rawAct) throws NeedLoginException {
		UserContext context = checkLoginForApp(request);
		long uid = context.getUid();
		AjaxResult result = new AjaxResult();
		try {
			if (rawAct != null) {
				rawAct.setCreateUid(uid);
			}
			rawAct = rawActService.addRawAct(rawAct);
		} catch (Exception e) {
			result.setErrorCode(e.getMessage());
			result.setSuccess(false);
			return result;
		}
		result.setSuccess(true);
		return result;
	}

	private Map<String, String> getError(String message) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("error", "1");
		map.put("message", message);
		return map;
	}
}
