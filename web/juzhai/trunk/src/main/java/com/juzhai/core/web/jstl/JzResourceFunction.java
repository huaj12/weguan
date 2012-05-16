package com.juzhai.core.web.jstl;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

import com.juzhai.core.image.JzImageSizeType;
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
	private static String webPostImagePath;
	private static String webIdeaImagePath;
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
			webPostImagePath = prop.getProperty("web.post.image.path");
			webIdeaImagePath = prop.getProperty("web.idea.image.path");
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
								sizeType.getType()) + fileName);
			} else {
				return fileName;
			}
		}
	}

	public static String ideaTempLogo(String fileName) {
		if (StringUtils.isNotEmpty(fileName)) {
			return StaticUtil.u(webTempImagePath + fileName);
		}
		return null;
	}

	public static String postPic(long postId, long ideaId, String fileName,
			int size) {
		if (ideaId > 0) {
			return ideaPic(ideaId, fileName, size);
		} else if (StringUtils.isEmpty(fileName) || postId <= 0) {
			return null;
		} else {
			JzImageSizeType sizeType = JzImageSizeType.getSizeTypeBySize(size);
			if (null == sizeType) {
				return null;
			}
			return StaticUtil.u(webPostImagePath
					+ ImageUtil.generateHierarchyImageWebPath(postId,
							sizeType.getType()) + fileName);
		}
	}

	public static String ideaPic(long ideaId, String fileName, int size) {
		if (StringUtils.isEmpty(fileName) || ideaId <= 0) {
			return null;
		} else {
			JzImageSizeType sizeType = JzImageSizeType.getSizeTypeBySize(size);
			if (null == sizeType) {
				return null;
			}
			return StaticUtil.u(webIdeaImagePath
					+ ImageUtil.generateHierarchyImageWebPath(ideaId,
							sizeType.getType()) + fileName);
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
				return StaticUtil.u("/images/web2/face_" + size + ".jpg");
			} else {
				return StaticUtil.u(webUserImagePath
						+ ImageUtil.generateHierarchyImageWebPath(userid,
								sizeType.getType()) + fileName);
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
