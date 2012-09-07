package com.juzhai.common.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.juzhai.common.InitData;
import com.juzhai.common.model.City;
import com.juzhai.common.model.Province;
import com.juzhai.common.model.Town;
import com.juzhai.core.web.AjaxResult;

@Controller
@RequestMapping(value = "base")
public class JzDataController {

	@RequestMapping(value = "/initProvince", method = RequestMethod.GET)
	@ResponseBody
	public AjaxResult initProvince(HttpServletRequest request) {
		Map<Long, String> map = new HashMap<Long, String>();
		for (Map.Entry<Long, Province> entry : InitData.PROVINCE_MAP.entrySet()) {
			map.put(entry.getKey(), entry.getValue().getName());
		}
		AjaxResult result = new AjaxResult();
		result.setResult(map);
		return result;
	}

	@RequestMapping(value = "/initCity", method = RequestMethod.GET)
	@ResponseBody
	public AjaxResult initCity(HttpServletRequest request, long provinceId) {
		Map<Long, String> map = new HashMap<Long, String>();
		for (Entry<Long, City> entry : InitData.CITY_MAP.entrySet()) {
			if (provinceId == entry.getValue().getProvinceId()) {
				map.put(entry.getKey(), entry.getValue().getName());
			}
		}
		AjaxResult result = new AjaxResult();
		result.setResult(map);
		return result;
	}

	@RequestMapping(value = "/initTown", method = RequestMethod.GET)
	@ResponseBody
	public AjaxResult initTown(HttpServletRequest request, long cityId) {
		Map<Long, String> map = new HashMap<Long, String>();
		for (Entry<Long, Town> entry : InitData.TOWN_MAP.entrySet()) {
			if (cityId == entry.getValue().getCityId()) {
				map.put(entry.getKey(), entry.getValue().getName());
			}
		}
		AjaxResult result = new AjaxResult();
		result.setResult(map);
		if (MapUtils.isEmpty(map)) {
			result.setSuccess(false);
		}
		return result;
	}

	@RequestMapping(value = "/selectProvince", method = RequestMethod.GET)
	public String selectProvince(Model model, String proId) {
		List<City> citys = new ArrayList<City>();
		for (Entry<Long, City> entry : InitData.CITY_MAP.entrySet()) {
			if (proId.equals(String.valueOf(entry.getValue().getProvinceId()))) {
				citys.add(entry.getValue());
			}
		}
		model.addAttribute("citys", citys);
		return "common/ajax/citys_list";
	}

	@RequestMapping(value = "/selectCity", method = RequestMethod.GET)
	public String selectCity(Model model, String cityId) {
		List<Town> towns = new ArrayList<Town>();
		for (Entry<Long, Town> entry : InitData.TOWN_MAP.entrySet()) {
			if (String.valueOf(entry.getValue().getCityId()).equals(cityId)) {
				towns.add(entry.getValue());
			}
		}
		if (CollectionUtils.isNotEmpty(towns)) {
			model.addAttribute("towns", towns);
		}
		return "common/ajax/towns_list";
	}
}
