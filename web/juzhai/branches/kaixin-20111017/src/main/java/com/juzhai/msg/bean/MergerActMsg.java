package com.juzhai.msg.bean;

import java.util.Date;
import java.util.List;

import com.juzhai.msg.bean.ActMsg.MsgType;

public class MergerActMsg<T extends Msg> extends Msg{
	
	public MergerActMsg(List<T> msgs,long uid) {
		this.msgs=msgs;
		this.uid=uid;
		this.stuts=false;
		super.setDate(new Date());
	}
	public MergerActMsg(){
		this.stuts=false;
		super.setDate(new Date());
	}

	private static final long serialVersionUID = 2280156094632417300L;
	
	//消息的状态true 已处理
	private boolean stuts;
	
	private List<T> msgs;
	
	private MsgType type;
	
	public MsgType getType() {
		return type;
	}
	public void setType(MsgType type) {
		this.type = type;
	}


	
	/**
	 * 发送者id
	 */
	private long uid;
	
	public boolean isStuts() {
		return stuts;
	}

	public void setStuts(boolean stuts) {
		this.stuts = stuts;
	}
	public List<T> getMsgs() {
		return msgs;
	}

	public void setMsgs(List<T> msgs) {
		this.msgs = msgs;
	}

	public long getUid() {
		return uid;
	}

	public void setUid(long uid) {
		this.uid = uid;
	}

	
	

}
