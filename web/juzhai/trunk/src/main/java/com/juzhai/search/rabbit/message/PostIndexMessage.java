package com.juzhai.search.rabbit.message;

import com.juzhai.act.model.Act;
import com.juzhai.core.rabbit.message.RabbitMessage;
import com.juzhai.post.model.Post;

public class PostIndexMessage extends RabbitMessage<PostIndexMessage, Post> {

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
