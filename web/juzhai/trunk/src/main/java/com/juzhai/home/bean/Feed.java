package com.juzhai.home.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;

import com.juzhai.act.model.Act;
import com.juzhai.passport.bean.ProfileCache;
import com.juzhai.passport.bean.TpFriend;

public class Feed implements Serializable {

	private static final long serialVersionUID = -2496594920623735162L;

	public enum FeedType {
		SPECIFIC, RANDOM, GRADE
	}

	private TpFriend tpFriend;

	private ProfileCache profileCache;

	private Date date;

	private List<Act> actList = new ArrayList<Act>();

	private FeedType feedType;

	public Feed(ProfileCache profileCache, FeedType feedType, Date date,
			Act... acts) {
		this.profileCache = profileCache;
		this.feedType = feedType;
		this.date = date;
		if (null != acts) {
			for (Act act : acts) {
				if (null != act) {
					this.actList.add(act);
				}
			}
		}
	}

	public Feed(TpFriend tpFriend, FeedType feedType, Act... acts) {
		this.tpFriend = tpFriend;
		this.feedType = feedType;
		if (null != acts) {
			for (Act act : acts) {
				if (null != act) {
					this.actList.add(act);
				}
			}
		}
	}

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

	public TpFriend getTpFriend() {
		return tpFriend;
	}

	public void setTpFriend(TpFriend tpFriend) {
		this.tpFriend = tpFriend;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Act getAct() {
		if (CollectionUtils.isEmpty(getActList())) {
			return null;
		} else {
			return getActList().get(0);
		}
	}
}
