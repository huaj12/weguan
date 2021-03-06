package com.juzhai.post.service;

import org.springframework.web.multipart.MultipartFile;

import com.juzhai.core.exception.UploadImageException;
import com.juzhai.core.image.JzImageSizeType;

public interface IIdeaImageService {
	/**
	 * 上传idea图片(有postId则复制post图片)
	 * 
	 * @param postId
	 * @param image
	 * @param ideaId
	 * @param picName
	 * @return
	 * @throws UploadImageException
	 */
	String uploadIdeaPic(Long postId, MultipartFile image, Long ideaId,
			String picName) throws UploadImageException;

	/**
	 * 上传idea临时图片
	 * 
	 * @param uid
	 * @param image
	 * @return
	 * @throws UploadImageException
	 */
	public String[] uploadTempIdeaImg(MultipartFile image)
			throws UploadImageException;

	/**
	 * 转换图片，从rawIdea的Logo转到idea的Logo
	 * 
	 * @param filePath
	 * @param actId
	 * @return
	 */
	String intoIdeaLogo(long ideaId, String rawIdeaLogo);

	/**
	 * 转化富文本图片
	 * 
	 * @param ideaId
	 * @param detail
	 * @return
	 */
	String intoEditorImg(long ideaId, String detail);

	/**
	 * 上传idea临时图片
	 * 
	 * @param imageUrl
	 * @return
	 * @throws UploadImageException
	 */
	public String[] uploadTempIdeaImg(String imageUrl)
			throws UploadImageException;

	/**
	 * 获取图片
	 * 
	 * @param ideaId
	 * @param fileName
	 * @param sizeType
	 * @return
	 */
	byte[] getIdeaFile(long ideaId, String fileName, JzImageSizeType sizeType);
}
