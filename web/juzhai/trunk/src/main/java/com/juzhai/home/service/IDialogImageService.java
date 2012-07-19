package com.juzhai.home.service;

import org.springframework.web.multipart.MultipartFile;

import com.juzhai.core.exception.UploadImageException;

public interface IDialogImageService {
	/**
	 * 上传post图片
	 * 
	 * @param dialogContentId
	 * @param image
	 * @return
	 * @throws UploadImageException
	 */
	String uploadDialogImg(long dialogContentId, MultipartFile image)
			throws UploadImageException;
}
