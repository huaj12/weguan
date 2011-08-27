package com.juzhai.msg.bean;

public class ActMsg extends Msg {
	private static final long serialVersionUID = -57231618815784983L;

	public enum MsgType {
		FIND_YOU_ACT, BROADCAST_ACT
	}

	private long actId;

	private long uid;

	private MsgType type;

	public ActMsg(long actId, long uid, MsgType type) {
		super();
		this.actId = actId;
		this.uid = uid;
		this.type = type;
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
}
