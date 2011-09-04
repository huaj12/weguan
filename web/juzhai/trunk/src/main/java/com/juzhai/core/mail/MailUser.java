package com.juzhai.core.mail;


public class MailUser {

	private String emailAddress;
	private String nickname;
	private String password;
	private String userName;

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
