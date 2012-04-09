package com.juzhai.passport.controller.form;

public class LoginForm {

	private String account;

	private String password;

	private String turnTo;

	private String verificationCode;

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

	public String getVerificationCode() {
		return verificationCode;
	}

	public void setVerificationCode(String verificationCode) {
		this.verificationCode = verificationCode;
	}
}
