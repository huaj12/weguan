package com.juzhai.msg.controller.view;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang.StringUtils;

import com.juzhai.act.model.Act;
import com.juzhai.msg.bean.ActMsg.MsgType;
import com.juzhai.passport.bean.ProfileCache;

public class ActMsgView implements Serializable {

	private static final long serialVersionUID = 7087543258625496760L;

	private static final SimpleDateFormat SDF = new SimpleDateFormat(
			"yyyy-MM-dd hh:mm");

	private Act act;

	private ProfileCache profileCache;

	private MsgType msgType;
	// 消息的状态
	private boolean stuts;

	private Date date;

	public MsgType getMsgType() {
		return msgType;
	}

	public void setMsgType(MsgType msgType) {
		this.msgType = msgType;
	}

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

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	/**
	 * 为邮件vm用
	 * 
	 * @return
	 */
	public String getDateFormat() {
		if (null == getDate()) {
			return StringUtils.EMPTY;
		}
		return SDF.format(getDate());
	}
}
