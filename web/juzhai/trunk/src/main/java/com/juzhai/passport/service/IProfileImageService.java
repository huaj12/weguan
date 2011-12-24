package com.juzhai.passport.service;

import org.springframework.web.multipart.MultipartFile;

import com.juzhai.act.exception.UploadImageException;

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
			int scaledH, int x, int y, int w, int h) throws UploadImageException;
}
