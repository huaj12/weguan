package com.juzhai.mobile.post.service;

import org.springframework.web.multipart.MultipartFile;

import com.juzhai.core.exception.UploadImageException;
import com.juzhai.core.web.session.UserContext;
import com.juzhai.post.controller.form.PostForm;
import com.juzhai.post.exception.InputPostException;

public interface IPostMService {

	/**
	 * 发布一个拒宅
	 * 
	 * @param uid
	 * @param postForm
	 * @throws InputPostException
	 * @throws UploadImageException
	 */
	long createPost(UserContext context, PostForm postForm,
			MultipartFile postImg) throws InputPostException,
			UploadImageException;
}
