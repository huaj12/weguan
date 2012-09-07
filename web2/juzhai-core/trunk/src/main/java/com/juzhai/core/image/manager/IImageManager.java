package com.juzhai.core.image.manager;

import java.awt.Image;
import java.io.File;
import java.net.HttpURLConnection;
import java.util.List;

import org.springframework.context.NoSuchMessageException;
import org.springframework.web.multipart.MultipartFile;

import com.juzhai.core.exception.UploadImageException;
import com.juzhai.core.image.bean.MarkFont;

public interface IImageManager {

	/**
	 * 上传临时文件
	 * 
	 * @param uid
	 * @param file
	 * @return [0]web访问地址，[1]file地址
	 * @throws NoSuchMessageException
	 * @throws UploadImageException
	 */
	String[] uploadTempImage(MultipartFile image) throws UploadImageException;

	/**
	 * 上传图片
	 * 
	 * @param directoryPath
	 * @param image
	 * @return fileName
	 * @throws UploadImageException
	 */
	String uploadImage(String directoryPath, MultipartFile image)
			throws UploadImageException;

	/**
	 * 复制图片
	 * 
	 * @param directoryPath
	 * @param fileName
	 * @param srcFile
	 */
	boolean copyImage(String directoryPath, String fileName, File srcFile);

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
	boolean cutImage(String srcPath, String distDirectoryPath,
			String distFileName, int srcScaledWidth, int srcScaledHeight,
			int srcCutX, int srcCutY, int distWidth, int distHeight);

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
	boolean reduceImage(String srcPath, String distDirectoryPath,
			String distFileName, int scaledWidth, int scaledHeight);

	/**
	 * 缩略图
	 * 
	 * @param srcPath
	 *            原图文件路径
	 * @param distDirectoryPath
	 *            保存新图片目录路径
	 * @param distFileName
	 *            保存新图片文件名
	 * @param scaledWidthOrHeight
	 *            缩放宽度
	 * @return 生成缩略图成功返回true，否则返回false
	 */
	boolean reduceImage(String srcPath, String distDirectoryPath,
			String distFileName, int scaledWidthOrHeight);

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
	 * @return 生成缩略图成功返回true，否则返回false
	 */
	boolean reduceImageWidth(String srcPath, String distDirectoryPath,
			String distFileName, int scaledWidth);

	/**
	 * 获取临时目录路径
	 * 
	 * @return
	 */
	String getUploadTempImageHome();

	/**
	 * 获取临时目录web访问路径
	 * 
	 * @return
	 */
	String getWebTempImagePath();

	/**
	 * 验证图片是否合法
	 * 
	 * @param image
	 * @throws UploadImageException
	 */
	void checkImage(MultipartFile image) throws UploadImageException;

	/**
	 * 给图片设置水印
	 * 
	 * @param iconPath水印图片路径
	 * 
	 * @param srcImgPath源图片路径
	 * 
	 * @param targerPath新图片路径
	 * 
	 * @param x水印x坐标
	 * 
	 * @param y
	 *            水印y坐标
	 * 
	 * @param degree水印旋转角度
	 * 
	 * @param content文字
	 * 
	 * @param fontX
	 *            文字x坐标
	 * 
	 * @param fontY文字y坐标
	 * 
	 * @param fontHeight
	 *            文字高度
	 */
	void markImage(String iconPath, String srcImgPath, String targerPath,
			String filename, int x, int y, Integer degree, List<MarkFont> fonts);

	/**
	 * 上传图片
	 * 
	 * @param directoryPath
	 * @param image
	 * @throws UploadImageException
	 */
	void uploadImage(String directoryPath, String filename, Image image,
			int width, int height, int x, int y) throws UploadImageException;

	/**
	 * 上传临时文件
	 * 
	 * @param imageUrl
	 * @return [0]web访问地址，[1]file地址
	 * @throws UploadImageException
	 */
	String[] uploadTempImage(String imageUrl) throws UploadImageException;

	/**
	 * 验证图片是否合法
	 * 
	 * @param image
	 * @throws UploadImageException
	 */
	void checkImage(HttpURLConnection httpURLConnection, byte[] bytes)
			throws UploadImageException;

}
