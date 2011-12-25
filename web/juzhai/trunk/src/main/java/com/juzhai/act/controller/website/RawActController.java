package com.juzhai.act.controller.website;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;

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

import com.juzhai.act.exception.UploadImageException;
import com.juzhai.act.model.Category;
import com.juzhai.act.model.RawAct;
import com.juzhai.act.service.IActCategoryService;
import com.juzhai.act.service.IActImageService;
import com.juzhai.act.service.IRawActService;
import com.juzhai.core.controller.BaseController;
import com.juzhai.core.exception.NeedLoginException;
import com.juzhai.core.web.AjaxResult;
import com.juzhai.core.web.session.UserContext;
import com.juzhai.passport.model.City;
import com.juzhai.passport.model.Province;
import com.juzhai.passport.model.Town;

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
		// TODO (review)验证是否登录
		checkLoginForWeb(request);// code by wujiajun
		assembleCiteys(model);
		if (null != success) {
			model.addAttribute("success", success);
		}
		return "/web/act/rawAct/show_add_rawact";

	}

	

	@RequestMapping(value = "/addRawAct", method = RequestMethod.POST)
	@ResponseBody
	// TODO (review) 参数过多，应该封装成一个form
	public AjaxResult addAct(HttpServletRequest request, long province,
			long city, long town, Model model, String name, String detail,
			String filePath, long categoryId, String address, String startTime,
			String endTime) throws NeedLoginException {
		UserContext context = checkLoginForWeb(request);
		AjaxResult result = new AjaxResult();
		RawAct rawAct = new RawAct();
		rawAct.setAddress(address);
		rawAct.setCategoryIds(String.valueOf(categoryId));
		rawAct.setCity(city);
		rawAct.setProvince(province);
		rawAct.setTown(town);
		rawAct.setLogo(filePath);
		rawAct.setDetail(detail);
		rawAct.setName(name);
		rawAct.setCreateUid(context.getUid());
		try {
			if (StringUtils.isNotEmpty(startTime)) {
				rawAct.setStartTime(DateUtils.parseDate(startTime,
						new String[] { "yyyy-MM-dd" }));
			}
			if (StringUtils.isNotEmpty(endTime)) {
				rawAct.setEndTime(DateUtils.parseDate(endTime,
						new String[] { "yyyy-MM-dd" }));
			}
			rawAct = rawActService.addRawAct(rawAct);
		} catch (Exception e) {
			result.setError(e.getMessage(), messageSource);
			return result;
		}
		return result;
	}

	private Map<String, Object> getError(String errorCode) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("error", 1);
		map.put("message", messageSource.getMessage(errorCode, null,
				Locale.SIMPLIFIED_CHINESE));
		return map;
	}
}
