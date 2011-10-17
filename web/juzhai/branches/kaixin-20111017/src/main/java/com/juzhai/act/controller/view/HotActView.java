package com.juzhai.act.controller.view;

import com.juzhai.act.model.Act;

public class HotActView {

	private Act act;

	private boolean hasUsed;

	public Act getAct() {
		return act;
	}

	public void setAct(Act act) {
		this.act = act;
	}

	public boolean isHasUsed() {
		return hasUsed;
	}

	public void setHasUsed(boolean hasUsed) {
		this.hasUsed = hasUsed;
	}
}
