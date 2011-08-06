/*
 *The code is written by 51juzhai, All rights is reserved
 */
package com.juzhai.passport.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class FriendsBean implements Serializable {

	private static final long serialVersionUID = -6897767622066199265L;

	/**
	 * 安装应用的好友（id为juzhai的用户id）
	 */
	private List<Long> installFriends = new ArrayList<Long>();

	/**
	 * 未安装应用的好友（id为在第三方的id）
	 */
	private List<String> uninstallFriends = new ArrayList<String>();

	public List<Long> getInstallFriends() {
		return installFriends;
	}

	public void setInstallFriends(List<Long> installFriends) {
		this.installFriends = installFriends;
	}

	public List<String> getUninstallFriends() {
		return uninstallFriends;
	}

	public void setUninstallFriends(List<String> uninstallFriends) {
		this.uninstallFriends = uninstallFriends;
	}
}
