package com.juzhai.core.web.jstl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map.Entry;

import org.apache.commons.lang.StringUtils;

import com.juzhai.common.model.City;
import com.juzhai.common.model.Town;
import com.juzhai.passport.InitData;
import com.juzhai.passport.model.Constellation;
import com.juzhai.passport.model.Thirdparty;
import com.juzhai.post.model.Category;

public class JzDataFunction {

	/**
	 * 获得城市名称
	 * 
	 * @param cityId
	 * @return
	 */
	public static String cityName(long cityId) {
		if (cityId <= 0) {
			return null;
		} else {
			City cityObj = com.juzhai.common.InitData.CITY_MAP.get(cityId);
			return null == cityObj ? null : cityObj.getName();
		}
	}

	/**
	 * 特定城市列表
	 * 
	 * @return
	 */
	public static List<City> specialCityList() {
		List<City> cityList = new ArrayList<City>(
				com.juzhai.common.InitData.SPECIAL_CITY_LIST.size());
		for (long cityId : com.juzhai.common.InitData.SPECIAL_CITY_LIST) {
			City city = com.juzhai.common.InitData.CITY_MAP.get(cityId);
			if (null != city) {
				cityList.add(city);
			}
		}
		return cityList;
	}

	/**
	 * 获取区列表
	 * 
	 * @param cityId
	 * @return
	 */
	public static List<Town> townList(long cityId) {
		if (cityId <= 0) {
			return Collections.emptyList();
		}
		List<Town> townList = new ArrayList<Town>();
		for (Entry<Long, Town> entry : com.juzhai.common.InitData.TOWN_MAP
				.entrySet()) {
			if (cityId == entry.getValue().getCityId()) {
				townList.add(entry.getValue());
			}
		}
		return townList;
	}

	/**
	 * 获得区名称
	 * 
	 * @param cityId
	 * @return
	 */
	public static String townName(long townId) {
		if (townId <= 0) {
			return null;
		} else {
			Town townObj = com.juzhai.common.InitData.TOWN_MAP.get(townId);
			return null == townObj ? null : townObj.getName();
		}
	}

	/**
	 * 获取星座
	 * 
	 * @param constellationId
	 * @return
	 */
	public static String constellationName(long constellationId) {
		if (constellationId <= 0) {
			return null;
		} else {
			Constellation c = InitData.CONSTELLATION_MAP.get(constellationId);
			return null == c ? null : c.getName();
		}
	}

	/**
	 * 获取分类名称
	 * 
	 * @param categoryId
	 * @return
	 */
	public static String categoryName(long categoryId) {
		if (categoryId <= 0) {
			return null;
		} else {
			Category c = com.juzhai.post.InitData.CATEGORY_MAP.get(categoryId);
			return null == c ? null : c.getName();
		}
	}

	/**
	 * 获取分类名称
	 * 
	 * @param categoryId
	 * @return
	 */
	public static String qqGroup(long cityId) {
		if (cityId <= 0) {
			return null;
		} else {
			return com.juzhai.common.InitData.SPECIAL_CITY_QQ_MAP.get(cityId);
		}
	}

	/**
	 * 获取appId
	 * 
	 * @param tpId
	 * @return
	 */
	public static String appId(long tpId) {
		Thirdparty tp = InitData.TP_MAP.get(tpId);
		return tp == null ? StringUtils.EMPTY : tp.getAppId();
	}

	/**
	 * 获取appKey
	 * 
	 * @param tpId
	 * @return
	 */
	public static String appKey(long tpId) {
		Thirdparty tp = InitData.TP_MAP.get(tpId);
		return tp == null ? StringUtils.EMPTY : tp.getAppKey();
	}

	/**
	 * 获取第三方名字
	 * 
	 * @param tpId
	 * @return
	 */
	public static String tpName(long tpId) {
		Thirdparty tp = InitData.TP_MAP.get(tpId);
		return tp == null ? StringUtils.EMPTY : tp.getName();
	}
}
