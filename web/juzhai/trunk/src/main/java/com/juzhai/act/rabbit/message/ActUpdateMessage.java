package com.juzhai.act.rabbit.message;

import java.util.HashSet;
import java.util.Set;

import com.juzhai.act.model.UserAct;
import com.juzhai.core.rabbit.message.RabbitMessage;

public class ActUpdateMessage extends RabbitMessage<ActUpdateMessage, UserAct> {

	private static final long serialVersionUID = -8699381814854095649L;

	private Set<Long> excludeUids;

	public Set<Long> getExcludeUids() {
		return excludeUids;
	}

	public void setExcludeUids(Set<Long> excludeUids) {
		this.excludeUids = excludeUids;
	}

	public void addExcludeUid(long uid) {
		if (uid > 0) {
			if (null == getExcludeUids()) {
				Set<Long> uids = new HashSet<Long>();
				uids.add(uid);
				setExcludeUids(uids);
			}
		}
	}
}
