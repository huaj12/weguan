package com.juzhai.home.bean;

import java.io.Serializable;
import java.util.Date;

public class ReadFeed implements Serializable {

	private static final long serialVersionUID = -6886668192161930828L;

	private long senderId;

	private long readerUid;

	private long actId;

	private ReadFeedType type;

	private Date time;

	public ReadFeed(long senderId, long readerUid, long actId, ReadFeedType type) {
		super();
		this.senderId = senderId;
		this.readerUid = readerUid;
		this.actId = actId;
		this.type = type;
		this.time = new Date();
	}

	public long getSenderId() {
		return senderId;
	}

	public void setSenderId(long senderId) {
		this.senderId = senderId;
	}

	public long getReaderUid() {
		return readerUid;
	}

	public void setReaderUid(long readerUid) {
		this.readerUid = readerUid;
	}

	public long getActId() {
		return actId;
	}

	public void setActId(long actId) {
		this.actId = actId;
	}

	public ReadFeedType getType() {
		return type;
	}

	public void setType(ReadFeedType type) {
		this.type = type;
	}

	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}
}
