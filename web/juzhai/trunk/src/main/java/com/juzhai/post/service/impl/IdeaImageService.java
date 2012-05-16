package com.juzhai.post.service.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
import com.juzhai.core.util.FileUtil;
import com.juzhai.core.util.ImageUtil;
import com.juzhai.core.util.StaticUtil;
import com.juzhai.post.service.IIdeaImageService;

@Service
public class IdeaImageService implements IIdeaImageService {
	private final Log log = LogFactory.getLog(getClass());
	@Value("${upload.idea.image.home}")
	private String uploadIdeaImageHome;
	@Value("${upload.post.image.home}")
	private String uploadPostImageHome;
	@Value("${web.idea.image.path}")
	private String webIdeaImagePath;
	@Autowired
	private IImageManager imageManager;

	@Override
	public String uploadIdeaPic(Long postId, MultipartFile image, Long ideaId,
			String picName) throws UploadImageException {
		String fileName = picName;
		// 没有上传图片则复制post的图片
		if (image != null && image.getSize() != 0) {
			String directoryPath = uploadIdeaImageHome
					+ ImageUtil.generateHierarchyImagePath(ideaId,
							JzImageSizeType.ORIGINAL.getType());
			fileName = imageManager.uploadImage(directoryPath, image);
			String srcFileName = directoryPath + fileName;
			// 大图
			String distDirectoryPath = uploadIdeaImageHome
					+ ImageUtil.generateHierarchyImagePath(ideaId,
							JzImageSizeType.BIG.getType());
			imageManager.reduceImageWidth(srcFileName, distDirectoryPath,
					fileName, JzImageSizeType.BIG.getType());

			// 中图
			distDirectoryPath = uploadIdeaImageHome
					+ ImageUtil.generateHierarchyImagePath(ideaId,
							JzImageSizeType.MIDDLE.getType());
			imageManager.reduceImage(srcFileName, distDirectoryPath, fileName,
					JzImageSizeType.MIDDLE.getType());
		}
		if (postId != null && postId > 0 && StringUtils.isNotEmpty(picName)) {
			for (JzImageSizeType sizeType : JzImageSizeType.values()) {
				String directoryPath = uploadIdeaImageHome
						+ ImageUtil.generateHierarchyImagePath(ideaId,
								sizeType.getType());
				File srcFile = new File(uploadPostImageHome
						+ ImageUtil.generateHierarchyImagePath(postId,
								sizeType.getType()) + picName);
				imageManager.copyImage(directoryPath, picName, srcFile);
			}
		}
		return fileName;
	}

	@Override
	public String[] uploadRawIdeaLogo(MultipartFile image)
			throws UploadImageException {
		return imageManager.uploadTempImage(image);
	}

	@Override
	public String intoIdeaLogo(long ideaId, String rawIdeaLogo) {
		try {
			File srcFile = new File(imageManager.getUploadTempImageHome()
					+ rawIdeaLogo);
			String fileName = srcFile.getName();
			String directoryPath = uploadIdeaImageHome
					+ ImageUtil.generateHierarchyImagePath(ideaId,
							//TODO (review) LogoSizeType?
							LogoSizeType.ORIGINAL.getType());
			FileUtil.writeFileToFile(directoryPath, fileName, srcFile);
			for (JzImageSizeType sizeType : JzImageSizeType.values()) {
				if (sizeType.getType() > 0) {
					String distDirectoryPath = uploadIdeaImageHome
							+ ImageUtil.generateHierarchyImagePath(ideaId,
									sizeType.getType());
					imageManager.reduceImage(directoryPath + fileName,
							distDirectoryPath, fileName, sizeType.getType(),
							sizeType.getType());
				}
			}
			return fileName;
		} catch (Exception e) {
			log.error("intoIdeaLogo is error ideaId=" + ideaId
					+ "  rawIdeaLogo=" + rawIdeaLogo + " errorMessage="
					+ e.getMessage());
			return null;
		}
	}

	@Override
	public String intoEditorImg(long ideaId, String detail) {
		String innerUrlPrefix = StaticUtil.getPrefixImage()
				+ imageManager.getWebTempImagePath();
		List<String> list = matchImage(detail);
		for (String url : list) {
			if (url.startsWith(innerUrlPrefix)) {
				String srcFileName = url.replace(innerUrlPrefix,
						imageManager.getUploadTempImageHome()).replace(
						ImageUtil.webSeparator, File.separator);
				File srcFile = new File(srcFileName);
				String directoryPath = uploadIdeaImageHome
						+ ImageUtil.generateHierarchyImagePath(ideaId,
								//TODO (review) LogoSizeType?
								LogoSizeType.ORIGINAL.getType());
				if (!FileUtil.writeFileToFile(directoryPath, srcFile.getName(),
						srcFile)) {
					return null;
				}
				String newUrl = StaticUtil.u(webIdeaImagePath
						+ ImageUtil.generateHierarchyImageWebPath(ideaId,
								//TODO (review) LogoSizeType?
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

}
