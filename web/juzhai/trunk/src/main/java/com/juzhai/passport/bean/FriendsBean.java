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
	 * 未安装应用的好友（id为在第三方的id）
	 */
	private List<String> uninstallFriends = new ArrayList<String>();

	public FriendsBean(Map<Long, Integer> friendsIntimacy,
			List<String> uninstallFriends) {
		super();
		this.friendsIntimacy = friendsIntimacy;
		this.uninstallFriends = uninstallFriends;
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

	public List<String> getUninstallFriends() {
		return uninstallFriends;
	}

	public void setUninstallFriends(List<String> uninstallFriends) {
		this.uninstallFriends = uninstallFriends;
	}
}
