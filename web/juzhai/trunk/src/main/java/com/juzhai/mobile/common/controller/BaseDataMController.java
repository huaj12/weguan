package com.juzhai.mobile.common.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.juzhai.core.web.AjaxResult;
import com.juzhai.mobile.common.controller.view.CategoryMView;
import com.juzhai.passport.model.City;
import com.juzhai.passport.model.Profession;
import com.juzhai.passport.model.Province;
import com.juzhai.post.InitData;
import com.juzhai.post.model.Category;

@Controller
@RequestMapping(value = "mobile/base")
public class BaseDataMController {

	@RequestMapping(value = "/categoryList", method = RequestMethod.GET)
	@ResponseBody
	public AjaxResult loadCategoryList(HttpServletRequest request) {
		List<Category> categoryList = new ArrayList<Category>(
				InitData.CATEGORY_MAP.values());
		List<CategoryMView> viewList = new ArrayList<CategoryMView>(
				categoryList.size());
		for (Category category : categoryList) {
			viewList.add(new CategoryMView(category));
		}
		AjaxResult result = new AjaxResult();
		result.setResult(viewList);
		return result;
	}

	@RequestMapping(value = "/professionList", method = RequestMethod.GET)
	@ResponseBody
	public AjaxResult loadProfessionList(HttpServletRequest request) {
		List<Profession> professionList = new ArrayList<Profession>(
				com.juzhai.passport.InitData.PROFESSION_MAP.values());
		List<Map<Long, String>> mapList = new ArrayList<Map<Long, String>>(
				professionList.size());
		for (Profession p : professionList) {
			Map<Long, String> professionMap = new HashMap<Long, String>(2);
			professionMap.put(p.getId(), p.getName());
			mapList.add(professionMap);
		}
		AjaxResult result = new AjaxResult();
		result.setResult(mapList);
		return result;
	}

	@RequestMapping(value = "/provinceCityList", method = RequestMethod.GET)
	@ResponseBody
	public AjaxResult loadProvinceCityList(HttpServletRequest request) {
		Map<String, List<Map<String, Object>>> resultMap = new HashMap<String, List<Map<String, Object>>>(
				3);
		List<Map<String, Object>> provinceList = new ArrayList<Map<String, Object>>();
		List<Map<String, Object>> cityList = new ArrayList<Map<String, Object>>();
		resultMap.put("provinceList", provinceList);
		resultMap.put("cityList", cityList);
		for (Map.Entry<Long, Province> entry : com.juzhai.common.InitData.PROVINCE_MAP
				.entrySet()) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("provinceId", entry.getValue().getId());
			map.put("provinceName", entry.getValue().getName());
			provinceList.add(map);
		}
		for (Map.Entry<Long, City> entry : com.juzhai.common.InitData.CITY_MAP
				.entrySet()) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("cityId", entry.getValue().getId());
			map.put("cityName", entry.getValue().getName());
			map.put("provinceId", entry.getValue().getProvinceId());
			cityList.add(map);
		}

		AjaxResult result = new AjaxResult();
		result.setResult(resultMap);
		return result;
	}
}
