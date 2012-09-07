package com.juzhai.mobile;

import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;

import com.juzhai.common.model.City;
import com.juzhai.common.model.Province;
import com.juzhai.common.model.Town;
import com.juzhai.common.service.ICommonDataRemoteService;
import com.juzhai.core.util.SpringUtil;
import com.juzhai.passport.bean.JoinTypeEnum;
import com.juzhai.passport.model.Constellation;
import com.juzhai.passport.model.Profession;
import com.juzhai.passport.model.Thirdparty;
import com.juzhai.passport.service.IPassportDataRemoteService;
import com.juzhai.post.model.Category;
import com.juzhai.post.service.IPostDataRemoteService;

public class InitData {

	private static Map<Long, Province> PROVINCE_MAP;
	private static Map<Long, City> CITY_MAP;
	private static Map<Long, Town> TOWN_MAP;
	private static Map<Long, Category> CATEGORY_MAP;
	private static Map<Long, Thirdparty> TP_MAP;
	private static Map<Long, Profession> PROFESSION_MAP;
	private static Map<Long, Constellation> CONSTELLATION_MAP;

	public static Map<Long, Province> getProvinceMap() {
		if (MapUtils.isEmpty(PROVINCE_MAP)) {
			ICommonDataRemoteService commonDataService = (ICommonDataRemoteService) SpringUtil
					.getBean("commonDataService");
			PROVINCE_MAP = commonDataService.getProvinceMap();
		}
		return PROVINCE_MAP;
	}

	public static Map<Long, City> getCityMap() {
		if (MapUtils.isEmpty(CITY_MAP)) {
			ICommonDataRemoteService commonDataService = (ICommonDataRemoteService) SpringUtil
					.getBean("commonDataService");
			CITY_MAP = commonDataService.getCityMap();
		}
		return CITY_MAP;
	}

	public static Map<Long, Town> getTownMap() {
		if (MapUtils.isEmpty(TOWN_MAP)) {
			ICommonDataRemoteService commonDataService = (ICommonDataRemoteService) SpringUtil
					.getBean("commonDataService");
			TOWN_MAP = commonDataService.getTownMap();
		}
		return TOWN_MAP;
	}

	public static Map<Long, Category> getCategoryMap() {
		if (MapUtils.isEmpty(CATEGORY_MAP)) {
			IPostDataRemoteService postDataService = (IPostDataRemoteService) SpringUtil
					.getBean("postDataService");
			CATEGORY_MAP = postDataService.getCategoryMap();
		}
		return CATEGORY_MAP;
	}

	public static Map<Long, Thirdparty> getTpMap() {
		if (MapUtils.isEmpty(TP_MAP)) {
			IPassportDataRemoteService passportDataService = (IPassportDataRemoteService) SpringUtil
					.getBean("passportDataService");
			TP_MAP = passportDataService.getTpMap();
		}
		return TP_MAP;
	}

	public static Thirdparty getTpByTpNameAndJoinType(String name,
			JoinTypeEnum joinType) {
		for (Thirdparty tp : getTpMap().values()) {
			if (StringUtils.equals(tp.getName(), name)
					&& StringUtils.equals(tp.getJoinType(), joinType.getName())) {
				return tp;
			}
		}
		return null;
	}

	public static Map<Long, Profession> getProfessionMap() {
		if (MapUtils.isEmpty(PROFESSION_MAP)) {
			IPassportDataRemoteService passportDataService = (IPassportDataRemoteService) SpringUtil
					.getBean("passportDataService");
			PROFESSION_MAP = passportDataService.getProfessionMap();
		}
		return PROFESSION_MAP;
	}

	public static Map<Long, Constellation> getConstellationMap() {
		if (MapUtils.isEmpty(CONSTELLATION_MAP)) {
			IPassportDataRemoteService passportDataService = (IPassportDataRemoteService) SpringUtil
					.getBean("passportDataService");
			CONSTELLATION_MAP = passportDataService.getConstellationMap();
		}
		return CONSTELLATION_MAP;
	}

	public static boolean hasTown(long cityId) {
		boolean flag = false;
		for (Entry<Long, Town> entry : getTownMap().entrySet()) {
			if (cityId == entry.getValue().getCityId()) {
				flag = true;
				break;
			}
		}
		return flag;
	}
}
