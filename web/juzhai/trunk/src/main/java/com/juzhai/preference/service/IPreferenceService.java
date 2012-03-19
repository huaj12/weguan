package com.juzhai.preference.service;

import java.util.List;

import com.juzhai.cms.controller.form.PreferenceForm;
import com.juzhai.cms.controller.form.PreferenceListForm;
import com.juzhai.passport.model.Preference;
import com.juzhai.preference.exception.InputPreferenceException;

public interface IPreferenceService {
	/**
	 * 添加用户偏好问题
	 * 
	 * @param form
	 * @throws InputPreferenceException
	 */
	void addPreference(PreferenceForm form) throws InputPreferenceException;

	/**
	 * 获取所有用户偏好问题从缓存里取
	 * 
	 * @return
	 */
	List<Preference> listCachePreference();

	/**
	 * 获取所有用户偏好问题
	 * 
	 * @return
	 */
	List<Preference> listPreference();

	/**
	 * 屏蔽偏好设置问题
	 * 
	 * @param id
	 */
	void shieldPreference(long id);

	/**
	 * 更新偏好设置顺序
	 * 
	 * @param preferenceListForm
	 */
	void updatePreferenceSort(PreferenceListForm preferenceListForm);

	/**
	 * 更新偏好设置
	 * 
	 * @param form
	 */
	void updatePreference(PreferenceForm form) throws InputPreferenceException;

	/**
	 * 获取偏好
	 * 
	 * @param Id
	 * @return
	 */
	Preference getPreference(Long id);

}
