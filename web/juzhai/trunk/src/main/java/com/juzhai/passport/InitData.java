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
import com.juzhai.passport.mapper.ConstellationMapper;
import com.juzhai.passport.mapper.ProfessionMapper;
import com.juzhai.passport.mapper.ProvinceMapper;
import com.juzhai.passport.mapper.ProvinceMappingMapper;
import com.juzhai.passport.mapper.ThirdpartyMapper;
import com.juzhai.passport.mapper.TownMapper;
import com.juzhai.passport.model.City;
import com.juzhai.passport.model.CityExample;
import com.juzhai.passport.model.CityMapping;
import com.juzhai.passport.model.CityMappingExample;
import com.juzhai.passport.model.Constellation;
import com.juzhai.passport.model.ConstellationExample;
import com.juzhai.passport.model.Profession;
import com.juzhai.passport.model.ProfessionExample;
import com.juzhai.passport.model.Province;
import com.juzhai.passport.model.ProvinceExample;
import com.juzhai.passport.model.ProvinceMapping;
import com.juzhai.passport.model.ProvinceMappingExample;
import com.juzhai.passport.model.Thirdparty;
import com.juzhai.passport.model.ThirdpartyExample;
import com.juzhai.passport.model.Town;
import com.juzhai.passport.model.TownExample;

@Component("passportInitData")
@Lazy(false)
public class InitData {

	public static final Map<Long, Thirdparty> TP_MAP = new HashMap<Long, Thirdparty>();
	public static final Map<Long, Province> PROVINCE_MAP = new HashMap<Long, Province>();
	public static final Map<Long, City> CITY_MAP = new HashMap<Long, City>();
	public static final Map<Long, Profession> PROFESSION_MAP = new HashMap<Long, Profession>();
	public static final Map<Long, Town> TOWN_MAP = new HashMap<Long, Town>();
	public static final List<Long> GUIDE_STEPS = new ArrayList<Long>();
	public static final Map<String, Long> CITY_MAPPING = new HashMap<String, Long>();
	public static final Map<String, Long> PROVINCE_MAPPING = new HashMap<String, Long>();
	public static final Map<Long, Constellation> CONSTELLATION_MAP = new HashMap<Long, Constellation>();
	@Autowired
	private ThirdpartyMapper thirdpartyMapper;
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
	@Autowired
	private ProfessionMapper professionMapper;
	@Autowired
	private ConstellationMapper constellationMapper;
	@Value("${freshman.guide.steps}")
	private String freshmanGuideSteps;

	@PostConstruct
	public void init() {
		initTp();
		initProvince();
		initCity();
		initTown();
		// initGuideSteps();
		initCityAndProvinceMapping();
		initConstellation();
		initProfession();
	}

	// 职业
	private void initProfession() {
		List<Profession> list = professionMapper
				.selectByExample(new ProfessionExample());
		for (Profession profession : list) {
			PROFESSION_MAP.put(profession.getId(), profession);
		}
	}

	private void initConstellation() {
		List<Constellation> list = constellationMapper
				.selectByExample(new ConstellationExample());
		for (Constellation constellation : list) {
			CONSTELLATION_MAP.put(constellation.getId(), constellation);
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

	public static Constellation getConstellation(int month, int date) {
		if (month <= 0 || date <= 0) {
			return null;
		}
		for (Constellation c : CONSTELLATION_MAP.values()) {
			if (c.getStartMonth() + 1 == month
					|| (c.getStartMonth() == 12 && month == 1)
					|| (c.getStartMonth() == month && c.getStartDate() <= date)) {
				if (c.getEndMonth() == month + 1
						|| (c.getEndMonth() == 1 && month == 12)
						|| (c.getEndMonth() == month && c.getEndDate() >= date)) {
					return c;
				}
			}
		}
		return null;
	}
}
