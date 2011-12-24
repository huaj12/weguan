package com.juzhai.act.service;

import org.springframework.web.multipart.MultipartFile;

import com.juzhai.act.exception.UploadImageException;
import com.juzhai.cms.bean.SizeType;

public interface IActImageService {

	/**
	 * 上传待审核项目Logo
	 * 
	 * @param uid
	 * @param image
	 * @throws UploadImageException
	 */
	String[] uploadRawActLogo(long uid, MultipartFile image)
			throws UploadImageException;

	/**
	 * 上传项目logo
	 * 
	 * @param uid
	 * @param image
	 * @return
	 * @throws UploadImageException
	 */
	String uploadActLogo(long uid, long actId, MultipartFile image) throws UploadImageException;

	/**
	 * 转换图片，从rawAct的Logo转到act的Logo
	 * 
	 * @param filePath
	 * @param actId
	 * @return
	 */
	String intoActLogo(long actId, String rawActLogo);

	/**
	 * 转换图片，从rawAct的富文本图片转到act的富文本图片
	 * 
	 * @param detail
	 * @param uid
	 * @return
	 */
	String intoEditorImg(long actId, String detail);

	/**
	 * 读取项目图片到字节数组
	 * 
	 * @param actId
	 * @param fileName
	 * @return
	 */
	byte[] getActFile(long actId, String fileName, SizeType sizeType);
}
