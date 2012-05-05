package com.juzhai.act.controller.form;

public class DatingForm {

	private long datingId;
	private long uid;
	private long actId;
	private int consumeType;
	private int contactType;
	private String contactValue;

	public long getDatingId() {
		return datingId;
	}

	public void setDatingId(long datingId) {
		this.datingId = datingId;
	}

	public long getUid() {
		return uid;
	}

	public void setUid(long uid) {
		this.uid = uid;
	}

	public long getActId() {
		return actId;
	}

	public void setActId(long actId) {
		this.actId = actId;
	}

	public int getConsumeType() {
		return consumeType;
	}

	public void setConsumeType(int consumeType) {
		this.consumeType = consumeType;
	}

	public int getContactType() {
		return contactType;
	}

	public void setContactType(int contactType) {
		this.contactType = contactType;
	}

	public String getContactValue() {
		return contactValue;
	}

	public void setContactValue(String contactValue) {
		this.contactValue = contactValue;
	}

}
