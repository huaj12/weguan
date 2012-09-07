package com.juzhai.post.service.impl;

import java.io.File;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.juzhai.core.exception.UploadImageException;
import com.juzhai.core.image.JzImageSizeType;
import com.juzhai.core.image.manager.IImageManager;
import com.juzhai.core.image.util.ImageUtil;
import com.juzhai.core.util.FileUtil;
import com.juzhai.post.service.IPostImageService;

@Service
public class PostImageService implements IPostImageService {

	@Autowired
	private IImageManager imageManager;
	@Value("${upload.post.image.home}")
	private String uploadPostImageHome;
	@Value("${upload.idea.image.home}")
	private String uploadIdeaImageHome;

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
						JzImageSizeType.ORIGINAL.getType());
		imageManager.copyImage(directoryPath, fileName, srcFile);
		// 大图
		String distDirectoryPath = uploadPostImageHome
				+ ImageUtil.generateHierarchyImagePath(postId,
						JzImageSizeType.BIG.getType());
		imageManager.reduceImageWidth(directoryPath + fileName,
				distDirectoryPath, fileName, JzImageSizeType.BIG.getType());

		// 中图
		distDirectoryPath = uploadPostImageHome
				+ ImageUtil.generateHierarchyImagePath(postId,
						JzImageSizeType.MIDDLE.getType());
		imageManager.reduceImage(directoryPath + fileName, distDirectoryPath,
				fileName, JzImageSizeType.MIDDLE.getType());

		return fileName;
	}

	@Override
	public String uploadPostImg(long postId, MultipartFile image)
			throws UploadImageException {
		String filePath = uploadPic(image)[1];
		return saveImg(postId, filePath);
	}

	@Override
	public void copyImgFromIdea(long postId, long ideaId, String imgName) {
		for (JzImageSizeType sizeType : JzImageSizeType.values()) {
			String directoryPath = uploadPostImageHome
					+ ImageUtil.generateHierarchyImagePath(postId,
							sizeType.getType());
			File srcFile = new File(uploadIdeaImageHome
					+ ImageUtil.generateHierarchyImagePath(ideaId,
							sizeType.getType()) + imgName);
			imageManager.copyImage(directoryPath, imgName, srcFile);
		}
	}

	@Override
	public void copyImgFromPost(long postId, long destPostId, String imgName) {
		for (JzImageSizeType sizeType : JzImageSizeType.values()) {
			String directoryPath = uploadPostImageHome
					+ ImageUtil.generateHierarchyImagePath(postId,
							sizeType.getType());
			File srcFile = new File(uploadPostImageHome
					+ ImageUtil.generateHierarchyImagePath(destPostId,
							sizeType.getType()) + imgName);
			imageManager.copyImage(directoryPath, imgName, srcFile);
		}
	}

	@Override
	public byte[] getPostFile(long postId, long ideaId, String fileName,
			JzImageSizeType sizeType) {
		try {
			String directoryPath = null;
			if (ideaId > 0) {
				directoryPath = uploadIdeaImageHome
						+ ImageUtil.generateHierarchyImagePath(ideaId,
								sizeType.getType());
			} else {
				directoryPath = uploadPostImageHome
						+ ImageUtil.generateHierarchyImagePath(postId,
								sizeType.getType());
			}
			File file = new File(directoryPath + fileName);
			return FileUtil.readFileToByteArray(file);
		} catch (IOException e) {
			return null;
		}
	}

}
