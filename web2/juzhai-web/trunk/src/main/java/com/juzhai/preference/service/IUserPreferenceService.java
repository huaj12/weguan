package com.juzhai.preference.service;

import java.util.List;

import com.juzhai.passport.controller.form.UserPreferenceListForm;
import com.juzhai.passport.controller.view.UserPreferenceView;
import com.juzhai.preference.exception.InputUserPreferenceException;
import com.juzhai.preference.model.Preference;
import com.juzhai.preference.model.UserPreference;

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

	/**
	 * 找出该用户所有填写过的题目
	 * 
	 * @param uid
	 * @return
	 */
	List<UserPreference> listUserPreference(Long uid);

	/**
	 * 
	 * @param uid
	 * @param preferenceId
	 * @return 如果是文本框或者单选框取第一个元素 如果是min——max 第一个元素是min值 第二个元素是max值 复选框有多个值 分隔符,
	 *         不存在返回null
	 */
	List<String> getUserAnswer(long uid, long preferenceId);

	/**
	 * 偏好设置列表
	 * 
	 * @param userPreferences
	 * @param preferences
	 * @return
	 */
	List<UserPreferenceView> convertToUserPreferenceView(
			List<UserPreference> userPreferences, List<Preference> preferences);

	/**
	 * 用户偏好列表（answer==null不显示）
	 * 
	 * @param userPreferences
	 * @param preferences
	 * @return
	 */
	List<UserPreferenceView> convertToUserHomePreferenceView(
			List<UserPreference> userPreferences, List<Preference> preferences);
}
