package com.juzhai.mobile.passport.controller.form;

public class RegisterMForm {

	private String account;

	private String nickname;

	private String pwd;

	private String confirmPwd;

	private String turnTo;

	private long inviterUid;

	private String verifyKey;

	private String verifyCode;

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	public String getConfirmPwd() {
		return confirmPwd;
	}

	public void setConfirmPwd(String confirmPwd) {
		this.confirmPwd = confirmPwd;
	}

	public String getTurnTo() {
		return turnTo;
	}

	public void setTurnTo(String turnTo) {
		this.turnTo = turnTo;
	}

	public String getVerifyKey() {
		return verifyKey;
	}

	public void setVerifyKey(String verifyKey) {
		this.verifyKey = verifyKey;
	}

	public String getVerifyCode() {
		return verifyCode;
	}

	public void setVerifyCode(String verifyCode) {
		this.verifyCode = verifyCode;
	}

	public long getInviterUid() {
		return inviterUid;
	}

	public void setInviterUid(long inviterUid) {
		this.inviterUid = inviterUid;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
}
