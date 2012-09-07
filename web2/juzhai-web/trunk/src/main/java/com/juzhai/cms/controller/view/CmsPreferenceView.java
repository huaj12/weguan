package com.juzhai.cms.controller.view;

import com.juzhai.preference.bean.Input;
import com.juzhai.preference.model.Preference;

public class CmsPreferenceView {
	private Preference preference;
	private Input input;
	private String[] defaultValues;

	public Preference getPreference() {
		return preference;
	}

	public void setPreference(Preference preference) {
		this.preference = preference;
	}

	public Input getInput() {
		return input;
	}

	public void setInput(Input input) {
		this.input = input;
	}

	public String[] getDefaultValues() {
		return defaultValues;
	}

	public void setDefaultValues(String[] defaultValues) {
		this.defaultValues = defaultValues;
	}

}
