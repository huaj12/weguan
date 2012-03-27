package com.juzhai.cms.controller.view;

import com.juzhai.passport.model.Report;

public class CmsReportView {
	private String reportNickname;
	private String nickname;
	private Report report;

	public CmsReportView(String reportNickname, String nickname, Report report) {
		this.report = report;
		this.nickname = nickname;
		this.reportNickname = reportNickname;
	}

	public String getReportNickname() {
		return reportNickname;
	}

	public void setReportNickname(String reportNickname) {
		this.reportNickname = reportNickname;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public Report getReport() {
		return report;
	}

	public void setReport(Report report) {
		this.report = report;
	}

}
