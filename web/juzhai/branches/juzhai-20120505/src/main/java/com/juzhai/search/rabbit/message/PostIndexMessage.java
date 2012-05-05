package com.juzhai.search.rabbit.message;

import com.juzhai.core.rabbit.message.RabbitMessage;

public class PostIndexMessage extends RabbitMessage<PostIndexMessage, Long> {

	private static final long serialVersionUID = 2313362104998235207L;

	private ActionType actionType;

	public PostIndexMessage buildActionType(ActionType actionType) {
		this.actionType = actionType;
		return this;
	}

	public ActionType getActionType() {
		return actionType;
	}

}
