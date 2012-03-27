package com.juzhai.plug.bean;

public enum ReportContentType {
	MESSAGE(1, "report.message.url"), COMMENT(2, "report.comment.url"), PROFILE(
			3, "report.profile.url");
	private int type;

	private String url;

	private ReportContentType(int type, String url) {
		this.type = type;
		this.url = url;
	}

	public int getType() {
		return type;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public static ReportContentType getReportContentTypeEnum(int type) {
		for (ReportContentType reportContentType : values()) {
			if (type == reportContentType.getType()) {
				return reportContentType;
			}
		}
		return null;
	}

	// TODO (done) 合并的方式错了。应该把url对应的key作为枚举里的一个成员变量。

}
