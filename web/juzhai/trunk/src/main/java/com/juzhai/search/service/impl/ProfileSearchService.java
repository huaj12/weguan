package com.juzhai.search.service.impl;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.juzhai.passport.model.Profile;
import com.juzhai.passport.service.IProfileService;
import com.juzhai.search.rabbit.message.ActionType;
import com.juzhai.search.rabbit.message.ProfileIndexMessage;
import com.juzhai.search.service.IProfileSearchService;
@Service
public class ProfileSearchService implements IProfileSearchService {
	@Autowired
	private IProfileService profileService;
	@Autowired
	private RabbitTemplate profileIndexCreateRabbitTemplate;
	@Override
	public void createIndex(long uid) {
		Profile profile=profileService.getProfile(uid);
		ProfileIndexMessage msgMessage = new ProfileIndexMessage();
		msgMessage.buildBody(profile).buildActionType(ActionType.CREATE);
		profileIndexCreateRabbitTemplate.convertAndSend(msgMessage);
	}

	@Override
	public void updateIndex(long uid) {
		Profile profile=profileService.getProfile(uid);
		ProfileIndexMessage msgMessage = new ProfileIndexMessage();
		msgMessage.buildBody(profile).buildActionType(ActionType.UPDATE);
		profileIndexCreateRabbitTemplate.convertAndSend(msgMessage);

	}

	@Override
	public void deleteIndex(long uid) {
		Profile profile=profileService.getProfile(uid);
		ProfileIndexMessage msgMessage = new ProfileIndexMessage();
		msgMessage.buildBody(profile).buildActionType(ActionType.DELETE);
		profileIndexCreateRabbitTemplate.convertAndSend(msgMessage);

	}

}
