package com.juzhai.preference.bean;

public enum SiftTypePreference {
	GENDER(1L), AGE(2L);

	private long preferenceId;

	private SiftTypePreference(long preferenceId) {
		this.preferenceId = preferenceId;
	}

	public long getPreferenceId() {
		return preferenceId;
	}
}
