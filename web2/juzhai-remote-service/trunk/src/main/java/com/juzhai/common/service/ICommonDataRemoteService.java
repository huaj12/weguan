package com.juzhai.common.service;

import java.util.List;
import java.util.Map;

import com.juzhai.common.model.City;
import com.juzhai.common.model.Face;
import com.juzhai.common.model.Province;
import com.juzhai.common.model.Town;

public interface ICommonDataRemoteService {

	Map<Long, Province> getProvinceMap();

	Map<Long, City> getCityMap();

	Map<Long, Town> getTownMap();

	Map<String, Long> getCityMapping();

	Map<String, Long> getProvinceMapping();

	List<Long> getSpecialCityList();

	Map<Long, String> getSpecialCityQqMap();

	Map<String, Face> getFaceMap();
}
