package com.juzhai.search.rabbit.message;

import com.juzhai.act.model.Act;
import com.juzhai.core.rabbit.message.RabbitMessage;
import com.juzhai.passport.model.Profile;

public class ProfileIndexMessage extends
		RabbitMessage<ProfileIndexMessage, Profile> {

	private static final long serialVersionUID = -4743258458214786019L;

	private ActionType actionType;

	public ProfileIndexMessage buildActionType(ActionType actionType) {
		this.actionType = actionType;
		return this;
	}

	public ActionType getActionType() {
		return actionType;
	}

}
