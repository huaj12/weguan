package com.juzhai.post.service.impl;

import java.io.File;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.juzhai.core.exception.UploadImageException;
import com.juzhai.core.image.JzImageSizeType;
import com.juzhai.core.image.manager.IImageManager;
import com.juzhai.core.util.ImageUtil;
import com.juzhai.post.service.IIdeaImageService;

@Service
public class IdeaImageService implements IIdeaImageService {
	@Value("${upload.idea.image.home}")
	private String uploadIdeaImageHome;
	@Value("${upload.post.image.home}")
	private String uploadPostImageHome;
	@Autowired
	private IImageManager imageManager;

	@Override
	public String uploadIdeaPic(Long postId, MultipartFile image, Long ideaId,
			String picName) throws UploadImageException {
		String fileName = picName;
		// 没有上传图片则复制post的图片
		if (image != null && image.getSize() != 0) {
			String directoryPath = uploadIdeaImageHome
					+ ImageUtil.generateHierarchyImagePath(ideaId,
							JzImageSizeType.ORIGINAL.getType());
			fileName = imageManager.uploadImage(directoryPath, image);
			String srcFileName = directoryPath + fileName;
			// 大图
			String distDirectoryPath = uploadIdeaImageHome
					+ ImageUtil.generateHierarchyImagePath(ideaId,
							JzImageSizeType.BIG.getType());
			imageManager.reduceImageWidth(srcFileName, distDirectoryPath,
					fileName, JzImageSizeType.BIG.getType());

			// 中图
			distDirectoryPath = uploadIdeaImageHome
					+ ImageUtil.generateHierarchyImagePath(ideaId,
							JzImageSizeType.MIDDLE.getType());
			imageManager.reduceImage(srcFileName, distDirectoryPath, fileName,
					JzImageSizeType.MIDDLE.getType());
		}
		if (postId != null && postId > 0 && StringUtils.isNotEmpty(picName)) {
			for (JzImageSizeType sizeType : JzImageSizeType.values()) {
				String directoryPath = uploadIdeaImageHome
						+ ImageUtil.generateHierarchyImagePath(ideaId,
								sizeType.getType());
				File srcFile = new File(uploadPostImageHome
						+ ImageUtil.generateHierarchyImagePath(postId,
								sizeType.getType()) + picName);
				imageManager.copyImage(directoryPath, picName, srcFile);
			}
		}
		return fileName;
	}

}
