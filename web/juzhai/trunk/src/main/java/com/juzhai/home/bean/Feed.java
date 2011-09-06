package com.juzhai.home.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.juzhai.act.model.Act;
import com.juzhai.passport.bean.ProfileCache;

public class Feed implements Serializable {

	private static final long serialVersionUID = -2496594920623735162L;

	public enum FeedType {
		SPECIFIC, RANDOM
	}

	private ProfileCache profileCache;

	private List<Act> actList = new ArrayList<Act>();

	private FeedType feedType;

	public ProfileCache getProfileCache() {
		return profileCache;
	}

	public void setProfileCache(ProfileCache profileCache) {
		this.profileCache = profileCache;
	}

	public FeedType getFeedType() {
		return feedType;
	}

	public void setFeedType(FeedType feedType) {
		this.feedType = feedType;
	}

	public List<Act> getActList() {
		return actList;
	}

	public void addAct(Act act) {
		getActList().add(act);
	}
}
