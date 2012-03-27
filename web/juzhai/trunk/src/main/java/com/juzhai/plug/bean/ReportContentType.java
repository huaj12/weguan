package com.juzhai.plug.bean;

public enum ReportContentType {
	MESSAGE(1), COMMENT(2), PROFILE(3);
	private int type;

	private ReportContentType(int type) {
		this.type = type;
	}

	public int getType() {
		return type;
	}

	public static ReportContentType getReportContentTypeEnum(int type) {
		for (ReportContentType reportContentType : values()) {
			if (type == reportContentType.getType()) {
				return reportContentType;
			}
		}
		return null;
	}

	public static String getReportUrlTemplate(int type) {
		String str = null;
		switch (ReportContentType.getReportContentTypeEnum(type)) {
		case MESSAGE:
			str = "report.message.url";
			break;
		case COMMENT:
			str = "report.comment.url";
			break;
		case PROFILE:
			str = "report.profile.url";
			break;
		}
		return str;
	}

}
