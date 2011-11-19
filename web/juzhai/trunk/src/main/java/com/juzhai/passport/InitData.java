/*
 *The code is written by 51juzhai, All rights is reserved
 */
package com.juzhai.passport;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import javax.annotation.PostConstruct;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import com.juzhai.passport.bean.JoinTypeEnum;
import com.juzhai.passport.mapper.CityMapper;
import com.juzhai.passport.mapper.CityMappingMapper;
import com.juzhai.passport.mapper.ProvinceMapper;
import com.juzhai.passport.mapper.ProvinceMappingMapper;
import com.juzhai.passport.mapper.ThirdpartyMapper;
import com.juzhai.passport.model.City;
import com.juzhai.passport.model.CityExample;
import com.juzhai.passport.model.CityMapping;
import com.juzhai.passport.model.CityMappingExample;
import com.juzhai.passport.model.Province;
import com.juzhai.passport.model.ProvinceExample;
import com.juzhai.passport.model.ProvinceMapping;
import com.juzhai.passport.model.ProvinceMappingExample;
import com.juzhai.passport.model.Thirdparty;
import com.juzhai.passport.model.ThirdpartyExample;

@Component("passportInitData")
@Lazy(false)
public class InitData {

	public static final Map<Long, Thirdparty> TP_MAP = new HashMap<Long, Thirdparty>();
	public static final Map<Long, Province> PROVINCE_MAP = new HashMap<Long, Province>();
	public static final Map<Long, City> CITY_MAP = new HashMap<Long, City>();
	public static final List<Long> GUIDE_STEPS = new ArrayList<Long>();
	public static final Map<String, Long> CITY_MAPPING = new HashMap<String, Long>();
	public static final Map<String, Long> PROVINCE_MAPPING = new HashMap<String, Long>();

	@Autowired
	private ThirdpartyMapper thirdpartyMapper;
	@Autowired
	private ProvinceMapper provinceMapper;
	@Autowired
	private CityMapper cityMapper;
	@Autowired
	private CityMappingMapper cityMappingMapper;
	@Autowired
	private ProvinceMappingMapper provinceMappingMapper;
	@Value("${freshman.guide.steps}")
	private String freshmanGuideSteps;

	@PostConstruct
	public void init() {
		initTp();
		initProvince();
		initCity();
		initGuideSteps();
		initCityAndProvinceMapping();
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

	private void initGuideSteps() {
		if (StringUtils.isNotEmpty(freshmanGuideSteps)) {
			StringTokenizer st = new StringTokenizer(freshmanGuideSteps, ",");
			while (st.hasMoreTokens()) {
				GUIDE_STEPS.add(Long.valueOf(st.nextToken()));
			}
		}
	}

	private void initTp() {
		List<Thirdparty> list = thirdpartyMapper
				.selectByExample(new ThirdpartyExample());
		for (Thirdparty tp : list) {
			TP_MAP.put(tp.getId(), tp);
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

	public static City getCityByName(String name) {
		for (City city : CITY_MAP.values()) {
			if (StringUtils.equals(name, city.getName())) {
				return city;
			}
		}
		return null;
	}

	public static Thirdparty getTpByTpNameAndJoinType(String name,
			JoinTypeEnum joinType) {
		for (Thirdparty tp : TP_MAP.values()) {
			if (StringUtils.equals(tp.getName(), name)
					&& StringUtils.equals(tp.getJoinType(), joinType.getName())) {
				return tp;
			}
		}
		return null;
	}
}
