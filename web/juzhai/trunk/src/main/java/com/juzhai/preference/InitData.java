package com.juzhai.preference;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.juzhai.preference.model.Preference;
import com.juzhai.preference.service.IPreferenceService;

@Component("preferenceInitData")
public class InitData {

	public final static Map<Long, Preference> PREFERENCE_MAP = new HashMap<Long, Preference>();
	// 用于显示的用户偏好
	// TODO (review) 从总的map里循环就行了
	public final static Map<Long, Preference> SHOW_PREFERENCE_MAP = new HashMap<Long, Preference>();
	@Autowired
	private IPreferenceService preferenceService;

	@PostConstruct
	public void init() {
		initPreference();
		initShowPreference();
	}

	private void initShowPreference() {
		List<Preference> list = preferenceService.listShowPreference();
		for (Preference preference : list) {
			SHOW_PREFERENCE_MAP.put(preference.getId(), preference);
		}

	}

	void initPreference() {
		List<Preference> list = preferenceService.listPreference();
		for (Preference preference : list) {
			PREFERENCE_MAP.put(preference.getId(), preference);
		}
	}

}
