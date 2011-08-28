package com.juzhai.msg.bean;

import java.util.Date;

public class ActMsg extends Msg {
	private static final long serialVersionUID = -57231618815784983L;

	public enum MsgType {
		FIND_YOU_ACT, BROADCAST_ACT
	}

	private long actId;

	private String actName;

	private long uid;

	private MsgType type;

	public ActMsg(long actId, long uid, MsgType type) {
		super();
		this.actId = actId;
		this.uid = uid;
		this.type = type;
		super.setDate(new Date());
	}

	public long getActId() {
		return actId;
	}

	public void setActId(long actId) {
		this.actId = actId;
	}

	public long getUid() {
		return uid;
	}

	public void setUid(long uid) {
		this.uid = uid;
	}

	public MsgType getType() {
		return type;
	}

	public void setType(MsgType type) {
		this.type = type;
	}

	public String getActName() {
		return actName;
	}

	public void setActName(String actName) {
		this.actName = actName;
	}

}
