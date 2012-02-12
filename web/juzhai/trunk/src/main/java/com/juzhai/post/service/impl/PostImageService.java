package com.juzhai.post.service.impl;

import java.io.File;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.juzhai.act.exception.UploadImageException;
import com.juzhai.core.image.LogoSizeType;
import com.juzhai.core.image.manager.IImageManager;
import com.juzhai.core.util.FileUtil;
import com.juzhai.core.util.ImageUtil;
import com.juzhai.post.model.Post;
import com.juzhai.post.service.IPostImageService;
import com.juzhai.post.service.IPostService;

@Service
public class PostImageService implements IPostImageService {

	@Autowired
	private IImageManager imageManager;
	@Value("${upload.post.image.home}")
	private String uploadPostImageHome;
	@Value("${upload.idea.image.home}")
	private String uploadIdeaImageHome;
	@Autowired
	private IPostService postService;

	@Override
	public String[] uploadPic(MultipartFile image) throws UploadImageException {
		return imageManager.uploadTempImage(image);
	}

	@Override
	public String saveImg(long postId, String filePath) {
		File srcFile = new File(imageManager.getUploadTempImageHome()
				+ filePath);
		String fileName = srcFile.getName();
		String directoryPath = uploadPostImageHome
				+ ImageUtil.generateHierarchyImagePath(postId,
						LogoSizeType.ORIGINAL);
		imageManager.copyImage(directoryPath, fileName, srcFile);
		return fileName;
	}

	@Override
	public void copyImgFromIdea(long postId, long ideaId, String imgName) {
		String directoryPath = uploadPostImageHome
				+ ImageUtil.generateHierarchyImagePath(postId,
						LogoSizeType.ORIGINAL);
		File srcFile = new File(uploadIdeaImageHome
				+ ImageUtil.generateHierarchyImagePath(ideaId,
						LogoSizeType.ORIGINAL) + imgName);
		imageManager.copyImage(directoryPath, imgName, srcFile);
	}

	@Override
	public void copyImgFromPost(long postId, long destPostId, String imgName) {
		String directoryPath = uploadPostImageHome
				+ ImageUtil.generateHierarchyImagePath(postId,
						LogoSizeType.ORIGINAL);
		File srcFile = new File(uploadPostImageHome
				+ ImageUtil.generateHierarchyImagePath(destPostId,
						LogoSizeType.ORIGINAL) + imgName);
		imageManager.copyImage(directoryPath, imgName, srcFile);
	}

	@Override
	public byte[] getPostFile(long postId, long ideaId, String fileName,
			LogoSizeType sizeType) {
		try {
			String directoryPath = null;
			if (ideaId > 0) {
				directoryPath = uploadIdeaImageHome
						+ ImageUtil.generateHierarchyImagePath(ideaId,
								LogoSizeType.ORIGINAL);
			} else {
				directoryPath = uploadPostImageHome
						+ ImageUtil
								.generateHierarchyImagePath(postId, sizeType);
			}
			File file = new File(directoryPath + fileName);
			return FileUtil.readFileToByteArray(file);
		} catch (IOException e) {
			return null;
		}
	}

}
