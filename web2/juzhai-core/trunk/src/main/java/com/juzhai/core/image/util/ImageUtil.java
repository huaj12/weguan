/*
 *The code is written by 51juzhai, All rights is reserved.
 */
package com.juzhai.core.image.util;

import java.io.File;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.multipart.MultipartFile;

import com.juzhai.core.util.FileUtil;

/**
 * @author wujiajun Created on 2010-8-10
 */
public class ImageUtil {

	public static final String webSeparator = "/";
	private static final Log log = LogFactory.getLog(ImageUtil.class);
	private static final int EXTERNAL_URL_CHAR = ':';
	private static final Map<String, String> IMAGE_TYPE_MAP = new HashMap<String, String>();

	static {
		IMAGE_TYPE_MAP.put("jpeg", "FFD8FF");
		IMAGE_TYPE_MAP.put("jpg", IMAGE_TYPE_MAP.get("jpeg"));
		IMAGE_TYPE_MAP.put("gif", "47494638");
		IMAGE_TYPE_MAP.put("png", "89504E47");
		IMAGE_TYPE_MAP.put("bmp", "424D");
	}

	/**
	 * 图片分层存储目录
	 * 
	 * @param id
	 *            标示符ID
	 * @param size
	 *            大小尺寸(0表示原图)
	 * @return 路劲
	 */
	public static String generateHierarchyImagePath(long id, int size) {
		String path = FileUtil.generateHierarchyPath(id);
		return path + File.separator + size + File.separator;
	}

	/**
	 * 生成图片文件名，所有图片都转成JPG
	 * 
	 * @return 文件名
	 */
	public static String generateUUIDJpgFileName() {
		return FileUtil.generateUUIDFileName("jpg");
	}

	/**
	 * 图片分层web浏览路径
	 * 
	 * @param id
	 * @param size
	 * @return 图片web路径
	 */
	public static String generateHierarchyImageWebPath(long id, int size) {
		StringBuilder path = new StringBuilder();
		path.append(FileUtil.generateHierarchyWebPath(id)).append(webSeparator)
				.append(size).append(webSeparator);
		return path.toString();
	}

	// /**
	// * 生成图片完整访问web路径
	// *
	// * @param domainContext
	// * @param id
	// * @param fileName
	// * @return 访问路径
	// */
	// public static String generateFullImageWebPath(String domainContext,
	// long id, String fileName, SizeType sizeType) {
	// StringBuilder fileUri = new StringBuilder();
	// fileUri.append(domainContext)
	// .append(ImageUtil.generateHierarchyImageWebPath(id, sizeType))
	// .append(fileName);
	// return fileUri.toString();
	// }

	/**
	 * 是否内部图片地址
	 * 
	 * @param picUrl
	 * @return
	 */
	public static boolean isInternalUrl(String picUrl) {
		if (StringUtils.isEmpty(picUrl)) {
			return true;
		} else {
			return picUrl.indexOf(EXTERNAL_URL_CHAR) < 0;
		}

	}

	/**
	 * check上传图片的合法性
	 * 
	 * @throws AppException
	 * @return 返回1通过 0读取图片失败 -1类型错误 -2文件大小错误
	 */
	public static int validationImage(String imageSuffix, int imageSize,
			MultipartFile image) {
		// String[] types = StringUtils.split(imageSuffix, "|");
		String contentType = image.getContentType().toLowerCase();
		boolean validType = false;
		// for (String type : types) {
		// if (StringUtils.contains(contentType, type)) {
		// validType = true;
		// break;
		// }
		// }
		// if (!validType) {
		// log.error("Image type[" + contentType + "] is invalid.");
		// return -1;
		// }

		// 通过文件的二进制头来判断文件类型
		validType = false;
		byte[] typeByteArray = new byte[4];
		try {
			image.getInputStream().read(typeByteArray);
		} catch (IOException e) {
			log.error("read image input stream error");
			return 0;
		}
		StringBuilder typeHexBuilder = new StringBuilder();
		for (byte b : typeByteArray) {
			typeHexBuilder.append(Integer.toHexString(b & 0xFF));
		}
		String typeHexString = typeHexBuilder.toString();
		if (StringUtils.isEmpty(typeHexString)) {
			log.error("read image input stream error");
			return 0;
		}
		for (String typeHex : IMAGE_TYPE_MAP.values()) {
			if (typeHexString.toString().toUpperCase().startsWith(typeHex)) {
				validType = true;
				break;
			}
		}
		if (!validType) {
			log.error("Image type[" + contentType + "] header is invalid.");
			return -1;
		}

		if (image.getSize() > imageSize) {
			log.error("Image file size is too large.");
			return -2;
		}
		return 1;
	}

	/**
	 * check上传图片的合法性
	 * 
	 * @throws AppException
	 * @return 返回1通过 -1类型错误 -2文件大小错误
	 */
	public static int validationImage(String imageSuffix, int imageSize,
			HttpURLConnection httpURLConnection, byte[] bytes) {
		String[] types = StringUtils.split(imageSuffix, "|");
		String contentType = httpURLConnection.getContentType();
		boolean validType = false;
		for (String type : types) {
			if (StringUtils.contains(contentType, type)) {
				validType = true;
				break;
			}
		}
		if (!validType) {
			log.error("Image type[" + contentType + "] is invalid.");
			return -1;
		}
		if (bytes == null || bytes.length < 4) {
			log.error("Image type[bytes length:" + bytes.length
					+ "] is invalid.");
			return -1;
		}
		// 通过文件的二进制头来判断文件类型
		validType = false;
		byte[] typeByteArray = new byte[4];
		for (int i = 0; i < typeByteArray.length; i++) {
			typeByteArray[i] = bytes[i];
		}
		StringBuilder typeHexBuilder = new StringBuilder();
		for (byte b : typeByteArray) {
			typeHexBuilder.append(Integer.toHexString(b & 0xFF));
		}
		String typeHexString = typeHexBuilder.toString();
		if (StringUtils.isEmpty(typeHexString)) {
			log.error("read image input stream error");
			return 0;
		}
		for (String typeHex : IMAGE_TYPE_MAP.values()) {
			if (typeHexString.toString().toUpperCase().startsWith(typeHex)) {
				validType = true;
				break;
			}
		}
		if (!validType) {
			log.error("Image type[" + contentType + "] header is invalid.");
			return -1;
		}
		// 根据响应获取文件大小
		long fileLength = httpURLConnection.getContentLength();
		if (fileLength == 0) {
			log.error("url Image file size is null");
			return -2;
		}
		if (fileLength > imageSize) {
			log.error("url Image file size is too large.");
			return -2;
		}
		return 1;
	}
}
