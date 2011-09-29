package com.juzhai.msg.bean;

import java.util.Date;

public class ActMsg extends Msg {
	private static final long serialVersionUID = -57231618815784983L;
	
	public enum MsgType {
		INVITE,		//拒宅邀请 
		RECOMMEND	//拒宅推荐
	}
	//消息的状态true 已处理
	private boolean stuts;

	public boolean isStuts() {
		return stuts;
	}

	public void setStuts(boolean stuts) {
		this.stuts = stuts;
	}

	private long actId;

	private String actName;
	/**
	 * 发送者id
	 */
	private long uid;

	private MsgType type;

	public ActMsg(long actId, MsgType type) {
		super();
		this.actId = actId;
		this.type = type;
		//消息的状态默认都是未处理
		this.stuts=false;
		super.setDate(new Date());
	}
	public ActMsg(long actId,long uid, MsgType type) {
		super();
		this.actId = actId;
		this.type = type;
		this.uid=uid;
		//消息的状态默认都是未处理
		this.stuts=false;
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
