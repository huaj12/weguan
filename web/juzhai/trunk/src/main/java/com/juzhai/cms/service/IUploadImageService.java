package com.juzhai.cms.service;


import org.springframework.web.multipart.MultipartFile;

public interface IUploadImageService {
	void uploadImg(long id, String fileName, MultipartFile imgFile);

	void deleteImg(long id, String fileName);

	String getImgType(MultipartFile imgFile);
	
	byte[] getFile(long id, String fileName);
}
