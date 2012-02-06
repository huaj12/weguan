package com.juzhai.core.web.jstl;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

import com.juzhai.core.image.LogoSizeType;
import com.juzhai.core.util.ImageUtil;
import com.juzhai.core.util.StaticUtil;
import com.juzhai.passport.InitData;
import com.juzhai.passport.model.Thirdparty;

@Component
public class JzResourceFunction {
	private static final String FILE_CONFIG_PATH = "/properties/file.properties";
	private static String webActImagePath;
	private static String webUserImagePath;
	private static String webTempImagePath;

	static {
		InputStream in = StaticUtil.class.getClassLoader().getResourceAsStream(
				FILE_CONFIG_PATH);
		if (in == null) {
			throw new RuntimeException(
					"The file: /properties/file.properties can't be found in Classpath.");
		}
		Properties prop = new Properties();
		try {
			prop.load(in);
			webActImagePath = prop.getProperty("web.act.image.path");
			webUserImagePath = prop.getProperty("web.user.image.path");
			webTempImagePath = prop.getProperty("web.temp.image.path");
		} catch (IOException e) {
			throw new RuntimeException("Load urls IO error.");
		}
	}

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
			return StaticUtil.u("/images/act_" + size + ".jpg");
		} else {
			if (ImageUtil.isInternalUrl(fileName)) {
				return StaticUtil.u(webActImagePath
						+ ImageUtil.generateHierarchyImageWebPath(actId,
								sizeType) + fileName);
			} else {
				return fileName;
			}
		}
	}

	public static String actTempLogo(String fileName) {
		if (StringUtils.isEmpty(fileName)) {
			return StaticUtil.u("/images/act_120.jpg");
		} else {
			return StaticUtil.u(webTempImagePath + fileName);
		}
	}

	public static String postPic(long postId, String fileName) {
		if (StringUtils.isEmpty(fileName) || postId <= 0) {
			return "";
		} else {
			return StaticUtil.u(webActImagePath
					+ ImageUtil.generateHierarchyImageWebPath(postId,
							LogoSizeType.ORIGINAL) + fileName);
		}
	}

	public static String ideaPic(long ideaId, String fileName) {
		if (StringUtils.isEmpty(fileName) || ideaId <= 0) {
			return "";
		} else {
			return StaticUtil.u(webActImagePath
					+ ImageUtil.generateHierarchyImageWebPath(ideaId,
							LogoSizeType.ORIGINAL) + fileName);
		}
	}

	/**
	 * 获取UserLogo
	 * 
	 * @param actId
	 * @param fileName
	 * @param size
	 * @return
	 */
	public static String userLogo(long userid, String fileName, int size) {
		if (ImageUtil.isInternalUrl(fileName)) {
			LogoSizeType sizeType = LogoSizeType.getSizeTypeBySize(size);
			if (StringUtils.isEmpty(fileName) || userid <= 0
					|| sizeType == null) {
				return StaticUtil.u("/images/face_" + size + ".jpg");
			} else {
				return StaticUtil.u(webUserImagePath
						+ ImageUtil.generateHierarchyImageWebPath(userid,
								sizeType) + fileName);
			}
		} else {
			return fileName;
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
		Thirdparty tp = InitData.TP_MAP.get(tpId);
		if (null != tp && StringUtils.isNotEmpty(tp.getUserHomeUrl())
				&& StringUtils.isNotEmpty(tpIdentity)) {
			return tp.getUserHomeUrl().replace("{0}", tpIdentity);
		}
		return "#";
	}

}
