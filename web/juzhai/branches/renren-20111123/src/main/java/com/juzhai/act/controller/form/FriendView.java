package com.juzhai.act.controller.form;

import java.io.Serializable;
import java.util.List;

import com.juzhai.act.model.Act;
import com.juzhai.passport.model.Profile;

public class FriendView implements Serializable {

	private static final long serialVersionUID = -4548990394055825494L;

	private Profile profile;

	private List<Act> actList;

	private int actCnt;

	public FriendView(Profile profile, List<Act> actList, int actCnt) {
		super();
		this.profile = profile;
		this.actList = actList;
		this.actCnt = actCnt;
	}

	public Profile getProfile() {
		return profile;
	}

	public void setProfile(Profile profile) {
		this.profile = profile;
	}

	public List<Act> getActList() {
		return actList;
	}

	public void setActList(List<Act> actList) {
		this.actList = actList;
	}

	public int getActCnt() {
		return actCnt;
	}

	public void setActCnt(int actCnt) {
		this.actCnt = actCnt;
	}

}
