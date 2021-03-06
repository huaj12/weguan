package com.juzhai.home.bean;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;

import com.juzhai.act.model.Act;
import com.juzhai.act.model.Question;
import com.juzhai.passport.bean.ProfileCache;
import com.juzhai.passport.bean.TpFriend;

public class Feed implements Serializable {

	private static final long serialVersionUID = -2496594920623735162L;

	private static final SimpleDateFormat SDF = new SimpleDateFormat(
			"yyyy-MM-dd hh:mm");

	public enum FeedType {
		SPECIFIC, YUE, QUESTION, INVITE
	}

	private TpFriend tpFriend;

	private ProfileCache profileCache;

	private Date date;

	private List<Act> actList = new ArrayList<Act>();

	private FeedType feedType;

	private Question question;

	private String tpHomeUrl;

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

	public Feed(TpFriend tpFriend, FeedType feedType, Question question,
			Act... acts) {
		this.tpFriend = tpFriend;
		this.feedType = feedType;
		this.question = question;
		if (null != acts) {
			for (Act act : acts) {
				if (null != act) {
					this.actList.add(act);
				}
			}
		}
	}

	public Feed(FeedType feedType, Date date) {
		this.feedType = feedType;
		this.date = date;
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

	public Question getQuestion() {
		return question;
	}

	public void setQuestion(Question question) {
		this.question = question;
	}

	public String[] getAnswers() {
		if (null == getQuestion()
				|| StringUtils.isEmpty(getQuestion().getAnswer())) {
			return ArrayUtils.EMPTY_STRING_ARRAY;
		} else {
			String[] answers = getQuestion().getAnswer().split("\\|");
			return answers;
		}
	}

	public String getQuestionNamePrefix() {
		if (null != getQuestion()) {
			return getQuestion().getName().split("\\{0\\}")[0];
		} else {
			return StringUtils.EMPTY;
		}
	}

	public String getQuestionNameSuffix() {
		if (null != getQuestion()) {
			return getQuestion().getName().split("\\{0\\}")[1];
		} else {
			return StringUtils.EMPTY;
		}
	}

	public String getTpHomeUrl() {
		if (StringUtils.isEmpty(tpHomeUrl)) {
			return "#";
		}
		if (tpFriend != null) {
			return tpHomeUrl.replace("{0}", tpFriend.getUserId());
		} else if (null != profileCache
				&& StringUtils.isNotEmpty(profileCache.getTpIdentity())) {
			return tpHomeUrl.replace("{0}", profileCache.getTpIdentity());
		} else {
			return "#";
		}
	}

	public void setTpHomeUrl(String tpHomeUrl) {
		this.tpHomeUrl = tpHomeUrl;
	}

	/**
	 * 为邮件vm用
	 * 
	 * @return
	 */
	public String getDateFormat() {
		if (null == getDate()) {
			return StringUtils.EMPTY;
		}
		return SDF.format(getDate());
	}
}
