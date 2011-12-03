package com.juzhai.core.mail.bean;

import java.io.Serializable;

public class MailUser implements Serializable {

	private static final long serialVersionUID = 4387390235604857303L;

	private String emailAddress;

	private String nickname;

	private String password;

	private String userName;

	public MailUser(String emailAddress, String nickname) {
		this.emailAddress = emailAddress;
		this.nickname = nickname;
	}

	public String getFriendlyEmailAddress() {
		if (null == emailAddress) {
			throw new IllegalStateException("Email must be specified.");
		}
		StringBuilder sb = new StringBuilder();
		if (null != nickname) {
			sb.append(nickname);
		}
		sb.append("<").append(emailAddress).append(">");
		return sb.toString();
	}

	public String getEmailAddress() {
		return emailAddress;
	}

	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
}
