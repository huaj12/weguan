package com.juzhai.mobile.dialog.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.juzhai.core.exception.UploadImageException;
import com.juzhai.core.image.manager.IImageManager;
import com.juzhai.core.web.session.UserContext;
import com.juzhai.home.exception.DialogException;
import com.juzhai.home.service.IDialogRemoteService;
import com.juzhai.mobile.dialog.service.IDialogMService;

@Service
public class DialogMService implements IDialogMService {

	@Autowired
	private IImageManager imageManager;
	@Autowired
	private IDialogRemoteService dialogService;

	@Override
	public long sendSMS(UserContext context, long targetUid, String content,
			MultipartFile image) throws DialogException, UploadImageException {
		String imagePath = null;
		if (image != null && !image.isEmpty()) {
			String[] paths = imageManager.uploadTempImage(image);
			if (paths != null && paths.length == 2) {
				imagePath = paths[1];
			}
		}
		return dialogService.sendSMS(context, targetUid, content, imagePath);
	}
}
