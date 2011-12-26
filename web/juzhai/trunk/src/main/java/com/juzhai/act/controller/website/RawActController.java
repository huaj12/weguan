package com.juzhai.act.controller.website;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.juzhai.act.controller.form.AddRawActForm;
import com.juzhai.act.exception.UploadImageException;
import com.juzhai.act.model.RawAct;
import com.juzhai.act.service.IActImageService;
import com.juzhai.act.service.IRawActService;
import com.juzhai.core.controller.BaseController;
import com.juzhai.core.exception.NeedLoginException;
import com.juzhai.core.util.DateFormat;
import com.juzhai.core.web.AjaxResult;
import com.juzhai.core.web.session.UserContext;

@Controller
@RequestMapping(value = "act")
public class RawActController extends BaseController {

	@Autowired
	private MessageSource messageSource;
	@Autowired
	private IRawActService rawActService;

	@Autowired
	private IActImageService actImageService;

	@RequestMapping(value = "/kindEditor/upload")
	@ResponseBody
	public Map<String, Object> kindEditorUpload(HttpServletRequest request,
			@RequestParam("imgFile") MultipartFile imgFile) {
		try {
			UserContext context = checkLoginForWeb(request);
			String[] urls = actImageService.uploadRawActLogo(context.getUid(),
					imgFile);
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("error", 0);
			map.put("url", urls[0]);
			return map;
		} catch (UploadImageException e) {
			return getError(e.getErrorCode());
		} catch (NeedLoginException e) {
			return getError(e.getErrorCode());
		}

	}

	@RequestMapping(value = "/logo/upload", method = RequestMethod.POST)
	@ResponseBody
	public AjaxResult addActImage(HttpServletRequest request, Model model,
			@RequestParam("rawActLogo") MultipartFile rawActLogo) {
		AjaxResult result = new AjaxResult();
		try {
			UserContext context = checkLoginForWeb(request);
			try {
				String[] urls = actImageService.uploadRawActLogo(
						context.getUid(), rawActLogo);
				result.setResult(urls);
			} catch (UploadImageException e) {
				result.setError(e.getErrorCode(), messageSource);
			}
		} catch (NeedLoginException e) {
			result.setError(NeedLoginException.IS_NOT_LOGIN, messageSource);
		}
		return result;
	}

	@RequestMapping(value = "/showAddRawAct", method = RequestMethod.GET)
	public String showAddRawAct(HttpServletRequest request, Model model,
			Boolean success) throws NeedLoginException {
		checkLoginForWeb(request);// code by wujiajun
		assembleCiteys(model);
		if (null != success) {
			model.addAttribute("success", success);
		}
		return "/web/act/rawAct/show_add_rawact";

	}

	@RequestMapping(value = "/addRawAct", method = RequestMethod.POST)
	@ResponseBody
	public AjaxResult addRawAct(HttpServletRequest request, Model model,
			AddRawActForm addRawActForm) throws NeedLoginException {
		UserContext context = checkLoginForWeb(request);
		AjaxResult result = new AjaxResult();
		RawAct rawAct = converAct(addRawActForm, context.getUid());
		try {
			rawAct = rawActService.addRawAct(rawAct);
		} catch (Exception e) {
			result.setError(e.getMessage(), messageSource);
			return result;
		}
		return result;
	}

	private RawAct converAct(AddRawActForm addRawActForm, long uid) {
		// TODO (done) 封装到Service里
		RawAct rawAct = new RawAct();
		rawAct.setAddress(addRawActForm.getAddress());
		rawAct.setCategoryIds(String.valueOf(addRawActForm.getCategoryId()));
		rawAct.setCity(addRawActForm.getCity());
		rawAct.setProvince(addRawActForm.getProvince());
		rawAct.setTown(addRawActForm.getTown());
		rawAct.setLogo(addRawActForm.getFilePath());
		rawAct.setDetail(addRawActForm.getDetail());
		rawAct.setName(addRawActForm.getName());
		rawAct.setCreateUid(uid);
		String startTime = addRawActForm.getStartTime();
		try {
			if (StringUtils.isNotEmpty(startTime)) {
				rawAct.setStartTime(DateUtils.parseDate(startTime,
						DateFormat.datePattern));
			}
			String endTime = addRawActForm.getEndTime();
			if (StringUtils.isNotEmpty(endTime)) {
				rawAct.setEndTime(DateUtils.parseDate(endTime,
						DateFormat.datePattern));
			}
		} catch (Exception e) {
			return null;
		}
		return rawAct;
	}

	private Map<String, Object> getError(String errorCode) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("error", 1);
		map.put("message", messageSource.getMessage(errorCode, null,
				Locale.SIMPLIFIED_CHINESE));
		return map;
	}
}
