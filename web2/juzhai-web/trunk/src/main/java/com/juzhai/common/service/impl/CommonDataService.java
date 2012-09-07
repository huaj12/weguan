package com.juzhai.common.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.juzhai.common.InitData;
import com.juzhai.common.model.City;
import com.juzhai.common.model.Face;
import com.juzhai.common.model.Province;
import com.juzhai.common.model.Town;
import com.juzhai.common.service.ICommonDataRemoteService;

@Service
public class CommonDataService implements ICommonDataRemoteService {

	@Override
	public Map<Long, Province> getProvinceMap() {
		return InitData.PROVINCE_MAP;
	}

	@Override
	public Map<Long, City> getCityMap() {
		return InitData.CITY_MAP;
	}

	@Override
	public Map<Long, Town> getTownMap() {
		return InitData.TOWN_MAP;
	}

	@Override
	public Map<String, Long> getCityMapping() {
		return InitData.CITY_MAPPING;
	}

	@Override
	public Map<String, Long> getProvinceMapping() {
		return InitData.PROVINCE_MAPPING;
	}

	@Override
	public List<Long> getSpecialCityList() {
		return InitData.SPECIAL_CITY_LIST;
	}

	@Override
	public Map<Long, String> getSpecialCityQqMap() {
		return InitData.SPECIAL_CITY_QQ_MAP;
	}

	@Override
	public Map<String, Face> getFaceMap() {
		return InitData.FACE_MAP;
	}

}
