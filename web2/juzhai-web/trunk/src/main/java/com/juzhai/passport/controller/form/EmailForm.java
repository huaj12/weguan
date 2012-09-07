package com.juzhai.passport.controller.form;

public class EmailForm {

	private String email;

	private boolean interestMe;

	private boolean datingMe;

	private boolean acceptDating;

	private boolean sysNotice;

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public boolean isInterestMe() {
		return interestMe;
	}

	public void setInterestMe(boolean interestMe) {
		this.interestMe = interestMe;
	}

	public boolean isDatingMe() {
		return datingMe;
	}

	public void setDatingMe(boolean datingMe) {
		this.datingMe = datingMe;
	}

	public boolean isAcceptDating() {
		return acceptDating;
	}

	public void setAcceptDating(boolean acceptDating) {
		this.acceptDating = acceptDating;
	}

	public boolean isSysNotice() {
		return sysNotice;
	}

	public void setSysNotice(boolean sysNotice) {
		this.sysNotice = sysNotice;
	}
}
