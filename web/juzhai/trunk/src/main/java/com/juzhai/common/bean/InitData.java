package com.juzhai.common.bean;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import javax.annotation.PostConstruct;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@Component("commonInitData")
@Lazy(false)
public class InitData {

	public static final List<Long> SPECIAL_CITY_LIST = new ArrayList<Long>();

	@Value("${special.city.ids}")
	private String specialCityIds;

	@PostConstruct
	public void init() {
		initSpecialCityIds();
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
