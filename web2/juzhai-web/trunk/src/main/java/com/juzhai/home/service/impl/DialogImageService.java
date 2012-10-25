package com.juzhai.home.service.impl;

import java.io.File;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.juzhai.core.exception.UploadImageException;
import com.juzhai.core.image.DialogSizeType;
import com.juzhai.core.image.manager.IImageManager;
import com.juzhai.core.image.util.ImageUtil;
import com.juzhai.home.service.IDialogImageService;

@Service
public class DialogImageService implements IDialogImageService {
	@Autowired
	private IImageManager imageManager;
	@Value("${upload.dialog.content.image.home}")
	private String uploadDialogContentImageHome;

	@Override
	public String uploadDialogImg(long dialogContentId, MultipartFile image)
			throws UploadImageException {
		String directoryPath = uploadDialogContentImageHome
				+ ImageUtil.generateHierarchyImagePath(dialogContentId,
						DialogSizeType.ORIGINAL.getType());
		String fileName = imageManager.uploadImage(directoryPath, image);
		// // 大图
		// String distDirectoryPath = uploadDialogImageHome
		// + ImageUtil.generateHierarchyImagePath(dialogContentId,
		// DialogSizeType.BIG.getType());
		// imageManager.reduceImageWidth(directoryPath + fileName,
		// distDirectoryPath, fileName, DialogSizeType.BIG.getType());

		// // 中图
		// distDirectoryPath = uploadDialogImageHome
		// + ImageUtil.generateHierarchyImagePath(dialogContentId,
		// DialogSizeType.MIDDLE.getType());
		// imageManager.reduceImage(directoryPath + fileName, distDirectoryPath,
		// fileName, DialogSizeType.MIDDLE.getType());

		// // 小图
		// distDirectoryPath = uploadDialogImageHome
		// + ImageUtil.generateHierarchyImagePath(dialogContentId,
		// DialogSizeType.SMALL.getType());
		// imageManager.reduceImage(directoryPath + fileName, distDirectoryPath,
		// fileName, DialogSizeType.SMALL.getType());

		return fileName;
	}

	@Override
	public String saveImg(long uid, String filePath) {
		// TODO 多个尺寸
		File srcFile = new File(imageManager.getUploadTempImageHome()
				+ filePath);
		String fileName = srcFile.getName();
		String directoryPath = uploadDialogContentImageHome
				+ ImageUtil.generateHierarchyImagePath(uid,
						DialogSizeType.ORIGINAL.getType());
		imageManager.copyImage(directoryPath, fileName, srcFile);

		// 大图
		String distDirectoryPath = uploadDialogContentImageHome
				+ ImageUtil.generateHierarchyImagePath(uid,
						DialogSizeType.BIG.getType());
		imageManager.reduceImageWidth(directoryPath + fileName,
				distDirectoryPath, fileName, DialogSizeType.BIG.getType());

		// 中图
		distDirectoryPath = uploadDialogContentImageHome
				+ ImageUtil.generateHierarchyImagePath(uid,
						DialogSizeType.MIDDLE.getType());
		imageManager.reduceImage(directoryPath + fileName, distDirectoryPath,
				fileName, DialogSizeType.MIDDLE.getType());

		return fileName;
	}
}
