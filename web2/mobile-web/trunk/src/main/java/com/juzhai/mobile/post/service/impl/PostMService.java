package com.juzhai.mobile.post.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.juzhai.core.exception.UploadImageException;
import com.juzhai.core.image.manager.IImageManager;
import com.juzhai.core.web.session.UserContext;
import com.juzhai.mobile.post.service.IPostMService;
import com.juzhai.post.controller.form.PostForm;
import com.juzhai.post.exception.InputPostException;
import com.juzhai.post.service.IPostRemoteService;

@Service
public class PostMService implements IPostMService {

	@Autowired
	private IPostRemoteService postService;
	@Autowired
	private IImageManager imageManager;

	@Override
	public long createPost(UserContext context, PostForm postForm,
			MultipartFile postImg) throws InputPostException,
			UploadImageException {
		if (postImg != null && !postImg.isEmpty()) {
			String[] paths = imageManager.uploadTempImage(postImg);
			if (null != paths && paths.length == 2) {
				postForm.setPic(paths[1]);
			}
		}
		long postId = postService.createPost(context.getUid(), postForm);
		if (postId > 0 && context.getTpId() > 0) {
			postService.synchronizePlatform(context.getUid(),
					context.getTpId(), postId);
		}
		return postId;
	}
}
