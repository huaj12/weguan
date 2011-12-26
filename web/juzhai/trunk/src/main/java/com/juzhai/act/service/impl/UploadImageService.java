package com.juzhai.act.service.impl;

import java.awt.Rectangle;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

@Deprecated
public class UploadImageService implements IUploadImageService {
	private final Log log = LogFactory.getLog(getClass());

//	@Value("${upload.act.image.home}")
//	private String uploadActImageHome;
//	@Value("${upload.user.image.home}")
//	private String uploadUserImageHome;
//	@Value("${upload.editor.image.home}")
//	private String uploadEditorImageHome;
//	@Value("${upload.temp.image.home}")
//	private String uploadTempImageHome;
//	@Value("${upload.image.size}")
//	private int uploadImageSize;
//	@Value("${upload.image.types}")
//	private String uploadImageTypes;
//	@Value("${editor.web.image.path}")
//	private String editorWebPath;
//	@Value("${temp.web.image.path}")
//	private String tempWebPath;

//	@Autowired
//	private MessageSource messageSource;
//
//	@Override
//	public void uploadImg(long id, String fileName, MultipartFile imgFile) {
//		try {
//			if (ImageUtil.validationImage(uploadImageTypes, uploadImageSize,
//					imgFile) == 1) {
//				String directoryPath = uploadActImageHome
//						+ ImageUtil.generateHierarchyImagePath(id,
//								SizeType.ORIGINAL);
//				FileUtil.writeStreamToFile(directoryPath, fileName,
//						imgFile.getInputStream());
//				for (SizeType sizeType : SizeType.values()) {
//					if (sizeType.getType() > 0) {
//						String distDirectoryPath = uploadActImageHome
//								+ ImageUtil.generateHierarchyImagePath(id,
//										sizeType);
//						ImageUtil.reduceImage(directoryPath + fileName,
//								distDirectoryPath, fileName,
//								sizeType.getType(), sizeType.getType());
//					}
//				}
//			} else {
//				log.error("upload img file type is error or img size to long actid="
//						+ id);
//			}
//		} catch (Exception e) {
//			log.error("upload img is error.actid=" + id, e);
//		}
//
//	}
//
//	@Override
//	public void deleteActImages(long id, String fileName) {
//		try {
//			String directoryPath = uploadActImageHome
//					+ ImageUtil.generateHierarchyImagePath(id,
//							SizeType.ORIGINAL);
//			new File(directoryPath + fileName).delete();
//			for (SizeType sizeType : SizeType.values()) {
//				if (sizeType.getType() > 0) {
//					String distDirectoryPath = uploadActImageHome
//							+ ImageUtil
//									.generateHierarchyImagePath(id, sizeType);
//					new File(distDirectoryPath + fileName).delete();
//				}
//			}
//		} catch (Exception e) {
//			log.error("delete img is error.actid=" + id, e);
//		}
//
//	}
//
//	@Override
//	public String getImgType(MultipartFile imgFile) {
//		if (imgFile == null)
//			return "";
//		String fileName = imgFile.getOriginalFilename();
//		// 获取上传文件类型的扩展名,先得到.的位置，再截取从.的下一个位置到文件的最后，最后得到扩展名
//		String ext = fileName.substring(fileName.lastIndexOf(".") + 1,
//				fileName.length());
//		// 对扩展名进行小写转换
//		ext = ext.toLowerCase();
//		return ext;
//
//	}
//
//	private byte[] getBytesFromFile(File file) throws IOException {
//		InputStream is = new FileInputStream(file);
//		// 获取文件大小
//		long length = file.length();
//		if (length > Integer.MAX_VALUE) {
//			// 文件太大，无法读取
//			throw new IOException("File is to large " + file.getName());
//		}
//		// 创建一个数据来保存文件数据
//		byte[] bytes = new byte[(int) length];
//		// 读取数据到byte数组中
//		int offset = 0;
//		int numRead = 0;
//		while (offset < bytes.length
//				&& (numRead = is.read(bytes, offset, bytes.length - offset)) >= 0) {
//			offset += numRead;
//		}
//		// 确保所有数据均被读取
//		if (offset < bytes.length) {
//			throw new IOException("Could not completely read file "
//					+ file.getName());
//		}
//		// Close the input stream and return bytes
//		is.close();
//		return bytes;
//	}
//
//	@Override
//	public byte[] getActFile(long id, String fileName) {
//		try {
//			String directoryPath = uploadActImageHome
//					+ ImageUtil.generateHierarchyImagePath(id, SizeType.BIG);
//			File file = new File(directoryPath + fileName);
//			return getBytesFromFile(file);
//		} catch (Exception e) {
//			log.error("getFile is error.actid=" + id + " error message:"
//					+ e.getMessage());
//			return null;
//		}
//	}
//
//	@Override
//	public String uploadTempImg(String fileName, MultipartFile imgFile)
//			throws UploadImageException {
//		return uploadTempImage(1, uploadTempImageHome, fileName, imgFile);
//
//	}
//
//	private String uploadTempImage(long id, String path, String fileName,
//			MultipartFile imgFile) throws UploadImageException {
//		Date date = new Date();
//		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//		path = path + sdf.format(date) + File.separator;
//		int code = ImageUtil.validationImage(uploadImageTypes, uploadImageSize,
//				imgFile);
//		if (code == 1) {
//			try {
//				String directoryPath = path
//						+ ImageUtil.generateHierarchyImagePath(id,
//								SizeType.ORIGINAL);
//				FileUtil.writeStreamToFile(directoryPath, fileName,
//						imgFile.getInputStream());
//				String url = StaticUtil.u(tempWebPath
//						+ sdf.format(date)
//						+ File.separator
//						+ ImageUtil.generateHierarchyImageWebPath(id,
//								SizeType.ORIGINAL) + fileName);
//				return url;
//			} catch (Exception e) {
//				throw new UploadImageException();
//			}
//		} else {
//			if (code == -2) {
//				throw new UploadImageException(messageSource.getMessage(
//						UploadImageException.UPLOAD_SIZE_ERROR, null,
//						Locale.SIMPLIFIED_CHINESE));
//			} else if (code == -1) {
//				throw new UploadImageException(messageSource.getMessage(
//						UploadImageException.UPLOAD_TYPE_ERROR, null,
//						Locale.SIMPLIFIED_CHINESE));
//			} else {
//				throw new UploadImageException(messageSource.getMessage(
//						UploadImageException.UPLOAD_ERROR, null,
//						Locale.SIMPLIFIED_CHINESE));
//			}
//		}
//	}
//
//	@Override
//	public String cutUserImage(String logo, long id, Integer x, Integer y,
//			Integer height, Integer width) {
//		try {
//			String directoryPath = uploadUserImageHome
//					+ ImageUtil.generateHierarchyImagePath(id,
//							SizeType.ORIGINAL);
//			String fileName = UUID.randomUUID().toString() + ".jpg";
//			// 获取原图
//			ImageUtil.getUrlImage(logo, directoryPath, fileName);
//			// 裁剪团图
//			String cutDirectoryPath = uploadUserImageHome
//					+ ImageUtil.generateHierarchyImagePath(id, SizeType.BIG);
//			ImageUtil.cutImage(directoryPath + fileName, cutDirectoryPath,
//					fileName, SizeType.BIG.getType(), SizeType.BIG.getType(),
//					x, y, width, height);
//			for (SizeType sizeType : SizeType.values()) {
//				if (sizeType.getType() > 0
//						&& sizeType.getType() < SizeType.BIG.getType()) {
//					String distDirectoryPath = uploadUserImageHome
//							+ ImageUtil
//									.generateHierarchyImagePath(id, sizeType);
//					ImageUtil.reduceImage(cutDirectoryPath + fileName,
//							distDirectoryPath, fileName, sizeType.getType(),
//							sizeType.getType());
//				}
//			}
//			return fileName;
//		} catch (Exception e) {
//			log.error("copyActImage is error tempLogo=" + logo
//					+ " errorMessage=" + e.getMessage());
//			return "";
//		}
//	}
//
//	@Override
//	public void deleteUserImages(long id, String fileName) {
//		for (SizeType sizeType : SizeType.values()) {
//			String path = uploadUserImageHome
//					+ ImageUtil.generateHierarchyImagePath(id, sizeType);
//			File file = new File(path + fileName);
//			if (file.exists()) {
//				file.delete();
//			}
//		}
//
//	}
//
//	private List<String> matchImage(String str) {
//		List<String> imgList = new ArrayList<String>();
//		String regEx = "<img.*?src=\"(.*?)\"";
//		Pattern pat = Pattern.compile(regEx);
//		Matcher mat = pat.matcher(str);
//		while (mat.find()) {
//			imgList.add(mat.group(1));
//		}
//		return imgList;
//	}

}
