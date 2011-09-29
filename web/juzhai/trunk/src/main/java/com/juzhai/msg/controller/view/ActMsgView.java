package com.juzhai.msg.controller.view;

import java.io.Serializable;

import com.juzhai.act.model.Act;
import com.juzhai.msg.bean.ActMsg.MsgType;
import com.juzhai.passport.bean.ProfileCache;

public class ActMsgView implements Serializable {

	private static final long serialVersionUID = 7087543258625496760L;

	private Act act;

	private ProfileCache profileCache;
	
	public MsgType getMsgType() {
		return msgType;
	}

	public void setMsgType(MsgType msgType) {
		this.msgType = msgType;
	}

	private MsgType msgType;
	//消息的状态
	private boolean stuts;
	
	public boolean isStuts() {
		return stuts;
	}

	public void setStuts(boolean stuts) {
		this.stuts = stuts;
	}

	public Act getAct() {
		return act;
	}

	public void setAct(Act act) {
		this.act = act;
	}

	public ProfileCache getProfileCache() {
		return profileCache;
	}

	public void setProfileCache(ProfileCache profileCache) {
		this.profileCache = profileCache;
	}
}
