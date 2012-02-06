package com.juzhai.post.service.impl;

import java.io.File;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.juzhai.act.exception.UploadImageException;
import com.juzhai.core.image.LogoSizeType;
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

	// TODO (done) 图片处理的代码又乱放了⋯⋯，逻辑也有问题，修改的时候找我
	@Override
	public String uploadIdeaPic(Long postId, MultipartFile image, Long ideaId,
			String picName) throws UploadImageException {
		String fileName = null;
		// 没有上传图片则复制post的图片
		String directoryPath = uploadIdeaImageHome
				+ ImageUtil.generateHierarchyImagePath(ideaId,
						LogoSizeType.ORIGINAL);
		if (image != null && image.getSize() != 0) {
			fileName = imageManager.uploadImage(directoryPath, image);
		}
		if (postId == null || postId == 0) {
			fileName = picName;
		} else {
			File srcFile = new File(uploadPostImageHome
					+ ImageUtil.generateHierarchyImagePath(
							Long.valueOf(postId), LogoSizeType.ORIGINAL)
					+ picName);
			fileName = picName;
			imageManager.copyImage(directoryPath, fileName, srcFile);
		}
		return fileName;
	}

}
