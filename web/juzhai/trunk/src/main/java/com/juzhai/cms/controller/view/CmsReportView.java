package com.juzhai.cms.controller.view;

import com.juzhai.passport.bean.ProfileCache;
import com.juzhai.passport.model.Report;

public class CmsReportView {
	private ProfileCache reportProfile;
	private ProfileCache createProfile;
	private Report report;

	public CmsReportView(ProfileCache reportProfile,
			ProfileCache createProfile, Report report) {
		this.report = report;
		this.reportProfile = reportProfile;
		this.createProfile = createProfile;
	}

	public ProfileCache getReportProfile() {
		return reportProfile;
	}

	public void setReportProfile(ProfileCache reportProfile) {
		this.reportProfile = reportProfile;
	}

	public ProfileCache getCreateProfile() {
		return createProfile;
	}

	public void setCreateProfile(ProfileCache createProfile) {
		this.createProfile = createProfile;
	}

	public Report getReport() {
		return report;
	}

	public void setReport(Report report) {
		this.report = report;
	}

}
