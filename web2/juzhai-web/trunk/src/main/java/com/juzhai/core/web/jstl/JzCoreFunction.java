package com.juzhai.core.web.jstl;

import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;

import com.juzhai.common.InitData;
import com.juzhai.common.model.City;
import com.juzhai.core.image.LogoSizeType;
import com.juzhai.core.image.util.ImageUtil;
import com.juzhai.core.util.StaticUtil;
import com.juzhai.core.util.TextTruncateUtil;
import com.juzhai.passport.model.Thirdparty;

@Deprecated
public class JzCoreFunction {

	/**
	 * 静态资源路径生成
	 * 
	 * @param uri
	 * @return
	 */
	public static String u(String uri) {
		return StaticUtil.u(uri);
	}

	/**
	 * 截取字符串
	 * 
	 * @param originalText
	 * @param targetLength
	 * @param suffix
	 * @return
	 */
	public static String truncate(String originalText, int targetLength,
			String suffix) {
		return TextTruncateUtil.truncate(originalText, targetLength, suffix);
	}

	/**
	 * 比较时间
	 * 
	 * @param date
	 * @return
	 */
	public static boolean dateAfter(Date date) {
		return date.before(new Date());
	}

	/**
	 * 多少秒之前
	 * 
	 * @param date
	 * @return
	 */
	public static long beforeSeconds(Date date) {
		return (System.currentTimeMillis() - date.getTime()) / 1000;
	}

	/**
	 * 多少分钟之前
	 * 
	 * @param date
	 * @return
	 */
	public static long beforeMinutes(Date date) {
		return (System.currentTimeMillis() - date.getTime()) / 60000;
	}

	/**
	 * 多少天之前
	 * 
	 * @param date
	 * @return
	 */
	public static long beforeDays(Date date) {
		return ((DateUtils.truncate(new Date(), Calendar.DATE).getTime() - DateUtils
				.truncate(date, Calendar.DATE).getTime()) / (24 * 60 * 60 * 1000));
	}

	/**
	 * 获取ActLogo
	 * 
	 * @param actId
	 * @param fileName
	 * @param size
	 * @return
	 */
	public static String actLogo(long actId, String fileName, int size) {
		LogoSizeType sizeType = LogoSizeType.getSizeTypeBySize(size);
		if (StringUtils.isEmpty(fileName) || actId <= 0 || sizeType == null) {
			return StaticUtil.u("/images/" + size + "_defaultActLogo.gif");
		} else {
			if (ImageUtil.isInternalUrl(fileName)) {
				return StaticUtil.u("/upload/act/"
						+ ImageUtil.generateHierarchyImageWebPath(actId,
								sizeType.getType()) + fileName);
			} else {
				return fileName;
			}
		}
	}

	/**
	 * 第三方用户首页
	 * 
	 * @param tpIdentity
	 * @param tpId
	 * @return
	 */
	public static String tpHomeUrl(String tpIdentity, long tpId) {
		Thirdparty tp = com.juzhai.passport.InitData.TP_MAP.get(tpId);
		if (null != tp && StringUtils.isNotEmpty(tp.getUserHomeUrl())
				&& StringUtils.isNotEmpty(tpIdentity)) {
			return tp.getUserHomeUrl().replace("{0}", tpIdentity);
		}
		return "#";
	}

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
	 * 获取appId
	 * 
	 * @param tpId
	 * @return
	 */
	public static String appId(long tpId) {
		Thirdparty tp = com.juzhai.passport.InitData.TP_MAP.get(tpId);
		return tp == null ? StringUtils.EMPTY : tp.getAppId();
	}

	/**
	 * 获取appKey
	 * 
	 * @param tpId
	 * @return
	 */
	public static String appKey(long tpId) {
		Thirdparty tp = com.juzhai.passport.InitData.TP_MAP.get(tpId);
		return tp == null ? StringUtils.EMPTY : tp.getAppKey();
	}

}
