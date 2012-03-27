package com.juzhai.plug.controller.form;

public class ReportForm {
	private long reportUid;
	private String contentUrl;
	private int reportType;
	private String description;
	private int contentType;
	private long contentId;

	public long getContentId() {
		return contentId;
	}

	public void setContentId(long contentId) {
		this.contentId = contentId;
	}

	public String getContentUrl() {
		return contentUrl;
	}

	public void setContentUrl(String contentUrl) {
		this.contentUrl = contentUrl;
	}

	public long getReportUid() {
		return reportUid;
	}

	public void setReportUid(long reportUid) {
		this.reportUid = reportUid;
	}

	public int getReportType() {
		return reportType;
	}

	public void setReportType(int reportType) {
		this.reportType = reportType;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getContentType() {
		return contentType;
	}

	public void setContentType(int contentType) {
		this.contentType = contentType;
	}

}
