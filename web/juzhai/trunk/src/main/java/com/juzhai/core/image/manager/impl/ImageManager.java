package com.juzhai.core.image.manager.impl;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.juzhai.core.exception.UploadImageException;
import com.juzhai.core.image.bean.MarkFont;
import com.juzhai.core.image.manager.IImageManager;
import com.juzhai.core.util.DateFormat;
import com.juzhai.core.util.FileUtil;
import com.juzhai.core.util.ImageUtil;
import com.juzhai.core.util.StaticUtil;

@Component
public class ImageManager implements IImageManager {
	private final Log log = LogFactory.getLog(getClass());

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

	@Override
	public void checkImage(MultipartFile image) throws UploadImageException {
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
		String dateFolder = DateFormat.SDF.format(new Date());
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
	public boolean reduceImage(String srcPath, String distDirectoryPath,
			String distFileName, int scaledWidth, int scaledHeight) {
		try {
			File srcFile = new File(srcPath);
			if (!srcFile.exists()) {
				log.error("image file dose not exist[path=" + srcPath + "].");
				return false;
			}
			Image srcImage = ImageIO.read(srcFile);
			return reduceImage(srcImage, distDirectoryPath, distFileName,
					scaledWidth, scaledHeight);
		} catch (IOException e) {
			log.error(e.getMessage(), e);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		} finally {
		}
		return false;
	}

	@Override
	public boolean reduceImage(String srcPath, String distDirectoryPath,
			String distFileName, int scaledWidthOrHeight) {
		try {
			File srcFile = new File(srcPath);
			if (!srcFile.exists()) {
				log.error("image file dose not exist[path=" + srcPath + "].");
				return false;
			}
			Image srcImage = ImageIO.read(srcFile);
			int width = srcImage.getWidth(null);
			int height = srcImage.getHeight(null);
			double scale = Math.max(width, height) * 1.0 / scaledWidthOrHeight;
			int scaledHeight = scale > 1 ? new Double(height * 1.0 / scale)
					.intValue() : height;
			int scaledWidth = scale > 1 ? new Double(width * 1.0 / scale)
					.intValue() : width;
			return reduceImage(srcImage, distDirectoryPath, distFileName,
					scaledWidth, scaledHeight);
		} catch (IOException e) {
			log.error(e.getMessage(), e);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		} finally {
		}
		return false;
	}

	@Override
	public boolean reduceImageWidth(String srcPath, String distDirectoryPath,
			String distFileName, int scaledWidth) {
		try {
			File srcFile = new File(srcPath);
			if (!srcFile.exists()) {
				log.error("image file dose not exist[path=" + srcPath + "].");
				return false;
			}
			Image srcImage = ImageIO.read(srcFile);
			int width = srcImage.getWidth(null);
			int height = srcImage.getHeight(null);
			double scale = width * 1.0 / scaledWidth;
			if (scale < 1) {
				scaledWidth = width;
			}
			int scaledHeight = scale > 1 ? new Double(height * 1.0 / scale)
					.intValue() : height;
			return reduceImage(srcImage, distDirectoryPath, distFileName,
					scaledWidth, scaledHeight);
		} catch (IOException e) {
			log.error(e.getMessage(), e);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		} finally {
		}
		return false;
	}

	private boolean reduceImage(Image srcImage, String distDirectoryPath,
			String distFileName, int scaledWidth, int scaledHeight) {
		try {
			if (srcImage == null) {
				log.error("src image is null.");
				return false;
			}
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
	public void markImage(String iconPath, String srcImgPath,
			String targerPath, String filename, int x, int y, Integer degree,
			List<MarkFont> fonts) {
		OutputStream os = null;
		try {

			Image srcImg = ImageIO.read(new File(srcImgPath));
			BufferedImage buffImg = new BufferedImage(srcImg.getWidth(null),
					srcImg.getHeight(null), BufferedImage.TYPE_INT_RGB);
			Graphics2D g = buffImg.createGraphics();

			// 设置对线段的锯齿状边缘处理
			g.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
					RenderingHints.VALUE_INTERPOLATION_BILINEAR);

			g.drawImage(
					srcImg.getScaledInstance(srcImg.getWidth(null),
							srcImg.getHeight(null), Image.SCALE_SMOOTH), 0, 0,
					null);

			if (null != degree) {
				// 设置水印旋转
				g.rotate(Math.toRadians(degree),
						(double) buffImg.getWidth() / 2,
						(double) buffImg.getHeight() / 2);
			}
			ImageIcon imgIcon = new ImageIcon(iconPath);
			Image img = imgIcon.getImage();
			// 表示水印图片的位置
			g.drawImage(img, x, y, null);
			if (CollectionUtils.isNotEmpty(fonts)) {
				for (MarkFont markFont : fonts) {
					if (StringUtils.isNotEmpty(markFont.getContent())) {
						if (null != markFont.getFont()) {
							g.setFont(markFont.getFont());
						}
						if (null != markFont.getColor()) {
							g.setColor(markFont.getColor());
						}

						g.drawString(markFont.getContent(), markFont.getX(),
								markFont.getY());
					}
				}
			}
			g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER));

			g.dispose();
			File directory = new File(targerPath);
			if (!directory.exists()) {
				directory.mkdirs();
			}
			os = new FileOutputStream(targerPath + File.separator + filename);
			// 生成图片
			ImageIO.write(buffImg, "JPG", os);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		} finally {
			try {
				if (null != os)
					os.close();
			} catch (Exception e) {
				log.error(e.getMessage(), e);
			}
		}
	}

	@Override
	public void uploadImage(String directoryPath, String filename, Image image,
			int width, int height, int x, int y) throws UploadImageException {
		BufferedImage tag = new BufferedImage(width, height,
				BufferedImage.TYPE_INT_RGB);
		tag.getGraphics().drawImage(image, x, y, width, height, null);

		FileOutputStream out = null;
		try {
			File directory = new File(directoryPath);
			if (!directory.exists()) {
				directory.mkdirs();
			}
			out = new FileOutputStream(directoryPath + filename);
			ImageIO.write(tag, "jpg", out);
		} catch (Exception e) {
			throw new UploadImageException(UploadImageException.SYSTEM_ERROR);
		} finally {
			try {
				if (null != out)
					out.close();
			} catch (Exception e) {
				log.error(e.getMessage(), e);
			}
		}
	}

	public static void main(String[] s) {
		List<MarkFont> list = new ArrayList<MarkFont>();
		list.add(new MarkFont(84, 70, new Font(Font.SERIF, Font.ITALIC, 16),
				Color.BLUE, "老迈Bde"));
		list.add(new MarkFont(104, 100, new Font(Font.DIALOG_INPUT,
				Font.ITALIC, 20), Color.gray, "不久后,你会在"));
		String content = "厕所";
		list.add(new MarkFont(250, 100, new Font(Font.DIALOG, Font.ITALIC, 25),
				Color.red, content));
		int tagerX = content.length() * 25;
		list.add(new MarkFont(250 + tagerX + 14, 100, new Font(Font.SERIF,
				Font.ITALIC, 20), Color.gray, "偶遇ta"));
		new ImageManager()
				.markImage(
						"E:\\juzhai\\font\\WebContent\\images\\user\\0\\0\\5\\180\\11681245-33b4-4123-9a4a-5c1ae09195c5.jpg",
						"D:/img/img/back.png",
						"C:\\Documents and Settings\\Administrator\\桌面\\",
						"xxoo.jpg", 198, 133, 0, list);
	}

	@Override
	public String[] uploadTempImage(String imageUrl)
			throws UploadImageException {
		HttpURLConnection urlcon = null;
		try {
			URL url = new URL(imageUrl);
			urlcon = (HttpURLConnection) url.openConnection();
		} catch (Exception e) {
			throw new UploadImageException(UploadImageException.UPLOAD_ERROR);
		}
		checkImage(urlcon);

		String dateFolder = DateFormat.SDF.format(new Date());
		String directoryPath = uploadTempImageHome + dateFolder
				+ File.separator;
		String fileName = ImageUtil.generateUUIDJpgFileName();
		try {
			if (!FileUtil.writeStreamToFile(directoryPath, fileName,
					urlcon.getInputStream())) {
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
	public void checkImage(HttpURLConnection httpURLConnection)
			throws UploadImageException {
		try {
			if (httpURLConnection.getResponseCode() != 200) {
				throw new UploadImageException(
						UploadImageException.UPLOAD_ERROR);
			}
		} catch (IOException e) {
			throw new UploadImageException(UploadImageException.UPLOAD_ERROR);
		}
		// 根据响应获取文件大小
		long fileLength = httpURLConnection.getContentLength();
		if (fileLength == 0) {
			throw new UploadImageException(UploadImageException.UPLOAD_ERROR);
		}
		String url = httpURLConnection.getURL().toString();
		String filename = url.substring(url.lastIndexOf('/') + 1);
		int code = ImageUtil.validationImage(uploadImageTypes, uploadImageSize,
				fileLength, filename);
		switch (code) {
		case -1:
			throw new UploadImageException(
					UploadImageException.UPLOAD_TYPE_ERROR);
		case -2:
			throw new UploadImageException(
					UploadImageException.UPLOAD_SIZE_ERROR);
		}

	}

}
