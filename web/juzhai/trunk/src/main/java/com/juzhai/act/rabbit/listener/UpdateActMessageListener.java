package com.juzhai.act.rabbit.listener;

import org.springframework.stereotype.Component;

import com.juzhai.act.model.UserAct;
import com.juzhai.core.rabbit.listener.IRabbitMessageListener;

@Component
public class UpdateActMessageListener implements
		IRabbitMessageListener<UserAct, Object> {

	@Override
	public Object handleMessage(UserAct userAct) {
		System.out.println("uid:" + userAct.getUid());
		return null;
	}

}
