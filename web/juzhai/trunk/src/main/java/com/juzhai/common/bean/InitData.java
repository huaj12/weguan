package com.juzhai.common.bean;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import javax.annotation.PostConstruct;

import org.apache.commons.lang.StringUtils;
import org.codehaus.jackson.JsonGenerationException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import com.juzhai.core.util.JackSonSerializer;

@Component("commonInitData")
@Lazy(false)
public class InitData {

	public static final List<Long> SPECIAL_CITY_LIST = new ArrayList<Long>();
	public static final Map<Long, String> SPECIAL_CITY_QQ_MAP = new HashMap<Long, String>();

	@Value("${special.city.ids}")
	private String specialCityIds;
	@Value("${special.city.qq}")
	private String specialCityQq;

	@PostConstruct
	public void init() throws JsonGenerationException {
		initSpecialCityIds();
		initSpecialCityQq();
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
}
