package com.juzhai.common;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import javax.annotation.PostConstruct;

import org.apache.commons.lang.StringUtils;
import org.codehaus.jackson.JsonGenerationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import com.juzhai.core.util.JackSonSerializer;
import com.juzhai.passport.mapper.CityMapper;
import com.juzhai.passport.mapper.CityMappingMapper;
import com.juzhai.passport.mapper.ProvinceMapper;
import com.juzhai.passport.mapper.ProvinceMappingMapper;
import com.juzhai.passport.mapper.TownMapper;
import com.juzhai.passport.model.City;
import com.juzhai.passport.model.CityExample;
import com.juzhai.passport.model.CityMapping;
import com.juzhai.passport.model.CityMappingExample;
import com.juzhai.passport.model.Province;
import com.juzhai.passport.model.ProvinceExample;
import com.juzhai.passport.model.ProvinceMapping;
import com.juzhai.passport.model.ProvinceMappingExample;
import com.juzhai.passport.model.Town;
import com.juzhai.passport.model.TownExample;

@Component("commonInitData")
@Lazy(false)
public class InitData {

	public static final Map<Long, Province> PROVINCE_MAP = new HashMap<Long, Province>();
	public static final Map<Long, City> CITY_MAP = new HashMap<Long, City>();
	public static final Map<Long, Town> TOWN_MAP = new HashMap<Long, Town>();
	public static final Map<String, Long> CITY_MAPPING = new HashMap<String, Long>();
	public static final Map<String, Long> PROVINCE_MAPPING = new HashMap<String, Long>();
	public static final List<Long> SPECIAL_CITY_LIST = new ArrayList<Long>();
	public static final Map<Long, String> SPECIAL_CITY_QQ_MAP = new HashMap<Long, String>();

	@Autowired
	private ProvinceMapper provinceMapper;
	@Autowired
	private CityMapper cityMapper;
	@Autowired
	private TownMapper townMapper;
	@Autowired
	private CityMappingMapper cityMappingMapper;
	@Autowired
	private ProvinceMappingMapper provinceMappingMapper;
	@Value("${special.city.ids}")
	private String specialCityIds;
	@Value("${special.city.qq}")
	private String specialCityQq;

	@PostConstruct
	public void init() throws JsonGenerationException {
		initSpecialCityIds();
		initSpecialCityQq();
		initProvince();
		initCity();
		initTown();
		initCityAndProvinceMapping();
	}

	private void initSpecialCityQq() throws JsonGenerationException {
		if (StringUtils.isNotEmpty(specialCityQq)) {
			Map<String, String> qqMap = JackSonSerializer.toMap(specialCityQq);
			for (Map.Entry<String, String> entry : qqMap.entrySet()) {
				SPECIAL_CITY_QQ_MAP.put(Long.valueOf(entry.getKey()),
						entry.getValue());
			}
		}

	}

	private void initSpecialCityIds() {
		if (StringUtils.isNotEmpty(specialCityIds)) {
			StringTokenizer st = new StringTokenizer(specialCityIds, ",");
			while (st.hasMoreTokens()) {
				String cityId = st.nextToken();
				SPECIAL_CITY_LIST.add(Long.valueOf(cityId));
			}
		}
	}

	private void initCityAndProvinceMapping() {
		for (CityMapping cityMapping : cityMappingMapper
				.selectByExample(new CityMappingExample())) {
			CITY_MAPPING.put(cityMapping.getMappingCityName(),
					cityMapping.getCityId());
		}

		for (ProvinceMapping provinceMapping : provinceMappingMapper
				.selectByExample(new ProvinceMappingExample())) {
			PROVINCE_MAPPING.put(provinceMapping.getMappingProvinceName(),
					provinceMapping.getProvinceId());
		}
	}

	private void initTown() {
		List<Town> list = townMapper.selectByExample(new TownExample());
		for (Town town : list) {
			TOWN_MAP.put(town.getId(), town);
		}
	}

	private void initCity() {
		List<City> list = cityMapper.selectByExample(new CityExample());
		for (City city : list) {
			CITY_MAP.put(city.getId(), city);
		}
	}

	private void initProvince() {
		List<Province> list = provinceMapper
				.selectByExample(new ProvinceExample());
		for (Province province : list) {
			PROVINCE_MAP.put(province.getId(), province);
		}
	}

	public static Town getTownByNameAndCityId(long cityId, String name) {
		for (Town town : TOWN_MAP.values()) {
			if (town.getCityId() == cityId
					&& StringUtils.equals(town.getName(), name)) {
				return town;
			}
		}
		return null;
	}

	public static City getCityByName(String name) {
		City returnCity = null;
		for (City city : CITY_MAP.values()) {
			if (StringUtils.equals(name, city.getName())) {
				returnCity = city;
				break;
			}
		}
		if (null == returnCity) {
			Long cityId = CITY_MAPPING.get(name);
			if (null != cityId) {
				returnCity = CITY_MAP.get(cityId);
			}
		}
		return returnCity;
	}
}
