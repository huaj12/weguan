package com.juzhai.cms.controller.view;

import com.juzhai.cms.model.RawIdea;
import com.juzhai.passport.bean.ProfileCache;

public class CmsRawIdeaView {
	private RawIdea rawIdea;

	private ProfileCache createUser;

	private ProfileCache correctionUser;

	public CmsRawIdeaView(RawIdea rawIdea, ProfileCache createUser,
			ProfileCache correctionUser) {
		super();
		this.rawIdea = rawIdea;
		this.createUser = createUser;
		this.correctionUser = correctionUser;
	}

	public RawIdea getRawIdea() {
		return rawIdea;
	}

	public void setRawIdea(RawIdea rawIdea) {
		this.rawIdea = rawIdea;
	}

	public ProfileCache getCreateUser() {
		return createUser;
	}

	public void setCreateUser(ProfileCache createUser) {
		this.createUser = createUser;
	}

	public ProfileCache getCorrectionUser() {
		return correctionUser;
	}

	public void setCorrectionUser(ProfileCache correctionUser) {
		this.correctionUser = correctionUser;
	}

}
