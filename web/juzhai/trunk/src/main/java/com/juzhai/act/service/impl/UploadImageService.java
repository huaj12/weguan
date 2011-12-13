package com.juzhai.act.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.juzhai.act.exception.UploadImageException;
import com.juzhai.act.service.IUploadImageService;
import com.juzhai.cms.bean.SizeType;
import com.juzhai.core.util.FileUtil;
import com.juzhai.core.util.ImageUtil;
import com.juzhai.core.util.StaticUtil;

@Service
public class UploadImageService implements IUploadImageService {
	private final Log log = LogFactory.getLog(getClass());

	@Value("${upload.image.home}")
	private String uploadImageHome;
	@Value("${upload.temp.image.home}")
	private String uploadTempImageHome;
	@Value("${upload.image.size}")
	private int uploadImageSize;
	@Value("${upload.image.types}")
	private String uploadImageTypes;
	@Autowired
	private MessageSource messageSource;

	@Override
	public void uploadImg(long id, String fileName, MultipartFile imgFile) {
		try {
			if (ImageUtil.validationImage(uploadImageTypes, uploadImageSize,
					imgFile) == 1) {
				String directoryPath = uploadImageHome
						+ ImageUtil.generateHierarchyImagePath(id,
								SizeType.ORIGINAL);
				FileUtil.writeStreamToFile(directoryPath, fileName,
						imgFile.getInputStream());
				for (SizeType sizeType : SizeType.values()) {
					if (sizeType.getType() > 0) {
						String distDirectoryPath = uploadImageHome
								+ ImageUtil.generateHierarchyImagePath(id,
										sizeType);
						ImageUtil.reduceImage(directoryPath + fileName,
								distDirectoryPath, fileName,
								sizeType.getType(), sizeType.getType());
					}
				}
			} else {
				log.error("upload img file type is error or img size to long actid="
						+ id);
			}
		} catch (Exception e) {
			log.error("upload img is error.actid=" + id, e);
		}

	}

	@Override
	public void deleteImg(long id, String fileName) {
		try {
			String directoryPath = uploadImageHome
					+ ImageUtil.generateHierarchyImagePath(id,
							SizeType.ORIGINAL);
			new File(directoryPath + fileName).delete();
			for (SizeType sizeType : SizeType.values()) {
				if (sizeType.getType() > 0) {
					String distDirectoryPath = uploadImageHome
							+ ImageUtil
									.generateHierarchyImagePath(id, sizeType);
					new File(distDirectoryPath + fileName).delete();
				}
			}
		} catch (Exception e) {
			log.error("delete img is error.actid=" + id, e);
		}

	}

	@Override
	public String getImgType(MultipartFile imgFile) {
		if (imgFile == null)
			return "";
		String fileName = imgFile.getOriginalFilename();
		// 获取上传文件类型的扩展名,先得到.的位置，再截取从.的下一个位置到文件的最后，最后得到扩展名
		String ext = fileName.substring(fileName.lastIndexOf(".") + 1,
				fileName.length());
		// 对扩展名进行小写转换
		ext = ext.toLowerCase();
		return ext;

	}

	private byte[] getBytesFromFile(File file) throws IOException {
		InputStream is = new FileInputStream(file);
		// 获取文件大小
		long length = file.length();
		if (length > Integer.MAX_VALUE) {
			// 文件太大，无法读取
			throw new IOException("File is to large " + file.getName());
		}
		// 创建一个数据来保存文件数据
		byte[] bytes = new byte[(int) length];
		// 读取数据到byte数组中
		int offset = 0;
		int numRead = 0;
		while (offset < bytes.length
				&& (numRead = is.read(bytes, offset, bytes.length - offset)) >= 0) {
			offset += numRead;
		}
		// 确保所有数据均被读取
		if (offset < bytes.length) {
			throw new IOException("Could not completely read file "
					+ file.getName());
		}
		// Close the input stream and return bytes
		is.close();
		return bytes;
	}

	@Override
	public byte[] getFile(long id, String fileName) {
		try {
			String directoryPath = uploadImageHome
					+ ImageUtil.generateHierarchyImagePath(id, SizeType.BIG);
			File file = new File(directoryPath + fileName);
			return getBytesFromFile(file);
		} catch (Exception e) {
			log.error("getFile is error.actid=" + id + " error message:"
					+ e.getMessage());
			return null;
		}
	}

	@Override
	public String uploadEditorTempImg(String fileName, MultipartFile imgFile)
			throws UploadImageException {
		return uploadTempImage(1, uploadTempImageHome, fileName, imgFile);

	}

	@Override
	public String uploadActTempImg(String fileName, MultipartFile imgFile)
			throws UploadImageException {
		return uploadTempImage(1, uploadTempImageHome, fileName, imgFile);
	}

	private String uploadTempImage(long id, String path, String fileName,
			MultipartFile imgFile) throws UploadImageException {
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		path = path + sdf.format(date) + File.separator;
		int code = ImageUtil.validationImage(uploadImageTypes, uploadImageSize,
				imgFile);
		if (code == 1) {
			try {
				String directoryPath = path
						+ ImageUtil.generateHierarchyImagePath(id,
								SizeType.ORIGINAL);
				FileUtil.writeStreamToFile(directoryPath, fileName,
						imgFile.getInputStream());
				String url = StaticUtil.u("/images/"
						+ sdf.format(date)
						+ ImageUtil.generateHierarchyImageWebPath(id,
								SizeType.ORIGINAL) + fileName);
				return url;
			} catch (Exception e) {
				throw new UploadImageException();
			}
		} else {
			if (code == -2) {
				throw new UploadImageException(messageSource.getMessage(
						UploadImageException.UPLOAD_SIZE_ERROR, null,
						Locale.SIMPLIFIED_CHINESE));
			} else if (code == -1) {
				throw new UploadImageException(messageSource.getMessage(
						UploadImageException.UPLOAD_TYPE_ERROR, null,
						Locale.SIMPLIFIED_CHINESE));
			} else {
				throw new UploadImageException(messageSource.getMessage(
						UploadImageException.UPLOAD_ERROR, null,
						Locale.SIMPLIFIED_CHINESE));
			}
		}
	}

	@Override
	public void deleteEditorTempImgs(long id) {
		deleteImsg(id, uploadTempImageHome);
	}

	private void deleteImsg(long id, String path) {
		try {
			String directoryPath = path
					+ ImageUtil.generateHierarchyImagePath(id,
							SizeType.ORIGINAL);
			File f = new File(directoryPath);
			if (f.exists() && f.isDirectory()) {
				if (f.listFiles().length == 0) {
					f.delete();
				} else {
					File[] files = f.listFiles();
					for (File file : files) {
						file.delete();
					}
				}
			}
		} catch (Exception e) {
			log.error("deleteImsg is error id=" + id + " errorMessage="
					+ e.getMessage());
		}
	}

	@Override
	public void deleteActTempImages(long id) {
		deleteImsg(id, uploadTempImageHome);

	}

	@Override
	public void copyActImage(String tempLogo, long actId) {
		try {
			String directoryPath = uploadImageHome
					+ ImageUtil.generateHierarchyImagePath(actId,
							SizeType.ORIGINAL);
			File srcFile = new File(tempLogo);
			String fileName = srcFile.getName();
			FileUtil.writeFileToFile(directoryPath, fileName, srcFile);
			for (SizeType sizeType : SizeType.values()) {
				if (sizeType.getType() > 0) {
					String distDirectoryPath = uploadImageHome
							+ ImageUtil.generateHierarchyImagePath(actId,
									sizeType);
					ImageUtil.reduceImage(directoryPath + fileName,
							distDirectoryPath, fileName, sizeType.getType(),
							sizeType.getType());
				}
			}
		} catch (Exception e) {
			log.error("copyActImage is error tempLogo=" + tempLogo
					+ " errorMessage=" + e.getMessage());
		}
	}
}
