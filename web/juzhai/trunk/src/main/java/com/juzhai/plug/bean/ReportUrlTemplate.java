package com.juzhai.plug.bean;

public enum ReportUrlTemplate {
	MESSAGE_URL("report.message.url"), COMMENT_URL("report.comment.url"), PROFILE_URL(
			"report.profile.url");

	private String name;

	private ReportUrlTemplate(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}
}
