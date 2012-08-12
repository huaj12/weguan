package com.juzhai.plug.bean;

public enum SynchronizeInviteTemplate {
	SYNCHRONIZE_TEXT("synchronize.invite.text"), SYNCHRONIZE_TITLE(
			"synchronize.invite.title"), SYNCHRONIZE_IMAGE(
			"synchronize.invite.image"), SYNCHRONIZE_LINK(
			"synchronize.invite.link");

	private String name;

	private SynchronizeInviteTemplate(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

}
