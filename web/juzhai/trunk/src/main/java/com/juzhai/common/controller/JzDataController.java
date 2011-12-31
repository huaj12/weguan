package com.juzhai.common.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.juzhai.passport.model.City;
import com.juzhai.passport.model.Town;

@Controller
@RequestMapping(value = "base")
public class JzDataController {
	@RequestMapping(value = "/selectCity", method = RequestMethod.GET)
	public String selectCity(Model model, String proId) {
		List<City> citys = new ArrayList<City>();
		for (Entry<Long, City> entry : com.juzhai.passport.InitData.CITY_MAP
				.entrySet()) {
			if (proId.equals(String.valueOf(entry.getValue().getProvinceId()))) {
				citys.add(entry.getValue());
			}
		}
		model.addAttribute("citys", citys);
		return "common/ajax/citys_list";
	}
	
	@RequestMapping(value = "/selectTown", method = RequestMethod.GET)
	public String selectTown(Model model, String cityId) {
		List<Town> towns = new ArrayList<Town>();
		for (Entry<Long, Town> entry : com.juzhai.passport.InitData.TOWN_MAP
				.entrySet()) {
			if (cityId.equals(String.valueOf(entry.getValue().getCityId()))) {
				towns.add(entry.getValue());
			}
		}
		model.addAttribute("towns", towns);
		return "common/ajax/towns_list";
	}
}
