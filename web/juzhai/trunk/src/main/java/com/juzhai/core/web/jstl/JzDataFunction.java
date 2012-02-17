package com.juzhai.core.web.jstl;

import org.apache.commons.lang.StringUtils;

import com.juzhai.act.model.Category;
import com.juzhai.passport.InitData;
import com.juzhai.passport.model.City;
import com.juzhai.passport.model.Constellation;
import com.juzhai.passport.model.Thirdparty;
import com.juzhai.passport.model.Town;

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
			City cityObj = InitData.CITY_MAP.get(cityId);
			return null == cityObj ? null : cityObj.getName();
		}
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
			Town townObj = InitData.TOWN_MAP.get(townId);
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

}
