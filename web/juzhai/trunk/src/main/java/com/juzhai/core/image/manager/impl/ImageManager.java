package com.juzhai.core.image.manager.impl;

import java.awt.Color;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import javax.imageio.ImageIO;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.juzhai.act.exception.UploadImageException;
import com.juzhai.core.image.manager.IImageManager;
import com.juzhai.core.util.FileUtil;
import com.juzhai.core.util.ImageUtil;
import com.juzhai.core.util.StaticUtil;

@Component
public class ImageManager implements IImageManager {
	private final Log log = LogFactory.getLog(getClass());
	private final static SimpleDateFormat SDF = new SimpleDateFormat(
			"yyyy-MM-dd");
	@Value("${upload.temp.image.home}")
	private String uploadTempImageHome;
	@Value("${web.temp.image.path}")
	private String webTempImagePath;
	@Value("${upload.image.types}")
	private String uploadImageTypes;
	@Value("${upload.image.size}")
	private int uploadImageSize;

	@Override
	public String uploadImage(String directoryPath, MultipartFile image)
			throws UploadImageException {
		checkImage(image);
		try {
			String fileName = ImageUtil.generateUUIDJpgFileName();
			if (!FileUtil.writeStreamToFile(directoryPath, fileName,
					image.getInputStream())) {
				throw new UploadImageException(
						UploadImageException.SYSTEM_ERROR);
			}
			return fileName;
		} catch (IOException e) {
			throw new UploadImageException(UploadImageException.SYSTEM_ERROR, e);
		}
	}

	private void checkImage(MultipartFile image) throws UploadImageException {
		if (null == image) {
			throw new UploadImageException(UploadImageException.UPLOAD_ERROR);
		}
		int code = ImageUtil.validationImage(uploadImageTypes, uploadImageSize,
				image);
		switch (code) {
		case 0:
			throw new UploadImageException(UploadImageException.UPLOAD_ERROR);
		case -1:
			throw new UploadImageException(
					UploadImageException.UPLOAD_TYPE_ERROR);
		case -2:
			throw new UploadImageException(
					UploadImageException.UPLOAD_SIZE_ERROR);
		}
	}

	@Override
	public String[] uploadTempImage(MultipartFile image)
			throws UploadImageException {
		checkImage(image);
		String dateFolder = SDF.format(new Date());
		String directoryPath = uploadTempImageHome + dateFolder
				+ File.separator;
		String fileName = ImageUtil.generateUUIDJpgFileName();
		try {
			if (!FileUtil.writeStreamToFile(directoryPath, fileName,
					image.getInputStream())) {
				throw new UploadImageException(
						UploadImageException.SYSTEM_ERROR);
			}
			return new String[] {
					StaticUtil.u(webTempImagePath + dateFolder + File.separator
							+ fileName), dateFolder + File.separator + fileName };
		} catch (IOException e) {
			throw new UploadImageException(UploadImageException.SYSTEM_ERROR, e);
		}
	}

	@Override
	public boolean cutImage(String srcPath, String distDirectoryPath,
			String distFileName, int srcScaledWidth, int srcScaledHeight,
			int srcCutX, int srcCutY, int distWidth, int distHeight) {
		// FileOutputStream out = null;
		try {
			File srcFile = new File(srcPath);
			if (!srcFile.exists()) {
				log.error("image file dose not exist[path=" + srcPath + "].");
				return false;
			}
			Image srcImage = ImageIO.read(srcFile);
			int width = srcImage.getWidth(null);
			int height = srcImage.getHeight(null);
			double scale = 0D;
			if (srcScaledWidth > 0) {
				scale = width * 1.0 / srcScaledWidth;
			} else if (srcScaledHeight > 0) {
				scale = height * 1.0 / srcScaledHeight;
			}
			srcCutX = new Double(srcCutX * scale).intValue();
			srcCutY = new Double(srcCutY * scale).intValue();
			distWidth = new Double(distWidth * scale).intValue();
			distHeight = new Double(distHeight * scale).intValue();
			BufferedImage bufferedImage = new BufferedImage(distWidth,
					distHeight, BufferedImage.TYPE_INT_RGB);
			// bufferedImage.getGraphics().drawImage(
			// srcImage.getScaledInstance(srcScaledWidth, srcScaledHeight,
			// Image.SCALE_SMOOTH), 0, 0, distWidth, distHeight,
			// srcCutX, srcCutY, srcCutX + distWidth,
			// srcCutY + distHeight, Color.white, null);
			bufferedImage.getGraphics().drawImage(srcImage, 0, 0, distWidth,
					distHeight, srcCutX, srcCutY, srcCutX + distWidth,
					srcCutY + distHeight, Color.white, null);

			File distFile = FileUtil.newFile(distDirectoryPath, distFileName);
			if (null == distFile) {
				log.error("Create dist image file fail[distDirectoryPath="
						+ distDirectoryPath + ",distFileName=" + distFileName
						+ "].");
				return false;
			}
			// out = new FileOutputStream(distFile);
			ImageIO.write(bufferedImage, "JPEG", distFile);
			// JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
			// encoder.encode(bufferedImage);
			// out.close();
			return true;
		} catch (IOException e) {
			log.error(e.getMessage(), e);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		} finally {
			// if (null != out) {
			// try {
			// out.close();
			// } catch (IOException e) {
			// log.error(e.getMessage(), e);
			// }
			// }
		}
		return false;
	}

	@Override
	public boolean reduceImage(String srcPath, String distDirectoryPath,
			String distFileName, int scaledWidth, int scaledHeight) {
		try {
			File srcFile = new File(srcPath);
			if (!srcFile.exists()) {
				log.error("image file dose not exist[path=" + srcPath + "].");
				return false;
			}
			Image srcImage = ImageIO.read(srcFile);
			BufferedImage bufferedImage = new BufferedImage(scaledWidth,
					scaledHeight, BufferedImage.TYPE_INT_RGB);
			bufferedImage.getGraphics().drawImage(
					srcImage.getScaledInstance(scaledWidth, scaledHeight,
							Image.SCALE_SMOOTH), 0, 0, Color.white, null);
			File distFile = FileUtil.newFile(distDirectoryPath, distFileName);
			if (null == distFile) {
				log.error("Create dist image file fail[distDirectoryPath="
						+ distDirectoryPath + ",distFileName=" + distFileName
						+ "].");
				return false;
			}
			ImageIO.write(bufferedImage, "JPEG", distFile);
			return true;
		} catch (IOException e) {
			log.error(e.getMessage(), e);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		} finally {
		}
		return false;
	}

	@Override
	public String getUploadTempImageHome() {
		return this.uploadTempImageHome;
	}

	@Override
	public String getWebTempImagePath() {
		return webTempImagePath;
	}

	@Override
	public boolean copyImage(String directoryPath, String fileName, File srcFile) {
		return FileUtil.writeFileToFile(directoryPath, fileName, srcFile);
	}

	@Override
	public void deleteImage(long id, String fileName) {
		// TODO Auto-generated method stub

	}
}
