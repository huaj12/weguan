package com.juzhai.preference;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.juzhai.preference.model.Preference;
import com.juzhai.preference.service.IPreferenceService;

@Component("preferenceInitData")
public class InitData {

	public final static Map<Long, Preference> PREFERENCE_MAP = new LinkedHashMap<Long, Preference>();
	@Autowired
	private IPreferenceService preferenceService;

	@PostConstruct
	public void init() {
		initPreference();
	}

	void initPreference() {
		List<Preference> list = preferenceService.listPreference();
		for (Preference preference : list) {
			PREFERENCE_MAP.put(preference.getId(), preference);
		}
	}

}
