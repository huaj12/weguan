package com.juzhai.home.bean;

public enum DialogContentTemplate {

	INTEREST_USER("interest.dialog.content"), RESPONSE_POST(
			"response.dialog.content"), EDIT_PROFILE("profile.dialog.content"), PASS_LOGO(
			"pass.logo.dialog.content"), DENY_LOGO("deny.logo.dialog.content"), WELCOME_USER(
			"welcome.user.dialog.content"), BECOME_IDEA(
			"become.idea.dialog.content"), UPLOAD_LOGO(
			"upload.logo.dialog.content"), PRIVATE_DATE(
			"private.date.dialog.content"), USER_CREATE_IDEA(
			"user.create.idea.dialog.content"), USER_UPDATE_IDEA(
			"user.update.idea.dialog.content"), PASS_RAW_IDEA(
			"pass.raw.idea.dialog.content"), RESCUE_USER(
			"rescue.user.dialog.content");

	private String name;

	private DialogContentTemplate(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}
}
