package com.juzhai.core.image;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.commons.lang.StringUtils;

import com.juzhai.core.image.util.ImageUtil;
import com.juzhai.core.util.StaticUtil;

public class ImageUrl {

	private static final String FILE_CONFIG_PATH = "/properties/file.properties";
	private static String webUserImagePath;
	private static String webPostImagePath;
	private static String webIdeaImagePath;
	private static String webDialogContentImagePath;
	private static String webTempImagePath;

	static {
		InputStream in = ImageUrl.class.getClassLoader().getResourceAsStream(
				FILE_CONFIG_PATH);
		if (in == null) {
			throw new RuntimeException(
					"The file: /properties/file.properties can't be found in Classpath.");
		}
		Properties prop = new Properties();
		try {
			prop.load(in);
			webUserImagePath = prop.getProperty("web.user.image.path");
			webTempImagePath = prop.getProperty("web.temp.image.path");
			webPostImagePath = prop.getProperty("web.post.image.path");
			webIdeaImagePath = prop.getProperty("web.idea.image.path");
			webDialogContentImagePath = prop
					.getProperty("web.dialog.content.image.path");
		} catch (IOException e) {
			throw new RuntimeException("Load urls IO error.");
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

	public static String dialogContentPic(long dialogContentId,
			String fileName, int size) {
		if (StringUtils.isEmpty(fileName) || dialogContentId <= 0) {
			return null;
		} else {
			JzImageSizeType sizeType = JzImageSizeType.getSizeTypeBySize(size);
			if (null == sizeType) {
				return null;
			}
			return StaticUtil.u(webDialogContentImagePath
					+ ImageUtil.generateHierarchyImageWebPath(dialogContentId,
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
}
