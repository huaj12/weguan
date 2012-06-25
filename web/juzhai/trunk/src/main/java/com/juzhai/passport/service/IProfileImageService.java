package com.juzhai.passport.service;

import org.springframework.web.multipart.MultipartFile;

import com.juzhai.core.exception.UploadImageException;

public interface IProfileImageService {

	/**
	 * 用户头像上传
	 * 
	 * @param uid
	 * @param image
	 * @return [0]web访问地址，[1]file地址
	 * @throws UploadImageException
	 */
	String[] uploadLogo(long uid, MultipartFile image)
			throws UploadImageException;

	/**
	 * 剪切头像并生成缩略图
	 * 
	 * @param uid
	 * @param filePath
	 * @param x
	 * @param y
	 * @param w
	 * @param h
	 * @return
	 * @throws UploadImageException
	 */
	String cutAndReduceLogo(long uid, String filePath, int scaledW,
			int scaledH, int x, int y, int w, int h)
			throws UploadImageException;

	/**
	 * 用户头像上传
	 * 
	 * @param uid
	 *            图片网络地址
	 * @param url
	 * @return filename
	 * @throws UploadImageException
	 */
	String uploadLogo(long uid, String url) throws UploadImageException;

	/**
	 * 获取用户头像存储路径（如果是网络图片先下载）
	 * 
	 * @param uid
	 * @return
	 */
	String getUserImagePath(long uid) throws UploadImageException;

	/**
	 * 上传并且生成缩略图
	 * 
	 * @param uid
	 * @param image
	 * @throws UploadImageException
	 */
	String uploadAndReduceLogo(long uid, MultipartFile image)
			throws UploadImageException;
}
