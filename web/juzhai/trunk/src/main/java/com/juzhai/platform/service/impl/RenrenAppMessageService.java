package com.juzhai.platform.service.impl;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.juzhai.passport.bean.AuthInfo;
import com.juzhai.platform.service.IMessageService;
import com.renren.api.client.RenrenApiClient;
import com.renren.api.client.RenrenApiConfig;
@Service
public class RenrenAppMessageService implements IMessageService {

	@Override
	public boolean sendSysMessage(List<String> fuids, String linktext,
			String link, String word, String text, String picurl,
			AuthInfo authInfo) {
		RenrenApiClient client=newRenrenApiClient(authInfo.getAppKey(), authInfo.getAppSecret(), authInfo.getSessionKey());
		int count=client.getNotificationsService().send(StringUtils.join(fuids, ","), text+""+word);
		if(count>0){
			return true;
		}else{
			return false;	
		}
	}

	
	private RenrenApiClient newRenrenApiClient(String key, String secret, String sessionKey) {
		if(RenrenApiConfig.renrenApiKey==null||RenrenApiConfig.renrenApiSecret==null){
			RenrenApiConfig.renrenApiKey=key;
			RenrenApiConfig.renrenApiSecret=secret;
		}
		RenrenApiClient renrenApiClient = new RenrenApiClient(sessionKey);
		return renrenApiClient;
	}


	@Override
	public boolean sendMessage(String fuids, String content, AuthInfo authInfo) {
		// TODO Auto-generated method stub
		return false;
	}


	@Override
	public boolean sendFeed(String linktext, String link, String word,
			String text, String picurl, AuthInfo authInfo) {
		// TODO Auto-generated method stub
		return false;
	}

}
