package com.juzhai.passport.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.juzhai.act.exception.UploadImageException;
import com.juzhai.core.image.LogoSizeType;
import com.juzhai.core.image.manager.IImageManager;
import com.juzhai.core.util.ImageUtil;
import com.juzhai.passport.service.IProfileImageService;

@Service
public class ProfileImageService implements IProfileImageService {

	@Autowired
	private IImageManager imageManager;
	@Value(value = "${upload.user.image.home}")
	private String uploadUserImageHome;

	@Override
	public String[] uploadLogo(long uid, MultipartFile image)
			throws UploadImageException {
		return imageManager.uploadTempImage(image);
	}

	@Override
	public String cutAndReduceLogo(long uid, String filePath, int scaledW,
			int scaledH, int x, int y, int w, int h)
			throws UploadImageException {
		filePath = imageManager.getUploadTempImageHome() + filePath;
		String distDirectoryPath = uploadUserImageHome
				+ ImageUtil.generateHierarchyImagePath(uid,
						LogoSizeType.ORIGINAL.getType());
		String distFileName = ImageUtil.generateUUIDJpgFileName();
		if (!imageManager.cutImage(filePath, distDirectoryPath, distFileName,
				scaledW, scaledH, x, y, w, h)) {
			throw new UploadImageException(
					UploadImageException.UPLOAD_CUT_ERROR);
		}
		for (LogoSizeType sizeType : LogoSizeType.values()) {
			if (sizeType.getType() > 0) {
				String ddp = uploadUserImageHome
						+ ImageUtil.generateHierarchyImagePath(uid,
								sizeType.getType());
				imageManager.reduceImage(distDirectoryPath + distFileName, ddp,
						distFileName, sizeType.getType(), sizeType.getType());
			}
		}
		return distFileName;
	}
}
