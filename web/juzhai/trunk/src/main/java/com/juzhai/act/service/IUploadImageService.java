package com.juzhai.act.service;

import org.springframework.web.multipart.MultipartFile;

import com.juzhai.act.exception.UploadImageException;


public interface IUploadImageService {
	void uploadImg(long id, String fileName, MultipartFile imgFile);

	String uploadEditorTempImg(String fileName, MultipartFile imgFile)
			throws UploadImageException;

	String uploadActTempImg(String fileName, MultipartFile imgFile)
			throws UploadImageException;

	void deleteImg(long id, String fileName);
	
	void deleteEditorTempImgs(long id);
	
	void deleteActTempImages(long id);
	
	void copyActImage(String tempLogo,long actId);

	String getImgType(MultipartFile imgFile);

	byte[] getFile(long id, String fileName);
}
