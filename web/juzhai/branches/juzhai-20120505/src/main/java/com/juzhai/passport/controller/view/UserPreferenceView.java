package com.juzhai.passport.controller.view;

import com.juzhai.preference.bean.Input;
import com.juzhai.preference.model.Preference;
import com.juzhai.preference.model.UserPreference;

public class UserPreferenceView {
	private Preference preference;
	private UserPreference userPreference;
	private Input input;
	private String answer[];

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

	public String[] getAnswer() {
		return answer;
	}

	public void setAnswer(String[] answer) {
		this.answer = answer;
	}

}
