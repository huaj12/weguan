package com.juzhai.act.service.impl;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.juzhai.act.exception.UploadImageException;
import com.juzhai.act.service.IActImageService;
import com.juzhai.core.image.LogoSizeType;
import com.juzhai.core.image.manager.IImageManager;
import com.juzhai.core.util.FileUtil;
import com.juzhai.core.util.ImageUtil;
import com.juzhai.core.util.StaticUtil;

@Service
public class ActImageService implements IActImageService {

	private final Log log = LogFactory.getLog(getClass());
	@Autowired
	private IImageManager imageManager;
	@Value("${upload.act.image.home}")
	private String uploadActImageHome;
	@Value("${web.act.image.path}")
	private String webActImagePath;

	@Override
	public String[] uploadRawActLogo(long uid, MultipartFile image)
			throws UploadImageException {
		return imageManager.uploadTempImage(image);
	}

	@Override
	public String uploadActLogo(long uid, long actId, MultipartFile image)
			throws UploadImageException {
		String directoryPath = uploadActImageHome
				+ ImageUtil.generateHierarchyImagePath(actId,
						LogoSizeType.ORIGINAL.getType());
		String fileName = imageManager.uploadImage(directoryPath, image);
		String srcFileName = directoryPath + fileName;
		for (LogoSizeType sizeType : LogoSizeType.values()) {
			if (sizeType.getType() > 0) {
				String distDirectoryPath = uploadActImageHome
						+ ImageUtil.generateHierarchyImagePath(actId,
								sizeType.getType());
				imageManager.reduceImage(srcFileName, distDirectoryPath,
						fileName, sizeType.getType(), sizeType.getType());
			}
		}
		return fileName;
	}

	@Override
	public String intoActLogo(long actId, String rawActLogo) {
		try {
			File srcFile = new File(imageManager.getUploadTempImageHome()
					+ rawActLogo);
			String fileName = srcFile.getName();
			String directoryPath = uploadActImageHome
					+ ImageUtil.generateHierarchyImagePath(actId,
							LogoSizeType.ORIGINAL.getType());
			FileUtil.writeFileToFile(directoryPath, fileName, srcFile);
			for (LogoSizeType sizeType : LogoSizeType.values()) {
				if (sizeType.getType() > 0) {
					String distDirectoryPath = uploadActImageHome
							+ ImageUtil.generateHierarchyImagePath(actId,
									sizeType.getType());
					imageManager.reduceImage(directoryPath + fileName,
							distDirectoryPath, fileName, sizeType.getType(),
							sizeType.getType());
				}
			}
			return fileName;
		} catch (Exception e) {
			log.error("intoActLogo is error actId=" + actId + "  rawActLogo="
					+ rawActLogo + " errorMessage=" + e.getMessage());
			return null;
		}
	}

	@Override
	public String intoEditorImg(long actId, String detail) {
		String innerUrlPrefix = StaticUtil.getPrefixStatic()
				+ imageManager.getWebTempImagePath();
		List<String> list = matchImage(detail);
		for (String url : list) {
			if (url.startsWith(innerUrlPrefix)) {
				String srcFileName = url.replace(innerUrlPrefix,
						imageManager.getUploadTempImageHome()).replace(
						ImageUtil.webSeparator, File.separator);
				File srcFile = new File(srcFileName);
				String directoryPath = uploadActImageHome
						+ ImageUtil.generateHierarchyImagePath(actId,
								LogoSizeType.ORIGINAL.getType());
				if (!FileUtil.writeFileToFile(directoryPath, srcFile.getName(),
						srcFile)) {
					return null;
				}
				String newUrl = StaticUtil.u(webActImagePath
						+ ImageUtil.generateHierarchyImageWebPath(actId,
								LogoSizeType.ORIGINAL.getType())
						+ srcFile.getName());
				detail = detail.replace(url, newUrl);
			}
		}
		return detail;
	}

	private List<String> matchImage(String str) {
		List<String> imgList = new ArrayList<String>();
		String regEx = "<img.*?src=\"(.*?)\"";
		Pattern pat = Pattern.compile(regEx);
		Matcher mat = pat.matcher(str);
		while (mat.find()) {
			imgList.add(mat.group(1));
		}
		return imgList;
	}

	@Override
	public byte[] getActFile(long actId, String fileName, LogoSizeType sizeType) {
		try {
			String directoryPath = uploadActImageHome
					+ ImageUtil.generateHierarchyImagePath(actId,
							sizeType.getType());
			File file = new File(directoryPath + fileName);
			return FileUtil.readFileToByteArray(file);
		} catch (IOException e) {
			log.error("getFile is error.actid=" + actId + " error message:"
					+ e.getMessage());
			return null;
		}
	}
}
