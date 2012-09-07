package com.juzhai.mobile.dialog.service;

import org.springframework.web.multipart.MultipartFile;

import com.juzhai.core.exception.UploadImageException;
import com.juzhai.core.web.session.UserContext;
import com.juzhai.home.exception.DialogException;

public interface IDialogMService {

	/**
	 * 发送带有图片的私信
	 * 
	 * @param context
	 * @param targetUid
	 * @param content
	 * @param imagePath
	 * @return
	 * @throws DialogException
	 * @throws UploadImageException
	 */
	long sendSMS(UserContext context, long targetUid, String content,
			MultipartFile image) throws DialogException, UploadImageException;
}
