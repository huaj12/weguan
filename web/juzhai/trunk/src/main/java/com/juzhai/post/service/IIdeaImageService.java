package com.juzhai.post.service;

import org.springframework.web.multipart.MultipartFile;

import com.juzhai.act.exception.UploadImageException;

public interface IIdeaImageService {
	String uploadIdeaPic(Long postId,MultipartFile image,Long ideaId,String picName)throws UploadImageException;
}
