package com.juzhai.post.service;

import org.springframework.web.multipart.MultipartFile;

import com.juzhai.act.exception.UploadImageException;

public interface IPostImageService {

	/**
	 * 上传post图片
	 * 
	 * @param image
	 * @return
	 * @throws UploadImageException
	 */
	String[] uploadPic(MultipartFile image) throws UploadImageException;

	/**
	 * 保存图片
	 * 
	 * @param postId
	 * @param filePath
	 * @return
	 */
	String saveImg(long postId, String filePath);
}
