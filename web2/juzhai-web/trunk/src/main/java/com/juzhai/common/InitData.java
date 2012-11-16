package com.juzhai.common;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import javax.annotation.PostConstruct;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.codehaus.jackson.JsonGenerationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import com.juzhai.common.mapper.CityMapper;
import com.juzhai.common.mapper.CityMappingMapper;
import com.juzhai.common.mapper.FaceMapper;
import com.juzhai.common.mapper.IosEmojiMapper;
import com.juzhai.common.mapper.ProvinceMapper;
import com.juzhai.common.mapper.ProvinceMappingMapper;
import com.juzhai.common.mapper.TownMapper;
import com.juzhai.common.model.City;
import com.juzhai.common.model.CityExample;
import com.juzhai.common.model.CityMapping;
import com.juzhai.common.model.CityMappingExample;
import com.juzhai.common.model.Face;
import com.juzhai.common.model.FaceExample;
import com.juzhai.common.model.IosEmoji;
import com.juzhai.common.model.IosEmojiExample;
import com.juzhai.common.model.Province;
import com.juzhai.common.model.ProvinceExample;
import com.juzhai.common.model.ProvinceMapping;
import com.juzhai.common.model.ProvinceMappingExample;
import com.juzhai.common.model.Town;
import com.juzhai.common.model.TownExample;
import com.juzhai.common.util.IOSEmojiUtil;
import com.juzhai.core.util.JackSonSerializer;
import com.juzhai.core.util.StaticUtil;

@Component("commonInitData")
@Lazy(false)
public class InitData {

	private final Log log = LogFactory.getLog(getClass());

	public static final Map<Long, Province> PROVINCE_MAP = new HashMap<Long, Province>();
	public static final Map<Long, City> CITY_MAP = new HashMap<Long, City>();
	public static final Map<Long, Town> TOWN_MAP = new HashMap<Long, Town>();
	public static final Map<String, Long> CITY_MAPPING = new HashMap<String, Long>();
	public static final Map<String, Long> PROVINCE_MAPPING = new HashMap<String, Long>();
	public static final List<Long> SPECIAL_CITY_LIST = new ArrayList<Long>();
	public static final Map<Long, String> SPECIAL_CITY_QQ_MAP = new HashMap<Long, String>();
	public static final Map<String, Face> FACE_MAP = new LinkedHashMap<String, Face>();

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
	private IosEmojiMapper iosEmojiMapper;
	@Autowired
	private FaceMapper faceMapper;
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
		initFace();
		initEmoji();
	}

	private void initFace() {
		FaceExample example = new FaceExample();
		example.setOrderByClause("id asc");
		List<Face> faceList = faceMapper.selectByExample(example);
		for (Face face : faceList) {
			FACE_MAP.put(face.getName(), face);
		}
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

	private void initEmoji() {
		List<IosEmoji> iosEmojiList = iosEmojiMapper
				.selectByExample(new IosEmojiExample());
		String[] i5Emjs = new String[iosEmojiList.size()];
		String[] i4Emjs = new String[iosEmojiList.size()];
		String[] webEmjs = new String[iosEmojiList.size()];
		for (int i = 0; i < iosEmojiList.size(); i++) {
			try {
				i5Emjs[i] = IOSEmojiUtil.hexstr2String(iosEmojiList.get(i)
						.getUtf8());
				i4Emjs[i] = IOSEmojiUtil.sbunicode2utfString(iosEmojiList
						.get(i).getSbunicode());
				webEmjs[i] = "<img src=\"" + StaticUtil.getPrefixImage()
						+ "/images/face/emoji/"
						+ iosEmojiList.get(i).getFilename()
						+ "\" width=\"22\" height=\"22\" />";
			} catch (UnsupportedEncodingException e) {
				log.error(e.getMessage(), e);
			}
		}
		IOSEmojiUtil.initIosEmoji(i5Emjs, i4Emjs, null, null, webEmjs);
	}
}
