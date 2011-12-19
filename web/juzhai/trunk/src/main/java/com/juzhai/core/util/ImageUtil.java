/*
 *The code is written by 51juzhai, All rights is reserved.
 */
package com.juzhai.core.util;

import java.awt.Color;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.multipart.MultipartFile;

import com.sun.image.codec.jpeg.ImageFormatException;
import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageEncoder;
import com.juzhai.cms.bean.SizeType;

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
	public static String generateHierarchyImagePath(long id, SizeType size) {
		String path = FileUtil.generateHierarchyPath(id);
		return path + File.separator + size.getType() + File.separator;
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
	public static String generateHierarchyImageWebPath(long id, SizeType size) {
		StringBuilder path = new StringBuilder();
		path.append(FileUtil.generateHierarchyWebPath(id)).append(webSeparator)
				.append(size.getType()).append(webSeparator);
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
			return false;
		} else {
			return picUrl.indexOf(EXTERNAL_URL_CHAR) < 0;
		}

	}

	/**
	 * 剪切图片
	 * 
	 * @param srcPath
	 *            原图文件路径
	 * @param distDirectoryPath
	 *            保存新图片目录路径
	 * @param distFileName
	 *            保存新图片文件名
	 * @param srcScaledWidth
	 *            原图缩放宽度
	 * @param srcScaledHeight
	 *            原图缩放高度
	 * @param srcCutX
	 *            原图剪切起始X坐标
	 * @param srcCutY
	 *            原图剪切起始Y坐标
	 * @param distWidth
	 *            目标图片宽度
	 * @param distHeight
	 *            目标图片高度
	 * @return 剪切成功返回true，否则返回false
	 */
	public static boolean cutImage(String srcPath, String distDirectoryPath,
			String distFileName, int srcScaledWidth, int srcScaledHeight,
			int srcCutX, int srcCutY, int distWidth, int distHeight) {
		// FileOutputStream out = null;
		try {
			File srcFile = new File(srcPath);
			if (!srcFile.exists()) {
				log.error("image file dose not exist[path=" + srcPath + "].");
				return false;
			}
			Image srcImage = ImageIO.read(srcFile);
			BufferedImage bufferedImage = new BufferedImage(distWidth,
					distHeight, BufferedImage.TYPE_INT_RGB);
			bufferedImage.getGraphics().drawImage(
					srcImage.getScaledInstance(srcScaledWidth, srcScaledHeight,
							Image.SCALE_SMOOTH), 0, 0, distWidth, distHeight,
					srcCutX, srcCutY, srcCutX + distWidth,
					srcCutY + distHeight, Color.white, null);

			File distFile = FileUtil.newFile(distDirectoryPath, distFileName);
			if (null == distFile) {
				log.error("Create dist image file fail[distDirectoryPath="
						+ distDirectoryPath + ",distFileName=" + distFileName
						+ "].");
				return false;
			}
			// out = new FileOutputStream(distFile);
			ImageIO.write(bufferedImage, "JPEG", distFile);
			// JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
			// encoder.encode(bufferedImage);
			// out.close();
			return true;
		} catch (IOException e) {
			log.error(e.getMessage(), e);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		} finally {
			// if (null != out) {
			// try {
			// out.close();
			// } catch (IOException e) {
			// log.error(e.getMessage(), e);
			// }
			// }
		}
		return false;
	}

	/**
	 * 缩略图
	 * 
	 * @param srcPath
	 *            原图文件路径
	 * @param distDirectoryPath
	 *            保存新图片目录路径
	 * @param distFileName
	 *            保存新图片文件名
	 * @param scaledWidth
	 *            缩放宽度
	 * @param scaledHeight
	 *            缩放高度
	 * @return 生成缩略图成功返回true，否则返回false
	 */
	public static boolean reduceImage(String srcPath, String distDirectoryPath,
			String distFileName, int scaledWidth, int scaledHeight) {
		return cutImage(srcPath, distDirectoryPath, distFileName, scaledWidth,
				scaledHeight, 0, 0, scaledWidth, scaledHeight);
	}

	/**
	 * check上传图片的合法性
	 * 
	 * @throws AppException
	 * @return 返回1通过 0读取图片失败 -1类型错误 -2文件大小错误
	 */
	public static int validationImage(String imageSuffix, int imageSize,
			MultipartFile image) {
		String[] types = StringUtils.split(imageSuffix, "|");
		String contentType = image.getContentType();
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
			log.error("Image type[" + contentType + "] is invalid.");
			return -1;
		}

		if (image.getSize() > imageSize) {
			log.error("Image file size is too large.");
			return -2;
		}
		return 1;
	}
	/**
	 * 
	 * @param url
	 * @param newFilePath
	 * @param sizeReduceRank 
	 */
	public static void getUrlImage(String url, String directoryPath,String filename) {
		if (url == null) {
			return;
		}
		FileOutputStream out = null;
		try {
			Image image = javax.imageio.ImageIO.read(new URL(url));
			// 更改图片大小 sizeRank是原图的缩小的比例 若为2意思为将下载的文件保存为原理图片长宽的1/2
			BufferedImage bufferedImage = new BufferedImage(180, 180,
					BufferedImage.TYPE_INT_RGB);
			bufferedImage.getGraphics().drawImage(image, 0,0,  180, 180,
					null);
			File directory = new File(directoryPath);
			if (!directory.exists()) {
				directory.mkdirs();
			}
			out = new FileOutputStream(directoryPath+filename);
			encode(out, bufferedImage);
		} catch (Exception e) {
			log.error("getUrlImage is error."+e.getMessage());
		} finally {
			try {
				out.close();
			} catch (IOException e) {
				out = null;
			}
		}

	}

	// JPEG编码
	protected static boolean encode(FileOutputStream out,
			BufferedImage bufferedImage) throws ImageFormatException, IOException {
			JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
			encoder.encode(bufferedImage);
			return true;
	}
}
