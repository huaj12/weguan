/*
 *The code is written by 51juzhai, All rights is reserved
 */
package com.juzhai.passport.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FriendsBean implements Serializable {

	private static final long serialVersionUID = -6897767622066199265L;

	/**
	 * 安装应用的好友（id为juzhai的用户id）
	 */
	private Map<Long, Integer> friendsIntimacy = new HashMap<Long, Integer>();

	/**
	 * 所有第三方好友（id为在第三方的id）
	 */
	@Deprecated
	private List<TpFriend> tpFriends = new ArrayList<TpFriend>();

	/**
	 * 未安装应用的好友（id为在第三方的id）
	 */
	private List<TpFriend> unInstallFriends = new ArrayList<TpFriend>();

	@Deprecated
	public FriendsBean(Map<Long, Integer> friendsIntimacy,
			List<TpFriend> tpFriends, List<TpFriend> unInstallFriends) {
		super();
		this.friendsIntimacy = friendsIntimacy;
		this.tpFriends = tpFriends;
		this.unInstallFriends = unInstallFriends;
	}

	public FriendsBean(Map<Long, Integer> friendsIntimacy,
			List<TpFriend> unInstallFriends) {
		super();
		this.friendsIntimacy = friendsIntimacy;
		this.unInstallFriends = unInstallFriends;
	}

	public Map<Long, Integer> getFriendsIntimacy() {
		return friendsIntimacy;
	}

	public void setFriendsIntimacy(Map<Long, Integer> friendsIntimacy) {
		this.friendsIntimacy = friendsIntimacy;
	}

	public List<Long> getInstallFriends() {
		return new ArrayList<Long>(getFriendsIntimacy().keySet());
	}

	@Deprecated
	public List<TpFriend> getTpFriends() {
		return tpFriends;
	}

	@Deprecated
	public void setTpFriends(List<TpFriend> tpFriends) {
		this.tpFriends = tpFriends;
	}

	public List<TpFriend> getUnInstallFriends() {
		return unInstallFriends;
	}

	public void setUnInstallFriends(List<TpFriend> unInstallFriends) {
		this.unInstallFriends = unInstallFriends;
	}
}
