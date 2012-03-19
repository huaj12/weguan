package com.juzhai.preference;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.juzhai.passport.model.Preference;
import com.juzhai.preference.service.IPreferenceService;

@Component("preferenceInitData")
public class InitData {

	public static List<Preference> preferenceList = new ArrayList<Preference>();

	@Autowired
	private IPreferenceService preferenceService;

	@PostConstruct
	public void init() {
		initPreference();
	}

	void initPreference() {
		preferenceList.addAll(preferenceService.listPreference());
	}

}
