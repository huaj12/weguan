package com.juzhai.passport.service.impl;

import java.awt.Image;
import java.io.File;
import java.net.URL;

import javax.imageio.ImageIO;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.juzhai.core.exception.UploadImageException;
import com.juzhai.core.image.JzImageSizeType;
import com.juzhai.core.image.LogoSizeType;
import com.juzhai.core.image.manager.IImageManager;
import com.juzhai.core.image.util.ImageUtil;
import com.juzhai.passport.bean.ProfileCache;
import com.juzhai.passport.mapper.ProfileMapper;
import com.juzhai.passport.model.Profile;
import com.juzhai.passport.service.IProfileImageService;
import com.juzhai.passport.service.IProfileService;

@Service
public class ProfileImageService implements IProfileImageService {
	protected final Log log = LogFactory.getLog(getClass());
	@Autowired
	private IImageManager imageManager;
	@Value(value = "${upload.user.image.home}")
	private String uploadUserImageHome;
	@Autowired
	private IProfileService profileService;
	@Autowired
	private ProfileMapper profileMapper;

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

	@Override
	public String uploadLogo(long uid, String url) throws UploadImageException {
		Image srcFile;
		String filename = null;
		try {
			filename = ImageUtil.generateUUIDJpgFileName();
			srcFile = ImageIO.read(new URL(url));
			for (LogoSizeType sizeType : LogoSizeType.values()) {
				if (sizeType.getType() > 0) {
					String directoryPath = uploadUserImageHome
							+ ImageUtil.generateHierarchyImagePath(uid,
									sizeType.getType());
					imageManager.uploadImage(directoryPath, filename, srcFile,
							sizeType.getType(), sizeType.getType(), 0, 0);
				}
			}
		} catch (Exception e) {
			log.error("uploadLogo read image url is error", e);
			throw new UploadImageException(UploadImageException.SYSTEM_ERROR);
		}
		return filename;
	}

	@Override
	public String getUserImagePath(long uid) throws UploadImageException {
		ProfileCache cache = profileService.getProfileCacheByUid(uid);
		if (cache == null) {
			return null;
		}
		if (StringUtils.isEmpty(cache.getLogoPic())) {
			return null;
		}
		String filename = null;
		if (ImageUtil.isInternalUrl(cache.getLogoPic())) {
			filename = cache.getLogoPic();
		} else {
			filename = uploadLogo(uid, cache.getLogoPic());
			Profile profile = new Profile();
			profile.setUid(uid);
			profile.setLogoPic(filename);
			profileMapper.updateByPrimaryKeySelective(profile);
			profileService.clearProfileCache(uid);
		}
		String logoPic = ImageUtil.generateHierarchyImagePath(cache.getUid(),
				LogoSizeType.BIG.getType()) + filename;
		return uploadUserImageHome + logoPic;
	}

	@Override
	public String uploadAndReduceLogo(long uid, MultipartFile image)
			throws UploadImageException {
		// 上传文件
		String distDirectoryPath = uploadUserImageHome
				+ ImageUtil.generateHierarchyImagePath(uid,
						LogoSizeType.ORIGINAL.getType());
		String distFileName = imageManager
				.uploadImage(distDirectoryPath, image);
		// 生成缩略图
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

	@Override
	public String saveImg(long uid, String filePath) {
		File srcFile = new File(imageManager.getUploadTempImageHome()
				+ filePath);
		String fileName = srcFile.getName();
		String directoryPath = uploadUserImageHome
				+ ImageUtil.generateHierarchyImagePath(uid,
						JzImageSizeType.ORIGINAL.getType());
		imageManager.copyImage(directoryPath, fileName, srcFile);

		// 生成缩略图
		for (LogoSizeType sizeType : LogoSizeType.values()) {
			if (sizeType.getType() > 0) {
				String ddp = uploadUserImageHome
						+ ImageUtil.generateHierarchyImagePath(uid,
								sizeType.getType());
				imageManager.reduceImage(directoryPath + fileName, ddp,
						fileName, sizeType.getType(), sizeType.getType());
			}
		}

		return fileName;
	}
}
