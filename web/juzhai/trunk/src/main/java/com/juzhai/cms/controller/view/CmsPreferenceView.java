package com.juzhai.cms.controller.view;

import com.juzhai.passport.model.Preference;
import com.juzhai.preference.bean.Input;

public class CmsPreferenceView {
	private Preference preference;
	private Input input;

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

}
