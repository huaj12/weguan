package com.juzhai.mobile.passport.service;

import com.juzhai.core.exception.UploadImageException;
import com.juzhai.mobile.passport.controller.form.ProfileMForm;
import com.juzhai.passport.exception.ProfileInputException;

public interface IProfileMService {

	/**
	 * 保存头像和个人资料（用于手机应用）
	 * 
	 * @param uid
	 * @param profileForm
	 * @throws UploadImageException
	 * @throws ProfileInputException
	 */
	void updateLogoAndProfile(long uid, ProfileMForm profileForm)
			throws UploadImageException, ProfileInputException;

	/**
	 * 引导保存头像和个人资料（用于手机应用）
	 * 
	 * @param uid
	 * @param profileForm
	 * @throws UploadImageException
	 * @throws ProfileInputException
	 */
	void guideLogoAndProfile(long uid, ProfileMForm profileForm)
			throws UploadImageException, ProfileInputException;
}
