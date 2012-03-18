package com.juzhai.preference.service;

import com.juzhai.passport.controller.form.UserPreferenceListForm;
import com.juzhai.passport.model.UserPreference;
import com.juzhai.preference.exception.InputUserPreferenceException;

public interface IUserPreferenceService {
	/**
	 * 添加偏好
	 * 
	 * @param userPreferenceForm
	 */
	void addUserPreference(UserPreferenceListForm userPreferenceListForm,
			Long uid) throws InputUserPreferenceException;

	/**
	 * 获取该用户的填写的偏好
	 * 
	 * @param preferenceId
	 * @param uid
	 * @return
	 */
	UserPreference getUserPreference(Long preferenceId, Long uid);
}
