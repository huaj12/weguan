package com.juzhai.search.rabbit.message;

import com.juzhai.act.model.Act;
import com.juzhai.core.rabbit.message.RabbitMessage;

public class ActIndexMessage extends RabbitMessage<ActIndexMessage, Act> {

	private static final long serialVersionUID = -8699381814854095649L;

	public enum ActionType {
		CREATE, UPDATE, DELETE
	}

	private ActionType actionType;

	public ActIndexMessage buildActionType(ActionType actionType) {
		this.actionType = actionType;
		return this;
	}

	public ActionType getActionType() {
		return actionType;
	}

}
