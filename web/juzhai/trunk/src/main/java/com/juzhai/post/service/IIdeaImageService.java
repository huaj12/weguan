package com.juzhai.post.service;

import org.springframework.web.multipart.MultipartFile;

import com.juzhai.core.exception.UploadImageException;

public interface IIdeaImageService {
	/**
	 * 上传idea图片(有postId则复制post图片)
	 * 
	 * @param postId
	 * @param image
	 * @param ideaId
	 * @param picName
	 * @return
	 * @throws UploadImageException
	 */
	String uploadIdeaPic(Long postId, MultipartFile image, Long ideaId,
			String picName) throws UploadImageException;
}
