package com.juzhai.home.bean;

public enum DialogContentTemplate {

	INTEREST_USER("interest.dialog.content"), RESPONSE_POST(
			"response.dialog.content"), EDIT_PROFILE("profile.dialog.content"), PASS_LOGO(
			"pass.logo.dialog.content");

	private String name;

	private DialogContentTemplate(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}
}
