package com.juzhai.plug.bean;

public enum ReportUrlTemplate {
	MESSAGE_URL("report_message_url"), COMMENT_URL("report_comment_url"), PROFILE_URL(
			"report_profile_url");

	private String name;

	private ReportUrlTemplate(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}
}
