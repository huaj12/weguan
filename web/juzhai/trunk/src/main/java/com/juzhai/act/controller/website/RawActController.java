package com.juzhai.act.controller.website;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;
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

import com.juzhai.act.bean.SuitAge;
import com.juzhai.act.bean.SuitGender;
import com.juzhai.act.bean.SuitStatus;
import com.juzhai.act.exception.UploadImageException;
import com.juzhai.act.model.Category;
import com.juzhai.act.model.RawAct;
import com.juzhai.act.service.IActCategoryService;
import com.juzhai.act.service.IRawActService;
import com.juzhai.act.service.IUploadImageService;
import com.juzhai.core.controller.BaseController;
import com.juzhai.core.exception.NeedLoginException;
import com.juzhai.core.web.AjaxResult;
import com.juzhai.core.web.session.UserContext;
import com.juzhai.passport.model.City;
import com.juzhai.passport.model.Province;

@Controller
@RequestMapping(value = "act")
public class RawActController extends BaseController {

	@Autowired
	private IUploadImageService uploadImageService;
	@Autowired
	private MessageSource messageSource;
	@Autowired
	IRawActService rawActService;
	@Autowired
	private IActCategoryService actCategoryService;
	
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

	@RequestMapping(value = "/ajax/temp/addActImage")
	@ResponseBody
	public Map<String, String> addActImage(HttpServletRequest request,
			Model model) {
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		MultipartFile imgFile = multipartRequest.getFile("fileupload");
		Map<String, String> map = new HashMap<String, String>();
		String url = "";
		if (imgFile != null && imgFile.getSize() > 0) {
			UUID uuid = UUID.randomUUID();
			String fileName = uuid.toString() + "."
					+ uploadImageService.getImgType(imgFile);
			try {
				url = uploadImageService.uploadActTempImg(fileName, imgFile);
			} catch (UploadImageException e) {
				return getError(e.getMessage());
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
		assembleCiteys(model);
		return "/web/act/showAddRawAct";

	}
	
	private void assembleCiteys(Model model) {
		List<City> citys = new ArrayList<City>();
		List<Province> provinces = new ArrayList<Province>();
		for (Entry<Long, City> entry : com.juzhai.passport.InitData.CITY_MAP
				.entrySet()) {
			citys.add(entry.getValue());
		}
		for (Entry<Long, Province> entry : com.juzhai.passport.InitData.PROVINCE_MAP
				.entrySet()) {
			provinces.add(entry.getValue());
		}
		List<Category> categoryList =actCategoryService.findAllCategory();
		model.addAttribute("categoryList", categoryList);
		model.addAttribute("citys", citys);
		model.addAttribute("provinces", provinces);
	}
	

	@RequestMapping(value = "/web/ajax/addAct", method = RequestMethod.POST)
	@ResponseBody
	public AjaxResult addAct(HttpServletRequest request,Long province,Long city, Model model,
			String name,String detail,String logo,String categoryIds,String address,String startTime,String endTime) throws NeedLoginException {
		UserContext context = checkLoginForApp(request);
		long uid = context.getUid();
		AjaxResult result = new AjaxResult();
		RawAct rawAct=new RawAct();
		rawAct.setAddress(address);
		rawAct.setCategoryIds(categoryIds);
		rawAct.setCity(city);
		rawAct.setProvince(province);
		rawAct.setLogo(logo);
		rawAct.setDetail(detail);
		rawAct.setName(name);
		try {
			if (StringUtils.isNotEmpty(startTime)) {
				rawAct.setStartTime( DateUtils.parseDate(startTime,
						new String[] { "yyyy-MM-dd" }));
			}
			if (StringUtils.isNotEmpty(endTime)) {
				rawAct.setEndTime( DateUtils.parseDate(endTime,
						new String[] { "yyyy-MM-dd" }));
			}
			if (rawAct != null) {
				rawAct.setCreateUid(uid);
			}
			rawAct = rawActService.addRawAct(rawAct);
		} catch (Exception e) {
			result.setError(e.getMessage(), messageSource);
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
