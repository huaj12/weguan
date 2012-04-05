package com.juzhai.passport.service.impl;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.net.URL;

import javax.imageio.ImageIO;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.juzhai.act.exception.UploadImageException;
import com.juzhai.core.image.LogoSizeType;
import com.juzhai.core.image.manager.IImageManager;
import com.juzhai.core.util.ImageUtil;
import com.juzhai.passport.bean.ProfileCache;
import com.juzhai.passport.mapper.ProfileMapper;
import com.juzhai.passport.model.Profile;
import com.juzhai.passport.service.IProfileImageService;
import com.juzhai.passport.service.IProfileService;

@Service
public class ProfileImageService implements IProfileImageService {

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

					BufferedImage tag = imageManager.cutImage(
							sizeType.getType(), sizeType.getType(), 0, 0,
							srcFile);

					String directoryPath = uploadUserImageHome
							+ ImageUtil.generateHierarchyImagePath(uid,
									sizeType.getType());
					imageManager.uploadImage(directoryPath, filename, tag);
				}
			}
		} catch (Exception e) {
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
		// TODO (done) 有没有考虑过用户没有logo(没有有效头像的用户)？
		if (StringUtils.isEmpty(cache.getLogoPic())) {
			return null;
		}
		String logoPic = null;
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
		// TODO (done) 下面的代码是不是和if里的有重复呢？
		logoPic = ImageUtil.generateHierarchyImagePath(cache.getUid(),
				LogoSizeType.BIG.getType()) + filename;
		return uploadUserImageHome + logoPic;
	}
}
