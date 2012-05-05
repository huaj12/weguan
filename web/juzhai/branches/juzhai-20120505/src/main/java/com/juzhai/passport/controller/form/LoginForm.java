package com.juzhai.passport.controller.form;

public class LoginForm {

	private String account;

	private String password;

	private String turnTo;

	private String verifyKey;

	private String verifyCode;

	private boolean remember;

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
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

	public boolean isRemember() {
		return remember;
	}

	public void setRemember(boolean remember) {
		this.remember = remember;
	}
}
