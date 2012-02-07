package com.juzhai.post.service;

import org.springframework.web.multipart.MultipartFile;

import com.juzhai.act.exception.UploadImageException;

public interface IIdeaImageService {

	// TODO (review) 注释
	String uploadIdeaPic(Long postId, MultipartFile image, Long ideaId,
			String picName) throws UploadImageException;
}
