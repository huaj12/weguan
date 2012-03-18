package com.juzhai.passport.controller.view;

import com.juzhai.passport.model.Preference;
import com.juzhai.passport.model.UserPreference;
import com.juzhai.preference.bean.Input;

public class UserPreferenceView {
	private Preference preference;
	private UserPreference userPreference;
	private Input input;

	public UserPreferenceView(Preference preference,
			UserPreference userPreference, Input input) {
		this.preference = preference;
		this.userPreference = userPreference;
		this.input = input;
	}

	public Preference getPreference() {
		return preference;
	}

	public void setPreference(Preference preference) {
		this.preference = preference;
	}

	public UserPreference getUserPreference() {
		return userPreference;
	}

	public void setUserPreference(UserPreference userPreference) {
		this.userPreference = userPreference;
	}

	public Input getInput() {
		return input;
	}

	public void setInput(Input input) {
		this.input = input;
	}

}
