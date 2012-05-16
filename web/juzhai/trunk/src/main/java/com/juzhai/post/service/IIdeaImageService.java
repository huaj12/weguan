package com.juzhai.post.service;

import org.springframework.web.multipart.MultipartFile;

import com.juzhai.core.exception.UploadImageException;

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
	// TODO (done)
	// 从名字上理解，“上传rawIdea的Logo”，事实上还会用在，富文本编辑器里上传图片。所以直接把“上传idea临时图片”翻译成方法名
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
}
